package com.example.android.popularmoviesapp_stage1.model;

import java.io.Serializable;

public class MovieData implements Serializable {
    private String id;
    private String originalTitle;
    private String moviePoster;
    private String moviePlot;
    private String userRating;
    private String releaseDate;

    public MovieData() {

    }

    public MovieData(String id, String originalTitle, String moviePoster, String moviePlot,
                     String userRating, String releaseDate) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.moviePoster = moviePoster;
        this.moviePlot = moviePlot;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMoviePoster() {
        return this.moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMoviePlot() {
        return this.moviePlot;
    }

    public void setMoviePlot(String moviePlot) {
        this.moviePlot = moviePlot;
    }

    public String getUserRating() {
        return this.userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


}
