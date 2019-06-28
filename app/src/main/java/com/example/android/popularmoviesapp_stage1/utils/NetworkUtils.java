package com.example.android.popularmoviesapp_stage1.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
    private static final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie";
    private static final String API_KEY_PARAM = "api_key";

    /**
     * Builds the URL used to talk to the movieDB server using a movie search. This is based
     * on the query capabilities of the movie provider that we are using.
     *
     * @param movieSearchQuery The movie that will be queried for.
     * @param apiKey the api key required to fetch the movie details
     * @return The URL to use to query the weather server.
     */

    public static URL buildUrl(String movieSearchQuery, String apiKey) {
        Uri builtUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendEncodedPath(movieSearchQuery)
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
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

    public static String buildUrlForMoviePoster(String poster) {

        String finalPath = MOVIE_POSTER_BASE_URL + "/" + poster;
        return finalPath;

    }


}
