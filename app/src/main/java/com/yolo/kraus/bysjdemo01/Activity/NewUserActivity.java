package com.yolo.kraus.bysjdemo01.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yolo.kraus.bysjdemo01.Logic.NewUserLogic;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.viewfeatures.NewUserView;
import com.yolo.kraus.ui.TemplateTitle;

/**
 * Created by Kraus on 2018/5/22.
 */

public class NewUserActivity extends Activity implements View.OnClickListener,NewUserView {

    private TemplateTitle title;
    private NewUserLogic newUserLogic;
    private EditText edName;
    private EditText edPwd;
    private TextView rightTxt;
    private final  static String TAG = NewUserActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        init();

    }

    private void init()
    {
        title = findViewById(R.id.new_user_title);
        title.setBackListener(this);
        title.setMoreTextAction(this);

        rightTxt = title.findViewById(R.id.txt_more);

        edName.findViewById(R.id.new_ed_name);
        edPwd.findViewById(R.id.new_ed_password);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.title_back:
                finish();
                break;
            case R.id.txt_more:
                if(!edName.getText().toString().isEmpty() && !edPwd.getText().toString().isEmpty())
                {
                    //todo button 不可选择判断
                    rightTxt.setClickable(false);
                    NewUserLogic.getInstance().setData(edName.getText().toString(),edPwd.getText().toString());
                    NewUserLogic.getInstance().start();

                }
                else
                {
                    Toast.makeText(this,"请输入账号或者密码",Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }

    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void registerError(Throwable e) {

    }

    @Override
    public void finish() {
        rightTxt.setClickable(true);
        super.finish();

    }
}
