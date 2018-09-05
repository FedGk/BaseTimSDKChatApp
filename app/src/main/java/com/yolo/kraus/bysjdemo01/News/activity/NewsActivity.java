package com.yolo.kraus.bysjdemo01.News.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yolo.kraus.bysjdemo01.Activity.StartActivity;
import com.yolo.kraus.bysjdemo01.News.model.NewsJson;
import com.yolo.kraus.bysjdemo01.News.presenter.NewsPresenter;
import com.yolo.kraus.bysjdemo01.News.presenter.NewsPresenterImpl;
import com.yolo.kraus.bysjdemo01.News.view.NewsView;
import com.yolo.kraus.bysjdemo01.News.view.RecyclerViewNewsAdapter;
import com.yolo.kraus.bysjdemo01.R;
import com.yolo.kraus.ui.TemplateTitle;

/**
 * Created by Kraus on 2018/6/21.
 */

public class NewsActivity extends AppCompatActivity implements NewsView,View.OnClickListener, RecyclerViewNewsAdapter.OnItemClickListener {

    private TemplateTitle Title;

    private NewsPresenter newsPresenter;

    private RecyclerView recyclerView;

    private RecyclerViewNewsAdapter adapter;

    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newspaper);

        Title = findViewById(R.id.news_title);

        Title.setBackListener(this);
        Title.setMoreTextAction(this);

        recyclerView = findViewById(R.id.recycle_view);

        newsPresenter = new NewsPresenterImpl(this);
        newsPresenter.getNewsData();







    }




    @Override
    public void setNewsData(NewsJson datas) {
        adapter = new RecyclerViewNewsAdapter(this,datas);

        adapter.setItemClickListener(this);

        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case com.yolo.kraus.ui.R.id.title_back:
                finish();
                break;
            case com.yolo.kraus.ui.R.id.txt_more:
                Toast.makeText(this,"flash",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onItemClick(int position,String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }
}
