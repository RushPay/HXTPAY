package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CodeTransferActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.icon_img_codetransfer_act) CircleImageView iconImgCodetransferAct;
    @BindView(R.id.name_tv_codetransfer_act) TextView nameTvCodetransferAct;
    @BindView(R.id.monery_tv_codetransfer_act) TextInputLayout moneryTvCodetransferAct;
    @BindView(R.id.transfer_btn_codetransfer_act) Button transferBtnCodetransferAct;
    @BindView(R.id.activity_code_transfer) LinearLayout activityCodeTransfer;
    private String iconUrl;
    private String name;
    private String mid;

    @Override
    public int layoutContentId() {
        return R.layout.activity_code_transfer;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        iconUrl = intent.getStringExtra("iconUrl");
        name = intent.getStringExtra("name");
        mid = intent.getStringExtra("mid");

        imgBack.setImageResource(R.mipmap.back_bg);
        imgBack.setOnClickListener(this);

        titleTv.setText("转账");

        ImageLoadUtil.disPlayImage(iconUrl,iconImgCodetransferAct);
        nameTvCodetransferAct.setText(name);
        transferBtnCodetransferAct.setOnClickListener(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.transfer_btn_codetransfer_act:
                String inputStrin = getInputStrin();
                if (!TextUtils.isEmpty(inputStrin)){

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

    private String getInputStrin(){
        String string = moneryTvCodetransferAct.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(string)){
            moneryTvCodetransferAct.setError("金额不能为空");
            return null;
        }else {
            return string;
        }
    }

    public static void actionStart(Context context,String iconUrl,String name,String mid){
        Intent intent=new Intent(context,CodeTransferActivity.class);
        intent.putExtra("iconUrl",iconUrl);
        intent.putExtra("name",name);
        intent.putExtra("mid",mid);
        context.startActivity(intent);
    }


}
