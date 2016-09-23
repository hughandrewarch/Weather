package com.hughandrewarch.weather.data.LocalWeather;

import com.hughandrewarch.weather.data.JSONParser;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
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

    public String getTimestamp()
    {
        String pattern = "h:mm:ss aa";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(getTime());
    }

    public String getDay()
    {
        String pattern = "EEEE";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(getTime());
    }

    public String getDate()
    {
        String pattern = "EEE, MMM d";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(getTime());
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

        long lTime = data.optLong("dt")*1000;
        time = new Date(lTime);
    }
}
