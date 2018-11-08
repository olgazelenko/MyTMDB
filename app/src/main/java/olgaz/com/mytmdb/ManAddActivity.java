package olgaz.com.mytmdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

//Activity get started when the add manual option was selected from the Options Menu in the Main Activity
public class ManAddActivity extends SuperMovieActivity {

    private CheckBox watched, unwatched;
    private DataProvider dataProvider;
    //public DataProvider dataProvider = new DataProvider(this);
    private String dateFacDB = null;
    private String isWatched = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        dataProvider = new DataProvider(this);
        final MovieModel movie = new MovieModel();


        Intent myint = getIntent();

        //Adding movie from the blank to the DB
        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MovieModel> movieModelArrayList;
                //Function from the Super activity that reads the blank and set the movie
                setMovieFromBlank(movie);
                String subject = movie.getTitle();

                dataProvider.AddMovie(movie); // adding the movie to the DB
               Toast.makeText(ManAddActivity.this, subject + "  movie Added", Toast.LENGTH_LONG).show();

            }

        });

        //Moving to the Favorite Activity
        Button favor = findViewById(R.id.favor);
        favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MovieModel> movieModelArrayList;
                movieModelArrayList = DataProvider.getMoviesListFromDB(); // getting all the movies from the DB
                Intent intent = new Intent(ManAddActivity.this, FavorMovies.class);
                intent.putExtra(getString(R.string.MovieList), movieModelArrayList);
                ManAddActivity.this.startActivity(intent);

            }
        });


    }

}
