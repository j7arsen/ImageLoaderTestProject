package com.j7arsen.imageloader.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by arsen on 21.03.17.
 */

public abstract class BaseFragment extends Fragment {

    private CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    public void unsubscribeAll() {
        if (!mSubscriptions.isUnsubscribed())
            mSubscriptions.unsubscribe();
    }

    public void unsubscribe(Subscription subscription) {
        if(mSubscriptions.hasSubscriptions())
            if(!subscription.isUnsubscribed())
                mSubscriptions.remove(subscription);
    }

    public void subscribe(final Observable<Object> observable) {
        mSubscriptions.add(observable.subscribe());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribeAll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
