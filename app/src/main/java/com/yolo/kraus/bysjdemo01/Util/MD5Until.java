package com.yolo.kraus.bysjdemo01.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/*
生成32位MD5值
 */

public class MD5Until {
    private static MessageDigest md5 = null;

    private static String SecretKey = "2faed6f97f3848ebbc5af8fb0ea940cd";

    private static String AccessKey = "JeGxGJfXLFxnCkg0";

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMd5(String timestamp) {

        String temp = SecretKey+timestamp+AccessKey;

        try {
            byte[] bs = md5.digest(temp.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(40);
            for (byte x : bs) {
                if ((x & 0xff) >> 4 == 0) {
                    sb.append("0").append(Integer.toHexString(x & 0xff));
                } else {
                    sb.append(Integer.toHexString(x & 0xff));
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
