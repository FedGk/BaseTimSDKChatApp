package com.yolo.kraus.bysjdemo01.Activity;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.imsdk.TIMManager;
import com.yolo.kraus.bysjdemo01.Model.FriendshipInfo;
import com.yolo.kraus.bysjdemo01.Model.GroupInfo;
import com.yolo.kraus.bysjdemo01.Model.nowInfo;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.Util.LocalSpUtil;
import com.yolo.kraus.bysjdemo01.event.MessageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraus on 2018/4/25.
 */

public class HomeActivity extends FragmentActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private LayoutInflater layoutInflater;
    private FragmentTabHost mTabHost;
    private int mTitleArray[] = {R.string.home_conversation_tab, R.string.home_contact_tab, R.string.home_setting_tab};
    private int mImageViewArray[] = {R.drawable.tab_conversation, R.drawable.tab_contact, R.drawable.tab_setting};
    private final Class fragmentArray[] = {ConversationFragment.class, ContactFragment.class, SettingFragment.class};
    private String mTextviewArray[] = {"contact", "conversation", "setting"};
    private ImageView msgUnread;
    private final int REQUEST_PHONE_PERMISSIONS = 0;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            Log.d(TAG, "onKeyDown: back home");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "HomeActivity onCreate: ");
        setContentView(R.layout.activity_home);

        if (requestPermission())
        {

            Intent intent = new Intent(HomeActivity.this,StartActivity.class);
            finish();
            startActivity(intent);
        }
        else
        {
            initView();
//            Toast.makeText(this, getString(TIMManager.getInstance().getEnv() == 0 ? R.string.env_normal : R.string.env_test), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView()
    {
        Log.d(TAG, "initView: ");
        layoutInflater = LayoutInflater.from(this);
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.contentPanel);
        int fragmentCount = fragmentArray.length;
        for (int i = 0; i < fragmentCount; ++i)
        {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.getTabWidget().setDividerDrawable(null);

        }

    }


    private View getTabItemView(int index)
    {
        View view = layoutInflater.inflate(R.layout.home_tab,null);
        ImageView icon = view.findViewById(R.id.icon);
        icon.setImageResource(mImageViewArray[index]);
        TextView title = view.findViewById(R.id.title);
        title.setText(mTitleArray[index]);
        if(index == 0)
        {
            msgUnread = view.findViewById(R.id.tabUnread);
        }
        return view;

    }

    /**
     * 设置未读tab显示
     */
    public void setMsgUnread(boolean noUnread){
        msgUnread.setVisibility(noUnread?View.GONE:View.VISIBLE);
    }




    private boolean requestPermission(){
        if (afterM())
        {
            final List<String> permissionsList = new ArrayList<>();
            int i1= checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int i2 = PackageManager.PERMISSION_GRANTED;

            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                    (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED))
            {
                return true;
            }
        }
        return false;
    }



    private boolean afterM(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public void logout(){
        nowInfo.getInstance().setId(null);
        MessageEvent.getInstance().clear();
        FriendshipInfo.getInstance().clear();
        GroupInfo.getInstance().clear();
        LocalSpUtil.getInstance().cleaner();
        Intent intent = new Intent(HomeActivity.this,StartActivity.class);
        finish();
        startActivity(intent);

    }
}
