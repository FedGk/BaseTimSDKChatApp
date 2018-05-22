package com.yolo.kraus.bysjdemo01;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMGroupSettings;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushListener;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.ext.group.TIMGroupAssistantListener;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.yolo.kraus.bysjdemo01.business.InitBusiness;

/**
 * Created by Kraus on 2018/4/21.
 */

public class ImApplication extends Application {


    private static Context context;

    public static int getSDKAPPID() {
        return SDKAPPID;
    }

    private static  final int SDKAPPID = 1400078041;
    final Uri notifyMusic = Uri.parse("android.resource://com.yolo.kraus.bysjdemo01/" + R.raw.dudulu);


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        if(MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify){
                        //消息被设置为需要提醒
                        notification.doNotify(getApplicationContext(), R.mipmap.ic_launcher);
                        notification.setSound(notifyMusic);
                    }
                }
            });
        }

        InitBusiness.start(context);
    }

    public static Context getContext() {
        return context;
    }


}
