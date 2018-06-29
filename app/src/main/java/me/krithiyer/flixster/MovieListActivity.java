package me.krithiyer.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import me.krithiyer.flixster.models.Movie;
import me.krithiyer.flixster.models.Config;

public class MovieListActivity extends AppCompatActivity {

    // constants for repeated string values
    // base URL for API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    // API key parameter
    public final static String API_KEY_PARAM = "api_key";
    // logging in this activity
    public final static String TAG = "MovieListActivity";

    // instance fields
    AsyncHttpClient client;
    // list of currently playing movies
    ArrayList<Movie> movies;
    // recycler view
    RecyclerView rvMovies;
    // adapter wired to recycler view
    MovieAdapter adapter;
    // image config
    Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        // initialize client
        client = new AsyncHttpClient();
        // initialize movies list
        movies = new ArrayList<>();
        // initialize adapter
        adapter = new MovieAdapter(movies);

        // resolve recycler view, connect layout manager and adapter
        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        // configure on app creation
        getConfiguration();
    }

    // get list of currently playing movies from API
    private void getNowPlaying() {
        String url = API_BASE_URL + "/movie/now_playing";
        // set request parameters
        RequestParams params = new RequestParams();
        // API key always required
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // load results into movies
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        Movie movie = new Movie(results.getJSONObject(i));
                        movies.add(movie);
                        // notify adapter of row added
                        adapter.notifyItemInserted(movies.size() - 1);
                    }
                    Log.i(TAG, String.format("Loaded %s movies", results.length()));
                } catch (JSONException e) {
                    logError("Failed to parse now playing movies", e, true);
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from now_playing endpoint", throwable, true);
            }
        });
    }

    // get configuration from API
    private void getConfiguration() {
        // url creation
        String url = API_BASE_URL + "/configuration";
        // set request parameters
        RequestParams params = new RequestParams();
        // API key always required
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        // GET request (expect JSON object response)
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    config = new Config(response);
                    Log.i(TAG, String.format("Loaded configuration with baseImageURl %s and posterSize %s",
                            config.getBaseImageURL(), config.getPosterSize()));
                    adapter.setConfig(config);
                    // get now playing movie list
                    getNowPlaying();
                } catch (JSONException e) {
                    logError("Failed parsing configuration", e, true);
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration", throwable, true);
            }
        });
    }

    // log template for handling errors
    private void logError(String message, Throwable error, boolean alertUser) {
        // always log error
        Log.e(TAG, message, error);
        // alert user
        if (alertUser) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
