package com.android.flixster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.flixster.R;
import com.android.flixster.models.Movie;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class Detailed_Activity extends YouTubeBaseActivity {

    String youtube_video_key;
    YouTubePlayerView youTubePlayerView;
    boolean popular = false;
    public static  final String VIDEO_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movieObject"));
        if(movie.getVote_average() >= 5){
            popular = true;
        }

        TextView titleDisplay = findViewById(R.id.moveTitleDetailedText);
        TextView overViewDisplay = findViewById(R.id.overviewDetailedText);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView releaseDate = findViewById(R.id.date);
        youTubePlayerView = findViewById(R.id.player);

        titleDisplay.setText(movie.getTitle());
        releaseDate.setText(movie.getRelease_date());
        overViewDisplay.setText(movie.getOverview());
        ratingBar.setRating((float)movie.getVote_average());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEO_URL,movie.getId()), new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try{
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if(results.length() == 0){
                        return;
                    }
                    youtube_video_key = results.getJSONObject(0).getString("key");
                    initializeYoutubePlayer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String errorResponse, Throwable t) {
                Toast.makeText(Detailed_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeYoutubePlayer() {

        youTubePlayerView.initialize(getString(R.string.YOUTUBE_API_KEY), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(youtube_video_key);
                if(popular){
                    youTubePlayer.loadVideo(youtube_video_key);
                    youTubePlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(Detailed_Activity.this, "Unable to load trailer!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}