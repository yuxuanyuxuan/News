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
import com.example.a17282.news.bean.Title;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuxuan on 2017/5/17.
 */

public class TitleAdapter extends BaseAdapter {
    private Context context;
    public List<Title> titleList = new ArrayList<>();
    public TitleAdapter(Context context) {
        this.context = context;

    }
    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public Object getItem(int position) {
        return titleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder = null;
        Title title = titleList.get(position);
        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_title,null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.tv_bigSize);
            viewHolder.description =(TextView)view.findViewById(R.id.tv_smallSize);
            viewHolder.picture = (ImageView) view.findViewById(R.id.iv_photo);
            view.setTag(viewHolder);

        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(title.getTitle());
        viewHolder.description.setText(title.getDescription());
        Glide.with(context).load(title.getImageUrl()).into(viewHolder.picture);

        return view;
    }

    private class ViewHolder {
        TextView title;
        TextView description;
        ImageView picture;
    }
}
