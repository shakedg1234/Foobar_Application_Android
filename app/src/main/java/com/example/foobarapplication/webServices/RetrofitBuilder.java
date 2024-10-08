package com.example.foobarapplication.webServices;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitBuilder {

    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            synchronized (RetrofitBuilder.class) {
                if (retrofit == null) {

                    // Create OkHttpClient with timeout settings
                    OkHttpClient okHttpClient = new OkHttpClient.Builder().
                            connectTimeout(30, TimeUnit.SECONDS).
                            readTimeout(30, TimeUnit.SECONDS).
                            writeTimeout(30, TimeUnit.SECONDS).build();

                    // Build Retrofit instance with base URL and OkHttpClient
                    retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:80/").client(okHttpClient).callbackExecutor(Executors.newSingleThreadExecutor())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        // Return the singleton instance of Retrofit
        return retrofit;
    }
}
