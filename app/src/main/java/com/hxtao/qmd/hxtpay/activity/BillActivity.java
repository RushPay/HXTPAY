package com.hxtao.qmd.hxtpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BillActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.aa_bill_act_btn) Button aaBillActBtn;
    @BindView(R.id.random_bill_act_btn) Button randomBillActBtn;
    @BindView(R.id.tuhao_bill_act_btn) Button tuhaoBillActBtn;
    @BindView(R.id.party_monery_ed) EditText partyMoneryEd;
    @BindView(R.id.activity_bill) LinearLayout activityBill;
    private String monery;
    private String p_id;

    private int payWay=0;
    private Intent backIntent=null;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    @Override
    public int layoutContentId() {
        return R.layout.activity_bill;
    }

    @Override
    public void initView() {
        backIntent= getIntent();
        p_id = backIntent.getStringExtra("pid");
        setResult(1,backIntent);

        imgBack.setImageResource(R.mipmap.back_title_bg);
        titleTv.setText("选择支付方式");
    }

    @Override
    public void initListener() {
        imgBack.setOnClickListener(this);

        aaBillActBtn.setOnClickListener(this);
        randomBillActBtn.setOnClickListener(this);
        tuhaoBillActBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.aa_bill_act_btn:
                getInputMonery();
                sendBillWay("1",monery,p_id);
                payWay=1;
                break;
            case R.id.random_bill_act_btn:
                getInputMonery();
                sendBillWay("2",monery,p_id);
                payWay=2;
                break;
            case R.id.tuhao_bill_act_btn:
                getInputMonery();
                sendBillWay("3",monery,p_id);
                payWay=3;
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

    private void getInputMonery() {
        monery = partyMoneryEd.getText().toString().trim();
        if (TextUtils.isEmpty(monery)){
            partyMoneryEd.setError("请输入消费总金额");
        }
    }

    private void sendBillWay(String way,String strMonery,String pid){
        RequestParams requestParams=new RequestParams(HXTUrl.HXTHTTP_SAVEPARTYINFO);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("method",way);
        requestParams.addBodyParameter("money",strMonery);
        requestParams.addBodyParameter("p_id",pid);

//        Log.e("method",""+way);
//        Log.e("money",""+strMonery);
//        Log.e("p_id",""+pid);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("BillActivity",result);
                if (result!=null){
                    Gson gson=new Gson();
                    AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                    switch (addFriendResult.getStatus()){
                        case 1:
                            ToastUtil.createToastConfig().ToastShow(BillActivity.this, addFriendResult.getInfo(), 15000);
                            finish();
                            break;
                        case 0:
                            ToastUtil.createToastConfig().ToastShow(BillActivity.this, addFriendResult.getInfo(), 15000);
                            break;
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(BillActivity.this, "网络异常请检查网络", 15000);
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
