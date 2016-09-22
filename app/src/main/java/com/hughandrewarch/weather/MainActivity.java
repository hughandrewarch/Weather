package com.hughandrewarch.weather;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hughandrewarch.weather.data.LocalWeather.Current;
import com.hughandrewarch.weather.data.LocalWeather.Forecast;
import com.hughandrewarch.weather.service.OpenWeatherService;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private OpenWeatherService openWeatherService;

    private Animator colourAnimator;

    private LinearLayout back;
    private TextView title;
    private TextView currentTemp;
    private TextView currentCond;
    private TextView currentUpdated;
    private ImageView currentIcon;

    public enum COLOUR
    {
        RED,
        MAGENTA,
        BLUE,
        YELLOW,
        GREEN,
        CYAN,
    }

    COLOUR now = COLOUR.BLUE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openWeatherService = new OpenWeatherService();

        back = (LinearLayout)findViewById(R.id.main_back);
        title = (TextView)findViewById(R.id.temp_text);
        currentTemp = (TextView)findViewById(R.id.current_temp);
        currentCond = (TextView)findViewById(R.id.current_cond);
        currentUpdated = (TextView)findViewById(R.id.current_updated);
        currentIcon = (ImageView) findViewById(R.id.current_icon);

        switch(now)
        {
            case RED:
                back.setBackgroundResource(R.color.red);break;
            case MAGENTA:
                back.setBackgroundResource(R.color.magenta);break;
            case BLUE:
                back.setBackgroundResource(R.color.blue);break;
            case CYAN:
                back.setBackgroundResource(R.color.cyan);break;
            case GREEN:
                back.setBackgroundResource(R.color.green);break;
            case YELLOW:
                back.setBackgroundResource(R.color.yellow);break;
        }

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        openWeatherService.setListener(openWeatherServiceListener);
        openWeatherService.getCurrentWeather();

        if(colourAnimator == null)
        {   colourAnimator = colourAnimator();
            colourAnimator.setDuration(3000).start();
        }
        else
        {   colourAnimator.resume();    }
    }

        @Override
    protected void onPause()
    {
        super.onPause();
        openWeatherService.removeListener();
        colourAnimator.pause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }


    private OpenWeatherService.OpenWeatherServiceListener openWeatherServiceListener = new OpenWeatherService.OpenWeatherServiceListener() {
        @Override
        public void serviceCurrentSuccess(Current current) {
            title.setText(current.getWeather().getDescription());

            currentCond.setText(current.getWeather().getDescription().toUpperCase());
            currentTemp.setText(current.getMain().getTempString(true));
            String update = "UPDATED " + current.getTimestamp();
            currentUpdated.setText(update);

            String url = "http://openweathermap.org/img/w/" + current.getWeather().getIcon() + ".png";
            Picasso.with(getBaseContext()).load(url).into(currentIcon);
        }

        @Override
        public void serviceCurrentFailure(Exception exception) {

        }

        @Override
        public void serviceForecastSuccess(Forecast forecast) {

        }

        @Override
        public void serviceForecastFailure(Exception exception) {

        }
    };

    private ValueAnimator colourAnimator() {

        ValueAnimator animator = ValueAnimator.ofInt(0, 100);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int iProgress = (int)valueAnimator.getAnimatedValue();
                double progress = iProgress*1.0;
                progress /= 100;

                int current, next;

                switch(now)
                {
                    default:
                    case RED:
                        current = getResources().getColor(R.color.red);
                        next = getResources().getColor(R.color.magenta);
                        break;
                    case MAGENTA:
                        current = getResources().getColor(R.color.magenta);
                        next = getResources().getColor(R.color.blue);
                        break;
                    case BLUE:
                        current = getResources().getColor(R.color.blue);
                        next = getResources().getColor(R.color.cyan);
                        break;
                    case CYAN:
                        current = getResources().getColor(R.color.cyan);
                        next = getResources().getColor(R.color.green);
                        break;
                    case GREEN:
                        current = getResources().getColor(R.color.green);
                        next = getResources().getColor(R.color.yellow);
                        break;
                    case YELLOW:
                        current = getResources().getColor(R.color.yellow);
                        next = getResources().getColor(R.color.red);
                        break;
                }

                ArgbEvaluator argb = new ArgbEvaluator();
                back.setBackgroundColor((int)argb.evaluate((float)progress, current, next));
            }
        });

        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                switch(now)
                {
                    default:
                    case RED:
                        now = COLOUR.MAGENTA;
                        break;
                    case MAGENTA:
                        now = COLOUR.BLUE;
                        break;
                    case BLUE:
                        now = COLOUR.CYAN;
                        break;
                    case CYAN:
                        now = COLOUR.GREEN;
                        break;
                    case GREEN:
                        now = COLOUR.YELLOW;
                        break;
                    case YELLOW:
                        now = COLOUR.RED;
                        break;
                }

                Animator asd = colourAnimator();
                asd.setDuration(3000).start();


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });

        return animator;
    }


}
