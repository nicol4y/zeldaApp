package com.example.workshop30.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private com.example.workshop30.network.API api;

    private RetrofitClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.api = retrofit.create(com.example.workshop30.network.API.class);

    }

    public com.example.workshop30.network.API getAPI() {
        return this.api;
    }



    private static RetrofitClient instance = null;
    public static synchronized RetrofitClient getInstance(){
        if ( instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }

}
