package com.android.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    long id;
    String title;
    String poster_path;
    String overview;
    String release_date;
    double vote_average;
    String backdrop_path;

    public Movie(JSONObject jsonObject) throws JSONException {
        id = jsonObject.getInt("id");
        title = jsonObject.getString("original_title");
        poster_path = jsonObject.getString("poster_path");
        overview = jsonObject.getString("overview");
        release_date = jsonObject.getString("release_date");
        vote_average = jsonObject.getDouble("vote_average");
        backdrop_path = jsonObject.getString("backdrop_path");
    }

    public static List<Movie> getMovies(JSONArray jsonArray) throws JSONException {
        List<Movie> movie_list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Movie movie = new Movie(jsonObject);
            movie_list.add(movie);
        }
        return movie_list;
    }

    public Movie(){
    }

    public long getId(){
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getPoster_path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",poster_path);
    }
    public String getOverview() {
        return overview;
    }
    public String getRelease_date() {
        return release_date;
    }
    public double getVote_average() {
        return vote_average;
    }
    public String getBackdrop_path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdrop_path);
    }
}
