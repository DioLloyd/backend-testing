package ru.diolloyd.lesson5atRetrofit.utils;

import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

@UtilityClass
public class RetrofitUtils {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    public Retrofit getRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging.setLevel(BODY))
                .build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(ConfigUtils.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
