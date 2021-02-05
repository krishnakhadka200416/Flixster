package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {
    private static final String YOUTUBE_API_KEY = "AIzaSyAtgq2z7wAuJ6AuXqurrg4kpSUgjyRRMtg";
    public static final String Videos_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    TextView dtTitle;
    TextView dtOverview;
    RatingBar ratingBar;
    double rating;
    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dtTitle = findViewById(R.id.dtTitle);
        dtOverview = findViewById(R.id.dtOverview);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        dtTitle.setText(movie.getTitle());
        dtOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getrating());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(Videos_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if(results.length() == 0)
                    {
                        return;
                    }

                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("Detail Activity", youtubeKey);
                    initializeYoutube(youtubeKey);

                } catch (JSONException e) {
                    Log.e("Detailed Activity", "Failed");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d("Detail Activity", "failure");
            }
        });
    }

    private void initializeYoutube(final String youtubeKey) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("Detail Activity", "onInitializeSucess");
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("Detail Activity", "onInitializeFailure");
            }
        });
    }
}