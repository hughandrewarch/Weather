package com.hughandrewarch.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hughandrewarch.weather.data.LocalWeather.Forecast;
import com.hughandrewarch.weather.data.LocalWeather.Temperature;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {

    private List<Forecast> forecasts;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView forecastTemp;
        public TextView forecastCond;
        public TextView forecastDate;
        public ImageView forecastIcon;

        public MyViewHolder(View view) {
            super(view);
            forecastTemp = (TextView) view.findViewById(R.id.forecast_temp);
            forecastCond = (TextView) view.findViewById(R.id.forecast_cond);
            forecastDate = (TextView) view.findViewById(R.id.forecast_date);
            forecastIcon = (ImageView) view.findViewById(R.id.forecast_icon);
        }
    }


    public ForecastAdapter(List<Forecast> forecasts, Context context) {
        this.forecasts = forecasts;
        mContext = context;
    }
    public ForecastAdapter(Context context) {
        forecasts = new ArrayList<>();
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_list_row, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Forecast forecast = forecasts.get(position);
        holder.forecastCond.setText(forecast.getWeather().getDescription().toUpperCase());
        holder.forecastDate.setText(forecast.getDate());

        String temp_range;
        temp_range = "HIGH: " + forecast.getTemperature().getTempString(Temperature.TYPE.MAX, true) +
                     " LOW: " + forecast.getTemperature().getTempString(Temperature.TYPE.MIN, true);
        holder.forecastTemp.setText(temp_range);

        String url = "http://openweathermap.org/img/w/" + forecast.getWeather().getIcon() + ".png";
        Picasso.with(mContext).load(url).into(holder.forecastIcon);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }
}

