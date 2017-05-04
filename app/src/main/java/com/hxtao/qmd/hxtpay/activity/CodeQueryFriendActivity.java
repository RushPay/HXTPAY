package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CodeQueryFriendActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.icon_img_codequeery_act) ImageView iconImgCodequeeryAct;
    @BindView(R.id.name_tv_codequeery_act) TextView nameTvCodequeeryAct;
    @BindView(R.id.add_btn_codequeery_act) Button addBtnCodequeeryAct;
    private String imgUrl;
    private String name;
    private String accont;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    addBtnCodequeeryAct.setEnabled(false);
                    addBtnCodequeeryAct.setText("已添加");
                    break;
            }
        }
    };
    @Override
    public int layoutContentId() {
        return R.layout.activity_code_query_friend;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        imgUrl = intent.getStringExtra("imgUrl");
        name = intent.getStringExtra("name");
        accont = intent.getStringExtra("accont");

        imgBack.setImageResource(R.mipmap.back_bg);
        imgBack.setOnClickListener(this);
        titleTv.setText("添加朋友");

        ImageLoadUtil.disPlayImage(imgUrl,iconImgCodequeeryAct);
        nameTvCodequeeryAct.setText(name);
        addBtnCodequeeryAct.setOnClickListener(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.add_btn_codequeery_act:
                codeAddFriend(accont);
                break;
        }
    }

    private void codeAddFriend(String accont) {
        RequestParams requestParams=new RequestParams(HXTUrl.HXTHTTP_ADDFRIEND);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("fid",accont);
        Log.e("fid",accont);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("onSuccess",result);
                Gson gson=new Gson();
                AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                int status = addFriendResult.getStatus();
                if (status==1){
                    Message message=handler.obtainMessage();
                    message.what=100;
                    message.sendToTarget();
                }else {
                    ToastUtil.createToastConfig().ToastShow(CodeQueryFriendActivity.this,addFriendResult.getInfo(),1500);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("onError",ex.toString());
                ToastUtil.createToastConfig().ToastShow(CodeQueryFriendActivity.this,"网络异常请检查网络",15000);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    public static void actionStart(Context context ,String imgUrl,String name,String accont){
        Intent intent =new Intent(context,CodeQueryFriendActivity.class);
        intent.putExtra("imgUrl",imgUrl);
        intent.putExtra("name",name);
        intent.putExtra("accont",accont);
        context.startActivity(intent);
    }
}
