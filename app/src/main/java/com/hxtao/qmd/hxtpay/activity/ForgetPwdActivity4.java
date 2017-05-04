package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hxtao.qmd.hxtpay.utils.HXTUrl.HXTHTTP_RESETPASSWORD;

public class ForgetPwdActivity4 extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.pwd_ed_forget_act) EditText pwdEdForgetAct;
    @BindView(R.id.next_btn_forget_act3) Button nextBtnForgetAct3;
    private String phoneNum;
    private String authCode;
    private String pwdStr;
    private String surePwd;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    new AlertDialog.Builder(ForgetPwdActivity4.this)
                            .setTitle("           登录密码已重置成功")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
                    break;
            }
        }
    };
    @Override
    public int layoutContentId() {
        return R.layout.activity_forget_pwd4;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");
        authCode = intent.getStringExtra("authCode");
        pwdStr = intent.getStringExtra("pwdStr");

        titleTv.setText("重置登录密码");
        imgBack.setImageResource(R.mipmap.back_title_bg);
        imgBack.setOnClickListener(this);

        nextBtnForgetAct3.setOnClickListener(this);
    }

    @Override
    public void initListener() {
        pwdEdForgetAct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length>0){
                    nextBtnForgetAct3.setEnabled(true);
                    nextBtnForgetAct3.setBackgroundColor(getResources().getColor(R.color.login_bg));
                }else {
                    nextBtnForgetAct3.setEnabled(false);
                    nextBtnForgetAct3.setBackgroundColor(getResources().getColor(R.color.colorGray));
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_btn_forget_act3:
                surePwd = pwdEdForgetAct.getText().toString().trim();
                if (TextUtils.equals(pwdStr,surePwd)){//密码相同  向服务端提交修改密码请求
                    resetPassword(phoneNum,pwdStr,authCode);
                }else {
                    ToastUtil.createToastConfig().ToastShow(ForgetPwdActivity4.this,"两次密码输入不一致",5000);
                }
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    public static void actionStart(Context context, String phoneNum, String authCode, String pwdStr) {
        Intent intent = new Intent(context, ForgetPwdActivity4.class);
        intent.putExtra("phoneNum", phoneNum);
        intent.putExtra("authCode", authCode);
        intent.putExtra("pwdStr", pwdStr);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    private void resetPassword(String phoneNum,String password,String messageCode){
        RequestParams requestParams=new RequestParams(HXTHTTP_RESETPASSWORD);
        requestParams.addBodyParameter("mobile",phoneNum);
        requestParams.addBodyParameter("password",password);
        requestParams.addBodyParameter("messageCode",messageCode);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                if (addFriendResult.getStatus()==1){
                    Message message=handler.obtainMessage();
                    message.what=100;
                    message.sendToTarget();
                }else {
                    ToastUtil.createToastConfig().ToastShow(ForgetPwdActivity4.this,addFriendResult.getInfo(),5000);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(ForgetPwdActivity4.this,"网络异常请检查网络",5000);
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
