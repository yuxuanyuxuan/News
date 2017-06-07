package com.example.a17282.news.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yuxuan on 2017/5/24.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myFavoriteBook.db";
    public static final String TABLE_NAME = "FavoriteBook";
    public static final String TABLE_NAME2 = "CommentBook";
//    private String title;
//    private String description;
//    private String imageUrl;
//    private String url;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME + " (Id integer primary key autoincrement,Title text,Description text,ImageUrl text,Url text)";
        String sql2 = "create table if not exists "+ TABLE_NAME2 +" (Id integer primary key autoincrement,Comment text,ImageUrl text,TitleContent text,Url text)";
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists " + TABLE_NAME;
        String sql2 = "drop table if exists" + TABLE_NAME2;
        db.execSQL(sql);
        db.execSQL(sql2);
        onCreate(db);

    }
}
