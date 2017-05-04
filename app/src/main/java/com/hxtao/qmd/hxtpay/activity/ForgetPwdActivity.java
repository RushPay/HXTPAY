package com.hxtao.qmd.hxtpay.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
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
import com.hxtao.qmd.hxtpay.utils.RegexUtils;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hxtao.qmd.hxtpay.utils.HXTUrl.HXTHTTP_SENDMESSAGE;

public class ForgetPwdActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.phoneNum_ed_forget_act) EditText phoneNumEdForgetAct;
    @BindView(R.id.next_btn_forget_act) Button nextBtnForgetAct;
    private String phoneNum;
    private String iuput;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    String messageCode= (String) msg.obj;
                    ForgetPwdActivity2.actionStart(ForgetPwdActivity.this,iuput,messageCode);
                    finish();
                    break;
            }
        }
    };

    @Override
    public int layoutContentId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initView() {
        imgBack.setImageResource(R.mipmap.back_title_bg);
        imgBack.setOnClickListener(this);
        titleTv.setText("重置登录密码");
        nextBtnForgetAct.setOnClickListener(this);
    }

    @Override
    public void initListener() {
        phoneNumEdForgetAct.addTextChangedListener(new TextWatcher() {
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
                    nextBtnForgetAct.setEnabled(true);
                    nextBtnForgetAct.setBackgroundColor(getResources().getColor(R.color.login_bg));
                }else {
                    nextBtnForgetAct.setEnabled(false);
                    nextBtnForgetAct.setBackgroundColor(getResources().getColor(R.color.colorGray));
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
            case R.id.next_btn_forget_act:
                iuput = getIuput();
                boolean isMobile = RegexUtils.checkMobile(iuput);
                if (isMobile){
                    sendMessg(iuput);
                }else {
                    ToastUtil.createToastConfig().ToastShow(ForgetPwdActivity.this,"请输入正确手机号",5000);
                }
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    private String getIuput() {
        phoneNum = phoneNumEdForgetAct.getText().toString().trim();
//        boolean isMobile = RegexUtils.checkMobile(phoneNum);
      return phoneNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
                    ToastUtil.createToastConfig().ToastShow(ForgetPwdActivity.this,messageResultBeen.getInfo(),5000);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(ForgetPwdActivity.this,"网络异常请检查网络",5000);
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
