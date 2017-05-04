package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.text.TextUtils;
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
import com.hxtao.qmd.hxtpay.been.AddFriendInfo;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/28.
 */

public class AddMeAdapter extends BaseAdapter {
    private Context context;
    List<AddFriendInfo.DataBean> dataInfoList;

    public AddMeAdapter(Context context, List<AddFriendInfo.DataBean> dataInfoList) {
        this.context = context;
        this.dataInfoList = dataInfoList;
    }

    @Override
    public int getCount() {
        return dataInfoList==null?0:dataInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AddMeViewHolder addMeViewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.itme_addme_layout,parent,false);
            addMeViewHolder=new AddMeViewHolder();

            addMeViewHolder.addIcon= (ImageView) convertView.findViewById(R.id.addicon_add_img);
            addMeViewHolder.addName= (TextView) convertView.findViewById(R.id.addname_add_tv);
            addMeViewHolder.addAccount= (TextView) convertView.findViewById(R.id.addaccount_add_tv);
            addMeViewHolder.addAccept= (Button) convertView.findViewById(R.id.addaccept_add_btn);
            addMeViewHolder.addDeny= (Button) convertView.findViewById(R.id.adddeny_add_btn);

            convertView.setTag(addMeViewHolder);
        }else {
            addMeViewHolder= (AddMeViewHolder) convertView.getTag();
        }

        final AddFriendInfo.DataBean dataBean = dataInfoList.get(position);
        final String id = dataBean.getId();
        if (!TextUtils.isEmpty(dataBean.getUsername())){
            addMeViewHolder.addName.setText(dataBean.getUsername());
        }
        if (!TextUtils.isEmpty(dataBean.getAccount())){
            addMeViewHolder.addAccount.setText(dataBean.getAccount());
        }
        if (!TextUtils.isEmpty(dataBean.getIcon())){
            ImageLoadUtil.disPlayImage(dataBean.getIcon(),addMeViewHolder.addIcon);
//            Picasso.with(context).load(dataBean.getIcon()).into(addMeViewHolder.addIcon);
        }

        addMeViewHolder.addAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealChoose("1",id);
                addMeViewHolder.addAccept.setEnabled(false);
                addMeViewHolder.addDeny.setEnabled(false);
                ToastUtil.createToastConfig().ToastShow(context,"接受",15000);
            }
        });
        addMeViewHolder.addDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealChoose("2",id);
                addMeViewHolder.addDeny.setEnabled(false);
                addMeViewHolder.addAccept.setEnabled(false);
                ToastUtil.createToastConfig().ToastShow(context,"拒绝",15000);
            }
        });

        return convertView;
    }

    static class AddMeViewHolder{
        public ImageView addIcon;
        public TextView addName;
        public TextView addAccount;
        public Button addAccept;
        public Button addDeny;
    }

    public void dealChoose(String choose,String id){
        RequestParams requestParams=new RequestParams(HXTUrl.HXTHTTP_DEALMESSAGE);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("op",choose);
        requestParams.addBodyParameter("mid",id);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("onSuccess",result);
                Gson gson=new Gson();
                AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                ToastUtil.createToastConfig().ToastShow(context,addFriendResult.getInfo(),15000);
//                if (addFriendResult.getStatus()==1){
//                    ToastUtil.createToastConfig().ToastShow(context,addFriendResult.getInfo(),15000);
//                }else {
//
//                }
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
}

