package com.yolo.kraus.bysjdemo01.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.yolo.kraus.bysjdemo01.ImApplication;
import com.yolo.kraus.bysjdemo01.Logic.JumpLogic;
import com.yolo.kraus.bysjdemo01.Model.nowInfo;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.Util.LocalSpUtil;
import com.yolo.kraus.bysjdemo01.business.LoginBusiness;
import com.yolo.kraus.bysjdemo01.event.FriendshipEvent;
import com.yolo.kraus.bysjdemo01.event.GroupEvent;
import com.yolo.kraus.bysjdemo01.event.MessageEvent;
import com.yolo.kraus.bysjdemo01.viewfeatures.jumpView;
import com.yolo.kraus.ui.NotifyDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraus on 2018/5/3.
 */

public class StartActivity  extends BaseActivity implements jumpView,TIMCallBack{

    private static final  String TAG = StartActivity.class.getSimpleName();
    private  JumpLogic jumpLogic = new JumpLogic(this);
    private int LOGIN_RESULT_CODE = 0;
    private final int REQUEST_PHONE_PERMISSIONS = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //动态权限申请

        final List<String> permissionsList = new ArrayList<>();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if((checkSelfPermission(Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.READ_PHONE_STATE);
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED))
                permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionsList.size()==0)
            {
                init();
            }
            else
            {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),REQUEST_PHONE_PERMISSIONS);
            }

        }
        else
        {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case REQUEST_PHONE_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    init();
                }
                else
                {
                    Toast.makeText(this, getString(R.string.need_permission),Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void init()
    {
        setUserInfoBylocal();
        jumpLogic.start();
    }

    private void setUserInfoBylocal()
    {
        LocalSpUtil.getInstance().get();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: "+resultCode);
        if(LOGIN_RESULT_CODE  == requestCode)
        {
            navToHome();
        }


    }

    @Override
    public void navToHome() {
        Log.d("DENGLU", "now user is："+ TIMManager.getInstance().getLoginUser());

        TIMUserConfig userConfig = new TIMUserConfig()
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.i(TAG, "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新userSig重新登录SDK
                        Log.i(TAG, "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.i(TAG, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i(TAG, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i(TAG, "onWifiNeedAuth");
                    }
                });


        userConfig = FriendshipEvent.getInstance().init(userConfig);
        userConfig = GroupEvent.getInstance().init(userConfig);
        userConfig = MessageEvent.getInstance().init(userConfig);
        TIMManager.getInstance().setUserConfig(userConfig);

        //login
        LoginBusiness.loginIm(nowInfo.getInstance().getId(), nowInfo.getInstance().getUserSig(),this);

        //消息监听
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                return false;
            }
        });

    }

    @Override
    public void navToLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean isUserLogin() {
        Log.d(TAG, "isUserLogin: ");
        return LocalSpUtil.getInstance().get();
    }

    @Override
    public void onError(int i, String s) {
        Log.e(TAG, "IM LOGIN ERROR : "+i+" msg: "+s);

        switch (i)
        {
            case 6208:
                //离线状态下被其他终端踢下线
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                break;
            case 6201:
            case 6012:
            case 6200:
                Toast.makeText(this,getString(R.string.login_error_timeout), Toast.LENGTH_SHORT).show();
                navToLogin();
                finish();
                break;
            default:
                Toast.makeText(this,getString(R.string.login_error)+"error id:"+i,Toast.LENGTH_LONG).show();
                navToLogin();
                finish();
                break;
        }
    }

    @Override
    public void onSuccess() {
        Log.d(TAG, "TIm sdk login onSuccess jump to HomeActivity ");
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
