package me.krithiyer.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Movie {
    // API values
     String title;
     String overview;
     String posterPath; // path not full URL
     String backdropPath;
     double rating;
     String release;
     int id;

     public Movie() {}

    // initialize JSON data
    public Movie(JSONObject object) throws JSONException {
        // input data
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        rating = object.getDouble("vote_average");
        release = object.getString("release_date");
        id = object.getInt("id");
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

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getRating() {
        return rating;
    }

    public String getRelease() {
        return release;
    }

    public int getId() {
        return id;
    }
}
