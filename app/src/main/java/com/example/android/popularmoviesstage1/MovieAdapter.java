package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesstage1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by ckha on 9/11/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    Movie[] mMovies;
    private int mNumberItems;
    private Context context;

    final private GridItemClickListener mOnClickListener;

    public MovieAdapter(int numberOfItems, GridItemClickListener listener) {
        mMovies = new Movie[numberOfItems];
        mNumberItems = mMovies.length;
        mOnClickListener = listener;
    }

    public void setMovies(Movie[] movies) {
        mMovies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForGridItem = R.layout.movies_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForGridItem, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieItemImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);

            movieItemImageView = (ImageView) itemView.findViewById(R.id.iv_movie_item);
            itemView.setOnClickListener(this);
        }

        public void bind(int index) {
            Movie movie = mMovies[index];
            String posterUrlPath = "temp";
            if (movie != null) {
                posterUrlPath = NetworkUtils.buildPosterUrlString(movie.getPosterPath());
            }
            // Use Picasso to load the poster image from the string URL.
            Picasso.with(context).load(posterUrlPath).into(movieItemImageView);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onGridItemClick(clickedPosition);
        }
    }

    public interface GridItemClickListener {
        void onGridItemClick(int clickedItemIndex);
    }
}
