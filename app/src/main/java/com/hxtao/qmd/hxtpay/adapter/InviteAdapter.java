package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.been.PersonInfo;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Description:
 * @Author: Cyf on 2017/1/3.
 */

public class InviteAdapter extends BaseAdapter {
    public Context context;
    public List<PersonInfo> friendList;

    public Map<String,String> checkMap=new HashMap<>();

    public Map<String,Boolean> checkState=new HashMap<>();


    public Map<String, String> getCheckMap() {
        return checkMap;
    }

    public InviteAdapter(Context context, List<PersonInfo> friendList) {
        this.context = context;
        this.friendList = friendList;
    }

    @Override
    public int getCount() {
        return friendList==null?0:friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        InviteViewHolder inviteViewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.itme_invite_layout,parent,false);
            inviteViewHolder=new InviteViewHolder();

            inviteViewHolder.iconImg= (CircleImageView) convertView.findViewById(R.id.icon_item_invite);
            inviteViewHolder.nameTv= (TextView) convertView.findViewById(R.id.name_itme_invite);
            inviteViewHolder.checkBox= (CheckBox) convertView.findViewById(R.id.check_itme_invite);

            convertView.setTag(inviteViewHolder);
        }else {
            inviteViewHolder= (InviteViewHolder) convertView.getTag();
        }

        PersonInfo personInfo = friendList.get(position);
        String icon = personInfo.getIcon();
        String username = personInfo.getUsername();
        final String id = personInfo.getId();
        if (!TextUtils.isEmpty(icon)){
            ImageLoadUtil.disPlayImage(icon,inviteViewHolder.iconImg);
//            Picasso.with(context).load(icon).into(inviteViewHolder.iconImg);
        }
        if (!TextUtils.isEmpty(username)){
            inviteViewHolder.nameTv.setText(username);
        }

        if (checkState.containsKey(id)){
            inviteViewHolder.checkBox.setChecked(checkState.get(id));
        }

//        inviteViewHolder.checkBox.setChecked();
        inviteViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (!checkMap.containsKey(String.valueOf(position))){
                        checkMap.put(String.valueOf(position),id);
                    }
                    checkState.put(String.valueOf(position),isChecked);
                }else {
                    if (checkMap.containsKey(String.valueOf(position))){
                        checkMap.remove(String.valueOf(position));
                    }
                    checkState.put(String.valueOf(position),isChecked);
                }

            }
        });
        if (checkState.containsKey(String.valueOf(position))){
//            Log.e("checkState","ture");
            inviteViewHolder.checkBox.setChecked(checkState.get(String.valueOf(position)));
        }else {
            inviteViewHolder.checkBox.setChecked(false);
        }
        return convertView;
    }

    static class InviteViewHolder{
        public CircleImageView iconImg;
        public TextView nameTv;
        public CheckBox checkBox;
    }
}
