package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.been.PartyNumber;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Description:
 * @Author: Cyf on 2017/1/5.
 */

public class TransferAdapter extends BaseAdapter {
    private Context context;
    private List<PartyNumber.NumberInfo> numberInfoList;

    public TransferAdapter(Context context, List<PartyNumber.NumberInfo> numberInfoList) {
        this.context = context;
        this.numberInfoList = numberInfoList;
    }

    @Override
    public int getCount() {
        return numberInfoList==null?0:numberInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return numberInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TransferViewHolder transferViewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.itme_trabsfer_layout,parent,false);
            transferViewHolder=new TransferViewHolder();

            transferViewHolder.iconImg= (CircleImageView) convertView.findViewById(R.id.icon_item_transfer);
            transferViewHolder.nameTv= (TextView) convertView.findViewById(R.id.name_itme_transfer);
            transferViewHolder.checkBox= (CheckBox) convertView.findViewById(R.id.check_itme_transfer);

            convertView.setTag(transferViewHolder);
        }else {
            transferViewHolder= (TransferViewHolder) convertView.getTag();
        }
        PartyNumber.NumberInfo numberInfo = numberInfoList.get(position);
        ImageLoadUtil.disPlayImage(numberInfo.getIcon(),transferViewHolder.iconImg);
//        Picasso.with(context).load(numberInfo.getIcon()).into(transferViewHolder.iconImg);
        transferViewHolder.nameTv.setText(numberInfo.getUsername());
        transferViewHolder.checkBox.setChecked(numberInfo.isChoose());

        return convertView;
    }

    static class TransferViewHolder{
        public CircleImageView iconImg;
        public TextView nameTv;
        public CheckBox checkBox;    }
}
