package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
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

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_VARIATION_NORMAL;
import static android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD;

public class EditerPayPwdActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.pwd_edt_paypwd_act) EditText pwdEdtPaypwdAct;
    @BindView(R.id.next_btn_paypwd_act) Button nextBtnPaypwdAct;
    @BindView(R.id.check_btn_paypwd_act) Button checkBtnPaypwdAct;
    private String payPwd;

    private Map<String, String> pwdMap;
    private String input;

    private boolean check = false;

    private boolean showPwd = false;

    @Override
    public int layoutContentId() {
        return R.layout.activity_editer_pay_pwd;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        input = intent.getStringExtra("input");
        titleTv.setText("重置支付密码");

    }

    @Override
    public void initListener() {
        imgBack.setOnClickListener(this);
        nextBtnPaypwdAct.setOnClickListener(this);
        checkBtnPaypwdAct.setOnClickListener(this);
        pwdEdtPaypwdAct.addTextChangedListener(new TextWatcher() {
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
                    nextBtnPaypwdAct.setEnabled(true);
                    nextBtnPaypwdAct.setBackgroundColor(getResources().getColor(R.color.login_bg));
                } else {
                    nextBtnPaypwdAct.setEnabled(false);
                    nextBtnPaypwdAct.setBackgroundColor(getResources().getColor(R.color.gray_uncheck));
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
            case R.id.next_btn_paypwd_act:
                payPwd = pwdEdtPaypwdAct.getText().toString();
//                Log.e("payPwd",payPwd);

                if (TextUtils.equals(input, payPwd)) {
                    editPayPwd();//修改支付密码
                } else {
                    ToastUtil.createToastConfig().ToastShow(EditerPayPwdActivity.this, "密码输入不一致", 15000);
                }
                break;

            case R.id.check_btn_paypwd_act:
                if (!check) {
                    checkBtnPaypwdAct.setBackground(getResources().getDrawable(R.drawable.btn_check_pressed));
                    pwdHideShow();
                    check = !check;
                } else {
                    checkBtnPaypwdAct.setBackground(getResources().getDrawable(R.drawable.btn_check_normal));
                    pwdHideShow();
                    check = !check;
                }
                break;
            case R.id.img_back:
                finish();
                break;

        }
    }

    private void editPayPwd() {
        pwdMap = new HashMap<>();
        pwdMap.put("sign", BaseApplication.getInstance().getSign());
        pwdMap.put("trade_password", payPwd);
        XutilsHttpPostUtils.hxtPostUtils(pwdMap, HXTUrl.HXTHTTP_EDITPAYPWD, new IOnLoadDataListener() {
            @Override
            public void onSuccess(String result, String tagUrl) {
//                Log.e("payPwd", result);
                Gson gson = new Gson();
                AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                if (1 == addFriendResult.getStatus()) {
//                    ToastUtil.createToastConfig().ToastShow(EditerPayPwdActivity.this, "支付密码修改成功", 15000);
                    new AlertView("支付密码已重置", null, null, new String[]{"确定"}, null, EditerPayPwdActivity.this,
                            AlertView.Style.Alert, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            finish();
                        }
                    }).show();
                }else {
                    ToastUtil.createToastConfig().ToastShow(EditerPayPwdActivity.this, "支付密码修改失败", 15000);
                }
            }

            @Override
            public void onError(String tagUrl) {

            }

            @Override
            public void networkError(String tagUrl) {

            }
        });
    }

    private void pwdHideShow() {
        if (!showPwd) {
            pwdEdtPaypwdAct.setInputType(TYPE_CLASS_NUMBER | TYPE_NUMBER_VARIATION_NORMAL);
            showPwd = !showPwd;
        } else {
            pwdEdtPaypwdAct.setInputType(TYPE_CLASS_NUMBER | TYPE_NUMBER_VARIATION_PASSWORD);
            showPwd = !showPwd;
        }
    }

    public static void startAction(Context context, String input) {
        Intent intent = new Intent(context, EditerPayPwdActivity.class);
        intent.putExtra("input", input);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
