package com.coderschool.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.*;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends YouTubeBaseActivity{
    private TextView tvTitle;
    private TextView tvReleaseDate;
    private TextView tvOverview;
    private TextView tvRating;
    private RatingBar rtbRatingBar;
    private YouTubePlayerView youTubePlayerView;
    private MovieAPI movieAPI;
    private String youtube_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        tvTitle = (TextView) findViewById(R.id.tvDetailTitle);
        tvReleaseDate = (TextView) findViewById(R.id.tvDetailReleaseDate);
        tvRating = (TextView) findViewById(R.id.tvRating);
        tvOverview = (TextView) findViewById(R.id.tvDetailOverview);
        rtbRatingBar = (RatingBar) findViewById(R.id.rtbRatingBar);

//        Bundle extras = getIntent().getExtras();
//        tvTitle.setText(extras.get("title").toString());
//        tvReleaseDate.setText("Release Date: " + extras.get("releaseDate").toString());
//        tvOverview.setText(extras.get("overview").toString());
//        tvRating.setText("    " + extras.get("rating").toString() + "/10");
//        rtbRatingBar.setRating(Float.parseFloat(extras.get("rating").toString()));

        Movie currentMovie = (Movie) getIntent().getSerializableExtra("Movie");
        tvTitle.setText(currentMovie.getTitle());
        tvReleaseDate.setText("Release Date: " + currentMovie.getReleaseDate());
        tvOverview.setText(currentMovie.getOverview());
        tvRating.setText("    " + currentMovie.getRating() + "/10");
        rtbRatingBar.setRating(currentMovie.getRating());
        initYouTubePlayer(currentMovie);
    }

    private void initYouTubePlayer(final Movie movie){
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
        youTubePlayerView.initialize(BuildConfig.YOUTUBE_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                movieAPI = RetrofitUtil.create(BuildConfig.API_KEY).create(MovieAPI.class);
                movieAPI.getTrailer(String.valueOf(movie.getId())).enqueue(new Callback<ListTrailer>() {
                    @Override
                    public void onResponse(Call<ListTrailer> call, Response<ListTrailer> response) {
                        youtube_key = response.body().getList().get(0).getKey();
                        youTubePlayer.cueVideo(youtube_key);
                    }

                    @Override
                    public void onFailure(Call<ListTrailer> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Failed to get video key!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(getBaseContext(), "Fail to initialize youtube player!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
