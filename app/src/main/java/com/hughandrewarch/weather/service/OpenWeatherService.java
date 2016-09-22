package com.hughandrewarch.weather.service;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.hughandrewarch.weather.data.LocalWeather.Current;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenWeatherService {

    private OpenWeatherServiceListener listener;

    Observable.OnSubscribe<Response> asd = new Observable.OnSubscribe<Response>() {
        @Override
        public void call(Subscriber<? super Response> subscriber) {

            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.openweathermap.org/data/2.5/weather").newBuilder();
            urlBuilder.addQueryParameter("q", "Toronto");
            urlBuilder.addQueryParameter("appid", "01adc9e181a8fc0f406f42adceddae8a");
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                subscriber.onNext(response);
                subscriber.onCompleted();
                if (!response.isSuccessful()) subscriber.onError(new Exception("error"));

            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        }
    };

    public void blargh()
    {

        Observable.create(asd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                        int i = 0;
                        i++;

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response response) {

                        try {
                            JSONObject jResponse = new JSONObject(response.body().string());

                            Current current = new Current(jResponse);

                            if(listener!=null)
                            {   listener.serviceSuccess(current);   }

                        } catch (JSONException|IOException e) {
                            e.printStackTrace();
                            if(listener!=null)
                            {   listener.serviceFailure(e);   }
                        }

                    }
                });

    }

    public void setListener(OpenWeatherServiceListener oListener)
    {   listener = oListener;   }
    public void removeListener()
    {   listener = null;    }

    public interface OpenWeatherServiceListener {
        void serviceSuccess(Current current);

        void serviceFailure(Exception exception);
    }

}
