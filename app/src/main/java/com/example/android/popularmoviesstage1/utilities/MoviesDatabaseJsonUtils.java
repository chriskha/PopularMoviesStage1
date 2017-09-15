package com.example.android.popularmoviesstage1.utilities;

import com.example.android.popularmoviesstage1.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ckha on 9/6/17.
 */

public class MoviesDatabaseJsonUtils {

    public static final String KEY_ID = "id";
    public static final String KEY_ORIGINAL_TITLE = "original_title";
    public static final String KEY_POSTER_PATH = "poster_path";
    public static final String KEY_OVERVIEW = "overview";
    public static final String KEY_USER_RATING = "vote_average";
    public static final String KEY_RELEASE_DATE = "release_date";

    public static String[] getSimpleMoviesStringsFromJson(String moviesJsonStr)
            throws JSONException {

        String[] parsedMoviesData = null;
        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        JSONArray moviesArray = moviesJson.getJSONArray("results");

        parsedMoviesData = new String[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            String movieId;
            String movieOriginalTitle;
            String moviePosterPath;
            String movieOverview;
            String movieUserRating;
            String movieReleaseDate;

            JSONObject movieJson = moviesArray.getJSONObject(i);

            movieId = movieJson.getString(KEY_ID);
            movieOriginalTitle = movieJson.getString(KEY_ORIGINAL_TITLE);
            moviePosterPath = movieJson.getString(KEY_POSTER_PATH);
            movieOverview = movieJson.getString(KEY_OVERVIEW);
            movieUserRating = movieJson.getString(KEY_USER_RATING);
            movieReleaseDate = movieJson.getString(KEY_RELEASE_DATE);

//            parsedMoviesData[i] = movieOriginalTitle + " - " + movieReleaseDate + " - " +
//                    movieUserRating + " - " + movieOverview;
            parsedMoviesData[i] = moviePosterPath;
        }

        return parsedMoviesData;
    }

    public static Movie[] getMoviesObjectsFromJson(String moviesJsonStr)
            throws JSONException {
        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray("results");
        Movie[] movies = new Movie[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject movieJson = moviesArray.getJSONObject(i);

            Movie movie = new Movie();
            movie.setId(movieJson.getString(KEY_ID));
            movie.setOriginalTitle(movieJson.getString(KEY_ORIGINAL_TITLE));
            movie.setPosterPath(movieJson.getString(KEY_POSTER_PATH));
            movie.setOverview(movieJson.getString(KEY_OVERVIEW));
            movie.setUserRating(movieJson.getString(KEY_USER_RATING));
            movie.setReleaseDate(movieJson.getString(KEY_RELEASE_DATE));

            movies[i] = movie;
        }

        return movies;
    }
}
