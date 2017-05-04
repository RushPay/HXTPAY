package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

public class EditerLoginPwd2Activity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.pwd_edt_login_act) EditText pwdEdtLoginAct;
    @BindView(R.id.check_btn_login_act) Button checkBtnLoginAct;
    @BindView(R.id.next_btn_login_act) Button nextBtnLoginAct;


    private boolean check=false;
    private boolean showPwd=false;

    private String loginPwd=null;
    private String pwd;

    @Override
    public int layoutContentId() {
        return R.layout.activity_editer_login_pwd2;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        pwd = intent.getStringExtra("loginPwd");

        titleTv.setText("重置登录密码");


    }

    @Override
    public void initListener() {
        imgBack.setOnClickListener(this);
        checkBtnLoginAct.setOnClickListener(this);
        nextBtnLoginAct.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.check_btn_login_act:
                if (!check){
                    checkBtnLoginAct.setBackground(getResources().getDrawable(R.drawable.btn_check_pressed));
                    pwdHideShow();
                    check=!check;
                }else {
                    checkBtnLoginAct.setBackground(getResources().getDrawable(R.drawable.btn_check_normal));
                    pwdHideShow();
                    check=!check;
                }
                break;
            case R.id.next_btn_login_act:
                loginPwd=pwdEdtLoginAct.getText().toString().trim();
                if (TextUtils.equals(pwd,loginPwd)){

                }else {
                    ToastUtil.createToastConfig().ToastShow(EditerLoginPwd2Activity.this,"与上次输入的密码不同",15000);
                }
                break;
        }
    }

    private void pwdHideShow() {
        if (!showPwd) {
            pwdEdtLoginAct.setInputType(TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            showPwd = !showPwd;
        } else {
            pwdEdtLoginAct.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
            showPwd = !showPwd;
        }
    }
    public static void startAction(Context context,String str){
        Intent intent=new Intent(context,EditerLoginPwd2Activity.class);
        intent.putExtra("loginPwd",str);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
