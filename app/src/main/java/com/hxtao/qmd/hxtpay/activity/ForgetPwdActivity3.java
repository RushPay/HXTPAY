package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.base.BaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPwdActivity3 extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.pwd_ed_forget_act) EditText pwdEdForgetAct;
    @BindView(R.id.next_btn_forget_act3) Button nextBtnForgetAct3;
    private String pwdStr;
    private String phoneNum;
    private String authCode;

    @Override
    public int layoutContentId() {
        return R.layout.activity_forget_pwd3;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");
        authCode = intent.getStringExtra("authCode");

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
        switch (v.getId()) {
            case R.id.next_btn_forget_act3:
                pwdStr = pwdEdForgetAct.getText().toString().trim();
                if (!TextUtils.isEmpty(pwdStr)){
                    if (inputPwdType(pwdStr)){
                        ForgetPwdActivity4.actionStart(ForgetPwdActivity3.this,phoneNum,authCode,pwdStr);
                        finish();
                    }else {
                        pwdEdForgetAct.setError("密码要为数字与字母混合");
                    }
                }
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

    public static void actionStart(Context context, String phoneNum, String authCode) {
        Intent intent = new Intent(context, ForgetPwdActivity3.class);
        intent.putExtra("phoneNum", phoneNum);
        intent.putExtra("authCode", authCode);
        context.startActivity(intent);
    }

    public boolean inputPwdType(String pwd){
        boolean numboolean=false;
        boolean letterboolean=false;
        char[] chars = pwd.toCharArray();
        Pattern pNum = Pattern.compile("[0-9]*");
        Pattern pLetter = Pattern.compile("[a-zA-Z]");
        for (char aChar : chars) {
            Matcher matcher = pNum.matcher(String.valueOf(aChar));
            if (matcher.matches()){
                numboolean=true;
//                Log.e("pNum",""+numboolean);
                break;
            }
        }
        for (char aChar : chars) {
            Matcher matcherLetter = pLetter.matcher(String.valueOf(aChar));
            if (matcherLetter.matches()){
                letterboolean=true;
//                Log.e("pLetter",""+numboolean);
                break;
            }
        }
        if (numboolean&&letterboolean){
//            Log.e("return","true");
            return true;
        }else {
//            Log.e("return","false");
            return false;
        }
    }
}
