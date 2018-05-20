package com.yolo.kraus.bysjdemo01.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.imsdk.TIMValueCallBack;
import com.yolo.kraus.bysjdemo01.Logic.GroupManagerLogic;
import com.yolo.kraus.bysjdemo01.R;

/**
 * Created by Kraus on 2018/5/16.
 */

public class CreateGroupActivity extends Activity {
    TextView mAddMembers;
    EditText mInputView;
    String type;
    private final int CHOOSE_MEM_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        type = getIntent().getStringExtra("type");
        mInputView = (EditText) findViewById(R.id.input_group_name);
        mAddMembers = (TextView) findViewById(R.id.btn_add_group_member);
        mAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInputView.getText().toString().equals("")){
                    Toast.makeText(CreateGroupActivity.this, getString(R.string.create_group_need_name), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CreateGroupActivity.this, ChooseFriendActivity.class);
                    startActivityForResult(intent, CHOOSE_MEM_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (CHOOSE_MEM_CODE == requestCode) {
            if (resultCode == RESULT_OK){
                GroupManagerLogic.createGroup(mInputView.getText().toString(),
                        type,
                        data.getStringArrayListExtra("select"),
                        new TIMValueCallBack<String>() {
                            @Override
                            public void onError(int i, String s) {
                                if (i == 80001){
                                    Toast.makeText(CreateGroupActivity.this, getString(R.string.create_group_fail_because_wording), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(CreateGroupActivity.this, getString(R.string.create_group_fail), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onSuccess(String s) {
                                Toast.makeText(CreateGroupActivity.this, getString(R.string.create_group_succeed), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                );
            }
        }
    }
}
