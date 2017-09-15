package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesstage1.utilities.MovieConstants;
import com.example.android.popularmoviesstage1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by ckha on 9/12/17.
 */

public class DetailActivity extends AppCompatActivity {

    private TextView mMovieTitleDisplay;
    private TextView mMovieOverview;
    private TextView mMovieUserRating;
    private TextView mMovieReleaseDate;
    private ImageView mMoviePoster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mMovieTitleDisplay = (TextView) findViewById(R.id.tv_movie_title);
        mMovieOverview = (TextView) findViewById(R.id.tv_overview);
        mMovieUserRating = (TextView) findViewById(R.id.tv_user_rating);
        mMovieReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        String moviePosterPath = null;

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(MovieConstants.TITLE_EXTRA)) {
            mMovieTitleDisplay.setText(intentThatStartedThisActivity.getStringExtra(MovieConstants.TITLE_EXTRA));
        }
        if (intentThatStartedThisActivity.hasExtra(MovieConstants.OVERVIEW_EXTRA)) {
            mMovieOverview.setText(intentThatStartedThisActivity.getStringExtra(MovieConstants.OVERVIEW_EXTRA));
        }
        if (intentThatStartedThisActivity.hasExtra(MovieConstants.USER_RATINGS_EXTRA)) {
            mMovieUserRating.setText(intentThatStartedThisActivity.getStringExtra(MovieConstants.USER_RATINGS_EXTRA));
        }
        if (intentThatStartedThisActivity.hasExtra(MovieConstants.RELEASE_DATE_EXTRA)) {
            mMovieReleaseDate.setText(intentThatStartedThisActivity.getStringExtra(MovieConstants.RELEASE_DATE_EXTRA));
        }
        if (intentThatStartedThisActivity.hasExtra(MovieConstants.POSTER_PATH_EXTRA)) {
            String pathExtra = intentThatStartedThisActivity.getStringExtra(MovieConstants.POSTER_PATH_EXTRA);
            moviePosterPath = NetworkUtils.buildPosterUrlString(pathExtra);
        }

        // Load the movie poster into the ImageView
        Picasso.with(this).load(moviePosterPath).into(mMoviePoster);
    }
}
