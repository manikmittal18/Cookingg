package com.manik.quiz.cooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

public class SearchCategory extends AppCompatActivity {

    private RequestQueue requestQueue;
    JsonObjectRequest request;
    TextView namee,ctg;
    ImageView imageView;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_category);

        namee = findViewById(R.id.meal);
        ctg = findViewById(R.id.category);
        imageView =  findViewById(R.id.image);

        requestQueue = Volley.newRequestQueue(this);

        s = getIntent().getExtras().getString("s");

        request = new JsonObjectRequest(Request.Method.GET, "https://www.themealdb.com/api/json/v1/1/search.php?s="+s, null, new Response.Listener<JSONObject>() {
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
    }
}