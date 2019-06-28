package com.example.android.popularmoviesapp_stage1;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesapp_stage1.model.MovieData;
import com.example.android.popularmoviesapp_stage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private MovieData movieData;
    private ImageView movieDetailsImageView;
    private TextView originalTitleLabelTextView;
    private TextView originalTitleTextView;
    private TextView plotLabelTextView;
    private TextView plotTextView;
    private TextView userRatingLabelTextView;
    private TextView userRatingTextView;
    private TextView releaseDateLabelTextView;
    private TextView releaseDateTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setTitle(R.string.details_screen_title);


        movieDetailsImageView = findViewById(R.id.movie_details_image_iv);
        originalTitleLabelTextView = findViewById(R.id.original_title_label_tv);
        originalTitleTextView = findViewById(R.id.original_title_tv);
        plotLabelTextView = findViewById(R.id.plot_label_tv);
        plotTextView = findViewById(R.id.plot_tv);
        userRatingLabelTextView = findViewById(R.id.user_rating_label_tv);
        userRatingTextView = findViewById(R.id.user_rating_tv);
        releaseDateLabelTextView = findViewById(R.id.release_date_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        movieData = (MovieData) intent.getSerializableExtra("movieItem");
        if (movieData == null) {
            closeOnError();
            return;
        }

        populateUI(movieData);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(MovieData movieData) {
        //Set the image View with Picasso
        try {
            String posterPathURL = NetworkUtils.buildUrlForMoviePoster(movieData.getMoviePoster());
            Picasso.with(this)
                    .load(posterPathURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(movieDetailsImageView);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

        //Set original title text view
        if(null != movieData.getOriginalTitle() && !movieData.getOriginalTitle().isEmpty()) {
            originalTitleTextView.setText(movieData.getOriginalTitle());
        } else {
            originalTitleTextView.setText("Data not available");
        }

        //Set plot text view
        if(null != movieData.getMoviePlot() && !movieData.getMoviePlot().isEmpty()) {
            plotTextView.setText(movieData.getMoviePlot());
        } else {
            plotTextView.setText("Data not available");
        }

        //set user rating text view
        if(null != movieData.getUserRating() && !movieData.getUserRating().isEmpty()) {
            userRatingTextView.setText(movieData.getUserRating());
        } else {
            userRatingTextView.setText("Data not available");
        }

        //set release date text view
        if(null != movieData.getReleaseDate() && !movieData.getReleaseDate().isEmpty()) {
            releaseDateTextView.setText(movieData.getReleaseDate());
        } else {
            releaseDateTextView.setText("Data not available");
        }

    }

}

