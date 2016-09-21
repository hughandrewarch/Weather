package com.hughandrewarch.weather.data.LocalWeather;

import com.hughandrewarch.weather.data.JSONParser;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by HughAndrew on 2016-09-21.
 */
public class Weather implements JSONParser {

    private long id;
    private String main;
    private String description;
    private String icon;

    public Weather(JSONObject data)
    {
        parse(data);
    }

    public long getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public void parse(JSONObject data) {

        id = data.optLong("units");
        main = data.optString("main");
        description = data.optString("description");
        icon = data.optString("icon");
    }
}
