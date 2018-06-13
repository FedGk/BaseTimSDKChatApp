package com.yolo.kraus.bysjdemo01.Model;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.yolo.kraus.bysjdemo01.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Kraus on 2018/6/13.
 */

public class CityModel {

    private static final String TAG = CityModel.class.getSimpleName() ;
    public static int code = 1;
    public static String Key1 = "CityName";

    public static void loadXML(Resources r)
    {
        XmlResourceParser xrp = r.getXml(R.xml.city_name);

        int counter = 0;
        try {
            //如果没有到文件尾继续执行
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                //如果是开始标签
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    //获取标签名称
                    String name = xrp.getName();
                    //判断标签名称是否等于friend
                    if(name.equals("province")){
                        Log.d(TAG, "省： "+xrp.getAttributeValue(0));

                    }
                    if(name.equals("item")){
                        Log.d(TAG, "市: "+xrp.nextText());

                    }
                } else if (xrp.getEventType() == XmlPullParser.END_TAG) {
                } else if (xrp.getEventType() == XmlPullParser.TEXT) {
                }
                //下一个标签
                xrp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
