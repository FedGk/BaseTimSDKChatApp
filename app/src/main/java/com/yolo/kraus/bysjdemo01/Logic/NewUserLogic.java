package com.yolo.kraus.bysjdemo01.Logic;

import android.util.Log;

import com.yolo.kraus.bysjdemo01.Activity.NewUserActivity;
import com.yolo.kraus.bysjdemo01.Http.JsonBean.NewUserBean;
import com.yolo.kraus.bysjdemo01.Http.loginHttp;
import com.yolo.kraus.bysjdemo01.Http.mInterface.callBackBase;
import com.yolo.kraus.bysjdemo01.viewfeatures.NewUserView;

import java.util.List;

/**
 * Created by Kraus on 2018/5/22.
 */

public class NewUserLogic {

    private static  final String TAG = NewUserLogic.class.getSimpleName();

    private NewUserView view = null;

    private NewUserBean.User user ;

    private static NewUserLogic ourInstance = new NewUserLogic();

    public static NewUserLogic getInstance()
    {
        return ourInstance;
    }


    public void start()
    {
        applyNewUser();
    }

    //todo http请求增加user
    private void applyNewUser()
    {
        loginHttp.mAddUser(user, new callBackBase<NewUserBean.newBack>() {
            @Override
            public void onSuccess(NewUserBean.newBack datas) {
                Log.d(TAG, "onSuccess: "+datas.getCode());
                switch (datas.getCode())
                {
                    case 0:
                        view.registerFaile();
                        break;
                    case 200:
                        view.registerSuccess();
                        break;
                }

            }

            @Override
            public void onError(Throwable e) {
                view.registerError(e);
            }
        });
    }

    public void setData(String name,String password,NewUserView view)
    {
        user =  new NewUserBean.User(name,password);
        this.view = view;
    }

}
