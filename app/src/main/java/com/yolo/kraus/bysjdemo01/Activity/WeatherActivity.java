package com.yolo.kraus.bysjdemo01.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.WeatherBean;
import com.yolo.kraus.bysjdemo01.ImApplication;
import com.yolo.kraus.bysjdemo01.Logic.WeatherLogic;
import com.yolo.kraus.bysjdemo01.Model.CityModel;
import com.yolo.kraus.bysjdemo01.Model.WeatherModel;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.viewfeatures.WeatherView;
import com.yolo.kraus.ui.TemplateTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraus on 2018/5/25.
 */

public class WeatherActivity extends AppCompatActivity implements WeatherView  {
    private static final String TAG = WeatherActivity.class.getSimpleName();
    private WeatherLogic wl = new WeatherLogic();

    private TextView mTodayTV;
    private ImageView mTodayWeatherImage;
    private TextView mTodayTemperatureTV;
    private TextView mTodayWindTV;
    private TextView mTodayWeatherTV;
    private TextView mCityTV;
    private ProgressBar mProgressBar;
    private LinearLayout mWeatherLayout;
    private LinearLayout mWeatherContentLayout;
    private TemplateTitle mTitle;

    private String city = ImApplication.getInstance().getCityName();
    private static  int REQUEST_CODE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        
        mTodayTV = findViewById(R.id.today);
        mTodayWeatherImage = findViewById(R.id.weatherImage);
        mTodayTemperatureTV = findViewById(R.id.weatherTemp);
        mTodayWindTV = findViewById(R.id.wind);
        mTodayWeatherTV = findViewById(R.id.weather);
        mCityTV = findViewById(R.id.city);
        mProgressBar = findViewById(R.id.progress);
        mWeatherLayout = findViewById(R.id.weather_layout);
        mWeatherContentLayout = findViewById(R.id.weather_content);
        mTitle = findViewById(R.id.weather_title);
        mTitle.setMoreTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == com.yolo.kraus.ui.R.id.txt_more)
                {
                    Intent intent = new Intent();
                    intent.setClass(WeatherActivity.this,CityActivity.class);
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }
        });
        wl.init(WeatherActivity.this,city,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode: "+requestCode+"resultCode: "+resultCode);
        if(resultCode== CityModel.code)
        {
            if(requestCode == REQUEST_CODE)
                city = data.getStringExtra(CityModel.Key1);
                ImApplication.getInstance().setCityName(city);
                wl.init(WeatherActivity.this,city,this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CityModel.Key1,city);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        city = savedInstanceState.getString(CityModel.Key1);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showWeatherLayout() {
        mWeatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCity(String city) {
        mCityTV.setText(city);
    }

    @Override
    public void setToday(String data) {
        mTodayTV.setText(data);
    }

    @Override
    public void setTemperature(String temperature) {
        mTodayTemperatureTV.setText(temperature);
    }

    @Override
    public void setWind(String wind) {
        mTodayWindTV.setText(wind);
    }

    @Override
    public void setWeather(String weather) {
        mTodayWeatherTV.setText(weather);
    }

    @Override
    public void setWeatherImage(int res) {
        mTodayWeatherImage.setImageResource(res);
    }

    @Override
    public void setWeatherData(List<WeatherBean.DataBean.ForecastBean> data) {
        List<View> adapterList = new ArrayList<View>();
        for(WeatherBean.DataBean.ForecastBean simple : data)
        {
            View view = LayoutInflater.from(ImApplication.getContext()).inflate(R.layout.item_weather, null, false);
            TextView dateTV = (TextView) view.findViewById(R.id.date);
            ImageView todayWeatherImage = (ImageView) view.findViewById(R.id.weatherImage);
            TextView todayTemperatureTV = (TextView) view.findViewById(R.id.weatherTemp);
            TextView todayWindTV = (TextView) view.findViewById(R.id.wind);
            TextView todayWeatherTV = (TextView) view.findViewById(R.id.weather);

            dateTV.setText(simple.getDate());
            todayTemperatureTV.setText(simple.getHigh());
            todayWindTV.setText(simple.getFengxiang());
            todayWeatherTV.setText(simple.getType());
            todayWeatherImage.setImageResource(WeatherModel.getWeatherImage(simple.getType()));
            mWeatherContentLayout.addView(view);
            adapterList.add(view);
        }

    }

    @Override
    public void showErrorToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
