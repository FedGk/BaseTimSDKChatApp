package com.yolo.kraus.bysjdemo01.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushSettings;
import com.tencent.imsdk.TIMValueCallBack;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.ui.LineControllerView;

/**
 * Created by Kraus on 2018/5/20.
 */

public class MessageNotifySettingActivity extends Activity {
    private String TAG = "MessageNotifySettingActivity";
    TIMOfflinePushSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_notify_setting);
        final Uri notifyMusic = Uri.parse("android.resource://com.yolo.kraus.bysjdemo01/" + R.raw.dudulu);
        TIMManager.getInstance().getOfflinePushSettings(new TIMValueCallBack<TIMOfflinePushSettings>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "get offline push setting error " + s);
            }

            @Override
            public void onSuccess(TIMOfflinePushSettings timOfflinePushSettings) {
                settings = timOfflinePushSettings;
                LineControllerView messagePush = (LineControllerView) findViewById(R.id.messagePush);
                messagePush.setSwitch(settings.isEnabled());
                messagePush.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        settings.setEnabled(isChecked);
                        TIMManager.getInstance().setOfflinePushSettings(settings);
                    }
                });
                LineControllerView c2cMusic = (LineControllerView) findViewById(R.id.c2cMusic);
                c2cMusic.setSwitch(settings.getC2cMsgRemindSound() != null);
                c2cMusic.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        settings.setC2cMsgRemindSound(isChecked ? notifyMusic : null);
                        TIMManager.getInstance().setOfflinePushSettings(settings);
                    }
                });
                LineControllerView groupMusic = (LineControllerView) findViewById(R.id.groupMusic);
                groupMusic.setSwitch(settings.getGroupMsgRemindSound() != null);
                groupMusic.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        settings.setGroupMsgRemindSound(isChecked ? notifyMusic : null);
                        TIMManager.getInstance().setOfflinePushSettings(settings);
                    }
                });
            }
        });

    }

}
