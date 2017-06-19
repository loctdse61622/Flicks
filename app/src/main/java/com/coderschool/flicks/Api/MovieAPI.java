package com.coderschool.flicks.Api;

import com.coderschool.flicks.Model.ListTrailer;
import com.coderschool.flicks.Model.NowPlaying;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieAPI {

    @GET("now_playing")
    Call<NowPlaying> nowPlaying();

    @GET("{id}/videos")
    Call<ListTrailer> getTrailer(@Path("id") String id);
}
