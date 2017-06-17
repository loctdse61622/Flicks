package com.coderschool.flicks;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private List<Movie> movies;
    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, -1);
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getRating() >= 7){
            return 1;
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        Movie movie = movies.get(position);

        if(convertView == null){
            if(getItemViewType(position) == 1){
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_high_rated_movie, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
                convertView.setTag(viewHolder);
            } else {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_low_rated_movie, parent, false);
                viewHolder = new ViewHolder(convertView);
                viewHolder.tvTitle.setText(movie.getTitle());
                viewHolder.tvOverview.setText(movie.getOverview());
                convertView.setTag(viewHolder);
            }
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(getItemViewType(position) == 1){
            Picasso.with(getContext())
                    .load(movie.getBackdropPath())
                    .placeholder(R.drawable.default_placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(viewHolder.ivPoster);
        } else {
            viewHolder.tvTitle.setText(movie.getTitle());
            viewHolder.tvOverview.setText(movie.getOverview());
            Configuration configuration = getContext().getResources().getConfiguration();
            if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                Picasso.with(getContext())
                    .load(movie.getBackdropPath())
                    .fit().centerCrop()
                    .placeholder(R.drawable.default_placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(viewHolder.ivPoster);
            } else {
                Picasso.with(getContext())
                    .load(movie.getPosterPath())
                    .fit().centerCrop()
                    .placeholder(R.drawable.default_placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(viewHolder.ivPoster);
            }
        }

        viewHolder.ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie currentMovie = movies.get(position);
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra("Movie", currentMovie);
//                intent.putExtra("title", currentMovie.getTitle());
//                intent.putExtra("releaseDate", currentMovie.getReleaseDate());
//                intent.putExtra("overview", currentMovie.getOverview());
//                intent.putExtra("rating", currentMovie.getRating());
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder{
        public ImageView ivPoster;
        public TextView tvTitle = null;
        public TextView tvOverview = null;

        public ViewHolder(View convertView){
            ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
            tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
        }

        public ViewHolder(){}
    }
}
