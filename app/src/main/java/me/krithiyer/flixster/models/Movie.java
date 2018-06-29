package me.krithiyer.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    // API values
    private String title;
    private String synopsis;
    private String posterPath; // path not full URL

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPosterPath() {
        return posterPath;
    }

    // initialize JSON data
    public Movie(JSONObject o) throws JSONException {
        // input data
        title = o.getString("title");

        synopsis = o.getString("overview");
        posterPath = o.getString("poster_path");
    }
}
