package com.hughandrewarch.weather.data.LocalWeather;

import com.hughandrewarch.weather.data.JSONParser;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by HughAndrew on 2016-09-21.
 */
public class Current implements JSONParser {

    public Date time;

    private Weather weather;
    private Main main;

    public Date getTime() {
        return time;
    }

    public Weather getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Current(JSONObject data)
    {
        parse(data);
    }

    @Override
    public void parse(JSONObject data) {

        weather = new Weather(data.optJSONArray("weather").optJSONObject(0));
        main = new Main(data.optJSONObject("main"));

        time = new Date(data.optLong("time"));
    }
}
