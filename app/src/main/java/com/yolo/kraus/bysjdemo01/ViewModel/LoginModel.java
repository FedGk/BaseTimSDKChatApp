package com.yolo.kraus.bysjdemo01.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableBoolean;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.qalsdk.base.remote.ToServiceMsg;
import com.yolo.kraus.bysjdemo01.Bean.UserInfo;
import com.yolo.kraus.bysjdemo01.Http.JsonBean.JsonLogin;
import com.yolo.kraus.bysjdemo01.Http.loginHttp;
import com.yolo.kraus.bysjdemo01.Http.mInterface.callBackBase;
import com.yolo.kraus.bysjdemo01.ImApplication;
import com.yolo.kraus.bysjdemo01.Model.nowInfo;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.viewfeatures.loginView;

import java.util.List;

/**
 * Created by Kraus on 2018/4/10.
 */

public class LoginModel  {


    private loginView cb;

    private static  final String TAG = "LMTag";



    public void loginButtonClick(final UserInfo userInfo)
    {

        cb.setButtonEnable();
        if(userInfo.getPassword()!=null && userInfo.getUsername()!=null)
        {

            final JsonLogin.User user  = new JsonLogin.User(userInfo.getUsername(),userInfo.getPassword());

            loginHttp.mLoginCheck(user, new callBackBase<List<JsonLogin.loginBack>>() {
                @Override
                public void onSuccess(List<JsonLogin.loginBack> datas) {
                    userInfo.state.set(true);
                    nowInfo.getInstance().setId(userInfo.getUsername());
                    nowInfo.getInstance().setUserSig(datas.get(0).getSig());
                    cb.serverToIM();
                }

                @Override
                public void onError(Throwable e) {
                    cb.setButtonable();
                    userInfo.state.set(false);
                }
            });
        }
        userInfo.state.set(false);

    }

    public void registerUser()
    {
        Toast.makeText(ImApplication.getContext(),"ok",Toast.LENGTH_SHORT).show();
    }


    public void setCb(loginView cb) {
        this.cb = cb;
    }
}
