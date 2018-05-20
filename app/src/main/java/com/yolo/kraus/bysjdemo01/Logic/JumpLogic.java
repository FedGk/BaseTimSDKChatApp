package com.yolo.kraus.bysjdemo01.Logic;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.yolo.kraus.bysjdemo01.ImApplication;
import com.yolo.kraus.bysjdemo01.viewfeatures.jumpView;

import java.util.logging.Handler;

/**
 * Created by Kraus on 2018/4/25.
 */

public class JumpLogic {
    jumpView view ;

    private static final  String TAG = JumpLogic.class.getSimpleName();

    public JumpLogic(jumpView view)
    {
        this.view = view;
    }

    /**
     * 登录页面逻辑驱动
     */

    public void start()
    {

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(view.isUserLogin() )//
                {

                    //true 返回true代表已经有人登录过且未退出登录，返回假代表没有人登录过
                    view.navToHome();
                }
                else
                {
                    //false 则 停留在login界面 针对error code 做出提示
                    view.navToLogin();
                }
            }
        },1000);
    }
}
