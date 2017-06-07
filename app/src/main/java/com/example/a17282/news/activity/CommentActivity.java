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
import com.example.a17282.news.adapter.CommentAdapter;
import com.example.a17282.news.adapter.MyDBDao;
import com.example.a17282.news.bean.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yuxuan on 2017/5/26.
 */

public class CommentActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv_comment)
    ListView lvComment;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    private CommentAdapter mAdapter;
    private MyDBDao myDBDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back2);

        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setTitle("评论");
        myDBDao = new MyDBDao(this);
        mAdapter = new CommentAdapter(this);
        myDBDao.getAllComment(mAdapter);
        lvComment.setAdapter(mAdapter);
        lvComment.setEmptyView(tvEmpty);

        lvComment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comment comment = mAdapter.commentList.get(position);
                Intent intent = new Intent(CommentActivity.this, ContentActivity.class);
                intent.putExtra("Url", comment.url);
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
