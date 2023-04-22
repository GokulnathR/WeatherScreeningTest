package com.screening.knowyourweather.utils;


import com.screening.knowyourweather.utils.model.LatLngModel;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class RxJavaUtils {
    private static RxJavaUtils mInstance;
    public static synchronized RxJavaUtils getInstance() {
        if (mInstance == null) {
            mInstance = new RxJavaUtils();
        }
        return mInstance;
    }

    private PublishSubject<String> publisher = PublishSubject.create();
    private PublishSubject<LatLngModel> latlngPublisher = PublishSubject.create();

    public void publish(String event) {
        publisher.onNext(event);
    }

    public void publish(LatLngModel event) {
        latlngPublisher.onNext(event);
    }

    // Listen should return an Observable
    public Observable<String> listen() {
        return publisher;
    }

    public Observable<LatLngModel> listenLocation() {
        return latlngPublisher;
    }


}
