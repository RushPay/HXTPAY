package com.hxtao.qmd.hxtpay.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.been.MessageResultBeen;
import com.hxtao.qmd.hxtpay.utils.RegexUtils;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hxtao.qmd.hxtpay.utils.HXTUrl.HXTHTTP_SENDMESSAGEREGISTER;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.register_back) Button registerBack;
    @BindView(R.id.register_phone_et) EditText registerPhoneEt;
    @BindView(R.id.register_pwd_et) EditText registerPwdEt;
    @BindView(R.id.register_btn) Button registerBtn;
    private String userPhone;
    private String userPwd;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    String messageCode= (String) msg.obj;
                    RegisterCodeActivity.actionStart(RegisterActivity.this,userPhone,userPwd,messageCode);
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setWidgetOnClick();
        setOnListener();
    }

    private void setOnListener() {
        registerPhoneEt.addTextChangedListener(new TextWatcher() {
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
                    registerBtn.setEnabled(true);
                    registerBtn.setBackgroundColor(getResources().getColor(R.color.login_bg));
                } else {
                    registerBtn.setEnabled(false);
                    registerBtn.setBackgroundColor(getResources().getColor(R.color.colorGray));
                }
            }
        });
    }

    private void setWidgetOnClick() {
        registerBack.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back://返回
                finish();
                break;
            case R.id.register_btn://注册
                getIputString();

                break;
        }
    }

    private void getIputString() {
        String phone = registerPhoneEt.getText().toString().trim();
        String pwd = registerPwdEt.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            registerPhoneEt.setError("用户名不能为空");
            return;
        }
        if (!RegexUtils.checkMobile(phone)) {
            registerPhoneEt.setError("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            registerPwdEt.setError("密码不能为空");
            return;
        }
        if (!inputPwdType(pwd)){
            registerPwdEt.setError("密码要为数字与字母混合");
            return;
        }
        userPhone = phone;
        userPwd = pwd;

        new AlertDialog.Builder(RegisterActivity.this)
                .setTitle("               确认手机号")
                .setMessage("     短信验证码将发送到你的手机"+"\n                  "+userPhone)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendMessg(userPhone);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void sendMessg(String phoneNum){
        RequestParams requestParams=new RequestParams(HXTHTTP_SENDMESSAGEREGISTER);
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
                   ToastUtil.createToastConfig().ToastShow(RegisterActivity.this,messageResultBeen.getInfo(),5000);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(RegisterActivity.this,"网络异常请检查网络",5000);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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
