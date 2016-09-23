package com.hughandrewarch.weather.data.LocalWeather;

import com.hughandrewarch.weather.data.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HughAndrew on 2016-09-21.
 */
public class Forecast extends Current {

    private List<Current> forecast;

    private Temperature temperature;

    public Forecast(JSONObject data) {
        super(data);
    }

    public Temperature getTemperature()
    {   return temperature; }

    @Deprecated
    @Override
    public Main getMain()
    {
        return null;
    }

    @Override
    public void parse(JSONObject data) {

        weather = new Weather(data.optJSONArray("weather").optJSONObject(0));
        temperature = new Temperature(data.optJSONObject("temp"));

        long lTime = data.optLong("dt")*1000;
        time = new Date(lTime);
    }
}
