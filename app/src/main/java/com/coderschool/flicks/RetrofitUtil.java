package com.coderschool.flicks;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitUtil {
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    public static Retrofit create(String api_key){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client(api_key))
                .baseUrl(BASE_URL)
                .build();
    }

    public static OkHttpClient client(final String api_key){
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url()
                                .newBuilder()
                                .addQueryParameter("api_key", api_key)
                                .build();
                        request = request.newBuilder()
                                .url(url)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

}
