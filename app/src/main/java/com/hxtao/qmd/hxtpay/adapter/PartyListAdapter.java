package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.activity.PartyDetailsActivity;
import com.hxtao.qmd.hxtpay.been.PartDataListBeen;
import com.hxtao.qmd.hxtpay.been.PartyInfoBeen;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/30.
 */

public class PartyListAdapter extends BaseAdapter{
    private Context context;
    private List<PartDataListBeen> datePartyList;

    public PartyListAdapter(Context context, List<PartDataListBeen> datePartyList) {
        this.context = context;
        this.datePartyList = datePartyList;
    }

    @Override
    public int getCount() {
        return datePartyList==null?0:datePartyList.size();
    }

    @Override
    public Object getItem(int position) {
        return datePartyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PartyItmeViewHolder partyItmeViewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.itme_party_layout,parent,false);
            partyItmeViewHolder=new PartyItmeViewHolder();
            partyItmeViewHolder.dateTv= (TextView) convertView.findViewById(R.id.date_part_itme_tv);
            partyItmeViewHolder.detilsListView= (ListView) convertView.findViewById(R.id.detils_listview_part_itme_tv);

            convertView.setTag(partyItmeViewHolder);
        }else {
            partyItmeViewHolder= (PartyItmeViewHolder) convertView.getTag();
        }

        PartDataListBeen partDataListBeen = datePartyList.get(position);
        String date = partDataListBeen.getDate();

        final List<PartyInfoBeen> partDataList = partDataListBeen.getPartDataList();
        if (date!=null){
            partyItmeViewHolder.dateTv.setText(date);
        }
        if (partDataList!=null&&partDataList.size()>0){

            partyItmeViewHolder.detilsListView.setAdapter(new PartyDetilsAdapter(context,partDataList));

            //跳转聚会详情
            partyItmeViewHolder.detilsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Log.e("detilsListView",""+position);
                    //获取到party的Id
                    PartyInfoBeen partyInfoBeen = partDataList.get(position);
                    String p_id = partyInfoBeen.getId();
                    Intent intent=new Intent(context, PartyDetailsActivity.class);
                    intent.putExtra("p_id",p_id);
                    context.startActivity(intent);
                }
            });
        }
        setListViewHeightBasedOnChildren(partyItmeViewHolder.detilsListView);
        return convertView;
    }

    static class PartyItmeViewHolder{
        public TextView dateTv;// 聚会日期
        public ListView detilsListView; // 日期分组聚会列表
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {        return;    }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
