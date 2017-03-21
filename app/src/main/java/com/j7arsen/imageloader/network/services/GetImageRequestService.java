package com.j7arsen.imageloader.network.services;

import com.j7arsen.imageloader.network.Urls;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by arsen on 21.03.17.
 */

public interface GetImageRequestService {

    @GET(Urls.GET_IMAGE)
    Observable<List<String>> getImageList();

}