package com.yolo.kraus.bysjdemo01.business;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;
import com.yolo.kraus.bysjdemo01.ImApplication;


/**
 * 初始化
 * 包括imsdk等
 */
public class InitBusiness {

    private static final String TAG = InitBusiness.class.getSimpleName();

    private InitBusiness(){}

    public static void start(Context context){
        initImsdk(context, 0);
    }

    public static void start(Context context, int logLevel){
        initImsdk(context, logLevel);
    }


    /**
     * 初始化imsdk
     */
    private static void initImsdk(Context context, int logLevel){
        TIMSdkConfig config = new TIMSdkConfig(ImApplication.getSDKAPPID());
        config.enableLogPrint(true)
                .setLogLevel(TIMLogLevel.values()[logLevel])
                .setLogPath(Environment.getExternalStorageDirectory().getPath()+"/justfortest/")
                .enableCrashReport(false);
        //初始化imsdk
        TIMManager.getInstance().init(context, config);
        //禁止服务器自动代替上报已读
        Log.d(TAG, "initIMsdk");

    }





}
