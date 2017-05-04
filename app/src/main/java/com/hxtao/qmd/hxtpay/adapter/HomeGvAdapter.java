package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hxtao.qmd.hxtpay.R;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/23.
 */

public class HomeGvAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Bitmap> list;
    public HomeGvAdapter(Context context, List<Bitmap> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeViewHolder homeViewHolder;

        if (convertView==null){
            convertView=inflater.inflate(R.layout.itme_home_gv,parent,false);
            homeViewHolder=new HomeViewHolder();
            homeViewHolder.imageView= (ImageView) convertView.findViewById(R.id.itme_home_img);
            convertView.setTag(homeViewHolder);
        }else {
            homeViewHolder= (HomeViewHolder) convertView.getTag();
        }

        homeViewHolder.imageView.setImageBitmap(list.get(position));
        return convertView;
    }

    static class HomeViewHolder{
        ImageView imageView;
    }

}
