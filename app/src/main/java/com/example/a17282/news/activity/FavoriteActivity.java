package com.example.a17282.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a17282.news.R;
import com.example.a17282.news.adapter.MyDBDao;
import com.example.a17282.news.adapter.TitleAdapter;
import com.example.a17282.news.bean.Title;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Yuxuan on 2017/5/24.
 */

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.lv_favorite)
    ListView lvFavorite;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    private TitleAdapter mAdapter;
    private MyDBDao myDBDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back2);

        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setTitle("收藏");
        ButterKnife.bind(this);
        myDBDao = new MyDBDao(this);

        mAdapter = new TitleAdapter(this);
        myDBDao.getAllNews(mAdapter);
        lvFavorite.setAdapter(mAdapter);
        lvFavorite.setEmptyView(tvEmpty);
        lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Title title = mAdapter.titleList.get(position);

                Intent intent = new Intent(FavoriteActivity.this, ContentActivity.class);
                intent.putExtra("Url", title.getUrl());
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
