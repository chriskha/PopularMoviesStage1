package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmoviesstage1.utilities.MovieConstants;
import com.example.android.popularmoviesstage1.utilities.MoviesDatabaseJsonUtils;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.GridItemClickListener {

    private static final int NUM_MOVIE_ITEMS = 20;
    private static final int GRID_SPAN = 2;
    private MovieAdapter mAdapter;
    private RecyclerView mMoviesGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchMovies();

        mMoviesGrid = (RecyclerView) findViewById(R.id.rv_movies);

        // Create GridLayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(this, GRID_SPAN);
        mMoviesGrid.setLayoutManager(layoutManager);

        mMoviesGrid.setHasFixedSize(true);

        mAdapter = new MovieAdapter(NUM_MOVIE_ITEMS, this);
        mMoviesGrid.setAdapter(mAdapter);
    }

    @Override
    public void onGridItemClick(int clickedItemIndex) {
        Context context = MainActivity.this;
        Class destinationActivity = DetailActivity.class;

        Intent startActivityIntent = new Intent(context, destinationActivity);

        Movie movieData = mAdapter.mMovies[clickedItemIndex];

        // Put the extra data of this item into intent so we can display it in DetailActivity.
        startActivityIntent.putExtra(MovieConstants.TITLE_EXTRA, movieData.getOriginalTitle());
        startActivityIntent.putExtra(MovieConstants.OVERVIEW_EXTRA, movieData.getOverview());
        startActivityIntent.putExtra(MovieConstants.POSTER_PATH_EXTRA, movieData.getPosterPath());
        startActivityIntent.putExtra(MovieConstants.RELEASE_DATE_EXTRA, movieData.getReleaseDate());
        startActivityIntent.putExtra(MovieConstants.USER_RATINGS_EXTRA, movieData.getUserRating());

        startActivity(startActivityIntent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        switch (menuItemSelected) {
            case R.id.action_sort_most_popular:
                fetchMovies(R.id.action_sort_most_popular);
                break;
            case R.id.action_sort_top_rated:
                fetchMovies(R.id.action_sort_top_rated);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void fetchMovies() {
        fetchMovies(R.id.action_sort_most_popular);
    }

    private void fetchMovies(int sortType) {
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
        fetchMoviesTask.execute(sortType);
    }

    private class FetchMoviesTask extends AsyncTask<Integer, Void, Movie[]> {
        @Override
        protected Movie[] doInBackground(Integer... ints) {
            int sortType = ints[0];
            URL url = NetworkUtils.buildUrl(sortType);

            try {
                String results = NetworkUtils.getResponseFromHttpUrl(url);
                return MoviesDatabaseJsonUtils
                        .getMoviesObjectsFromJson(results);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movieData) {
            if (movieData != null) {
                mAdapter.setMovies(movieData);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
