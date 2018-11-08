package olgaz.com.mytmdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

//Class takes Favorite Movies from the DB
public class FavorMovies extends AppCompatActivity {

    private ArrayList<MovieModel> movieModelArrayList;
    private DBItemAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor_movies);
        final DataProvider dataProvider = new DataProvider(this);

        //Getting Movie List with Intent
        Intent myint = getIntent();
        if (myint != null) {
            movieModelArrayList = (ArrayList<MovieModel>) getIntent().getSerializableExtra(getString(R.string.MovieList));
        }

        final ListView listView = findViewById(R.id.flist);

        //setting Custom List View usView Addapter
        mAdapter = new DBItemAdapter(this, movieModelArrayList);
        listView.setAdapter(mAdapter);

        // Detele all movies from the favorite list
        Button del = findViewById(R.id.delete);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Allert Dialog checks if really need to delete
                AlertDialog.Builder builder = new AlertDialog.Builder(FavorMovies.this);
                builder.setTitle("Delete All");
                builder.setMessage("You are about to delete all the movies. \nAre you sure you really want to proceed?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataProvider.deleteAllMovies();  // delete all movies from the DB
                        ListView listView = findViewById(R.id.flist);
                        listView.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "You've choosen to delete all the movies", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You've changed your mind to delete all the movies", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });

        //this button move us to the main activity where we can get movies from the internet and Options menu
        Button main = findViewById(R.id.main);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavorMovies.this, MainActivity.class);
                FavorMovies.this.startActivity(intent);
            }
        });

        registerForContextMenu(listView);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.list_menu, menu);

    }

    //Context Menu - to delete or update one movie item
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final int position = info.position;
        switch (item.getItemId()) {
            case R.id.delete: // delete current movie
                //Allert Dialog checks if delete
                AlertDialog.Builder builder = new AlertDialog.Builder(FavorMovies.this);
                builder.setTitle("Delete Movie");
                builder.setMessage("Are you sure you want to delete a movie?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataProvider.deleteItem(movieModelArrayList.get(position)); // delete movie from the DB
                        movieModelArrayList.remove(position); // remove movie from the list
                        mAdapter.notifyDataSetChanged(); // update that Data was changed
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You've changed your mind to delete a movie", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
                break;
            case R.id.edit: // edit case send user to the Edit Activity using Intents
                Intent intent = new Intent(FavorMovies.this, EditActivities.class );
                MovieModel movie = movieModelArrayList.get(position);
                intent.putExtra(getString(R.string.Movie_id), movieModelArrayList.get(position).getId());
                intent.putExtra(getString(R.string.Movie_edit), movieModelArrayList.get(position));
                FavorMovies.this.startActivity(intent);
                break;

        }
        return true;
    }

   //function that operate with Dialog for "DELETE ALL" button
    public void showAlertDialogButtonClicked(View view) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure you want to delete all movies?");

        // add the buttons
        builder.setPositiveButton("Yes", null);
        builder.setNegativeButton("No", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
