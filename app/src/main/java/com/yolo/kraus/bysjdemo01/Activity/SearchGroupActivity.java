package com.yolo.kraus.bysjdemo01.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.imsdk.ext.group.TIMGroupDetailInfo;
import com.yolo.kraus.bysjdemo01.Logic.GroupManagerLogic;
import com.yolo.kraus.bysjdemo01.MAdapter.ProfileSummaryAdapter;
import com.yolo.kraus.bysjdemo01.Model.GroupProfile;
import com.yolo.kraus.bysjdemo01.Model.ProfileSummary;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.viewfeatures.GroupInfoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kraus on 2018/5/16.
 */

public class SearchGroupActivity extends Activity implements GroupInfoView, View.OnKeyListener{
    private final String TAG = "SearchGroupActivity";

    private GroupManagerLogic groupManagerPresenter;
    private List<ProfileSummary> list= new ArrayList<>();
    private ProfileSummaryAdapter adapter;
    private EditText searchInput;
    private ListView listView;
    private TextView tvNoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group);
        searchInput = (EditText) findViewById(R.id.inputSearch);
        tvNoResult = (TextView) findViewById(R.id.noResult);
        listView =(ListView) findViewById(R.id.list);
        adapter = new ProfileSummaryAdapter(this, R.layout.item_profile_summary, list);
        listView.setAdapter(adapter);
        groupManagerPresenter = new GroupManagerLogic(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.get(position).onClick(SearchGroupActivity.this);
            }
        });
        TextView tvCancel = (TextView) findViewById(R.id.cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchInput.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP){   // 忽略其它事件
            return false;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                list.clear();
                adapter.notifyDataSetChanged();
                String key = searchInput.getText().toString();
                if (key.equals("")) return true;
                Intent intent = new Intent(this, ApplyGroupActivity.class);
                intent.putExtra("identify", key);
                startActivity(intent);
//                groupManagerPresenter.searchGroupByID(key);
                return true;
            default:
                return false;
        }
    }

    /**
     * 显示群资料
     *
     * @param groupInfos 群资料信息列表
     */
    @Override
    public void showGroupInfo(List<TIMGroupDetailInfo> groupInfos) {
        list.clear();
        for (TIMGroupDetailInfo item : groupInfos){
            Log.d(TAG, "showGroupInfo: "+item.getGroupName());
            list.add(new GroupProfile(item));
        }
        adapter.notifyDataSetChanged();

        tvNoResult.setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
