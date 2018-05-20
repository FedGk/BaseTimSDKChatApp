package com.yolo.kraus.bysjdemo01.Http.mInterface;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.JsonLogin;

import java.util.List;
import java.util.Observable;

import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Kraus on 2018/4/20.
 */

public interface iLogin {
    @POST("login/verif_user")
    io.reactivex.Observable<List<JsonLogin.loginBack>> verifUser(@Body JsonLogin.User user);
}
