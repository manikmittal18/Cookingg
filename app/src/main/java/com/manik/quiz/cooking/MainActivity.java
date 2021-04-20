package com.manik.quiz.cooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    JsonObjectRequest request;
    TextView namee,ctg;
    ImageView imageView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        namee = findViewById(R.id.meal);
        ctg = findViewById(R.id.category);
        imageView =  findViewById(R.id.image);
        searchView = findViewById(R.id.search);

        requestQueue = Volley.newRequestQueue(this);

        request = new JsonObjectRequest(Request.Method.GET, "https://www.themealdb.com/api/json/v1/1/random.php", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("meals");
                    JSONObject employee = jsonArray.getJSONObject(0);
                    String name = employee.getString("strMeal");
                    String category = employee.getString("strCategory");
                    String imagee = employee.getString("strMealThumb");
                    namee.setText(name);
                    ctg.setText(category);
                    Glide.with(getApplicationContext()).load(imagee).into(imageView);

                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

        searchView.setQueryHint("Search Recipe");

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, ""+query, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),SearchCategory.class);
                i.putExtra("s",query);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
}