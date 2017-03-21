package com.j7arsen.imageloader.utils;

import com.j7arsen.imageloader.app.ImageLoaderApplication;

/**
 * Created by arsen on 21.03.17.
 */

public class FieldConverter {

    public static String getString(int resId) {
        return ImageLoaderApplication.mInstance.getResources().getString(resId);
    }

    public static int getColor(int resId) {
        return ImageLoaderApplication.mInstance.getResources().getColor(resId);
    }

}
