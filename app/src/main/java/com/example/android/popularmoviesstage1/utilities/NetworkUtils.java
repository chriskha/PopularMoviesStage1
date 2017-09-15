package com.example.android.popularmoviesstage1.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmoviesstage1.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ckha on 9/6/17.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String POSTER_SIZE_W92 = "w92";
    private static final String POSTER_SIZE_W154 = "w154";
    private static final String POSTER_SIZE_W185 = "w185";
    private static final String POSTER_SIZE_W342 = "w342";
    private static final String POSTER_SIZE_W500 = "w500";
    private static final String POSTER_SIZE_W780 = "w780";
    private static final String POSTER_SIZE_ORIGINAL = "original";

    private static final String DYNAMIC_MOVIES_POSTER_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String DYNAMIC_MOVIES_POPULAR_URL =
            "http://api.themoviedb.org/3/movie/popular";
    private static final String DYNAMIC_MOVIES_TOP_RATED_URL =
            "http://api.themoviedb.org/3/movie/top_rated";

    private static final String MOVIES_BASE_URL = DYNAMIC_MOVIES_POPULAR_URL;

    private final static String API_KEY_PARAM = "api_key";

    public static URL buildUrl() {
        return buildUrl(R.id.action_sort_most_popular);
    }

    public static URL buildUrl(int sortType) {
        String base_url;

        switch (sortType) {
            case R.id.action_sort_most_popular:
                base_url = DYNAMIC_MOVIES_POPULAR_URL;
                break;
            case R.id.action_sort_top_rated:
                base_url = DYNAMIC_MOVIES_TOP_RATED_URL;
                break;
            default:
                base_url = DYNAMIC_MOVIES_POPULAR_URL;
                break;
        }

        Uri builtUri = Uri.parse(base_url).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, MovieConstants.API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String buildPosterUrlString(String posterPath) {
        return DYNAMIC_MOVIES_POSTER_IMAGE_URL + POSTER_SIZE_W500 + "/" + posterPath;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
