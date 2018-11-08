package olgaz.com.mytmdb;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Date;

import com.squareup.picasso.Picasso;

//Supper Activity - provide 2 functions
public class SuperMovieActivity extends Activity {

    private DataProvider dataProvider;
    private String dateFacDB = "";
    private MovieModel movie = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

    }

    // Function that takes movie details from the DB and set them on the blank
    protected   void setBlank(MovieModel movie){
        EditText title = findViewById(R.id.title);

        title.setText(movie.getOriginal_title());
        EditText body = findViewById(R.id.body);
        body.setText(movie.getOverview());

        EditText url = findViewById(R.id.url);
        String urlpic = movie.getPoster_path();
        String picurl = getString(R.string.img_prefix, urlpic);
        url.setText(urlpic);

        ImageView img = findViewById(R.id.img);
        Picasso.get().load(picurl).into(img);

        Date date = new Date();
        dateFacDB = DateFormat.format(getString(R.string.Date_format), date).toString();
        movie.setDateMovieAdded(dateFacDB);
    }

    // Function that takes movie details from the blank and set them to movie
    protected void setMovieFromBlank(MovieModel movie){
        EditText title = findViewById(R.id.title);
        String subject = title.getText().toString();
        movie.setTitle(subject);

        EditText body = findViewById(R.id.body);
        String over = body.getText().toString();
        movie.setOverview(over);

        EditText url = findViewById(R.id.url);
        String url_path = url.getText().toString();
        movie.setPoster_path(url_path);

        ImageView img = findViewById(R.id.img);
        String picurl = getString(R.string.img_prefix, url_path);
        Picasso.get().load(picurl).into(img);

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

    }
}
