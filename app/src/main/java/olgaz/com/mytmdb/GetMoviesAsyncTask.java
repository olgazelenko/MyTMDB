package olgaz.com.mytmdb;


import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//Async Task that takes care to bring the movies from the Internet according to the marker
//If marker = 1  - it'll bring all the movies
//If marker = 2  - it'll bring the movies according to the searched string

public class GetMoviesAsyncTask extends AsyncTask<String, Integer, ArrayList<MovieModel>> {

    private ListView mListView;
    private MainActivity mMainActivity;
    private SearchActivity mSearchActivity;
    private int marker;



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //TODO : HANDLE simpleProgressBar

        if(marker==2) {
            ProgressBar spinner = mSearchActivity.findViewById(R.id.progressBar1);
            spinner.setVisibility(View.VISIBLE);
            mSearchActivity.getProgressBar();
        }
    }

    //Constructor that sets the marker
    public GetMoviesAsyncTask(MainActivity MainActivity) {
        mMainActivity = MainActivity;
         marker = 1;
    }

    //Constructor that sets the marker
    public GetMoviesAsyncTask(SearchActivity SearchActivity) {
        mSearchActivity = SearchActivity;
        marker = 2;
    }

    public void setmListView(ListView mListView) {
        this.mListView = mListView;
    }


    @Override
    protected ArrayList<MovieModel> doInBackground(String... urls) {
        OkHttpClient client = new OkHttpClient();
        String urlQuery = urls[0];
        Request request = new Request.Builder()
                .url(urlQuery)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) try {
            throw new IOException("Unexpected code " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return getMoviesListFromJson(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    //get Movies from Json function
    public ArrayList<MovieModel> getMoviesListFromJson(String jsonResponse) {
        List<MovieModel> stubMovieData = new ArrayList<MovieModel>();
        Gson gson = new GsonBuilder().create();
        MovieResponse response = gson.fromJson(jsonResponse, MovieResponse.class);
        stubMovieData = response.results;
        ArrayList<MovieModel> arrList = new ArrayList<>();
        arrList.addAll(stubMovieData);
        return arrList;
    }


    public class MovieResponse {

        private List<MovieModel> results;

        // public constructor is necessary for collections
        public MovieResponse() {
            results = new ArrayList<MovieModel>();
        }

    }


    @Override

    //Show movie with stoping a Progress Bar in case of Search was running
    protected void onPostExecute(ArrayList<MovieModel> arrList) {
        if(marker==1) {
            mMainActivity.showsMovies(arrList);
        } else if (marker ==2)
        {

            mSearchActivity.showsMovies(arrList);
            ProgressBar spinner = mSearchActivity.findViewById(R.id.progressBar1);
            spinner.setVisibility(View.GONE);
            }


    }
}


