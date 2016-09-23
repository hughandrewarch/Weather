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

    public double getTemp(boolean metric) {

        if(metric)
        {   return temp -  273.15; }
        else
        {   return temp;    }
    }
    public String getTempString(boolean metric) {

        double temp = getTemp(metric);

        if(metric)
        {   return (int)(temp) + "°C";    }
        else
        {   return (int)(temp) + "°F";    }
    }

    public double getTempMax(boolean metric) {

        if(metric)
        {   return temp_max -  273.15; }
        else
        {   return temp_max;    }
    }

    public String getTempMaxString(boolean metric) {

        double temp = getTempMax(metric);

        if(metric)
        {   return (int)(temp) + "°C";    }
        else
        {   return (int)(temp) + "°F";    }
    }

    public double getTempMin(boolean metric) {

        if(metric)
        {   return temp_min -  273.15; }
        else
        {   return temp_min;    }
    }

    public String getTempMinString(boolean metric) {

        double temp = getTempMin(metric);

        if(metric)
        {   return (int)(temp) + "°C";    }
        else
        {   return (int)(temp) + "°F";    }
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
