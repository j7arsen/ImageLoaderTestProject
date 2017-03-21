package com.j7arsen.imageloader.progress;

import android.app.Activity;
import android.app.Fragment;

import com.j7arsen.imageloader.error.ErrorHandler;

/**
 * Created by arsen on 21.03.17.
 */

public class ProgressDialogManager {

    private static ProgressDialogManager ourInstance = new ProgressDialogManager();

    private ProgressDialog mProgressDialog;

    private ProgressDialogManager() {
    }

    public static ProgressDialogManager getInstance() {
        return ourInstance;
    }

    private void subscribe(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog();
        }
    }


    public void unsubscribe(){
        if( mProgressDialog != null && mProgressDialog.isAdded()){
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public void startLoading(Activity activity){
        subscribe();
        if (!mProgressDialog.isVisible() && !mProgressDialog.isAdded()) {
            mProgressDialog.show(activity.getFragmentManager(), ProgressDialog.TAG);
        }
        mProgressDialog.startLoading();
    }

    public void startLoading(Fragment fragment){
        subscribe();
        mProgressDialog.show(fragment.getFragmentManager(), ProgressDialog.TAG);
        mProgressDialog.startLoading();
    }

    public void errorLoading(ErrorHandler errorHandler){
        mProgressDialog.errorLoading(errorHandler);
    }

    public void completeLoading(){
        mProgressDialog.completeLoading();
        unsubscribe();
    }

}
