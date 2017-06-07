package com.example.a17282.news.bean;

/**
 * Created by Yuxuan on 2017/5/26.
 */

public class Comment {
    public String comment;
    public String imageUrl;
    public String url;
    public String titleContext;

    public Comment(String comment, String imageUrl, String url, String titleContext) {
        this.comment = comment;
        this.imageUrl = imageUrl;
        this.url = url;
        this.titleContext = titleContext;
    }

    public Comment() {

    }

}
