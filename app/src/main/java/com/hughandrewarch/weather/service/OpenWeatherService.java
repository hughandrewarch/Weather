package com.hughandrewarch.weather.service;

import android.os.Looper;

import com.hughandrewarch.weather.data.LocalWeather.Current;
import com.hughandrewarch.weather.data.LocalWeather.Forecast;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    //Forecast2
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

                        List<Forecast> forecasts = new ArrayList<>();

                        JSONArray jList = jResponse.optJSONArray("list");
                        for(int i = 0; i < jList.length(); i++)
                        {   forecasts.add(new Forecast(jList.optJSONObject(i)));  }

                        if(listener!=null)
                        {   listener.serviceForecastSuccess(forecasts);   }

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

        void serviceForecastSuccess(List<Forecast> forecasts);
        void serviceForecastFailure(Exception exception);
    }

}
