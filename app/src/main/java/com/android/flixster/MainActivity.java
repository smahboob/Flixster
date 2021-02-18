package com.android.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.flixster.adapter.RecyclerAdapter;

import android.os.Bundle;
import android.widget.TextView;

import com.android.flixster.models.Movie;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    private RequestQueue mQueue;
    private List<Movie> now_playing_list;
    private RecyclerView recyclerView;
    RecyclerAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.movie_recycler_view);
        mQueue = Volley.newRequestQueue(this);
        now_playing_list = new ArrayList<>();

        fetchData();
    }

    public void fetchData(){
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NOW_PLAYING_URL,null,
            //success
            response -> {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    now_playing_list.addAll(Movie.getMovies(jsonArray));
                    //movieAdapter.notifyDataSetChanged();
                    renderData();
                }
                catch (JSONException e) { e.printStackTrace();}
            },
            //error
            error -> { error.printStackTrace(); }
        );

        mQueue.add(jsonObjectRequest);
    }

    public void renderData(){
        movieAdapter = new RecyclerAdapter(this,now_playing_list);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
