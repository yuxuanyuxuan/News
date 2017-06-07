package com.example.a17282.news.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a17282.news.R;
import com.example.a17282.news.bean.EventMessage;
import com.example.a17282.news.fragment.LeftFragment;
import com.example.a17282.news.fragment.RightFragment;
import com.google.common.eventbus.EventBus;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NavigationView navigationView;
    private final String[] mTitle = new String[]{"社会","新闻"};
    private View drawView;
    private TextView login;
    private CircleImageView civ_portrait;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setTitle("新闻");

        de.greenrobot.event.EventBus.getDefault().register(this);
        //右下角分享按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                      .setAction("Action", null).show();
                Intent intent_shared = new Intent();
                intent_shared.setAction(Intent.ACTION_SEND);
                intent_shared.setType("text/plain");
                intent_shared.putExtra(Intent.EXTRA_SUBJECT, "我要分享");
                intent_shared.putExtra(Intent.EXTRA_TEXT, "推荐你使用newsAPP");

                intent_shared = Intent.createChooser(intent_shared, "分享");
                startActivity(intent_shared);
            }
        });
        //抽屉控件
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawView = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        Toast.makeText(MainActivity.this,"啥都没有也更新？",Toast.LENGTH_LONG).show();

        initTab();
        initLogin();
        //全屏侧拉
//      setDrawerLeftEdgeSize(this,drawer,1);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        de.greenrobot.event.EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEventMainThread(EventMessage event) {
        login.setText(event.getMsg());

        SharedPreferences mySharedPreference = getSharedPreferences("user2", Activity.MODE_PRIVATE);
        //读取Base64格式的图像数据

        mySharedPreference.getString("username", event.getMsg());
        String image = mySharedPreference.getString("head", "");
        if (image.equals("")) {

        } else {
            //对Base64格式的字符串进行解码，还原成字节数组
            byte[] imageBytes = Base64.decode(image.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);

            civ_portrait.setImageDrawable(Drawable.createFromStream(bais, "head"));

            try {
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }




    private void initLogin() {
        //设置头侧拉

        login = (TextView) drawView.findViewById(R.id.tv_login);
        login.setText("立即登录");

        civ_portrait = (CircleImageView) drawView.findViewById(R.id.civ_portrait);
        civ_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = login.getText().toString().trim();
                if(str == "立即登录") {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(MainActivity.this,UserHubActivity.class);
                    intent.putExtra("username",str);
                    startActivity(intent);
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = login.getText().toString().trim();
                if(str =="立即登录") {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(MainActivity.this,UserHubActivity.class);
                    intent.putExtra("username",str);
                    startActivity(intent);
                }

            }
        });


    }


    private void initTab() {
        //TAB 连个fragment
        mTabLayout = (TabLayout)findViewById(R.id.id_tabLayout);
        mViewPager = (ViewPager)findViewById(R.id.id_viewPager);

        final List<android.support.v4.app.Fragment> list_fragment = new ArrayList<>();

        LeftFragment leftFragment = new LeftFragment();
        RightFragment rightFragment = new RightFragment();

        list_fragment.add(leftFragment);
        list_fragment.add(rightFragment);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list_fragment.get(position);
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position];
            }
        });
        mTabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            // Handle the camera action
        } else if (id == R.id.nav_favorite) {
            Intent intent = new Intent(MainActivity.this,FavoriteActivity.class);
            startActivity(intent);

        }  else if (id == R.id.nav_comment) {
            Intent intent = new Intent(MainActivity.this,CommentActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_photo) {
            Intent intent = new Intent(MainActivity.this,PhotoActivity.class);
            startActivity(intent);



        } else if(id == R.id.nav_update){
            Bugly.init(MainActivity.this,"fc321ebaa0",false);
            Beta.checkUpgrade();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //全屏侧拉
    public static void setDrawerLeftEdgeSize(Activity activity,
                                             DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null)
            return;
        try {
// find ViewDragHelper and set it accessible
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField(
                    "mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField
                    .get(drawerLayout);
// find edgesize and set is accessible
            Field edgeSizeField = leftDragger.getClass().getDeclaredField(
                    "mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
// set new edgesize
// Point displaySize = new Point();
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize,
                    (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
// ignore
        } catch (IllegalArgumentException e) {
// ignore
        } catch (IllegalAccessException e) {
// ignore
        }
    }




}
