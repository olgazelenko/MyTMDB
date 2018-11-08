package olgaz.com.mytmdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

//Activity that operates search of movie from the internet according to the customer`s string
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act);


        Intent myint = getIntent();

        Button go = findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //get movies
                GetMoviesAsyncTask getMoviesAsyncTask = new GetMoviesAsyncTask(SearchActivity.this);
                EditText search = findViewById(R.id.search);
                String searchStr = search.getText().toString(); // customer string
                String urlQuery = (getString(R.string.src_prefix) + searchStr + (getString(R.string.src_sufix)));
                getMoviesAsyncTask.execute(urlQuery);  // running Async Task

            }
        });

    }

    //Show Movies function running from Async Task
    public void showsMovies(final ArrayList<MovieModel> arrList) {
        ListView listView = findViewById(R.id.list);

       ArrayAdapter<MovieModel> moviesItemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrList);

        listView.setAdapter(moviesItemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, MovieActivity.class );
                MovieModel movie = arrList.get(position);

                intent.putExtra(getString(R.string.Movie), movie);
               SearchActivity.this.startActivity(intent);

            }
        });

    }

    //starting a Progress Bar
    public void getProgressBar () {
        ProgressBar spinner = findViewById(R.id.progressBar1);
                spinner.setVisibility(View.VISIBLE);
}
}