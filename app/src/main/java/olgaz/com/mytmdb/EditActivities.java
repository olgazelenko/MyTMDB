package olgaz.com.mytmdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

//This class take care of movie update
public class EditActivities extends SuperMovieActivity {


    private DataProvider dataProvider;
    private String dateFacDB = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_activities);
        setContentView(R.layout.activity_movie);

        //getting movie and id via Intent from a Favorites Activity
        final MovieModel movie = (MovieModel) getIntent().getExtras().getSerializable(getString(R.string.Movie_edit));
        final long id = getIntent().getExtras().getLong(getString(R.string.Movie_id)); //GetSerializable for the ID

        setBlank(movie); //funtion from a Super Activity that doing blank initialization

       //Checking if the movie was watched ot not
        CheckBox chkwatched = findViewById(R.id.chkwatched);
        String isWatched = movie.getWatchedOrNot();

        if (isWatched.equals(getString(R.string.Watched))) {
            chkwatched.setChecked(true);
        } else {
            chkwatched.setChecked(false);
        }

        // Update the movie data
        Button upd = findViewById(R.id.add);
        upd.setText("UPDATE");
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMovieFromBlank(movie);  // the function from Super Activity that read the blank and set details to Movie
                EditText url = findViewById(R.id.url);
                String url_path = url.getText().toString();
                movie.setPoster_path(url_path);

                dataProvider.UpdateMovie(movie); // Update the DB

                Toast.makeText(EditActivities.this, getString(R.string.Update_Toast) +movie.getTitle(), LENGTH_LONG).show();

            }
        });

        // Button to move to the Favorite Activity via intents
        Button favor = findViewById(R.id.favor);
        favor.setText("FAVORITE");
        favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MovieModel> movieModelArrayList;
                movieModelArrayList = DataProvider.getMoviesListFromDB();
                Intent intent = new Intent(EditActivities.this, FavorMovies.class);
                intent.putExtra(getString(R.string.MovieList), movieModelArrayList);
                EditActivities.this.startActivity(intent);

            }
        });

        //Button that cancelling or the changes were made for movie before clicking on Update button.
        Button cancel = findViewById(R.id.cancel);
        cancel.setVisibility(View.VISIBLE);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setBlank(movie);
            }

        });


    }
}