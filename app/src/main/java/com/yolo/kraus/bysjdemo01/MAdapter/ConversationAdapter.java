package com.yolo.kraus.bysjdemo01.MAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yolo.kraus.bysjdemo01.Model.Conversation;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.Util.TimeUtil;
import com.yolo.kraus.ui.CircleImageView;

import java.util.List;


/**
 * Created by Kraus on 2018/5/4.
 */

public class ConversationAdapter extends ArrayAdapter<Conversation>{

    private int resourceID;
    private View view;
    private ViewHolder viewHolder;



    public ConversationAdapter(@NonNull Context context, int resource, @NonNull List<Conversation> objects) {
        super(context, resource, objects);
        resourceID =resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        if(convertView!=null)
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        else
        {
            view = LayoutInflater.from(getContext()).inflate(resourceID,null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = view.findViewById(R.id.name);
            viewHolder.avatar = view.findViewById(R.id.avatar);
            viewHolder.lastMessage = view.findViewById(R.id.last_message);
            viewHolder.time = view.findViewById(R.id.message_time);
            viewHolder.unread = view.findViewById(R.id.unread_num);
            view.setTag(viewHolder);
        }

        final  Conversation data = getItem(position);
        viewHolder.tvName.setText(data.getName());
        viewHolder.avatar.setImageResource(data.getAvatar());
        viewHolder.lastMessage.setText(data.getLastMessageSummary());
        viewHolder.time.setText(TimeUtil.getTimeStr(data.getLastMessageTime()));

        long unRead = data.getUnreadNum();
        if(unRead <= 0)
        {
            viewHolder.unread.setVisibility(View.INVISIBLE);
        }
        else
        {
            viewHolder.unread.setVisibility(View.VISIBLE);
            String unReadStr = String.valueOf(unRead);
            if(unRead<10)
            {
                viewHolder.unread.setBackground(getContext().getResources().getDrawable(R.drawable.point1));
            }
            else
            {
                viewHolder.unread.setBackground(getContext().getResources().getDrawable(R.drawable.point2));
                if (unRead > 99)
                {
                    unReadStr = getContext().getResources().getString(R.string.time_more);
                }
            }
            viewHolder.unread.setText(unReadStr);
        }


        return view;
    }

    public class ViewHolder{
        public TextView tvName;
        public CircleImageView avatar;
        public TextView lastMessage;
        public TextView time;
        public TextView unread;
    }
}
