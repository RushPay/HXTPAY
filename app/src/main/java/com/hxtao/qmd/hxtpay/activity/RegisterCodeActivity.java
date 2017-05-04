package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.RegisterBeen;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.MD5Util;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterCodeActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.phone_tv_registercode_act) TextView phoneTvRegistercodeAct;
    @BindView(R.id.code_ed_registercode_act) EditText codeEdRegistercodeAct;
    @BindView(R.id.next_btn_registercode_act) Button nextBtnRegistercodeAct;
    private String phoneNum;
    private String userPwd;
    private String messageCode;
    private RegisterBeen registerBeen;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    registerBeen = (RegisterBeen) msg.obj;
                    if (registerBeen.getStatus() == 1) {
                        //跳转完善信息界面
//                            Log.e("RegisterActivity:", "status==1");
                        if (!TextUtils.isEmpty(registerBeen.getSign())) {
                            Log.e("RegisterActivity:", registerBeen.getSign());
                            BaseApplication.createApplication().setSign(registerBeen.getSign());
                            Intent intent = new Intent(RegisterCodeActivity.this, AddInforActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        //提示用户注册失败信息
                        Toast.makeText(RegisterCodeActivity.this, "注册失败:" + registerBeen.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    @Override
    public int layoutContentId() {
        return R.layout.activity_register_code;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        phoneNum = intent.getStringExtra("phoneNum");
        userPwd = intent.getStringExtra("userPwd");
        messageCode = intent.getStringExtra("messageCode");

        imgBack.setImageResource(R.mipmap.back_title_bg);
        imgBack.setOnClickListener(this);
        titleTv.setText("");

        phoneTvRegistercodeAct.setText(phoneNum);
        nextBtnRegistercodeAct.setOnClickListener(this);
    }

    @Override
    public void initListener() {
        codeEdRegistercodeAct.addTextChangedListener(new TextWatcher() {
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
                    nextBtnRegistercodeAct.setEnabled(true);
                    nextBtnRegistercodeAct.setBackgroundColor(getResources().getColor(R.color.login_bg));
                }else {
                    nextBtnRegistercodeAct.setEnabled(false);
                    nextBtnRegistercodeAct.setBackgroundColor(getResources().getColor(R.color.colorGray));
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
            case R.id.img_back:
                finish();
                break;
            case R.id.next_btn_registercode_act:
                String trimCode = codeEdRegistercodeAct.getText().toString().trim();
//                Log.e("trimCode",trimCode);
//                Log.e("messageCode",messageCode);
                if (TextUtils.equals(messageCode,trimCode)){
//                    Log.e("phoneNum",phoneNum);
                    registerUser(phoneNum,userPwd);
                }else {
                    codeEdRegistercodeAct.setError("验证码输入错误");
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public static void actionStart(Context context,String phoneNum,String userPwd,String messageCode){
        Intent intent=new Intent(context,RegisterCodeActivity.class);
        intent.putExtra("phoneNum",phoneNum);
        intent.putExtra("userPwd",userPwd);
        intent.putExtra("messageCode",messageCode);
        context.startActivity(intent);
    }

    private void registerUser(String phone, String pwd) {

        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd)) {
            String md5StrPhone = MD5Util.getMD5String(phone);
            RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_REHSITER);
            requestParams.addBodyParameter("account", phone);
            requestParams.addBodyParameter("password", pwd);
            requestParams.addBodyParameter("sign", md5StrPhone);
            requestParams.addBodyParameter("role", "1");
//            Log.e("phone", phone);
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    Log.e("onSuccess", result);
                    RegisterBeen registerBeen = gson.fromJson(result, RegisterBeen.class);
//                    Log.e("RegisterActivity:",registerBeen.toString());
                    if (registerBeen != null) {
                        Message message = handler.obtainMessage();
                        message.what = 100;
                        message.obj = registerBeen;
                        message.sendToTarget();
//                        Log.e("onSuccess", "数据解析完成");
                    } else {
                        //数据解析失败
                        Log.e("onError", "注册界面数据解析失败");
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ToastUtil.createToastConfig().ToastShow(RegisterCodeActivity.this,"网络异常请检查网络",1500);
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
}
