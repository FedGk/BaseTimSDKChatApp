package com.yolo.kraus.bysjdemo01.viewfeatures;

/**
 * Created by Kraus on 2018/4/25.
 */

public interface jumpView {

    /**
     * 跳转到主界面
     */
    void navToHome();


    /**
     * 跳转到登录界面
     */
    void navToLogin();

    /**
     * 是否已有用户登录
     */
    boolean isUserLogin();
}
