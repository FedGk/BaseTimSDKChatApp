package com.yolo.kraus.bysjdemo01.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.yolo.kraus.bysjdemo01.ImApplication;
import com.yolo.kraus.bysjdemo01.Model.nowInfo;



/**
 * Created by Kraus on 2018/5/3.
 * @author Kraus
 */

public class LocalSpUtil {

    private  SharedPreferences sharedPreferences  = ImApplication.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
    private  SharedPreferences.Editor editor = sharedPreferences.edit();
    private static LocalSpUtil Instance = new LocalSpUtil();

    private final String TAG = LocalSpUtil.class.getSimpleName();

    private  LocalSpUtil()
    {
    }

    public static LocalSpUtil  getInstance() {
        return Instance;
    }


    /**
     *
     * @return Returns true is first login ,false is this user had login pass
     */

    public  boolean save()
    {
        boolean var1 = sharedPreferences.getBoolean("flag",false);
        //该用户第一次登录
        if(!var1)
        {

            editor.putString("id", nowInfo.getInstance().getId());
            editor.putString("sig",nowInfo.getInstance().getUserSig());
            editor.putBoolean("flag",true);//第一次登录后标识符保存为true,
            editor.commit();
            return true;
        }//该用户非第一次登录
        else
            return false;

    }

    public SharedPreferences.Editor clean()
    {
        return editor.clear();
    }

    /**
     *
     * @return 返回true代表已经有人登录过且未退出登录，返回假代表没有人登录过
     */
    public boolean get()
    {
        boolean var1 = false;
        try
        {
            nowInfo.getInstance().setId(sharedPreferences.getString("id","def"));
            nowInfo.getInstance().setUserSig(sharedPreferences.getString("sig","def"));
            var1 = sharedPreferences.getBoolean("flag",false);
        }
        catch (Exception e)
        {
            Log.d(TAG, "get: "+e.toString());
        }



        return var1;
    }

    public void cleaner()
    {
        editor.clear();
        editor.commit();
    }

}
