package com.j7arsen.imageloader.managers;

import com.j7arsen.imageloader.network.ApiFactory;
import com.j7arsen.imageloader.network.services.GetImageRequestService;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by arsen on 21.03.17.
 */

public class RequestManager {

    private static RequestManager mInstance;

    private RequestManager() {

    }

    public static RequestManager getInstance() {
        if (mInstance == null) {
            mInstance = new RequestManager();
        }
        return mInstance;
    }

    public Observable<List<String>> getImageList() {
        GetImageRequestService service = ApiFactory.getImageList();
        return service.getImageList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }


}
