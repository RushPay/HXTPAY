package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.been.QueryUserBeen;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/27.
 */

public class QueryUserAdapter extends BaseAdapter {

    private Context context;
    private List<QueryUserBeen.DataBean> list;

    public QueryUserAdapter(Context context, List<QueryUserBeen.DataBean> list) {
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
        final QueryViewHolder queryViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itme_query_layout, parent, false);
            queryViewHolder = new QueryViewHolder();
            queryViewHolder.iconImg = (ImageView) convertView.findViewById(R.id.icon_query_img);
            queryViewHolder.nameTv = (TextView) convertView.findViewById(R.id.name_query_tv);
            queryViewHolder.accountTv = (TextView) convertView.findViewById(R.id.account_query_tv);
            queryViewHolder.acceptBtn = (Button) convertView.findViewById(R.id.accept_query_btn);

            convertView.setTag(queryViewHolder);
        } else {
            queryViewHolder = (QueryViewHolder) convertView.getTag();
        }
        QueryUserBeen.DataBean dataBean = list.get(position);
        final String id = dataBean.getId();
        String username = dataBean.getUsername();
        String account = dataBean.getAccount();
        String icon = dataBean.getIcon();
        if (username != null) {
            queryViewHolder.nameTv.setText(username.trim());
        }
        if (account != null) {
            queryViewHolder.accountTv.setText(account.trim());
        }
        if (icon != null) {
            ImageLoadUtil.disPlayImage(icon,queryViewHolder.iconImg);

//            Picasso.with(context).load(icon).into(queryViewHolder.iconImg);
        }
        queryViewHolder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtil.createToastConfig().ToastShow(context,id,15000);
                //确认添加好友

                RequestParams requestParams=new RequestParams(HXTUrl.HXTHTTP_ADDFRIEND);
                requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
                requestParams.addBodyParameter("fid",id);

                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("onSuccess",result);
                        Gson gson=new Gson();
                        AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                        int status = addFriendResult.getStatus();
                        if (status==1){
                            ToastUtil.createToastConfig().ToastShow(context,addFriendResult.getInfo(),1500);
                            queryViewHolder.acceptBtn.setText("已添加");
                            queryViewHolder.acceptBtn.setEnabled(false);
                        }else {
                            ToastUtil.createToastConfig().ToastShow(context,addFriendResult.getInfo(),1500);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e("onError",ex.toString());
                        ToastUtil.createToastConfig().ToastShow(context,"网络异常请检查网络",15000);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });

            }
        });
        return convertView;
    }

    static class QueryViewHolder {

        public ImageView iconImg;
        public TextView nameTv;
        public TextView accountTv;
        public Button acceptBtn;

    }

}
