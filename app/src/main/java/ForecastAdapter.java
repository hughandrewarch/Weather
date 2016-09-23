import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hughandrewarch.weather.R;
import com.hughandrewarch.weather.data.LocalWeather.Current;
import com.hughandrewarch.weather.data.LocalWeather.Forecast;
import com.squareup.picasso.Picasso;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {

    private Forecast forecast;
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


    public ForecastAdapter(Forecast forecast, Context context) {
        this.forecast = forecast;
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
        Current current = forecast.get(position);
        holder.forecastCond.setText(current.getWeather().getDescription().toUpperCase());
        holder.forecastDate.setText(current.getDate());

        String temp_range;
        temp_range = "HIGH: " + current.getMain().getTempMinString(true) +
                     " LOW: " + current.getMain().getTempMinString(true);
        holder.forecastTemp.setText(temp_range);

        String url = "http://openweathermap.org/img/w/" + current.getWeather().getIcon() + ".png";
        Picasso.with(mContext).load(url).into(holder.forecastIcon);
    }

    @Override
    public int getItemCount() {
        return forecast.getCount();
    }
}

