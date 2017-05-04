package com.hxtao.qmd.hxtpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.widgets.SelectPicPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MoneyTransferActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.icon_money_transfer_img) CircleImageView iconMoneyTransferImg;
    @BindView(R.id.name_money_transfer_tv) TextView nameMoneyTransferTv;
    @BindView(R.id.money_money_transfer_edt) EditText moneyMoneyTransferEdt;
    @BindView(R.id.monery_transfer_btn) Button moneryTransferBtn;
    private String mid;
    private String username;
    private String iconUrl;

    //自定义的弹出框类  密码输入框
    private SelectPicPopupWindow menuWindow;

    @Override
    public int layoutContentId() {
        return R.layout.activity_money_transfer;
    }

    @Override
    public void initView() {
        //获得上个界面的传值
        Intent intent = getIntent();
        Bundle person = intent.getBundleExtra("person");
        mid = person.getString("id");
        username = person.getString("username");
        iconUrl = person.getString("icon");
//        Log.e("mid", mid);
//        Log.e("iconUrl", iconUrl);


        imgBack.setImageResource(R.mipmap.back_title_bg);
        titleTv.setText("转账");
        //设置转账人的头像
        ImageLoadUtil.disPlayImage(iconUrl, iconMoneyTransferImg);
//        Log.e("username", username);
        nameMoneyTransferTv.setText(username);

    }

    @Override
    public void initListener() {
        imgBack.setOnClickListener(this);
        //转账的监听时间按
        moneryTransferBtn.setOnClickListener(this);
        //编辑框的文件改变监听事件
        moneyMoneyTransferEdt.addTextChangedListener(new TextWatcher() {
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
                    moneryTransferBtn.setEnabled(true);
                } else {
                    moneryTransferBtn.setEnabled(false);
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
            case R.id.monery_transfer_btn:

                if (!TextUtils.isEmpty(moneyMoneyTransferEdt.getText().toString()) && Double.valueOf(moneyMoneyTransferEdt.getText().toString()) > 0) {
                    menuWindow = new SelectPicPopupWindow(MoneyTransferActivity.this, mid, moneyMoneyTransferEdt.getText().toString());
//                    transferMoney(mid, moneyMoneyTransferEdt.getText().toString());
                    //显示窗口
                    menuWindow.showAtLocation(MoneyTransferActivity.this.findViewById(R.id.activity_money_transfer),
                            Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位
                }else {
                    ToastUtil.createToastConfig().ToastShow(MoneyTransferActivity.this,"转账金额不能为空且大于零",1500);
                }
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
}
