package com.example.a17282.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.a17282.news.R;
import com.example.a17282.news.adapter.MyPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17282 on 2017/4/14.
 */

public class LeadActivity extends Activity {

    private ViewPager viewPager;
    private MyPageAdapter adapter;
    private List<View> views = new ArrayList<View>();
    private ImageView[] points = new ImageView[3];
    private boolean isFirstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        SharedPreferences preferences = getSharedPreferences("four", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        isFirstRun = preferences.getBoolean("isFirstRun", true);
        //不是第一次进，直接进LOGO界面
        if(!isFirstRun){
            Intent intent = new Intent(LeadActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }




        initViews();
        initData();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                setPoint(arg0);

                if(arg0 >=2){
                    Intent intent = new Intent(LeadActivity.this,LogoActivity.class);
                    startActivity(intent);
                    finish();
                    //第一次进 设置为 false
                    editor.putBoolean("isFirstRun", false);
                    editor.commit();


                }

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }


    private void initData() {
        // TODO Auto-generated method stub
        views = new ArrayList<View>();
        View v1 =LayoutInflater.from(this).inflate(R.layout.activity_lead_item1, null);
        View v2 =LayoutInflater.from(this).inflate(R.layout.activity_lead_item2, null);
        View v3 =LayoutInflater.from(this).inflate(R.layout.activity_lead_item3, null);

        views.add(v1);
        views.add(v2);
        views.add(v3);

        adapter = new MyPageAdapter(views);





    }

    private void initViews() {
        // TODO Auto-generated method stub
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        points[0] = (ImageView)findViewById(R.id.iv_point1);
        points[1] = (ImageView)findViewById(R.id.iv_point2);
        points[2] = (ImageView)findViewById(R.id.iv_point3);


        setPoint(0);

    }

    private void setPoint(int index) {
        // TODO Auto-generated method stub

        for(int i =0;i<points.length;i++){
            if(i == index) {
                points[i].setVisibility(View.VISIBLE);

            }else{
                points[i].setVisibility(View.INVISIBLE);
            }

        }



    }

}
