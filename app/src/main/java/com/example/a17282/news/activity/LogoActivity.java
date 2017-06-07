package com.example.a17282.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.a17282.news.R;

/**
 * Created by 17282 on 2017/4/14.
 */

public class LogoActivity extends Activity{

    private ImageView iv_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        iv_logo = (ImageView)findViewById(R.id.iv_logo);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.logo_anim);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    Intent intent = new Intent(LogoActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        iv_logo.startAnimation(anim);



    }
}
