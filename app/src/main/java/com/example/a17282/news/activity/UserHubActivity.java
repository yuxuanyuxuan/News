package com.example.a17282.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;

import com.example.a17282.news.R;
import com.example.a17282.news.bean.EventMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Boyoo on 2017/5/9.
 */

public class UserHubActivity extends AppCompatActivity {


    @BindView(R.id.bt_esc)
    Button btEsc;
    private CircleImageView civ_userPortrait;
    private Bitmap head;
    private TextView tv_username;
    private String path = Environment.getExternalStorageDirectory().getPath() + "/crop/";
    private ListView lv_userContent;

    private String username;
    String fileName;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back2);

        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setTitle("用户中心");

        ButterKnife.bind(this);


        initView();
        initClick();
        username = getIntent().getStringExtra("username");
        tv_username.setText(username);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserHubActivity.this, android.R.layout.simple_list_item_1);
        String introduction = "这个人很懒什么都没留下...";
        String fans = "0";
        String follows = "0";
        adapter.add("个人简介:" + introduction);
        adapter.add("我的粉丝:" + fans);
        adapter.add("我的关注:" + follows);
        lv_userContent.setAdapter(adapter);

        getHead();



    }

    private void initClick() {
        civ_userPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);

            }
        });
        btEsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHubActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initView() {
        civ_userPortrait = (CircleImageView) findViewById(R.id.civ_userPortrait);
        tv_username = (TextView) findViewById(R.id.tv_username);
        lv_userContent = (ListView) findViewById(R.id.lv_userContent);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back2);

        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setTitle("收藏");
    }

    public void getHead() {
        SharedPreferences mySharedPreference = getSharedPreferences("user2", Activity.MODE_PRIVATE);
        //读取Base64格式的图像数据
        mySharedPreference.getString("username", username);
         String image = mySharedPreference.getString("head", "");
        if (image.equals("")) {

        } else {
            //对Base64格式的字符串进行解码，还原成字节数组
            byte[] imageBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);

            civ_userPortrait.setImageDrawable(Drawable.createFromStream(bais, "head"));

            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    cropPhoto(data.getData());
                    break;
                case 3:
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    setPicToView(head);
                    saveHead();
                    getHead();
                    break;

            }
        }

    }

    private void saveHead() {
        SharedPreferences mySharedPreference = getSharedPreferences("user2", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = mySharedPreference.edit();

        edit.putString("username", username);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // BitmapFactory.decodeResource(getResources(),R.drawable.girl1).compress(Bitmap.CompressFormat.JPEG,50,baos);
        BitmapFactory.decodeFile(fileName).compress(Bitmap.CompressFormat.JPEG, 50, baos);
        String imageBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        edit.putString("head", imageBase64);

        edit.commit();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);

    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                EventBus.getDefault().post(new EventMessage(username));
                finish();

                break;
            default:
        }
        return true;
    }
}
