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
import com.hxtao.qmd.hxtpay.been.PersonEntity;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2017/1/16.
 */

public class TransferMoneyAdapter extends BaseAdapter {

    private Context context;
    private List<PersonEntity> list;

    public TransferMoneyAdapter(Context context, List<PersonEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        TransferMoneryViewHolder transferMoneryViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itme_friend_info_layout, parent, false);
            transferMoneryViewHolder = new TransferMoneryViewHolder();

            transferMoneryViewHolder.iconImg = (ImageView) convertView.findViewById(R.id.itme_icon_iv);
            transferMoneryViewHolder.nameTv = (TextView) convertView.findViewById(R.id.itme_name_tv);
            transferMoneryViewHolder.accountTv = (TextView) convertView.findViewById(R.id.itme_phonenum_tv);

            convertView.setTag(transferMoneryViewHolder);
        } else {
            transferMoneryViewHolder = (TransferMoneryViewHolder) convertView.getTag();
        }
        PersonEntity personEntity = list.get(position);
        if (!TextUtils.isEmpty(personEntity.getIcon())) {
            ImageLoadUtil.disPlayImage(personEntity.getIcon(),transferMoneryViewHolder.iconImg);
        }
        if (!TextUtils.isEmpty(personEntity.getUsername())) {
            transferMoneryViewHolder.nameTv.setText(personEntity.getUsername());
        }
        if (!TextUtils.isEmpty(personEntity.getAccount())) {
            transferMoneryViewHolder.accountTv.setText(personEntity.getAccount());
        }
        return convertView;
    }

    static class TransferMoneryViewHolder {
        public ImageView iconImg;
        public TextView nameTv, accountTv;
    }
}
