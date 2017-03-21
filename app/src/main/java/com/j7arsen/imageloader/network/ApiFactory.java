package com.j7arsen.imageloader.network;

import android.support.annotation.NonNull;

import com.j7arsen.imageloader.network.services.GetImageRequestService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arsen on 21.03.17.
 */

public class ApiFactory {

    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 30;
    private static final int TIMEOUT = 30;

    private static final OkHttpClient CLIENT = getOkHttpClient();

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }

    public static GetImageRequestService getImageList(){
        return getRetrofit().create(GetImageRequestService.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(CLIENT)
                .build();
    }

}
