package com.hughandrewarch.weather.data.LocalWeather;

import com.hughandrewarch.weather.data.JSONParser;

import org.json.JSONObject;

/**
 * Created by HughAndrew on 2016-09-21.
 */
public class Temperature implements JSONParser {

    private double day;
    private double max;
    private double min;
    private double night;
    private double eve;
    private double morn;

    public enum TYPE
    {
        DAY, MAX, MIN, NIGHT, EVE, MORN
    }

    public Temperature(JSONObject data)
    {   parse(data);    }

    public double getTemp(TYPE type, boolean metric) {

        double temp_temp;
        switch(type)
        {
            default:
            case DAY:
                temp_temp = day;
                break;
            case MAX:
                temp_temp = max;
                break;
            case MIN:
                temp_temp = min;
                break;
            case NIGHT:
                temp_temp = night;
                break;
            case EVE:
                temp_temp = eve;
                break;
            case MORN:
                temp_temp = morn;
                break;
        }

        if(metric)
        {   return temp_temp -  273.15; }
        else
        {   return temp_temp;    }
    }

    public String getTempString(TYPE type, boolean metric) {

        double temp = getTemp(type, metric);

        if(metric)
        {   return (int)(temp) + "°C";    }
        else
        {   return (int)(temp) + "°F";    }
    }

    @Override
    public void parse(JSONObject data) {

        day = data.optDouble("day");
        max = data.optDouble("max");
        min = data.optDouble("min");
        night = data.optDouble("night");
        eve = data.optDouble("eve");
        morn = data.optDouble("morn");
    }
}
