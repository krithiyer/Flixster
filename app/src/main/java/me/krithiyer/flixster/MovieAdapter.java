package me.krithiyer.flixster;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.krithiyer.flixster.models.Config;
import me.krithiyer.flixster.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // list of movies
    ArrayList<Movie> movies;
    // config needed for image URL
    Config config;
    // context for rendering
    Context context;

    public Config getConfig() {
      return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    // initialize with list
    public MovieAdapter(ArrayList<Movie> movie) {
        this.movies = movie;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get context and create inflator
        context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);
        // create view
        View movieView = inflator.inflate(R.layout.item_movie, parent, false);
        // return new ViewHolder
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get movie data at a specific index
        final Movie movie = movies.get(position);
        // populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());
        holder.tvRating.setText("Rating: " + Double.toString(movie.getRating()) + "/10");
        holder.tvReleaseDate.setText(("Released: " + movie.getRelease()));

        // determine current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        String imageURL = null;

        //if in portrait mode, load poster image
        if (isPortrait) {
            imageURL = config.getImageURL(config.getPosterSize(), movie.getPosterPath());
        } else {
            // load backdrop
            imageURL = config.getImageURL(config.getBackdropSize(), movie.getBackdropPath());

        }

        // get correct placeholder and image view
        int placeholderID = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? holder.ivPosterImage : holder.ivBackdropImage;

        holder.ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MovieListActivity) context).playYoutubeVideo(movie.getId());
            }
        });

        // load image using glide
        Glide.with(context)
                .load(imageURL)
                .apply(
                        RequestOptions.placeholderOf(placeholderID)
                        .error(placeholderID)
                                .fitCenter())
                .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(25, 0)))
                .into(imageView);

    }
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // create the viewholder as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivBackdropImage;
        TextView tvRating;
        TextView tvReleaseDate;
        ImageButton ibPlay;

        public ViewHolder(View itemView) {
            super(itemView);
            // lookup view objects
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivBackdropImage = (ImageView) itemView.findViewById(R.id.ivBackdropImage);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            tvReleaseDate = (TextView) itemView.findViewById(R.id.tvReleaseDate);
            ibPlay =  (ImageButton) itemView.findViewById((R.id.ibPlay));

        }
    }
}
