package com.yolo.kraus.bysjdemo01.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.yolo.kraus.bysjdemo01.ImApplication;
import com.yolo.kraus.bysjdemo01.Model.CityModel;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;

/**
 * Created by Kraus on 2018/6/12.
 */

public class CityActivity extends AppCompatActivity {
    private static final String TAG = CityActivity.class.getSimpleName();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CityPicker.getInstance()
                .setFragmentManager(getSupportFragmentManager())
                .enableAnimation(true)
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Intent intent = new Intent();
                        if(data ==null)
                        {
                            String city = ImApplication.getInstance().getCityName();
                            intent.putExtra(CityModel.Key1,city);
                        }
                        else
                            intent.putExtra(CityModel.Key1,data.getName());
//                        intent.setClass(CityActivity.this,WeatherActivity.class);
                        setResult(CityModel.code,intent);
                        finish();

                    }

                    @Override
                    public void onLocate() {

                    }
                })
                .show();


    }


}
