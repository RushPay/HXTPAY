package com.hxtao.qmd.hxtpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.hxtinterface.IOnLoadDataListener;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.utils.XutilsHttpPostUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonSetActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.logout_perfra_rl) RelativeLayout logoutPerfraRl;

    private Map<String, String> urlMap = new HashMap<>();

    @Override
    public int layoutContentId() {
        return R.layout.activity_person_set;
    }

    @Override
    public void initView() {
        titleTv.setText("设置");
        logoutPerfraRl.setOnClickListener(this);
        imgBack.setImageResource(R.mipmap.back_bg);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_perfra_rl:
//                Intent intent = new Intent();
//                intent.setAction("exit_app");
//                sendBroadcast(intent);
                urlMap.put("sign", BaseApplication.getInstance().getSign());
                XutilsHttpPostUtils.hxtPostUtils(urlMap, HXTUrl.HXTHTTP_LOGINOUT, new IOnLoadDataListener() {
                    @Override
                    public void onSuccess(String result, String tagUrl) {
                        Gson gson = new Gson();
                        AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                        switch (addFriendResult.getStatus()) {
                            case 1:
                                Intent intent = new Intent();
                                intent.setAction("exit_app");
                                sendBroadcast(intent);
                                ToastUtil.createToastConfig().ToastShow(PersonSetActivity.this,addFriendResult.getInfo(),5000);
                                break;
                            case 0:
                                ToastUtil.createToastConfig().ToastShow(PersonSetActivity.this,addFriendResult.getInfo(),5000);
                                break;
                        }
                    }

                    @Override
                    public void onError(String tagUrl) {

                    }

                    @Override
                    public void networkError(String tagUrl) {

                    }
                });
                logoutPerfraRl.setEnabled(false);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
