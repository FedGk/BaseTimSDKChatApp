package com.yolo.kraus.bysjdemo01.News.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yolo.kraus.bysjdemo01.GlideApp;
import com.yolo.kraus.bysjdemo01.News.model.NewsJson;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.bysjdemo01.Util.TimeStamp2Date;

public class RecyclerViewNewsAdapter extends RecyclerView.Adapter<RecyclerViewNewsAdapter.NewsHolder>{

    private Context ctx;
    private NewsJson data;

    public RecyclerViewNewsAdapter(Context context, NewsJson data)
    {
        this.ctx = context;
        this.data = data;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        NewsHolder newsHolder = new NewsHolder(v);

        return newsHolder;
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, final int position) {

        NewsJson.DataBean.NewsBean one = data.getData().getNews().get(position);

        if (one.getThumbnail_img().size()>0)
        {
            String url = (String) one.getThumbnail_img().get(0);
//            Glide.with(ctx).load(url).into(holder.newsImage);
            GlideApp.with(ctx)
                    .load(url)
                    .error(R.drawable.news_image_error)
                    .placeholder(R.drawable.news_image_error)
                    .into(holder.newsImage);
        }
        else
        {
            GlideApp.with(ctx).clear(holder.newsImage);
            holder.newsImage.setImageResource(R.drawable.news_image_error);
        }

        holder.newsTitle.setText(one.getTitle());
        holder.newsContent.setText(one.getSource());
        holder.newsTime.setText(TimeStamp2Date.TimeStamp2Date(String.valueOf(one.getGmt_publish())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ctx,""+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.getData().getCount();
    }




    class NewsHolder extends RecyclerView.ViewHolder
    {
        ImageView newsImage;
        TextView newsTitle;
        TextView newsContent;
        TextView newsTime;


        public NewsHolder(View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.news_image);
            newsContent = itemView.findViewById(R.id.news_content);
            newsTime = itemView.findViewById(R.id.news_time);
            newsTitle = itemView.findViewById(R.id.news_title);
        }
    }
}
