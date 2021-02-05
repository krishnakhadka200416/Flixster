package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Movie {

    String psoterPath;
    String title;
    String overview;
    Float rating;
    int movieId;
    public Movie(){

    }
    public Movie(JSONObject jsonObject) throws JSONException {
        psoterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        movieId = jsonObject.getInt("id");
        rating = (float)jsonObject.getDouble("vote_average");
    }

    public static List<Movie> fromJSONArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i<movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPsoterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",psoterPath);
    }

    public String getTitle() {
        return title;
    }

    public Float getrating(){return rating;}

    public String getOverview() {
        return overview;
    }

    public int getMovieId() {
        return movieId;
    }
}
