package com.yolo.kraus.bysjdemo01.Bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import com.yolo.kraus.bysjdemo01.BR;

/**
 * Created by Kraus on 2018/4/19.
 */

public class UserInfo extends BaseObservable {
    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    private String username;
    private String password;
    private String login_time;//本次登录的时间
    private String imei;//本次登录时的IMEI编号

    public UserInfo()
    {
    }

    public UserInfo(String name,String pwd,String time,String im)
    {
        this.username = name;
        this.password = pwd;
        this.login_time = time;
        this.imei = im;
    }

    public UserInfo(String name,String pwd) {
        this.username = name;
        this.password = pwd;
        this.login_time = null;
        this.imei = null;
    }
    @Bindable
    public String getUsername() {
        return username;
    }
    @Bindable
    public String getPassword() {
        return password;
    }

    public String getLogin_time() {
        return login_time;
    }

    public String getImei() {
        return imei;
    }

    public final ObservableBoolean state = new ObservableBoolean(false);








}
