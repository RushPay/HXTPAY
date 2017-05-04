package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.been.PersonInfo;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/27.
 */

public class InfoListAdapter extends BaseAdapter {
    private Context context;
    private List<PersonInfo> personInfoList;

    public InfoListAdapter(Context context, List<PersonInfo> personInfoList) {
        this.context = context;
        this.personInfoList = personInfoList;
    }

    @Override
    public int getCount() {
        return personInfoList==null?0:personInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return personInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InfoViewHolder infoViewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.itme_friend_info_layout,parent,false);
            infoViewHolder=new InfoViewHolder();
            infoViewHolder.iconImg= (ImageView) convertView.findViewById(R.id.itme_icon_iv);
            infoViewHolder.nameTv= (TextView) convertView.findViewById(R.id.itme_name_tv);
            infoViewHolder.phoneNum= (TextView) convertView.findViewById(R.id.itme_phonenum_tv);
            convertView.setTag(infoViewHolder);
        }else {
            infoViewHolder= (InfoViewHolder) convertView.getTag();
        }
        PersonInfo personInfo = personInfoList.get(position);
        String icon = personInfo.getIcon();
        String username = personInfo.getUsername();
        String account = personInfo.getAccount();

        if (!TextUtils.isEmpty(username)){
            infoViewHolder.nameTv.setText(username);
        }
        if (!TextUtils.isEmpty(account)){
            infoViewHolder.phoneNum.setText(account);
        }
        if (!TextUtils.isEmpty(icon)){
            ImageLoadUtil.disPlayImage(icon,infoViewHolder.iconImg);
//            Picasso.with(context).load(icon).into(infoViewHolder.iconImg);
        }

        return convertView;
    }

    static class InfoViewHolder{
        public ImageView iconImg;
        public TextView nameTv;
        public TextView phoneNum;
    }
}
