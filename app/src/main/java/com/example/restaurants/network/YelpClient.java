package com.example.restaurants.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.restaurants.constants.Constant.YELP_API_KEY;
import static com.example.restaurants.constants.Constant.YELP_BASE_URL;

public class YelpClient {
    private static Retrofit retrofit = null;
    public static YelpApi getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", YELP_API_KEY).build();
                    return chain.proceed(request);
                }
            }).build();


            retrofit = new Retrofit.Builder().baseUrl(YELP_BASE_URL).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(YelpApi.class);
    }
}
