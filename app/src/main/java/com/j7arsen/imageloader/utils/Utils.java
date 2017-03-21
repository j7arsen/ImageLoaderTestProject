package com.j7arsen.imageloader.utils;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by arsen on 21.03.17.
 */

public class Utils {

    public static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
    }

}
