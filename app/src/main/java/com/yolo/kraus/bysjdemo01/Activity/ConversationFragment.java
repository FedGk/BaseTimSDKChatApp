package com.yolo.kraus.bysjdemo01.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.group.TIMGroupPendencyItem;
import com.tencent.imsdk.ext.sns.TIMFriendFutureItem;
import com.yolo.kraus.bysjdemo01.Logic.ConversationLogic;
import com.yolo.kraus.bysjdemo01.Logic.FriendshipManagerLogic;
import com.yolo.kraus.bysjdemo01.Logic.GroupManagerLogic;
import com.yolo.kraus.bysjdemo01.MAdapter.ConversationAdapter;
import com.yolo.kraus.bysjdemo01.Model.Conversation;
import com.yolo.kraus.bysjdemo01.Model.FriendshipConversation;
import com.yolo.kraus.bysjdemo01.Model.GroupManageConversation;
import com.yolo.kraus.bysjdemo01.Model.MessageFactory;
import com.yolo.kraus.bysjdemo01.Model.NomalConversation;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.viewfeatures.ConversationView;
import com.yolo.kraus.bysjdemo01.viewfeatures.FriendshipMessageView;
import com.yolo.kraus.bysjdemo01.viewfeatures.GroupManageMessageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kraus on 2018/4/25.
 */

public class ConversationFragment extends Fragment implements ConversationView,FriendshipMessageView,GroupManageMessageView {

    private View view;
    private List<Conversation> conversationList = new LinkedList<>();//存储当前所有会话
    private ConversationAdapter adapter; // 会话界面消息展示适配器
    private ListView listView;//显示会话的控件实例
    private ConversationLogic mLogic;//会话窗口业务逻辑
    private FriendshipManagerLogic friendshipManagerLogic;//关系链会话逻辑
    private GroupManagerLogic groupManagerLogic;//群组会话逻辑
    private List<String> groupList;//总列表
    private FriendshipConversation friendshipConversation;//好友对话
    private GroupManageConversation groupManageConversation;//群组对话

    public ConversationFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null)
        {
            view = inflater.inflate(R.layout.activity_conversation,container,false);
            listView = view.findViewById(R.id.list);
            adapter = new ConversationAdapter(getActivity(),R.layout.item_conversation,conversationList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //跳转到聊天界面
                    conversationList.get(position).navToDetail(getActivity());
                    //判断当前点击view是否为群组聊天view
                    if (conversationList.get(position) instanceof GroupManageConversation)
                    {
                        groupManagerLogic.getGroupManageLastMessage();
                    }
                }
            });
            friendshipManagerLogic = new FriendshipManagerLogic(this);
            groupManagerLogic = new GroupManagerLogic(this);
            mLogic = new ConversationLogic(this);
            mLogic.getConversation();
            //为会话list注册上下文菜单
            registerForContextMenu(listView);

        }
        adapter.notifyDataSetChanged();
        //注册强制刷新界面api

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Conversation conversation = conversationList.get(info.position);
        //为当前选中的item增加id
        if (conversation instanceof NomalConversation)
        {
            menu.add(0, 1, Menu.NONE, getString(R.string.conversation_del));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        NomalConversation conversation = (NomalConversation) conversationList.get(info.position);
        switch (item.getItemId())
        {
            case 1:
                if (conversation != null)
                {
                    if (mLogic.delConversation(conversation.getType(), conversation.getIdentify()))
                    {
                        conversationList.remove(conversation);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }



    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }


    /**
     * 初始化界面或刷新界面
     *
     * @param conversationList
     */
    @Override
    public void initView(List<TIMConversation> conversationList) {
        this.conversationList.clear();
        groupList = new ArrayList<>();
        for(TIMConversation item : conversationList)
        {
            switch (item.getType())
            {
                case C2C:
                case Group:
                    this.conversationList.add(new NomalConversation(item));
                    groupList.add(item.getPeer());
                    break;
            }
        }
        friendshipManagerLogic.getFriendshipLastMessage();
        groupManagerLogic.getGroupManageLastMessage();
    }

    /**
     * 更新最新消息显示
     *
     * @param message 最后一条消息
     */
    @Override
    public void updateMessage(TIMMessage message) {
        //无新消息
        if (message == null)
        {
            adapter.notifyDataSetChanged();
            return;
        }
        //新的系统消息
        if (message.getConversation().getType() == TIMConversationType.System)
        {
            groupManagerLogic.getGroupManageLastMessage();
            return;
        }
//        if (MessageFactory.getMessage(message) instanceof CustomMessage) return;
        //新的好友或者群聊消息
        NomalConversation conversation = new NomalConversation(message.getConversation());//获取当前消息的会话
        //通过迭代器判断消息是否已经存在list中
        Iterator<Conversation> iterator =conversationList.iterator();
        while (iterator.hasNext())
        {
            Conversation c = iterator.next();
            //存在则移除该消息
            if (conversation.equals(c))
            {
                conversation = (NomalConversation) c;
                iterator.remove();
                break;
            }
        }
        //将当前消息设置最新
        conversation.setLastMessage(MessageFactory.getMessage(message));
        //添加消息
        conversationList.add(conversation);
        //对消息进行排序
        Collections.sort(conversationList);
        //刷新界面
        refresh();

    }

    /**
     * 更新好友关系链消息
     */
    @Override
    public void updateFriendshipMessage() {
        friendshipManagerLogic.getFriendshipLastMessage();

    }

    /**
     * 删除会话
     *
     * @param identify
     */
    @Override
    public void removeConversation(String identify) {
        Iterator<Conversation> iterator = conversationList.iterator();
        while(iterator.hasNext())
        {
            Conversation conversation = iterator.next();
            if (conversation.getIdentify()!=null && conversation.getIdentify().equals(identify))
            {
                iterator.remove();
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    /**
     * 更新群信息
     *
     * @param info
     */
    @Override
    public void updateGroupInfo(TIMGroupCacheInfo info) {
        for (Conversation conversation : conversationList)
        {
            //会话存在判断 会话类型判断
            if (conversation.getIdentify()!=null && conversation.getIdentify().equals(info.getGroupInfo().getGroupId()))
            {
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }


    /**
     * 获取群管理最后一条系统消息的回调
     *
     * @param message     最后一条消息
     * @param unreadCount 未读数
     */
    @Override
    public void onGetGroupManageLastMessage(TIMGroupPendencyItem message, long unreadCount) {
        if (groupManageConversation == null)
        {
            groupManageConversation = new GroupManageConversation(message);
            conversationList.add(groupManageConversation);
        }
        else
        {
            groupManageConversation.setLastMessage(message);
        }
        groupManageConversation.setUnreadCount(unreadCount);
        Collections.sort(conversationList);
        refresh();
    }

    /**
     * 获取群管理系统消息，的回调
     *
     * @param message 分页的消息列表
     */
    @Override
    public void onGetGroupManageMessage(List<TIMGroupPendencyItem> message) {
            groupManagerLogic.getGroupManageMessage(5);
    }

    /**
     * 获取好友关系链管理系统最后一条消息，的回调
     *
     * @param message 最后一条消息
     * @param unreadCount 未读数
     */
    @Override
    public void onGetFriendshipLastMessage(TIMFriendFutureItem message, long unreadCount) {
        if (friendshipConversation == null)
        {
            friendshipConversation = new FriendshipConversation(message);
            conversationList.add(friendshipConversation);
        }
        else
        {
            friendshipConversation.setLastMessage(message);
        }
        friendshipConversation.setUnreadCount(unreadCount);
        Collections.sort(conversationList);
        refresh();
    }

    /**
     * 获取好友关系链管理最后一条系统消息，的回调
     *
     * @param message 消息列表
     */
    @Override
    public void onGetFriendshipMessage(List<TIMFriendFutureItem> message) {
        friendshipManagerLogic.getFriendshipLastMessage();
    }


    /**
     * 刷新
     */
    @Override
    public void refresh() {
        Collections.sort(conversationList);
        //刷新界面的核心代码，通知会话界面适配器更新数据->更新UI
        adapter.notifyDataSetChanged();

        if (getActivity() instanceof  HomeActivity)
            ((HomeActivity) getActivity()).setMsgUnread(getTotalUnreadNum() == 0);
    }

    /**
     *
     * @return 返回会话窗口未读消息的总数
     */
    private long getTotalUnreadNum(){
        long num = 0;
        for (Conversation conversation : conversationList){
            num += conversation.getUnreadNum();
        }
        return num;
    }
}
