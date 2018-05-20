package com.yolo.kraus.bysjdemo01.MAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yolo.kraus.bysjdemo01.Model.Message;
import com.yolo.kraus.bysjdemo01.R;

import java.util.List;

/**
 * Created by Kraus on 2018/5/7.
 */

public class ChatAdapter extends ArrayAdapter<Message> {

    private final String TAG = ChatAdapter.class.getSimpleName();

    private int resourceId;
    private View view;
    private ViewHolder viewHolder;

    //确保listview不重绘没有改变部分
    @Override
    public boolean hasStableIds() {
//        return super.hasStableIds();
        return true;
    }


    /**
     *将hasStableIds()方法重写为true后必须保证每个view都有唯一id
     *
     * @param position
     * @return
     *
     */
    @Override
    public long getItemId(int position) {
//        if(view!=null)
//        {
//            return view.getId();
//        }
//        else
//            return position;
        return view != null ? view.getId() : position;
    }



    public ChatAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
        //todo 绑定资源
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        //重用converview 提高listview性能
        if (convertView != null)
        {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        else
        {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.leftMessage = view.findViewById(R.id.leftMessage);
            viewHolder.rightMessage = view.findViewById(R.id.rightMessage);
            viewHolder.leftPanel = view.findViewById(R.id.leftPanel);
            viewHolder.rightPanel = view.findViewById(R.id.rightPanel);
            viewHolder.sending = view.findViewById(R.id.sending);
            viewHolder.error = view.findViewById(R.id.sendError);
            viewHolder.sender = view.findViewById(R.id.sender);
            viewHolder.rightDesc = view.findViewById(R.id.rightDesc);
            viewHolder.systemMessage = view.findViewById(R.id.systemMessage);
            view.setTag(viewHolder);
        }

        if(position < getCount())
        {
            final Message data = getItem(position);
            data.showMessage(viewHolder,getContext());
        }

        return view;

    }

    public class ViewHolder{
        public RelativeLayout leftMessage;
        public RelativeLayout rightMessage;
        public RelativeLayout leftPanel;
        public RelativeLayout rightPanel;
        public ProgressBar sending;
        public ImageView error;
        public TextView sender;
        public TextView systemMessage;
        public TextView rightDesc;
    }
}
