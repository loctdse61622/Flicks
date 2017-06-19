package com.coderschool.flicks.Activity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coderschool.flicks.Api.MovieAPI;
import com.coderschool.flicks.Model.ListTrailer;
import com.coderschool.flicks.Model.Movie;
import com.coderschool.flicks.R;
import com.coderschool.flicks.Utils.RetrofitUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends YouTubeBaseActivity{

    @BindView(R.id.tvDetailTitle)
    TextView tvTitle;
    @BindView(R.id.tvDetailReleaseDate)
    TextView tvReleaseDate;
    @BindView(R.id.tvDetailOverview)
    TextView tvOverview;
    @BindView(R.id.tvRating)
    TextView tvRating;
    @BindView(R.id.rtbRatingBar)
    RatingBar rtbRatingBar;
    @BindView(R.id.player)
    YouTubePlayerView youTubePlayerView;

    private MovieAPI movieAPI;
    private String youtube_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.coderschool.flicks.R.layout.movie_details);
        ButterKnife.bind(this);

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
        youTubePlayerView.initialize(com.coderschool.flicks.BuildConfig.YOUTUBE_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                movieAPI = RetrofitUtil.create(com.coderschool.flicks.BuildConfig.API_KEY).create(MovieAPI.class);
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
