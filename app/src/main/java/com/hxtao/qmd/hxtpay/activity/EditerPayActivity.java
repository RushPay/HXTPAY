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

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_VARIATION_NORMAL;
import static android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD;

public class EditerPayActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.pwd_edt_pay_act) EditText pwdEdtPayAct;
    @BindView(R.id.check_btn_pay_act) Button checkBtnPayAct;
    @BindView(R.id.next_btn_pay_act) Button nextBtnPayAct;
    private String inputStr;
    private boolean check=false;
    private boolean showPwd=false;
    @Override
    public int layoutContentId() {
        return R.layout.activity_editer_pay;
    }

    @Override
    public void initView() {
        titleTv.setText("重置支付密码");
    }

    @Override
    public void initListener() {
        imgBack.setOnClickListener(this);
        nextBtnPayAct.setOnClickListener(this);
        checkBtnPayAct.setOnClickListener(this);
        pwdEdtPayAct.addTextChangedListener(new TextWatcher() {
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
                    nextBtnPayAct.setEnabled(true);
                    nextBtnPayAct.setBackgroundColor(getResources().getColor(R.color.login_bg));
                } else {
                    nextBtnPayAct.setEnabled(false);
                    nextBtnPayAct.setBackgroundColor(getResources().getColor(R.color.gray_uncheck));
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
        switch (v.getId()) {
            case R.id.next_btn_pay_act://下一步跳转界面
                inputStr = pwdEdtPayAct.getText().toString();
                if (inputStr.length()==6){
                    EditerPayPwdActivity.startAction(EditerPayActivity.this,inputStr);
                    finish();
                }else {
                ToastUtil.createToastConfig().ToastShow(EditerPayActivity.this,"请输入六位数字密码",15000);
                }

                break;
            case R.id.check_btn_pay_act:
                if (!check){
                    checkBtnPayAct.setBackground(getResources().getDrawable(R.drawable.btn_check_pressed));
                    pwdHideShow();
                    check=!check;
                }else {
                    checkBtnPayAct.setBackground(getResources().getDrawable(R.drawable.btn_check_normal));
                    pwdHideShow();
                    check=!check;
                }

                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
    private void pwdHideShow() {

        if (!showPwd) {
            pwdEdtPayAct.setInputType(TYPE_CLASS_NUMBER | TYPE_NUMBER_VARIATION_NORMAL);
            showPwd = !showPwd;
        } else {
            pwdEdtPayAct.setInputType(TYPE_CLASS_NUMBER | TYPE_NUMBER_VARIATION_PASSWORD);
            showPwd = !showPwd;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
