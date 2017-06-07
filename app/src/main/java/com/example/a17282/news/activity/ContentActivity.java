package com.example.a17282.news.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a17282.news.R;
import com.example.a17282.news.adapter.MyDBDao;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yuxuan on 2017/5/17.
 */

public class ContentActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.bt_sendComment)
    Button btSendComment;

    private MyDBDao myDBDao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back2);
        String title = getIntent().getStringExtra("Title");
        supportActionBar.setTitle(title);


        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        final String url = getIntent().getStringExtra("Url");
        final String imageUrl = getIntent().getStringExtra("ImageUrl");
        final String titleContent = getIntent().getStringExtra("TitleContent");
        webView.loadUrl(url);


        myDBDao = new MyDBDao(this);

        btSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //很假这一句要放进来
                String comment = etComment.getText().toString();
                myDBDao.insertComments(comment, imageUrl, titleContent, url);
                Toast.makeText(ContentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
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
