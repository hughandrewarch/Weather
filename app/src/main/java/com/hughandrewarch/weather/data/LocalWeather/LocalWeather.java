package com.hughandrewarch.weather.data.LocalWeather;

import com.hughandrewarch.weather.data.JSONParser;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by HughAndrew on 2016-09-21.
 */
public class LocalWeather implements JSONParser {

    public Date time;

    private Weather weather;

    public double temp;
    public double temp_min;
    public double temp_max;

    @Override
    public void parse(JSONObject data) {

        weather = new Weather(data.optJSONObject("weather"));

        time = new Date(data.optLong("time"));
    }
}
