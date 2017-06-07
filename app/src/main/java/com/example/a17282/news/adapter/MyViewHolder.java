package com.example.a17282.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.a17282.news.R;

/**
 * Created by Yuxuan on 2017/5/20.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView mImage;

    public MyViewHolder(View itemView) {
        super(itemView);
        mImage = (ImageView)itemView.findViewById(R.id.image_photo_baidu);

    }


}