package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.MessageResultBeen;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.widgets.TimeButton;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hxtao.qmd.hxtpay.utils.HXTUrl.HXTHTTP_SENDMESSAGE;

public class ForgetPwdActivity2 extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.authCode_ed_forget_act) EditText authCodeEdForgetAct;
    @BindView(R.id.next_btn_forget_act2) Button nextBtnForgetAct2;
    @BindView(R.id.phoneNum_tv_forgetact2) TextView phoneNumTvForgetact2;
    @BindView(R.id.time_btn_forget_act2) TimeButton timeBtnForgetAct2;
    private String authCode;
    private String phoneNum;
    private String messageCode;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    messageCode= (String) msg.obj;
                    break;
            }
        }
    };
    @Override
    public int layoutContentId() {
        return R.layout.activity_forget_pwd2;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");
        messageCode = intent.getStringExtra("messageCode");

        imgBack.setImageResource(R.mipmap.back_title_bg);
        imgBack.setOnClickListener(this);
        titleTv.setText("重置登录密码");
        phoneNumTvForgetact2.setText(phoneNumDeal(phoneNum));
        nextBtnForgetAct2.setOnClickListener(this);
        timeBtnForgetAct2.setOnClickListener(this);
    }

    private String phoneNumDeal(String num) {
        String startNum = num.substring(0, 3);
        String endNum = num.substring(7, num.length());
        num = startNum + "****" + endNum;
        return num;
    }

    @Override
    public void initListener() {
        authCodeEdForgetAct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0) {
                    nextBtnForgetAct2.setEnabled(true);
                    nextBtnForgetAct2.setBackgroundColor(getResources().getColor(R.color.login_bg));
                } else {
                    nextBtnForgetAct2.setEnabled(false);
                    nextBtnForgetAct2.setBackgroundColor(getResources().getColor(R.color.colorGray));
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn_forget_act2:
                authCode = authCodeEdForgetAct.getText().toString().trim();
                if (!TextUtils.isEmpty(authCode)&&TextUtils.equals(messageCode,authCode)) {
                    ForgetPwdActivity3.actionStart(ForgetPwdActivity2.this, phoneNum, authCode);
                    finish();
                }else {
                    ToastUtil.createToastConfig().ToastShow(ForgetPwdActivity2.this,"验证码输入错误,请重新输入",5000);
                }
                break;
            case R.id.time_btn_forget_act2:
                sendMessg(phoneNum);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public static void actionStart(Context context, String phoneNum,String messageCode) {
        Intent intent = new Intent(context, ForgetPwdActivity2.class);
        intent.putExtra("phoneNum", phoneNum);
        intent.putExtra("messageCode", messageCode);
        context.startActivity(intent);
    }

    private void sendMessg(String phoneNum){
        RequestParams requestParams=new RequestParams(HXTHTTP_SENDMESSAGE);
        requestParams.addBodyParameter("mobile",phoneNum);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("ForgetPwdActivity",result);
                Gson gson=new Gson();
                MessageResultBeen messageResultBeen = gson.fromJson(result, MessageResultBeen.class);
                if (messageResultBeen.getStatus()==1){
                    Message message=handler.obtainMessage();
                    message.what=100;
                    message.obj=messageResultBeen.getData();
                    message.sendToTarget();
                }else {
                    ToastUtil.createToastConfig().ToastShow(ForgetPwdActivity2.this,messageResultBeen.getInfo(),5000);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
