package com.example.a17282.news.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.a17282.news.bean.Comment;
import com.example.a17282.news.bean.Title;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuxuan on 2017/5/24.
 */

public class MyDBDao {
    private Context context;
    private MyDBHelper myDBHelper;
    private String[] ORDER_COLUMNS = {"Id","Title","Description","ImageUrl","Url"};
    private String[] ORDER_COLUMNS2 = {"Id","Comment","ImageUrl","TitleContent","Url"};
    public MyDBDao(Context context){
        this.context = context;
        myDBHelper = new MyDBHelper(context);

    }
    //插入新闻
    public void insertNews(Title title) {
        SQLiteDatabase db = null;
//        Id integer primary key autoincrement,Title text,Description text,ImageUrl text,Url text

        db = myDBHelper.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title",title.getTitle());
        contentValues.put("Description",title.getDescription());
        contentValues.put("ImageUrl",title.getImageUrl());
        contentValues.put("Url",title.getUrl());

        db.insertOrThrow(MyDBHelper.TABLE_NAME,null,contentValues);

        db.setTransactionSuccessful();

        if(db !=null) {
            db.endTransaction();
            db.close();
        }
    }
    public void insertComments(String comment,String imageUrl,String titleContent,String url) {
        SQLiteDatabase db = null;
        db = myDBHelper.getWritableDatabase();
//        Comment text,ImageUrl text,TitleContent text,Url text
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Comment",comment);
        contentValues.put("ImageUrl",imageUrl);
        contentValues.put("TitleContent",titleContent);
        contentValues.put("Url",url);

        db.insertOrThrow(MyDBHelper.TABLE_NAME2,null,contentValues);
        db.setTransactionSuccessful();

        if(db != null) {
            db.endTransaction();
            db.close();
        }

    }

    //获得所有新闻
    public Title getAllNews(TitleAdapter adapter){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        //是reader？？？
        db = myDBHelper.getWritableDatabase();
        cursor = db.query(MyDBHelper.TABLE_NAME,ORDER_COLUMNS,null,null,null,null,null);

        if(cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Title parseTitle = parseTitle(cursor);
                adapter.titleList.add(parseTitle);

            }


        }
        if(cursor !=null){
            cursor.close();
        }
        if(db !=null) {
            db.close();
        }
        return null;
    }
    public Comment getAllComment(CommentAdapter adapter) {
        SQLiteDatabase db= null;
        Cursor cursor = null;
        db = myDBHelper.getWritableDatabase();
        cursor = db.query(MyDBHelper.TABLE_NAME2,ORDER_COLUMNS2,null,null,null,null,null);

        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Comment comment = parseComment(cursor);
                adapter.commentList.add(comment);

            }
        }
        if(cursor !=null) {
            cursor.close();
        }
        if(db !=null) {
            db.close();

        }
        return null;



    }

    private Comment parseComment(Cursor cursor) {
        //        Comment text,ImageUrl text,TitleContent text,Url text
        Comment comment = new Comment();
        comment.comment = (cursor.getString(cursor.getColumnIndex("Comment")));
        comment.imageUrl = (cursor.getString(cursor.getColumnIndex("ImageUrl")));
        comment.titleContext = (cursor.getString(cursor.getColumnIndex("TitleContent")));
        comment.url = (cursor.getString(cursor.getColumnIndex("Url")));
        return comment;


    }


    private Title parseTitle(Cursor cursor){
        Title title = new Title();
        title.title = (cursor.getString(cursor.getColumnIndex("Title")));
        title.description = (cursor.getString(cursor.getColumnIndex("Description")));
        title.imageUrl = (cursor.getString(cursor.getColumnIndex("ImageUrl")));
        title.url = (cursor.getString(cursor.getColumnIndex("Url")));
        return title;
    }


}
