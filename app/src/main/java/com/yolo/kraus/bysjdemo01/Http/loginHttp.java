package com.yolo.kraus.bysjdemo01.Http;

import android.util.Log;

import com.yolo.kraus.bysjdemo01.Http.JsonBean.JsonLogin;
import com.yolo.kraus.bysjdemo01.Http.mInterface.callBackBase;
import com.yolo.kraus.bysjdemo01.Http.mInterface.iLogin;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kraus on 2018/4/20.
 */

public class loginHttp {
    private static String TAG = loginHttp.class.getSimpleName();
    public static void mLoginCheck(JsonLogin.User user, final callBackBase cb)
    {
        iLogin mLogin  = BaseHttp.mRetrofit().create(iLogin.class);
        mLogin.verifUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<JsonLogin.loginBack>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(List<JsonLogin.loginBack> loginBacks) {
                            cb.onSuccess(loginBacks);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.toString());
                        cb.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
