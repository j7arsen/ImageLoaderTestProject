package com.j7arsen.imageloader.app;

import android.content.Context;

import com.j7arsen.imageloader.R;

/**
 * Created by arsen on 21.03.17.
 */

public class UIConfig {

    public static boolean isTablet(Context context){
        return context.getResources().getBoolean(R.bool.isTablet);
    }

}
