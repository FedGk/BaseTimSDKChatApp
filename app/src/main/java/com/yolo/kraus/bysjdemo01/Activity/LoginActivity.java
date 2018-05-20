package com.yolo.kraus.bysjdemo01.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.yolo.kraus.bysjdemo01.Bean.UserInfo;
import com.yolo.kraus.bysjdemo01.Logic.JumpLogic;
import com.yolo.kraus.bysjdemo01.Model.nowInfo;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.Util.Constants;
import com.yolo.kraus.bysjdemo01.Util.LocalSpUtil;
import com.yolo.kraus.bysjdemo01.ViewModel.LoginModel;
import com.yolo.kraus.bysjdemo01.business.LoginBusiness;
import com.yolo.kraus.bysjdemo01.databinding.UserLoginBinding;
import com.yolo.kraus.bysjdemo01.event.FriendshipEvent;
import com.yolo.kraus.bysjdemo01.event.GroupEvent;
import com.yolo.kraus.bysjdemo01.event.MessageEvent;
import com.yolo.kraus.bysjdemo01.viewfeatures.jumpView;
import com.yolo.kraus.bysjdemo01.viewfeatures.loginView;

import com.yolo.kraus.ui.NotifyDialog;

import java.util.List;

/**
 * Created by Kraus on 2018/4/19.
 */

public class LoginActivity  extends BaseActivity implements loginView{

    private static final String TAG = "LoginActivity";
    private  UserLoginBinding binding;
    private  UserInfo userInfo = new UserInfo();


    private boolean isShow  = true;//密码框显示或隐藏标记，默认true为隐藏


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.user_login);
        binding.setUser(userInfo);
        binding.setVm(new LoginModel());
        binding.getVm().setCb(this);


        initListener();
    }
    //UI入口
    private void initListener()
    {
        refleshPwdTextState();
        loginPass();
    }

    //hide or show password EditText
    private void refleshPwdTextState()
    {

        binding.loginPwdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow)//show password
                {
                    binding.loginPwdImage.setImageDrawable(getResources().getDrawable(R.drawable.login_open_eye));
                    binding.loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.loginPassword.setSelection(binding.loginPassword.getText().toString().length());
                    isShow = ! isShow;
                }
                else //hide password
                {
                    binding.loginPwdImage.setImageDrawable(getResources().getDrawable(R.drawable.login_close_eye));
                    binding.loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.loginPassword.setSelection(binding.loginPassword.getText().toString().length());
                    isShow = ! isShow;
                }
            }
        });
    }
    //注册监听：是否通过login verif
    public void loginPass()
    {
        //通过app服务器确认后更新model中state字段
        userInfo.state.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Log.d(TAG, "onPropertyChanged: "+userInfo.state.get());
            }
        });


    }


    /**
     * {@link LoginModel#loginButtonClick(UserInfo)}函数成功后回调到活动的接口
     */
    @Override
    public void serverToIM() {

        LocalSpUtil.getInstance().save();//保存账户信息到本地
        //Todo 跳转到tim sdk login 接口 或者跳转回StartActivity重新启用登录逻辑
        Intent intent = new Intent();
        intent.setClass(this,StartActivity.class);
        startActivityForResult(intent, Constants.EXTRA_CLASS_LOGIN);
        Log.d(TAG, "serverToIM: 跳转到tim sdk login 接口");

    }

//    //im登陆error回调
//    @Override
//    public void onError(int i, String s) {
//        Log.e(TAG, "IM LOGIN ERROR : "+i+" msg: "+s);
//
//        switch (i)
//        {
//            case 6208:
//                //离线状态下被其他终端踢下线
//                NotifyDialog dialog = new NotifyDialog();
//                dialog.show(getString(R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                break;
//            case 6200:
//                Toast.makeText(this,getString(R.string.login_error_timeout), Toast.LENGTH_SHORT).show();
//                navToLogin();
//                break;
//            default:
//                Toast.makeText(this,getString(R.string.login_error),Toast.LENGTH_SHORT).show();
//                navToLogin();
//                break;
//        }
//    }
    //im登录Success回调,注册跳转事件
//    @Override
//    public void onSuccess() {
//        Log.d(TAG, "login onSuccess: ");
//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    //初始化当前用户的好友逻辑关系群组关系等配置并添加刷新,login成功后跳转至回调接口onSuccess()否则跳转到onError()
//    @Override
//    public void navToHome() {
//        Log.d("DENGLU", "now user is："+TIMManager.getInstance().getLoginUser());
//
//        TIMUserConfig userConfig = new TIMUserConfig()
//                .setUserStatusListener(new TIMUserStatusListener() {
//                    @Override
//                    public void onForceOffline() {
//                        //被其他终端踢下线
//                        Log.i(TAG, "onForceOffline");
//                    }
//
//                    @Override
//                    public void onUserSigExpired() {
//                        //用户签名过期了，需要刷新userSig重新登录SDK
//                        Log.i(TAG, "onUserSigExpired");
//                    }
//                })
//                //设置连接状态事件监听器
//                .setConnectionListener(new TIMConnListener() {
//                    @Override
//                    public void onConnected() {
//                        Log.i(TAG, "onConnected");
//                    }
//
//                    @Override
//                    public void onDisconnected(int code, String desc) {
//                        Log.i(TAG, "onDisconnected");
//                    }
//
//                    @Override
//                    public void onWifiNeedAuth(String name) {
//                        Log.i(TAG, "onWifiNeedAuth");
//                    }
//                });
//
//
//        userConfig = FriendshipEvent.getInstance().init(userConfig);
//        userConfig = GroupEvent.getInstance().init(userConfig);
//        userConfig = MessageEvent.getInstance().init(userConfig);
//        TIMManager.getInstance().setUserConfig(userConfig);
//
//        //login
//        LoginBusiness.loginIm(nowInfo.getInstance().getId(), nowInfo.getInstance().getUserSig(),this);
//
//        //消息监听
//        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
//            @Override
//            public boolean onNewMessages(List<TIMMessage> list) {
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public void navToLogin() {
//        Log.d(TAG, "navToLogin: ");
//        new NotifyDialog().show(getString(R.string.login_error), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Log.d(TAG, "navToLogin  Dialog onClick: ");
//            }
//        });
//    }
//
//    @Override
//    public boolean isUserLogin() {
//        Log.d(TAG, "isUserLogin: ");
//        return nowInfo.getInstance().getId()!=null;
//    }


}
