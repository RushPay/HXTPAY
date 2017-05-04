package com.hxtao.qmd.hxtpay.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.been.FriendLetterListBeen;
import com.hxtao.qmd.hxtpay.been.PersonInfo;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import static com.hxtao.qmd.hxtpay.utils.HXTUrl.HXTHTTP_DELFRIEND;

/**
 * @Description:
 * @Author: Cyf on 2016/12/27.
 */

public class LetterListAdapter extends BaseAdapter {

    private Context context;
    private List<FriendLetterListBeen> infoDataList;

    private boolean showFirst = false;
    private InfoListAdapter infoListAdapter=null;
    public LetterListAdapter(Context context, List<FriendLetterListBeen> infoDataList) {
        this.context = context;
        this.infoDataList = infoDataList;


    }

    @Override
    public int getCount() {

        return infoDataList == null ? 0 : infoDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LetterViewHolder letterViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itme_friend_letter_layout, parent, false);
            letterViewHolder = new LetterViewHolder();
            letterViewHolder.letterTv = (TextView) convertView.findViewById(R.id.itme_letter_tv);
            letterViewHolder.infoListView = (ListView) convertView.findViewById(R.id.itme_info_lv);
            convertView.setTag(letterViewHolder);
        } else {
            letterViewHolder = (LetterViewHolder) convertView.getTag();
        }
        FriendLetterListBeen letterListBeen = infoDataList.get(position);
        String letter =letterListBeen.getLeter();
        final List<PersonInfo> infoBeenList = letterListBeen.getPersonInfoList();
        if (!TextUtils.isEmpty(letter)){
            letterViewHolder.letterTv.setVisibility(View.VISIBLE);
            letterViewHolder.letterTv.setText(letter);
        }
        if (infoBeenList!=null&&infoBeenList.size()>0){
            letterViewHolder.infoListView.setVisibility(View.VISIBLE);
            infoListAdapter=new InfoListAdapter(context, infoBeenList);
            letterViewHolder.infoListView.setAdapter(infoListAdapter);
            setListViewHeightBasedOnChildren(letterViewHolder.infoListView );

//            itme长按点击监听 删除好友
            letterViewHolder.infoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    PersonInfo personInfo = infoBeenList.get(position);
                    final String deleteId = personInfo.getId();
//                    Log.e("onItmLongClick",deleteId);
                    new AlertDialog.Builder(context)
                            .setTitle("             是否确认删除好友")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteFriend(deleteId);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                    return false;
                }
            });
        }

        return convertView;
    }

    static class LetterViewHolder {
        public TextView letterTv;
        public ListView infoListView;
    }

        /**
         * @param  listview
         * 此方法是本次listview嵌套listview的核心方法：计算parentlistview item的高度。
         * 如果不使用此方法，无论innerlistview有多少个item，则只会显示一个item。
         **/

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

    private void deleteFriend(String deleteId){
        RequestParams requestParams=new RequestParams(HXTHTTP_DELFRIEND);
        requestParams.addBodyParameter("sign", BaseApplication.getInstance().getSign());
        requestParams.addBodyParameter("fid",deleteId);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                if (addFriendResult.getStatus()==1){
                    ToastUtil.createToastConfig().ToastShow(context,addFriendResult.getInfo(),5000);
                }else {
                    ToastUtil.createToastConfig().ToastShow(context,addFriendResult.getInfo(),5000);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(context,"网络异常请检查网络",5000);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}

