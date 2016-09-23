package com.hughandrewarch.weather.data.LocalWeather;

import com.hughandrewarch.weather.data.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HughAndrew on 2016-09-21.
 */
public class Forecast2 implements JSONParser {

    private List<Current> forecast;


    public Forecast2(JSONObject data)
    {
        this();
        parse(data);
    }

    public Forecast2()
    {
        forecast = new ArrayList<>();
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
