package com.example.a17282.news.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.a17282.news.R;
import com.example.a17282.news.activity.ContentActivity;
import com.example.a17282.news.adapter.MyDBDao;
import com.example.a17282.news.adapter.TitleAdapter;
import com.example.a17282.news.bean.Title;
import com.example.a17282.news.gson.News;

import org.json.JSONObject;

import com.example.a17282.news.util.GsonUtil;

import java.util.List;


/**
 * Created by 17282 on 2017/4/19.
 */

public class LeftFragment extends Fragment {


    private ListView listView;
    private SwipeRefreshLayout mSwipeRefresh;
    private View view;
    private TitleAdapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    private MyDBDao myDBDao;


    @Nullable
    @Override

    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.left_fragment, null);
        myDBDao = new MyDBDao(getActivity());
        initView();
        initAction();



        return view;
    }



    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        listView = (ListView) view.findViewById(R.id.listView);
        mAdapter = new TitleAdapter(getActivity());
        listView.setAdapter(mAdapter);


    }
    private void initAction() {

            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.darker_gray);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(true);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //to be continue
                            getNews();
                           // listView.setSelection(0);

                            Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();

                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 1200);
                }
            });
            getNews();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Title title = mAdapter.titleList.get(position);

                    Intent intent = new Intent(getActivity(),ContentActivity.class);
                    intent.putExtra("Url",title.getUrl());
                    intent.putExtra("Title","新闻");
                    intent.putExtra("ImageUrl",title.getImageUrl());
                    intent.putExtra("TitleContent",title.getTitle());
                    startActivity(intent);

                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("加入收藏");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Title title = mAdapter.titleList.get(position);
                            myDBDao.insertNews(title);

                        }
                    });
                    builder.setNegativeButton("否",null);
                    builder.show();

                    return true;
                }
            });



        }




    public void getNews() {
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest request = new JsonObjectRequest("https://api.tianapi.com/social/?key=339a8b166f397f008236e596616a5f54&num=25&rand=1"
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String jsonData = response.toString();
                News news = GsonUtil.parseJsonWithGson(jsonData, News.class);
                int code = news.code;
                if(code == 200) {
                   mAdapter.titleList.clear();
                    for (News.NewsList newsList : news.newsList) {
                        Title title = new Title(newsList.title, newsList.description, newsList.picUrl, newsList.url);
                        mAdapter.titleList.add(title);
                        mAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"出错了",Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(request);


    }




}
