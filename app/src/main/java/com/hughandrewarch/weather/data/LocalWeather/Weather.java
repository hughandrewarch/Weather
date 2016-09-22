package com.hughandrewarch.weather.data.LocalWeather;

import com.hughandrewarch.weather.data.JSONParser;

import org.json.JSONObject;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HughAndrew on 2016-09-21.
 */
public class Weather implements JSONParser {

    private long id;
    private String main;
    private String description;
    private String icon;

    public static final Map<Integer, String> condMap;

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

        id = data.optLong("id");
        main = data.optString("main");
        description = data.optString("description");
        icon = data.optString("icon");
    }
    static {
        Map<Integer, String> aMap = new HashMap<>();
        aMap.put(1, "one");
        aMap.put(2, "two");
        aMap.put(200, "thunderstorm with light rain");
        aMap.put(201, "thunderstorm with rain");
        aMap.put(202, "thunderstorm with heavy rain");
        aMap.put(210, "light thunderstorm");
        aMap.put(211, "thunderstorm");
        aMap.put(212, "heavy thunderstorm");
        aMap.put(221, "ragged thunderstorm");
        aMap.put(230, "thunderstorm with light drizzle");
        aMap.put(231, "thunderstorm with drizzle");
        aMap.put(232, "thunderstorm with heavy drizzle");
        aMap.put(300, "light intensity drizzle");
        aMap.put(301, "drizzle");
        aMap.put(302, "heavy intensity drizzle");
        aMap.put(310, "light intensity drizzle rain");
        aMap.put(311, "drizzle rain");
        aMap.put(312, "heavy intensity drizzle rain");
        aMap.put(313, "shower rain and drizzle");
        aMap.put(314, "heavy shower rain and drizzle");
        aMap.put(321, "shower drizzle");
        aMap.put(500, "light rain");
        aMap.put(501, "moderate rain");
        aMap.put(502, "heavy intensity rain");
        aMap.put(503, "very heavy rain");
        aMap.put(504, "extreme rain");
        aMap.put(511, "freezing rain");
        aMap.put(520, "light intensity shower rain");
        aMap.put(521, "shower rain");
        aMap.put(522, "heavy intensity shower rain");
        aMap.put(531, "ragged shower rain");
        aMap.put(600, "light snow");
        aMap.put(601, "snow");
        aMap.put(602, "heavy snow");
        aMap.put(611, "sleet");
        aMap.put(612, "shower sleet");
        aMap.put(615, "light rain and snow");
        aMap.put(616, "rain and snow");
        aMap.put(620, "light shower snow");
        aMap.put(621, "shower snow");
        aMap.put(622, "heavy shower snow");
        aMap.put(701, "mist");
        aMap.put(711, "smoke");
        aMap.put(721, "haze");
        aMap.put(731, "sand, dust whirls");
        aMap.put(741, "fog");
        aMap.put(751, "sand");
        aMap.put(761, "dust");
        aMap.put(762, "volcanic ash");
        aMap.put(771, "squalls");
        aMap.put(781, "tornado");
        aMap.put(800, "clear sky");
        aMap.put(801, "few clouds");
        aMap.put(802, "scattered clouds");
        aMap.put(803, "broken clouds");
        aMap.put(804, "overcast clouds");
        aMap.put(900, "tornado");
        aMap.put(901, "tropical storm");
        aMap.put(902, "hurricane");
        aMap.put(903, "cold");
        aMap.put(904, "hot");
        aMap.put(905, "windy");
        aMap.put(906, "hail");
        aMap.put(951, "calm");
        aMap.put(952, "light breeze");
        aMap.put(953, "gentle breeze");
        aMap.put(954, "moderate breeze");
        aMap.put(955, "fresh breeze");
        aMap.put(956, "strong breeze");
        aMap.put(957, "high wind, near gale");
        aMap.put(958, "gale");
        aMap.put(959, "severe gale");
        aMap.put(960, "storm");
        aMap.put(961, "violent storm");
        aMap.put(962, "hurricane");

        condMap = Collections.unmodifiableMap(aMap);
    }



}
