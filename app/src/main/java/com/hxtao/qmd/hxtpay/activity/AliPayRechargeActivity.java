package com.hxtao.qmd.hxtpay.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.app.Constants;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.AliReacharge;
import com.hxtao.qmd.hxtpay.been.PayResult;
import com.hxtao.qmd.hxtpay.event.TransferEvent;
import com.hxtao.qmd.hxtpay.event.TypeEvent;
import com.hxtao.qmd.hxtpay.hxtinterface.IOnLoadDataListener;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.utils.XutilsHttpPostUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AliPayRechargeActivity extends BaseActivity  {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.monery_alirech_act_ed) EditText moneryAlirechActEd;
    @BindView(R.id.recharge_alirech_act_btn) Button rechargeAlirechActBtn;
    @BindView(R.id.recharge_group_alirech_act) RadioGroup rechargeGroupAlirechAct;
    private int checkId = -1;

    private IWXAPI api;

    private String requserStr;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100://获取服务端的返回的订单信息 根据返回信息调用对应的支付方式
                    if (msg.obj != null) {
                        String orderStr = (String) msg.obj;
                        sendOrderInfoAliPay(orderStr);
                    }
                    break;

                case 200://获取支付宝端返回的结果数据
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    Log.e("resultInfo", resultInfo);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖务端的异步通知。
                        Toast.makeText(AliPayRechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new TransferEvent(TypeEvent.TRANSFER));
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(AliPayRechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    };

    @Override
    public int layoutContentId() {
        return R.layout.activity_ali_pay_recharge;
    }

    @Override
    public void initView() {

        //初始化控件
        titleTv.setText("充值");
        imgBack.setImageResource(R.mipmap.back_title_bg);

        //控件点击监听
        imgBack.setOnClickListener(this);
        rechargeAlirechActBtn.setOnClickListener(this);

        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
    }

    @Override
    public void initListener() {
        rechargeGroupAlirechAct.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int checkedRadioButtonId = rechargeGroupAlirechAct.getCheckedRadioButtonId();
//                Log.e("checkedRadioButtonId",""+checkedRadioButtonId);
                checkId = checkedId;
                switch (checkedId) {
                    case R.id.wxpay_rbtn_alirech_act:

                        break;
                    case R.id.alipay_rbtn_alirech_act:

                        break;
                    case R.id.cjpay_rbtn_alirech_act:

                        break;
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
            case R.id.img_back:
                finish();
                break;
            case R.id.recharge_alirech_act_btn://充值
                if (-1 == checkId) {
                    ToastUtil.createToastConfig().ToastShow(AliPayRechargeActivity.this, "请选择充值方式", 15000);
                } else {
                    if (R.id.alipay_rbtn_alirech_act==checkId||R.id.wxpay_rbtn_alirech_act==checkId){
                    getOrderInfo();
                    }else {
                        ToastUtil.createToastConfig().ToastShow(AliPayRechargeActivity.this,"充值方式正在开发中...",15000);
                    }


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

    //向服务端请求订单信息
    public void getOrderInfo() {
        if (TextUtils.isEmpty(moneryAlirechActEd.getText().toString())) {
            moneryAlirechActEd.setError("充值金额不能为空");
            return;
        }
        switch (checkId) {
            case R.id.wxpay_rbtn_alirech_act:
                requserStr = HXTUrl.HXTHTTP_WEIXIN;
                break;
            case R.id.alipay_rbtn_alirech_act:
                requserStr = HXTUrl.HXTHTTP_ALIPAY;
                break;
        }
        Map<String, String> requsetMap = new HashMap<>();
        requsetMap.put("sign", BaseApplication.createApplication().getSign());
        requsetMap.put("money", moneryAlirechActEd.getText().toString());
        XutilsHttpPostUtils.hxtPostUtils(requsetMap, requserStr, new IOnLoadDataListener() {
            @Override
            public void onSuccess(String result, String tagUrl) {
                if (result != null) {
                    switch (checkId) {
                        case R.id.wxpay_rbtn_alirech_act:
                            wxRechargeMethod(result);
                            break;
                        case R.id.alipay_rbtn_alirech_act:
                            aliRechageMethod(result);
                            break;
                    }
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

    private void aliRechageMethod(String result) {
        Gson gson = new Gson();
        AliReacharge aliReacharge = gson.fromJson(result, AliReacharge.class);
        switch (aliReacharge.getStatus()) {
            case 1:
                Message message = handler.obtainMessage();
                message.what = 100;
                message.obj = aliReacharge.getData();
                message.sendToTarget();
                break;
            case 0:
                ToastUtil.createToastConfig().ToastShow(AliPayRechargeActivity.this, aliReacharge.getInfo(), 15000);
                break;
        }
    }

    private void wxRechargeMethod(String result) {
        try {
            JSONObject json = new JSONObject(result);
            JSONObject dataObject = json.getJSONObject("data");
            PayReq req = new PayReq();
            req.appId = dataObject.getString("appid");
            req.partnerId = dataObject.getString("partnerid");
            req.prepayId = dataObject.getString("prepayid");
            req.nonceStr = dataObject.getString("noncestr");
            req.timeStamp = dataObject.getString("timestamp");
            req.packageValue = dataObject.getString("package");
            req.sign = dataObject.getString("sign");
            api.sendReq(req);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //调用AliPay支付方式
    public void sendOrderInfoAliPay(String orderInfoStr) {
        final String orderInfo = orderInfoStr;// 订单信息
//        Log.e("orderInfo",orderInfo);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(AliPayRechargeActivity.this);
                Map<String, String> map = alipay.payV2(orderInfo, true);
//                Log.e("alipay",map.toString());
                Message msg = handler.obtainMessage();
                msg.what = 200;
                msg.obj = map;
                msg.sendToTarget();
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
