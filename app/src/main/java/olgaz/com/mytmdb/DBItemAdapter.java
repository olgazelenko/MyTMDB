package olgaz.com.mytmdb;

import android.content.Context;
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

//Custom ListView Adaptor class for an Item in the Favority View -(this is view of the movies from the DB)

public class DBItemAdapter extends ArrayAdapter<MovieModel> {

        private Context mContext;
        private ArrayList<MovieModel> mMovies;

       //Constructor
        public DBItemAdapter(Context context_, ArrayList<MovieModel> movie_) {
            super(context_, 0, movie_);
            mContext = context_;
            mMovies = movie_;

        }

        @NonNull
        @Override
        //List View initialization
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View listItem = convertView;
            if (listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.favor_item_row, parent, false);

            MovieModel currentMovie = mMovies.get(position); //get movie in the position...

            TextView title = listItem.findViewById(R.id.subjectdb);
            title.setText(currentMovie.getOriginal_title());  // title initialization

            TextView isWaitched = listItem.findViewById(R.id.iswatchedb);
            String watcheOrNot = currentMovie.getWatchedOrNot();
            isWaitched.setText(watcheOrNot);                // witched or not text initialization

            TextView AddedDate = listItem.findViewById(R.id.datedb);
            AddedDate.setText(currentMovie.getDateMovieAdded());    //date when the movie was added to DB initialization

            String url = currentMovie.getPoster_path();
            ImageView img = listItem.findViewById(R.id.picdb);
            String picurl = mContext.getResources().getString(R.string.img_prefix, url);
            Picasso.get().load(picurl).into(img);           // getting picture


            return listItem;

        }



}
