package olgaz.com.mytmdb;



import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

//Main Activity - shows all the movies in the internet (one page)
public class MainActivity extends AppCompatActivity {

    private DataProvider mDataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent myint = getIntent();


        // TODO: feel the list

        //get movies
        GetMoviesAsyncTask getMoviesAsyncTask = new GetMoviesAsyncTask(MainActivity.this);
        String urlQuery = getString(R.string.urlQuery) + String.valueOf(3);
        getMoviesAsyncTask.execute(urlQuery);

        }

//function show movies in the ListView using Movies Addapter
    public void showsMovies(final ArrayList<MovieModel> arrList) {
        ListView listView = findViewById(R.id.list);

       MoviesAddapter moviesItemsAdapter = new MoviesAddapter(this, arrList);
       listView.setAdapter(moviesItemsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int marker = 1;
                Intent intent = new Intent(MainActivity.this, MovieActivity.class );
                MovieModel movie = arrList.get(position);

                intent.putExtra(getString(R.string.Movie), movie);
                intent.putExtra(getString(R.string.marker), marker);

                MainActivity.this.startActivity(intent);

            }
        });

    }

    @Override

    //Option Menu functions
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addm:
                //Add movie manual
                Intent intent2 = new Intent(MainActivity.this, ManAddActivity.class);
                MainActivity.this.startActivity(intent2);
                break;
            case R.id.intMovie:
                //Search for movie from the internet according to the string
                Intent intent1 = new Intent(MainActivity.this, SearchActivity.class);
                MainActivity.this.startActivity(intent1);
                break;

            case R.id.exit:  // exit from application
                finishAffinity();
                break;

        }
        return true;
    }
}

