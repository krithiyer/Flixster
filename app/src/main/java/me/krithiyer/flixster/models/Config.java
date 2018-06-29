package me.krithiyer.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Config {
    // base image URL
    String baseImageURL;
    // poster size
    String posterSize;

   public Config(JSONObject object) throws JSONException {
       JSONObject images = object.getJSONObject("images");
       // getting image base URL from JSON format
       baseImageURL = images.getString("secure_base_url");
       // getting poster size from JSON format
       JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
       posterSize = posterSizeOptions.optString(3, "w342");
   }

   //helper method to construct URL
   public String getImageURL(String size, String path) {
       return String.format("%s%s%s", baseImageURL, size, path);
   }

   public String getBaseImageURL() {
       return baseImageURL;
   }

   public String getPosterSize() {
       return posterSize;
   }
}
