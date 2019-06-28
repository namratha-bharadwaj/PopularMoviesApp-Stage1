package com.example.android.popularmoviesapp_stage1.utils;

import android.content.Context;

import com.example.android.popularmoviesapp_stage1.model.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<MovieData> parseJsonForMovieData(Context context, String movieJsonString) throws JSONException {
        try {

            List<MovieData> movieDataList = new ArrayList<MovieData>();
            MovieData movieDataObj;
            JSONObject json_object = new JSONObject(movieJsonString);

            JSONArray resultsArray = new JSONArray(json_object.optString("results"));

            if(resultsArray != null) {
                for (int i = 0; i < resultsArray.length(); i++) {
                    String currentItem = resultsArray.optString(i, "");
                    JSONObject movieJson = new JSONObject(currentItem);
                    movieDataObj = new MovieData(
                            movieJson.optString("id", "Not Available"),
                            movieJson.optString("original_title", "Not Available"),
                            movieJson.optString("poster_path", "Not Available"),
                            movieJson.optString("overview", "Not Available"),
                            movieJson.optString("vote_average", "Not Available"),
                            movieJson.optString("release_date", "Not Available")
                    );
                    movieDataList.add(movieDataObj);

                }
                return movieDataList;
            } else {
                return null;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

}
