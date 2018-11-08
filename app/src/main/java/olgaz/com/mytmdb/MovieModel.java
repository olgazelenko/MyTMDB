package olgaz.com.mytmdb;

import android.text.Editable;

import java.io.Serializable;

//Movie Model Class
public class MovieModel implements Serializable {

    private static final String SMALL_POSTER_SIZE = "/w154";
    private static final String BIG_POSTER_SIZE = "/original";
    private boolean adult;
    private String backdrop_path;
    private long id;
    private String original_title;
    private String release_date;
    private String poster_path;
    private String overview;
    private float popularity;
    private String title;
    private boolean video;
    private float vote_average;
    private int vote_count;
    private String watchedOrNot;
    private String dateMovieAdded;

    public MovieModel(String title, String overview, String relDate) {
        this.title = title;
        this.overview = overview;
        this.release_date = relDate;
    }

    public MovieModel(String title, String overview, String relDate, String dateMovieAdded, int id) {
        this.title = title;
        this.overview = overview;
        this.release_date = relDate;
        this.dateMovieAdded = dateMovieAdded;
        this.id = id;
    }

    public MovieModel() {

    }

    public MovieModel(String subject, String over) {
        title = subject;
        overview = over;
    }


    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getWatchedOrNot() {
        return watchedOrNot;
    }

    public void setWatchedOrNot(String watchedOrNot) {
        this.watchedOrNot = watchedOrNot;
    }

    public String getDateMovieAdded() {
        return dateMovieAdded;
    }

    public void setDateMovieAdded(String dateMovieAdded) {
        this.dateMovieAdded = dateMovieAdded;
    }

    @Override
    public String toString() {
        return title;
    }

}


