package com.example.a17282.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a17282.news.R;
import com.example.a17282.news.bean.EventMessage;

import java.io.ByteArrayOutputStream;

import de.greenrobot.event.EventBus;

/**
 * Created by Boyoo on 2017/5/9.
 */

public class LoginActivity extends Activity {

    private TextView tv_sign;
    private EditText et_username;
    private EditText et_password;
    private Button bt_login;
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("user2",MODE_PRIVATE);
        edit = preferences.edit();

        initView();
        initClick();
//        initAdmin();

    }

    private void initClick() {
        //注册
        tv_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

        //登录
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                String savedUsername = preferences.getString("username","");
                String savedPassword = preferences.getString("password","");

                if(username.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this,"邮箱或密码为空",Toast.LENGTH_SHORT).show();
                }else if(username.equals(savedUsername) && password.equals(savedPassword)) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(LoginActivity.this, UserHubActivity.class);
                    intent.putExtra("username",savedUsername);
                    startActivity(intent);
                    //EventBus
//                    EventBus.getDefault().post(new EventMessage(username));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"邮箱或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        

    }

    private void initView() {
        tv_sign = (TextView)findViewById(R.id.tv_sign);
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
        bt_login = (Button)findViewById(R.id.bt_login);




    }
    private void initAdmin() {

        edit.putString("username","admin@gmail.com");
        edit.putString("password","123456");

        edit.commit();



    }
}
