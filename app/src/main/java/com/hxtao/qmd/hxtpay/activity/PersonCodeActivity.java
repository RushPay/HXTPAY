package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonCodeActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.icon_img_personcode_act) CircleImageView iconImgPersoncodeAct;
    @BindView(R.id.name_tv_personcode_act) TextView nameTvPersoncodeAct;
    @BindView(R.id.phone_tv_personcode_act) TextView phoneTvPersoncodeAct;
    @BindView(R.id.code_img_personcode_act) ImageView codeImgPersoncodeAct;

    private String iconUrl;
    private String userName;
    private String userPhone;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (msg.obj != null) {
                        codeImgPersoncodeAct.setImageBitmap((Bitmap) msg.obj);
                    }
                    break;
            }
        }
    };

    @Override
    public int layoutContentId() {
        return R.layout.activity_person_code;
    }

    @Override
    public void initView() {

        Intent intent = getIntent();
        iconUrl = intent.getStringExtra("iconUrl");
        userName = intent.getStringExtra("userName");
        userPhone = intent.getStringExtra("userPhone");

        titleTv.setText("我的二维码");
        titleTv.setTextColor(getResources().getColor(R.color.colorWhite));
        imgBack.setOnClickListener(this);
        imgBack.setImageResource(R.mipmap.back_titile_white_bg);

        ImageLoadUtil.disPlayImage(iconUrl, iconImgPersoncodeAct);
        nameTvPersoncodeAct.setText(userName);
        phoneTvPersoncodeAct.setText("抢买单帐号:" + userPhone);

       String imgurl= HXTUrl.HXTHTTP_GETOWNQRCODE+BaseApplication.createApplication().getSign();
//        Log.e("imgurl",imgurl);
//        Log.e("disPlayImage","http://qmd.hxtao.net/api/member/getOwnQRcode?sign=7ff13fffb954630662777173faa7ff8f");
        ImageLoadUtil.disPlayImage(imgurl,codeImgPersoncodeAct);
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

    public static void actionStart(Context context, String iconUrl, String userName, String userPhone) {
        Intent intent = new Intent(context, PersonCodeActivity.class);
        intent.putExtra("iconUrl", iconUrl);
        intent.putExtra("userName", userName);
        intent.putExtra("userPhone", userPhone);
        context.startActivity(intent);
    }
}
