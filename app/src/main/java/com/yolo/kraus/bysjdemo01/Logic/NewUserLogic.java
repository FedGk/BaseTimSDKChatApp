package com.yolo.kraus.bysjdemo01.Logic;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.NewUserBean;
import com.yolo.kraus.bysjdemo01.Http.loginHttp;
import com.yolo.kraus.bysjdemo01.Http.mInterface.callBackBase;
import com.yolo.kraus.bysjdemo01.viewfeatures.NewUserView;

/**
 * Created by Kraus on 2018/5/22.
 */

public class NewUserLogic {

    private NewUserView view;

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
        loginHttp.mAddUser(user, new callBackBase() {
            @Override
            public void onSuccess(Object datas) {
                view.registerSuccess();
            }

            @Override
            public void onError(Throwable e) {
                view.registerError(e);
            }
        });
    }

    public void setData(String name,String password)
    {
        user =  new NewUserBean.User(name,password);
    }

}
