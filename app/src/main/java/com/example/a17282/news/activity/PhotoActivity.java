package com.example.a17282.news.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.a17282.news.R;
import com.example.a17282.news.adapter.PhotoAdapter;
import com.example.a17282.news.bean.Photo;
import com.example.a17282.news.gson.Image;
import com.example.a17282.news.gson.News;
import com.example.a17282.news.util.GsonUtil;

import org.json.JSONObject;

/**
 * Created by Yuxuan on 2017/5/20.
 */

public class PhotoActivity extends AppCompatActivity {
    private PhotoAdapter mAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_layout);
        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back2);


        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setTitle("相册");



        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        mAdapter = new PhotoAdapter(this);

        getPhoto();
        recyclerView.setAdapter(mAdapter);



        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayoutPhoto);

        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.darker_gray);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //to be continue
                        getPhoto();
                        // listView.setSelection(0);

                        Toast.makeText(PhotoActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();

                        refreshLayout.setRefreshing(false);
                    }
                }, 1200);
            }
        });


    }
    public void getPhoto() {
        RequestQueue mQueue = Volley.newRequestQueue(this);
//https://api.tianapi.com/social/?key=339a8b166f397f008236e596616a5f54&num=25&rand=1
        JsonObjectRequest request = new JsonObjectRequest("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String jsonData = response.toString();
                Image images = GsonUtil.parseJsonWithGson(jsonData, Image.class);

//                for(News.NewsList newsList   :news.newsList) {
//                    Photo photo = new Photo(newsList.picUrl);
//                    mAdapter.mPhotos.add(photo);
//                    mAdapter.notifyDataSetChanged();
//                }
                mAdapter.mPhotos.clear();
                for(int i =0;i<images.results.size();i++) {
                    Image.ResultsBean resultsBean = images.results.get(i);
                    Photo photo = new Photo(resultsBean.getUrl());
                    mAdapter.mPhotos.add(photo);
                    mAdapter.notifyDataSetChanged();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

}

