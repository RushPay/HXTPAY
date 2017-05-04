package com.hxtao.qmd.hxtpay.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

public class EditerLoginPwdActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.pwd_edt_login_act) EditText pwdEdtLoginAct;
    @BindView(R.id.check_btn_login_act) Button checkBtnLoginAct;
    @BindView(R.id.next_btn_login_act) Button nextBtnLoginAct;


    private boolean check=false;
    private boolean showPwd=false;

    private String loginPwd=null;
    @Override
    public int layoutContentId() {
        return R.layout.activity_editer_login_pwd;
    }

    @Override
    public void initView() {

        titleTv.setText("重置登录密码");

    }

    @Override
    public void initListener() {
        imgBack.setOnClickListener(this);
        checkBtnLoginAct.setOnClickListener(this);
        nextBtnLoginAct.setOnClickListener(this);

        pwdEdtLoginAct.addTextChangedListener(new TextWatcher() {
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
                    nextBtnLoginAct.setEnabled(true);
                    nextBtnLoginAct.setBackgroundColor(getResources().getColor(R.color.login_bg));
                } else {
                    nextBtnLoginAct.setEnabled(false);
                    nextBtnLoginAct.setBackgroundColor(getResources().getColor(R.color.gray_uncheck));
                }
            }
        });
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
                if (inputPwdType(loginPwd)){
                    EditerLoginPwd2Activity.startAction(EditerLoginPwdActivity.this,loginPwd);
                    finish();
                }else {
                    ToastUtil.createToastConfig().ToastShow(EditerLoginPwdActivity.this,"请输入数字与字母组合的密码",15000);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
