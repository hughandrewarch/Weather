package com.hughandrewarch.weather.data.LocalWeather;

import com.hughandrewarch.weather.data.JSONParser;

import org.json.JSONObject;

/**
 * Created by HughAndrew on 2016-09-21.
 */
public class Main implements JSONParser {

    private double temp;
    private double temp_max;
    private double temp_min;

    public double getTemp() {
        return temp;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public Main(JSONObject data)
    {   parse(data);    }

    @Override
    public void parse(JSONObject data) {

        temp = data.optDouble("temp");
        temp_max = data.optDouble("temp_max");
        temp_min = data.optDouble("temp_min");
    }
}
