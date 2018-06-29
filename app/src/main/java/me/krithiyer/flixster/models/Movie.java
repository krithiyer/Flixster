package me.krithiyer.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    // API values
    private String title;
    private String overview;
    private String posterPath; // path not full URL

    // initialize JSON data
    public Movie(JSONObject object) throws JSONException {
        // input data
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

}
