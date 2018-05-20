package com.yolo.kraus.bysjdemo01.Model;

/**
 * Created by Kraus on 2018/4/24.
 */

public class nowInfo {
    private String id;
    private String userSig;

    private static nowInfo ourInstance = new nowInfo();

    public static nowInfo getInstance() {
        return ourInstance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserSig() {
        return userSig;
    }

    public void setUserSig(String userSig) {
        this.userSig = userSig;
    }
}
