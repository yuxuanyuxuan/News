package com.example.a17282.news.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a17282.news.R;
import com.example.a17282.news.bean.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuxuan on 2017/5/26.
 */

public class CommentAdapter extends BaseAdapter{
   public List<Comment> commentList = new ArrayList<>();
    private Context context;
    public CommentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = null;
        ViewHolder viewHolder = null;
        Comment comment = commentList.get(position);

        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_comment,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_comment = (TextView)view.findViewById(R.id.cctv_comment);
            viewHolder.tv_titleContent = (TextView)view.findViewById(R.id.ctv_title);
            viewHolder.iv_imageUrl = (ImageView)view.findViewById(R.id.ctv_imageUrl);
            view.setTag(viewHolder);

        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();


        }
        viewHolder.tv_comment.setText(comment.comment);
        viewHolder.tv_titleContent.setText(comment.titleContext);
        Glide.with(context).load(comment.imageUrl).into(viewHolder.iv_imageUrl);
        return view;
    }

    private class ViewHolder {
        TextView tv_comment;
        ImageView iv_imageUrl;
        TextView tv_titleContent;




    }
}
