package olgaz.com.mytmdb;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


//Movie Addapter to set movie details inside the Move list View
public class MoviesAddapter extends ArrayAdapter<MovieModel> {

    private Context mContext;
    private ArrayList<MovieModel> mMovies;

    public MoviesAddapter(Context context_, ArrayList<MovieModel> movie_) {
        super(context_, 0, movie_);
        mContext = context_;
        mMovies = movie_;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.movie_item_row, parent, false);

        MovieModel currentMovie = mMovies.get(position);

        String urlpic =currentMovie.getPoster_path();
        String picurl = mContext.getResources().getString(R.string.img_prefix, urlpic);
        ImageView img = listItem.findViewById(R.id.mpic);
        Picasso.get().load(picurl).into(img);

        TextView title = listItem.findViewById(R.id.mtitle);
        title.setText(currentMovie.getOriginal_title());

        TextView RelDate = listItem.findViewById(R.id.reldate);
       RelDate.setText(currentMovie.getRelease_date());



        return listItem;

    }



    private String getDate(String time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

}
