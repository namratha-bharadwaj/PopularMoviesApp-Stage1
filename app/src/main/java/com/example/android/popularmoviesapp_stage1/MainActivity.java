package com.example.android.popularmoviesapp_stage1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesapp_stage1.model.MovieData;
import com.example.android.popularmoviesapp_stage1.utils.JsonUtils;
import com.example.android.popularmoviesapp_stage1.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private List<MovieData> mMoviesList;

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    //Movie sorting
    private static final String MOST_POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static String currentSort = MOST_POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        mRecyclerView = findViewById(R.id.recyclerView_movie_list);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(mMoviesList, this, this);
        mRecyclerView.setAdapter(mMovieAdapter);


        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        loadMovieData();

    }

    /**
     * This method will get the user's preferred movie, and then tell some
     * background method to get the movie data in the background.
     */
    private void loadMovieData() {
        showMovieDataView();
        executeMovieSearchQuery();

    }

    private void executeMovieSearchQuery() {
        String movieQuery = currentSort;
        URL movieSearchUrl = NetworkUtils.buildUrl(movieQuery, getText(R.string.api_key).toString());
        new MoviesQueryTask().execute(movieSearchUrl);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setTitle(getString(R.string.app_title));
        if(id == R.id.sort_popular_menu_item && !currentSort.equals(MOST_POPULAR)) {
            ClearMovieItemList();
            currentSort = MOST_POPULAR;
            loadMovieData();
            return true;
        }
        if(id == R.id.sort_topRated_menu_item && !currentSort.equals(TOP_RATED)) {
            ClearMovieItemList();
            currentSort = TOP_RATED;
            loadMovieData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ClearMovieItemList() {
        if (mMoviesList != null) {
            mMoviesList.clear();
        } else {
            mMoviesList = new ArrayList<MovieData>();
        }
    }

    @Override
    public void OnListItemClick(MovieData movieItem) {
        Context context = this;
        Class destinationClass = MovieDetails.class;
        Intent myIntent = new Intent(context, destinationClass);
        myIntent.putExtra("movieItem", movieItem);
        startActivity(myIntent);
    }

    public class MoviesQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String searchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(searchResults != null && !searchResults.isEmpty()) {
                try {
                    mMoviesList = JsonUtils.parseJsonForMovieData(MainActivity.this, searchResults);
                    if (mMoviesList != null)
                        mMovieAdapter.setMovieData(mMoviesList);
                    else
                        showErrorMessageView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                showErrorMessageView();
            }

        }
    }

    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessageView() {
        /* First, hide the currently visible data */
        mRecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

}
