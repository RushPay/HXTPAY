package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.event.TransferEvent;
import com.hxtao.qmd.hxtpay.event.TypeEvent;
import com.hxtao.qmd.hxtpay.hxtinterface.IOnLoadDataListener;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.utils.XutilsHttpPostUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KitingActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.kitingway_group_kititng_act) RadioGroup kitingwayGroupKititngAct;
    @BindView(R.id.dollar_edt_kiting_act) EditText dollarEdtKitingAct;
    @BindView(R.id.account_edt_kiting_act) EditText accountEdtKitingAct;
    @BindView(R.id.name_edt_kiting_act) EditText nameEdtKitingAct;
    @BindView(R.id.sure_btn_kiting_act) Button sureBtnKitingAct;
    @BindView(R.id.al_rbtn_kiting_act) RadioButton alRbtnKitingAct;
    @BindView(R.id.bank_rbtn_kiting_act) RadioButton bankRbtnKitingAct;
    private String dollarStr;
    private String accountStr;
    private String nameStr;

    private String type = "2";

    private Map<String, String> requestMap;

    @Override
    public int layoutContentId() {
        return R.layout.activity_kiting;
    }

    @Override
    public void initView() {

        titleTv.setText("提现");
        //默认选择支付宝提现方式
        kitingwayGroupKititngAct.check(R.id.al_rbtn_kiting_act);


    }

    private boolean getUserInput() {
        dollarStr = dollarEdtKitingAct.getText().toString().trim();
        accountStr = accountEdtKitingAct.getText().toString().trim();
        nameStr = nameEdtKitingAct.getText().toString().trim();
        if (TextUtils.isEmpty(dollarStr)) {
            dollarEdtKitingAct.setError("转账金额不能为空");
            return false;
        }
        if (TextUtils.isEmpty(accountStr)) {
            accountEdtKitingAct.setError("提现账户不能为空");
            return false;
        }
        if (TextUtils.isEmpty(nameStr)) {
            nameEdtKitingAct.setError("真实姓名不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void initListener() {

        //提现方式改变的监听
        kitingwayGroupKititngAct.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.al_rbtn_kiting_act == checkedId) {
                    type = "2";
                    accountEdtKitingAct.setHint(R.string.kiting_accouny_al);
                } else {
                    accountEdtKitingAct.setHint(R.string.kiting_accouny_bank);
                    type = "1";
                }
            }
        });

        //确认提现监听
        sureBtnKitingAct.setOnClickListener(this);

        nameEdtKitingAct.addTextChangedListener(new TextWatcher() {
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
                    sureBtnKitingAct.setEnabled(true);
                    sureBtnKitingAct.setBackgroundColor(getResources().getColor(R.color.login_bg));
                } else {
                    sureBtnKitingAct.setEnabled(false);
                    sureBtnKitingAct.setBackgroundColor(getResources().getColor(R.color.gray_uncheck));
                }
            }
        });

        imgBack.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_btn_kiting_act://确认提现监听
                if (getUserInput()) {
                    postKitingMethod();
                }
                break;

            case R.id.img_back:
                finish();
                break;
        }
    }

    private void postKitingMethod() {
        requestMap = new HashMap<>();
        requestMap.put("sign", BaseApplication.getInstance().getSign());
        requestMap.put("type", type);
        requestMap.put("number", accountStr);
        requestMap.put("name", nameStr);
        requestMap.put("money", dollarStr);
//                    Log.e("KitingActivity","type:"+type+"--accountStr:"+accountStr+"--nameStr:"+nameStr+"--dollarSt:"+dollarStr);
        XutilsHttpPostUtils.hxtPostUtils(requestMap, HXTUrl.HXTHTTP_WITHDRAW, new IOnLoadDataListener() {
            @Override
            public void onSuccess(String result, String tagUrl) {
//                            Log.e("result",result);
                Gson gson = new Gson();
                AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                if (1 == addFriendResult.getStatus()) {
                    ToastUtil.createToastConfig().ToastShow(KitingActivity.this, addFriendResult.getInfo(), 15000);
                    EventBus.getDefault().post(new TransferEvent(TypeEvent.TRANSFER));
                } else {
                    ToastUtil.createToastConfig().ToastShow(KitingActivity.this, addFriendResult.getInfo(), 15000);
                }
            }

            @Override
            public void onError(String tagUrl) {
                ToastUtil.createToastConfig().ToastShow(KitingActivity.this, "网络异常请检查网络", 15000);
            }

            @Override
            public void networkError(String tagUrl) {

            }
        });
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, KitingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
