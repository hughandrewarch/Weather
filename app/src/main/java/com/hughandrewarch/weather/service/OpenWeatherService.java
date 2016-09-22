package com.hughandrewarch.weather.service;

import android.os.Looper;

import com.hughandrewarch.weather.data.LocalWeather.Current;
import com.hughandrewarch.weather.data.LocalWeather.Forecast;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenWeatherService {

    private OpenWeatherServiceListener listener;
    private static OkHttpClient client = new OkHttpClient();

    private Observable.OnSubscribe<JSONObject> currentObservable = new Observable.OnSubscribe<JSONObject>() {
        @Override
        public void call(Subscriber<? super JSONObject> subscriber) {

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.openweathermap.org/data/2.5/weather").newBuilder();
            urlBuilder.addQueryParameter("q", "Toronto");
            urlBuilder.addQueryParameter("appid", "01adc9e181a8fc0f406f42adceddae8a");
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {

                Response response = client.newCall(request).execute();
                JSONObject jResponse = new JSONObject(response.body().string());
                subscriber.onNext(jResponse);
                subscriber.onCompleted();
                if (!response.isSuccessful())
                {   subscriber.onError(new Exception("error")); }

            } catch (IOException|JSONException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        }
    };

    public void getCurrentWeather()
    {

        Observable.create(currentObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JSONObject jResponse) {

                        boolean currents = Looper.myLooper() == Looper.getMainLooper();
                        Current current = new Current(jResponse);

                        if(listener!=null)
                        {   listener.serviceCurrentSuccess(current);   }

                    }
                });

    }

    //Forecast
    private Observable.OnSubscribe<JSONObject> forecastObservable = new Observable.OnSubscribe<JSONObject>() {
        @Override
        public void call(Subscriber<? super JSONObject> subscriber) {

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://api.openweathermap.org/data/2.5/forecast/daily").newBuilder();
            urlBuilder.addQueryParameter("q", "Toronto");
            urlBuilder.addQueryParameter("appid", "01adc9e181a8fc0f406f42adceddae8a");
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                JSONObject jResponse = new JSONObject(response.body().string());
                subscriber.onNext(jResponse);
                subscriber.onCompleted();
                if (!response.isSuccessful()) subscriber.onError(new Exception("error"));

            } catch (IOException|JSONException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        }
    };

    public void getForecastWeather()
    {

        Observable.create(forecastObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JSONObject jResponse) {

                        Forecast forecast = new Forecast(jResponse);

                        if(listener!=null)
                        {   listener.serviceForecastSuccess(forecast);   }

                    }
                });

    }

    public void setListener(OpenWeatherServiceListener oListener)
    {   listener = oListener;   }
    public void removeListener()
    {   listener = null;    }

    public interface OpenWeatherServiceListener {
        
        void serviceCurrentSuccess(Current current);
        void serviceCurrentFailure(Exception exception);

        void serviceForecastSuccess(Forecast forecast);
        void serviceForecastFailure(Exception exception);

        void fack(Response response);
    }

}
