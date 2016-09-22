package com.hughandrewarch.weather;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hughandrewarch.weather.data.LocalWeather.Current;
import com.hughandrewarch.weather.service.OpenWeatherService;

public class MainActivity extends AppCompatActivity {

    OpenWeatherService openWeatherService;

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openWeatherService = new OpenWeatherService();
        openWeatherService.setListener(openWeatherServiceListener);

        title = (TextView)findViewById(R.id.temp_text);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeatherService.blargh();
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
            boolean temp = Looper.myLooper() == Looper.getMainLooper();
            title.setText(current.getWeather().getDescription());



        }

        @Override
        public void serviceFailure(Exception exception) {

        }
    };
}
