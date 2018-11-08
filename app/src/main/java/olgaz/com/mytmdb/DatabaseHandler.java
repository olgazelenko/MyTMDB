package olgaz.com.mytmdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import olgaz.com.mytmdb.Constants;
import olgaz.com.mytmdb.MovieModel;

import static olgaz.com.mytmdb.Constants.DATE_MOVIE_ADDED;
import static olgaz.com.mytmdb.Constants.KEY_ID;
import static olgaz.com.mytmdb.Constants.MOVIE_POSTER_URL;
import static olgaz.com.mytmdb.Constants.TABLE_NAME;

//The class operating with the Data Base

public class DatabaseHandler extends SQLiteOpenHelper {

    private final ArrayList<MovieModel> moviesList = new ArrayList<>();
    private String[] ids;

    // Constructor
    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        onCreate(this.getWritableDatabase());
    }


    // onCreate function that's creating the DB Table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MOVIES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + Constants.MOVIE_NAME +
                " TEXT, " + Constants.MOVIE_DESCRIPTION + " TEXT, " + MOVIE_POSTER_URL +
                " TEXT, " + Constants.MOVIE_WATCHED_OR_NOT + " TEXT, " + DATE_MOVIE_ADDED +
                " LONG);";

        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // create a new one table
        onCreate(db);
    }

    //Delete all rows from DB table
    protected void deleteAllMovies() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        db.close();
    }

    // add content to the movies table
    protected void addMovieToDB(MovieModel movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.MOVIE_NAME, movie.getTitle());
        values.put(Constants.MOVIE_DESCRIPTION, movie.getOverview());
        values.put(MOVIE_POSTER_URL, movie.getPoster_path());
        values.put(Constants.MOVIE_WATCHED_OR_NOT, movie.getWatchedOrNot());
        values.put(DATE_MOVIE_ADDED, movie.getDateMovieAdded());

        try {
            long id = db.insertOrThrow(TABLE_NAME, null, values);
            movie.setId(id);
            Log.d("DBHandler", "insert new movie with id: " + id);
        } catch (SQLiteException ex) {

            Log.e("DBHandler", ex.getMessage());
            throw ex;
        } finally {
            db.close();
        }

    }

    // get all movies from the DB
    protected ArrayList<MovieModel> getMoviesListFromDB() {
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, Constants.MOVIE_NAME,
                        Constants.MOVIE_DESCRIPTION, MOVIE_POSTER_URL, Constants.MOVIE_WATCHED_OR_NOT,
                        DATE_MOVIE_ADDED}, null, null, null, null,
                DATE_MOVIE_ADDED + " DESC");

        // loop through cursor
        if (cursor.moveToFirst()) {
            do {
                MovieModel movie = new MovieModel(cursor.getString(cursor.getColumnIndex(Constants.MOVIE_NAME)), cursor.getString(cursor.getColumnIndex(Constants.MOVIE_DESCRIPTION)), cursor.getString(cursor.getColumnIndex(MOVIE_POSTER_URL)), cursor.getString(cursor.getColumnIndex(DATE_MOVIE_ADDED)), cursor.getInt(cursor.getColumnIndex(KEY_ID)));

                movie.setPoster_path(cursor.getString(cursor.getColumnIndex(Constants.MOVIE_POSTER_URL)));
                movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(Constants.MOVIE_NAME)));
                movie.setWatchedOrNot(cursor.getString(cursor.getColumnIndex(Constants.MOVIE_WATCHED_OR_NOT)));
                movie.setDateMovieAdded(cursor.getString(cursor.getColumnIndex(Constants.DATE_MOVIE_ADDED)));

                moviesList.add(movie);

            } while (cursor.moveToNext());
        }

        db.close();
        return moviesList;
    }

    // Edit title, overview, picture URL and Watched or not value
    protected void updateMovieInDB(MovieModel movie) {

        SQLiteDatabase db = this.getReadableDatabase();
        try {
            ContentValues contentValues = new ContentValues();

            contentValues.put(Constants.MOVIE_NAME, movie.getTitle());
            contentValues.put(Constants.MOVIE_DESCRIPTION, movie.getOverview());
            contentValues.put(MOVIE_POSTER_URL, movie.getPoster_path());
            contentValues.put(Constants.MOVIE_WATCHED_OR_NOT, movie.getWatchedOrNot());

            int rowNumber = db.update(TABLE_NAME, contentValues, KEY_ID + " = ? ", new String[]{String.valueOf(movie.getId())});

            Log.d("DBHandler", "update new movie id: " + rowNumber + ", Name: " + movie.getTitle());
        } catch (SQLiteException ex) {

            Log.e("DBHandler", ex.getMessage());
        } finally {
            db.close();
        }

    }

    //Delete specific movie from the DB
    protected void deleteAMovieInDB(MovieModel movie) {

        SQLiteDatabase db = getWritableDatabase();

        try {
            int rowNumber = db.delete(TABLE_NAME, KEY_ID + " = ? ", new String[]{String.valueOf(movie.getId())});

            Log.d("DBHandler", "Deleted: " + rowNumber);
        } catch (SQLiteException ex) {

            Log.e("DBHandler", ex.getMessage());
        } finally {
            db.close();
        }
    }

}
