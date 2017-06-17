package com.coderschool.flicks;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private ListView lvMovies;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMovie();
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMovie();
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void loadMovie(){
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        Retrofit retrofit = RetrofitUtil.create(BuildConfig.API_KEY);
        MovieAPI movieAPI = retrofit.create(MovieAPI.class);
        movieAPI.nowPlaying().enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                List<Movie> movies = response.body().getMovies();
                lvMovies.setAdapter(new MovieAdapter(MainActivity.this, movies));
                Toast.makeText(getBaseContext(), "Load successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Load failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
