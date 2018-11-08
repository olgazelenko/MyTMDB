package olgaz.com.mytmdb;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

//The Main Activity shows the movie details inherits from the Super Activity and shown in the 3 states:
//1. Show Movie Details
//2. Add Movie Manual
//3. Update Movie
public class MovieActivity extends SuperMovieActivity {


    private DataProvider dataProvider;
    private String dateFacDB = "";
    private MovieModel movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        dataProvider = new DataProvider(this);
        int marker = 0;


        Intent myint = getIntent();
        if (myint != null) {
            movie = (MovieModel) myint.getSerializableExtra(getString(R.string.Movie));
            marker = myint.getIntExtra(getString(R.string.intVariableName), 0);
        }

        // Function from Super Activity that set movie details on blank
        setBlank(movie);

       //Add movie
        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MovieModel> movieModelArrayList;
                Date date = new Date();
                dateFacDB = DateFormat.format(getString(R.string.Date_format), date).toString();
                movie.setDateMovieAdded(dateFacDB);
                CheckBox watched = findViewById(R.id.chkwatched);
                String isWatched;
                if (watched.isChecked()) {
                    isWatched = getString(R.string.Watched);
                }else{
                    isWatched = getString(R.string.Not_Watched);
                }

                movie.setWatchedOrNot(isWatched);
                dataProvider.AddMovie(movie);
                String iswatchedornot = movie.getWatchedOrNot();
                Toast.makeText(MovieActivity.this, movie.getTitle() + "  movie Added", Toast.LENGTH_LONG).show();
            }
        });

        // move to Favorite Activity
        Button favor = findViewById(R.id.favor);
        favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MovieModel> movieModelArrayList;
                movieModelArrayList = DataProvider.getMoviesListFromDB();
                Intent intent = new Intent(MovieActivity.this, FavorMovies.class);
                intent.putExtra(getString(R.string.MovieList), movieModelArrayList);
                MovieActivity.this.startActivity(intent);

            }
        });


    }

}