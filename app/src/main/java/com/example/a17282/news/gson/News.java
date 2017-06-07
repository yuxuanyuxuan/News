package com.example.a17282.news.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yuxuan on 2017/5/17.
 */

public class News {
    public int code;
    public String msg;
    @SerializedName("newslist")
    public List<NewsList> newsList;


    public  class NewsList {
        @SerializedName("ctime")
        public String time;
        public String title;
        public String description;
        public String picUrl;
        public String url;
    }
}
