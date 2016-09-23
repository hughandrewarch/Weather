package com.hughandrewarch.weather.data.LocalWeather;

import com.hughandrewarch.weather.data.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HughAndrew on 2016-09-21.
 */
public class Forecast implements JSONParser {

    private List<Current> forecast;


    public Forecast(JSONObject data)
    {
        forecast = new ArrayList<>();
        parse(data);
    }

    public int getCount()
    {   return forecast.size(); }

    public Current get(int i)
    {   return forecast.get(i); }

    @Override
    public void parse(JSONObject data) {

        JSONArray jList = data.optJSONArray("list");
        for(int i = 0; i < jList.length(); i++)
        {   forecast.add(new Current(jList.optJSONObject(i)));  }

    }
}
