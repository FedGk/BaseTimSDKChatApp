package com.yolo.kraus.bysjdemo01.Http.mInterface;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.JsonLogin;
import com.yolo.kraus.bysjdemo01.Http.JsonBean.NewUserBean;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Kraus on 2018/5/22.
 */

public interface iNew {
    //todo 约束http访问协议
    @POST("login/addUser")
    Observable<List<NewUserBean.newBack>> addUser(@Body NewUserBean.User user);
}
