package com.yolo.kraus.bysjdemo01.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMFriendAllowType;
import com.tencent.imsdk.TIMUserProfile;
import com.yolo.kraus.bysjdemo01.Logic.FriendshipManagerLogic;
import com.yolo.kraus.bysjdemo01.News.activity.NewsActivity;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.business.LoginBusiness;
import com.yolo.kraus.bysjdemo01.viewfeatures.FriendInfoView;
import com.yolo.kraus.ui.LineControllerView;
import com.yolo.kraus.ui.ListPickerDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kraus on 2018/4/25.
 */

public class SettingFragment extends Fragment implements FriendInfoView {
    private static final String TAG = SettingFragment.class.getSimpleName();
    private View view;
    private FriendshipManagerLogic friendshipManagerPresenter;
    private TextView id,name;
    private LineControllerView nickName, friendConfirm;
    private final int REQ_CHANGE_NICK = 1000;
    private Map<String, TIMFriendAllowType> allowTypeContent;



    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_setting, container, false);
            id = (TextView) view.findViewById(R.id.idtext);
            name = (TextView) view.findViewById(R.id.name);
            friendshipManagerPresenter = new FriendshipManagerLogic(this);
            friendshipManagerPresenter.getMyProfile();
            TextView logout = (TextView) view.findViewById(R.id.logout);
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginBusiness.logout(new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            if (getActivity() != null){
                                Toast.makeText(getActivity(), getResources().getString(R.string.setting_logout_fail), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onSuccess() {
                            if (getActivity() != null && getActivity() instanceof HomeActivity){
                                ((HomeActivity) getActivity()).logout();
                            }
                        }
                    });
                }
            });
            nickName = (LineControllerView) view.findViewById(R.id.nickName);
            nickName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditActivity.navToEdit(SettingFragment.this, getResources().getString(R.string.setting_nick_name_change), name.getText().toString(), REQ_CHANGE_NICK, new EditActivity.EditInterface() {
                        @Override
                        public void onEdit(String text, TIMCallBack callBack) {
                            FriendshipManagerLogic.setMyNick(text, callBack);
                        }
                    }, 20);

                }
            });
            allowTypeContent = new HashMap<>();
            allowTypeContent.put(getString(R.string.friend_allow_all), TIMFriendAllowType.TIM_FRIEND_ALLOW_ANY);
            allowTypeContent.put(getString(R.string.friend_need_confirm), TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM);
            allowTypeContent.put(getString(R.string.friend_refuse_all), TIMFriendAllowType.TIM_FRIEND_DENY_ANY);
            final String[] stringList = allowTypeContent.keySet().toArray(new String[allowTypeContent.size()]);
            friendConfirm = (LineControllerView) view.findViewById(R.id.friendConfirm);
            friendConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new ListPickerDialog().show(stringList, getFragmentManager(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, final int which) {
                            FriendshipManagerLogic.setFriendAllowType(allowTypeContent.get(stringList[which]), new TIMCallBack() {
                                @Override
                                public void onError(int i, String s) {
                                    Toast.makeText(getActivity(), getString(R.string.setting_friend_confirm_change_err), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onSuccess() {
                                    friendConfirm.setContent(stringList[which]);
                                }
                            });
                        }
                    });
                }
            });
            LineControllerView news = (LineControllerView) view.findViewById(R.id.news);
            news.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), NewsActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(),getResources().getString(R.string.setting_news),Toast.LENGTH_LONG).show();

                }
            });
            LineControllerView weather = view.findViewById(R.id.weather);
            weather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    startActivity(intent);
                }
            });

            LineControllerView blackList = (LineControllerView) view.findViewById(R.id.blackList);
            blackList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BlackListActivity.class);
                    startActivity(intent);
                }
            });
            LineControllerView about = (LineControllerView) view.findViewById(R.id.about);
            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), AboutActivity.class);
//                    startActivity(intent);
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("关于")
                            .setMessage("J1901047组毕设作品！")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create();
                    alertDialog.show();
//                    Toast.makeText(getContext(),"还没做",Toast.LENGTH_LONG).show();
                }
            });

        }
        return view ;
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CHANGE_NICK){
            if (resultCode == getActivity().RESULT_OK){
                setNickName(data.getStringExtra(EditActivity.RETURN_EXTRA));
            }
        }

    }

    private void setNickName(String name){
        if (name == null) return;
        this.name.setText(name);
        nickName.setContent(name);
    }


    /**
     * 显示用户信息
     *
     * @param users 资料列表
     */
    @Override
    public void showUserInfo(List<TIMUserProfile> users) {
        id.setText(users.get(0).getIdentifier());
        setNickName(users.get(0).getNickName());
        for (String item : allowTypeContent.keySet()){
            if (allowTypeContent.get(item) == users.get(0).getAllowType()){
                friendConfirm.setContent(item);
                break;
            }
        }

    }
}
