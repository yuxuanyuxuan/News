package com.example.a17282.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a17282.news.R;

import org.w3c.dom.Text;

/**
 * Created by Boyoo on 2017/5/9.
 */

public class SignInActivity extends Activity {
    private SharedPreferences preferences;

    private EditText et_Susername;
    private EditText et_Spassword;
    private EditText et_reSpassword;
    private Button bt_signIn;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        preferences = getSharedPreferences("user2", MODE_PRIVATE);
        edit = preferences.edit();


        initView();



        bt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_Susername.getText().toString().trim();
                String password = et_Spassword.getText().toString().trim();
                String rePassowrd = et_reSpassword.getText().toString().trim();


                if(!password.equals(rePassowrd) ) {
                    Toast.makeText(SignInActivity.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                }else if(username.equals("") || password.equals("") ||rePassowrd.equals("")){
                    Toast.makeText(SignInActivity.this,"内容不能未空",Toast.LENGTH_SHORT).show();

                }else {

                    edit.putString("username", username);
                    edit.putString("password", password);
                    edit.commit();

                    Toast.makeText(SignInActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }






            }
        });


    }



    private void initView() {
        et_Susername = (EditText)findViewById(R.id.et_Susername);
        et_Spassword = (EditText)findViewById(R.id.et_Spassword);
        et_reSpassword = (EditText)findViewById(R.id.et_reSPassword);
        bt_signIn = (Button)findViewById(R.id.bt_signIn);
    }
}
