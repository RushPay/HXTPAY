package com.hxtao.qmd.hxtpay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.InfoBeen;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddInforActivity extends BaseActivity {


    @BindView(R.id.icon_info_img) ImageView iconInfoImg;
    @BindView(R.id.name_info_ed) EditText nameInfoEd;
    @BindView(R.id.pwd_info_ed) EditText pwdInfoEd;
    @BindView(R.id.surepwd_info_ed) EditText surepwdInfoEd;
    @BindView(R.id.sure_info_btn) Button sureInfoBtn;
    private String nameStr;
    private String pwdStr;
    private String surePwdStr;

    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case 100:
                    InfoBeen infoBeen = (InfoBeen) msg.obj;
                 int num=infoBeen.getStatus();
                    if (num==1){
                        //个人头像地址
                        BaseApplication.getInstance().setIcon(infoBeen.getData().getIcon());
                        BaseApplication.getInstance().setId(infoBeen.getData().getId());
                        BaseApplication.getInstance().setUsername(infoBeen.getData().getUsername());
                        BaseApplication.getInstance().setMoney(infoBeen.getData().getMoney());
                        BaseApplication.getInstance().setLast_login_time(infoBeen.getData().getLast_login_time());
                        BaseApplication.getInstance().setParty_times_month(infoBeen.getData().getParty_times_month());

                        Intent intent=new Intent(AddInforActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        sureInfoBtn.setEnabled(true);
                    }

                    break;
            }
        }
    };
    /**
     * 选择文件
     */
    public static final int TO_SELECT_PHOTO = 3;

    private String picPath = null;

    private String sign;

    @Override
    public int layoutContentId() {
        return R.layout.activity_add_infor;
    }

    @Override
    public void initView() {
        sign = BaseApplication.createApplication().getSign();
    }

    @Override
    public void initListener() {

        iconInfoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddInforActivity.this,SelectPicActivity.class);
                startActivityForResult(intent, TO_SELECT_PHOTO);
            }
        });



        //用户确认注册信息
        sureInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInInfor();

                if (!TextUtils.isEmpty(nameStr)&&!TextUtils.isEmpty(pwdStr)){
                    //判断两次密码是否一致
                    if (TextUtils.equals(pwdStr,surePwdStr)){
                        sureInfoBtn.setEnabled(false);
                        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_INFO);
                        requestParams.setMultipart(true);
                        requestParams.setConnectTimeout(100000);
//                        Log.e("picPath",picPath);
                        requestParams.addBodyParameter("username", nameStr);
                        requestParams.addBodyParameter("trade_password", pwdStr);
                        requestParams.addBodyParameter("sign", sign);
                        if (picPath!=null){
                            requestParams.addBodyParameter("icon", new File(picPath));
                        }
                        x.http().post(requestParams, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
//                                Log.e("onSuccess",result.toString());
                                Gson gson = new Gson();
                                InfoBeen infoBeen = gson.fromJson(result, InfoBeen.class);
                                if (infoBeen != null) {
                                    Message message = handler.obtainMessage();
                                    message.what = 100;
                                    message.obj = infoBeen;
                                    message.sendToTarget();
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                ToastUtil.createToastConfig().ToastShow(AddInforActivity.this, "网络异常请检查网络", 15000);
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }else {
                        //提示用户两次输入的密码不一直
                        ToastUtil.createToastConfig().ToastShow(AddInforActivity.this,"支付密码不一致请重新输入",1500);
                    }
                }else {
                    //提示用户  输入不能为空
                    ToastUtil.createToastConfig().ToastShow(AddInforActivity.this,"用户名或支付密码不能为空",1500);
                }
            }
        });
    }

    private void getInInfor() {

        nameStr = nameInfoEd.getText().toString().trim();
        pwdStr = pwdInfoEd.getText().toString().trim();
        surePwdStr = surepwdInfoEd.getText().toString().trim();

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO)
        {
            picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
//            Log.i("AddInfoActivity", "最终选择的图片="+picPath);
            ImageLoadUtil.disPlaySdCard(picPath,iconInfoImg);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
