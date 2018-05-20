package com.yolo.kraus.bysjdemo01.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.sns.TIMFriendResult;
import com.tencent.imsdk.ext.sns.TIMFriendStatus;
import com.yolo.kraus.bysjdemo01.Logic.FriendshipManagerLogic;
import com.yolo.kraus.bysjdemo01.Model.FriendshipInfo;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.viewfeatures.FriendshipManageView;
import com.yolo.kraus.ui.LineControllerView;
import com.yolo.kraus.ui.ListPickerDialog;
import com.yolo.kraus.ui.NotifyDialog;

import java.util.Collections;
import java.util.List;

/**
 * Created by Kraus on 2018/5/15.
 */

public class AddFriendActivity extends FragmentActivity implements View.OnClickListener, FriendshipManageView {

    private TextView tvName, btnAdd;
    private EditText editRemark, editMessage;
    private LineControllerView idField, groupField;
    private FriendshipManagerLogic presenter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        tvName = findViewById(R.id.name);
        idField = findViewById(R.id.id);
        id = getIntent().getStringExtra("id");
        tvName.setText(getIntent().getStringExtra("name"));
        idField.setContent(id);
        groupField = findViewById(R.id.group);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        editMessage = findViewById(R.id.editMessage);
        editRemark = findViewById(R.id.editNickname);
        presenter = new FriendshipManagerLogic(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAdd) {
            presenter.addFriend(id, editRemark.getText().toString(), groupField.getContent().equals(getString(R.string.default_group_name))?"":groupField.getContent(), editMessage.getText().toString());
        }else if (view.getId() == R.id.group){
            final String[] groups = FriendshipInfo.getInstance().getGroupsArray();
            for (int i = 0; i < groups.length; ++i) {
                if (groups[i].equals("")) {
                    groups[i] = getString(R.string.default_group_name);
                    break;
                }
            }
            new ListPickerDialog().show(groups, getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    groupField.setContent(groups[which]);
                }
            });
        }
    }

    /**
     * 添加好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onAddFriend(TIMFriendStatus status) {
        switch (status){
            case TIM_ADD_FRIEND_STATUS_PENDING:
                Toast.makeText(this, getResources().getString(R.string.add_friend_succeed), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_FRIEND_STATUS_SUCC:
                Toast.makeText(this, getResources().getString(R.string.add_friend_added), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_ADD_FRIEND_STATUS_FRIEND_SIDE_FORBID_ADD:
                Toast.makeText(this, getResources().getString(R.string.add_friend_refuse_all), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_ADD_FRIEND_STATUS_IN_OTHER_SIDE_BLACK_LIST:
                Toast.makeText(this, getResources().getString(R.string.add_friend_to_blacklist), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_ADD_FRIEND_STATUS_IN_SELF_BLACK_LIST:
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(R.string.add_friend_del_black_list), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FriendshipManagerLogic.delBlackList(Collections.singletonList(id), new TIMValueCallBack<List<TIMFriendResult>>() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(AddFriendActivity.this, getResources().getString(R.string.add_friend_del_black_err), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                                Toast.makeText(AddFriendActivity.this, getResources().getString(R.string.add_friend_del_black_succ), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            default:
                Toast.makeText(this, getResources().getString(R.string.add_friend_error), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    /**
     * 删除好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onDelFriend(TIMFriendStatus status) {

    }

    /**
     * 修改好友分组回调
     *
     * @param status    返回状态
     * @param groupName 分组名
     */
    @Override
    public void onChangeGroup(TIMFriendStatus status, String groupName) {

    }
}
