package com.hughandrewarch.weather;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hughandrewarch.weather.data.LocalWeather.Current;
import com.hughandrewarch.weather.service.OpenWeatherService;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    OpenWeatherService openWeatherService;

    private TextView title;
    private TextView currentTemp;
    private TextView currentCond;
    private TextView currentUpdated;
    private ImageView currentIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openWeatherService = new OpenWeatherService();
        openWeatherService.setListener(openWeatherServiceListener);

        title = (TextView)findViewById(R.id.temp_text);
        currentTemp = (TextView)findViewById(R.id.current_temp);
        currentCond = (TextView)findViewById(R.id.current_cond);
        currentUpdated = (TextView)findViewById(R.id.current_updated);
        currentIcon = (ImageView) findViewById(R.id.current_icon);

        openWeatherService.getCurrentWeather();

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeatherService.getCurrentWeather();
            }
        });

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        openWeatherService.removeListener();
    }


        private OpenWeatherService.OpenWeatherServiceListener openWeatherServiceListener = new OpenWeatherService.OpenWeatherServiceListener() {
        @Override
        public void serviceSuccess(Current current) {
            title.setText(current.getWeather().getDescription());

            currentCond.setText(current.getWeather().getDescription().toUpperCase());
            currentTemp.setText(current.getMain().getTempString(true));
            String update = "UPDATED " + current.getTimestamp();
            currentUpdated.setText(update);

            String url = "http://openweathermap.org/img/w/" + current.getWeather().getIcon() + ".png";
            Picasso.with(getBaseContext()).load(url).into(currentIcon);
        }

        @Override
        public void serviceFailure(Exception exception) {

        }
    };
}
