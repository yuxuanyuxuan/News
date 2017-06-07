package com.example.a17282.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.a17282.news.R;
import com.example.a17282.news.bean.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuxuan on 2017/5/20.
 */

public class PhotoAdapter extends RecyclerView.Adapter<MyViewHolder>{
    public List<Photo> mPhotos = new ArrayList<>();
    private List<Integer> mHeights;
    private Context context;
     public PhotoAdapter(Context context) {

        mHeights = new ArrayList<>();
         this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_image,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(itemView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        if(mHeights.size() <=position) {
            mHeights.add((int)(300+Math.random()*400));

        }
        //设置图片高度
        ViewGroup.LayoutParams layoutParams = holder.mImage.getLayoutParams();
        layoutParams.height = mHeights.get(position);
        holder.mImage.setLayoutParams(layoutParams);

        Photo photo = mPhotos.get(position);
        Glide.with(context).load(photo.getImageUrl()).into(holder.mImage);




    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }



}
