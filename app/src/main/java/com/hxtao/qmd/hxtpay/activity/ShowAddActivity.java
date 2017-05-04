package com.hxtao.qmd.hxtpay.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.adapter.AddMeAdapter;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.AddFriendInfo;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowAddActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.addme_showadd_lv) ListView addmeShowaddLv;

    private List<AddFriendInfo.DataBean> dataInfoList;

    private AddMeAdapter addMeAdapter;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100://获取数据成功
                    if (msg.obj != null) {
                        AddFriendInfo addInfo = (AddFriendInfo) msg.obj;
                        dataInfoList.clear();
                        dataInfoList.addAll(addInfo.getData());
                        addMeAdapter.notifyDataSetChanged();
                    }
                    break;
                case 200://获取数据失败
                    if (msg.obj != null) {
                        String errorMessage = (String) msg.obj;
                        ToastUtil.createToastConfig().ToastShow(ShowAddActivity.this, errorMessage, 15000);
                    }
                    break;
            }
        }
    };

    @Override
    public int layoutContentId() {
        return R.layout.activity_show_add;
    }

    @Override
    public void initView() {
        titleTv.setText("添加我的朋友");
        imgBack.setImageResource(R.mipmap.back_title_bg);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dataInfoList=new ArrayList<>();
        addMeAdapter=new AddMeAdapter(ShowAddActivity.this,dataInfoList);
        addmeShowaddLv.setAdapter(addMeAdapter);

        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_ADDMESSAGE);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        Log.e("ShowAddActivity",BaseApplication.createApplication().getSign());
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("ShowAddActivity", result);
                if (result != null) {
                    Gson gson = new Gson();
                    AddFriendInfo addFriendInfo = gson.fromJson(result, AddFriendInfo.class);
                    if (addFriendInfo != null) {
                        Message message = handler.obtainMessage();
                        int status = addFriendInfo.getStatus();
                        if (status == 1) {
                            message.what = 100;
                            message.obj = addFriendInfo;
                        } else {
                            message.what = 200;
                            message.obj = addFriendInfo.getInfo();
                        }
                        message.sendToTarget();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("onError", ex.toString());
                ToastUtil.createToastConfig().ToastShow(ShowAddActivity.this, "网络异常请检查网络", 15000);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
