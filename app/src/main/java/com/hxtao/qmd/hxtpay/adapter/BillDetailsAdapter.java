package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.been.PersonBillDetailsEntity;
import com.hxtao.qmd.hxtpay.utils.TimeUtils;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2017/1/19.
 */

public class BillDetailsAdapter extends BaseAdapter {

    private Context context;
    private List<PersonBillDetailsEntity.BillData> list;

    public BillDetailsAdapter(Context context, List<PersonBillDetailsEntity.BillData> list) {
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
        BillDetailsViewHolder billDetailsViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itme_bill_details_layout, parent, false);
            billDetailsViewHolder = new BillDetailsViewHolder();
            billDetailsViewHolder.titileTv = (TextView) convertView.findViewById(R.id.title_bill_details_itme_tv);
            billDetailsViewHolder.moneyTv = (TextView) convertView.findViewById(R.id.money_bill_details_itme_tv);
            billDetailsViewHolder.timeTv = (TextView) convertView.findViewById(R.id.time_bill_details_itme_tv);
            convertView.setTag(billDetailsViewHolder);
        } else {
            billDetailsViewHolder = (BillDetailsViewHolder) convertView.getTag();
        }
        PersonBillDetailsEntity.BillData billData = list.get(position);

        switch (Integer.valueOf(billData.getR_or_e())) {
            case 1://收入
                billDetailsViewHolder.titileTv.setText("存入账单");
                billDetailsViewHolder.titileTv.setTextColor(Color.BLUE);
                billDetailsViewHolder.moneyTv.setTextColor(Color.BLUE);
                billDetailsViewHolder.moneyTv.setText("+" + billData.getMoney());
                break;
            case 2://支出
                billDetailsViewHolder.titileTv.setTextColor(Color.RED);
                billDetailsViewHolder.titileTv.setText("消费支出");
                billDetailsViewHolder.moneyTv.setTextColor(Color.RED);
                billDetailsViewHolder.moneyTv.setText("-" + billData.getMoney());
                break;
        }
        billDetailsViewHolder.timeTv.setText(TimeUtils.createTimeUtils().getMinutesString(Long.valueOf(billData.getAddtime())));
        return convertView;
    }

    static class BillDetailsViewHolder {
        public TextView titileTv, moneyTv, timeTv;
    }
}
