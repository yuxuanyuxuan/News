package com.example.a17282.news.bean;

/**
 * Created by Yuxuan on 2017/6/3.
 */

public class EventMessage {
    private String msg;
    private String head;

    public String getHead() {
        return head;
    }

    public String getMsg() {
        return msg;
    }

    public EventMessage(String msg){
        this.msg = msg;
    }

}
