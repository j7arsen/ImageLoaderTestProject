package com.j7arsen.imageloader.app;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by arsen on 21.03.17.
 */

public class ImageLoaderApplication extends Application {

    public static Context mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Fresco.initialize(this);
    }

}
