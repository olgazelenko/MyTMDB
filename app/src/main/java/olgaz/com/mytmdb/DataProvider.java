package olgaz.com.mytmdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

//this is a class that run the functions from DatabaseHandler

public class DataProvider {
    private static DataProvider mInstance;
    private static DatabaseHandler mDatabaseHandler;




    public static DataProvider getInstance(Context context_) {
        if (mInstance == null) {
            mInstance = new DataProvider(context_);
        }
        return mInstance;
    }


    public DataProvider(Context context_) {
        mDatabaseHandler = new DatabaseHandler(context_);
    }

    public void CreateDB(SQLiteDatabase db) {
        mDatabaseHandler.onCreate(db);
    }


    public void AddMovie(MovieModel movie) {

        mDatabaseHandler.addMovieToDB(movie);

    }


    protected static ArrayList<MovieModel> getMoviesListFromDB() {
        ArrayList<MovieModel> movieModelArrayList;
        movieModelArrayList = mDatabaseHandler.getMoviesListFromDB();
        return movieModelArrayList;
    }

    ;

    protected void deleteAllMovies() {
        ArrayList<MovieModel> movieModelArrayList;
        mDatabaseHandler.deleteAllMovies();
    }




    public static void deleteItem(MovieModel movie) {
        mDatabaseHandler.deleteAMovieInDB(movie);

    }

    protected static void UpdateMovie ( MovieModel movie){
     mDatabaseHandler.updateMovieInDB(movie);
    }

}

