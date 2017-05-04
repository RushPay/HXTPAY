package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class CodePartyActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.organizer_tv_codeparty_act) TextView organizerTvCodepartyAct;
    @BindView(R.id.theme_tv_codeparty_act) TextView themeTvCodepartyAct;
    @BindView(R.id.details_tv_codeparty_act) TextView detailsTvCodepartyAct;
    @BindView(R.id.address_tv_codeparty_act) TextView addressTvCodepartyAct;
    @BindView(R.id.code_img_codeparty_act) ImageView codeImgCodepartyAct;
    private String pid;
    private String detials;
    private String theme;
    private String address;

    @Override
    public int layoutContentId() {
        return R.layout.activity_code_party;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        theme = intent.getStringExtra("theme");
        address = intent.getStringExtra("address");
        detials = intent.getStringExtra("detials");
        Log.e("CodePartyActivity","theme:"+theme+"---:address:"+address+"---:detials"+detials);
        imgBack.setImageResource(R.mipmap.back_title_bg);
        imgBack.setOnClickListener(this);

        titleTv.setText("聚会二维码");
        titleTv.setTextColor(getResources().getColor(R.color.colorWhite));
        String imgUrl=HXTUrl.HXTHTTP_GETPARTYQRCODE+ BaseApplication.getInstance().getSign()+"&id="+pid;
        Log.e("CodePartyActivity",imgUrl);
        ImageLoadUtil.disPlayImage(imgUrl,codeImgCodepartyAct);
        organizerTvCodepartyAct.setText(BaseApplication.getInstance().getUsername());
        themeTvCodepartyAct.setText(theme);
        detailsTvCodepartyAct.setText("聚会"+detials);
        addressTvCodepartyAct.setText("聚会地址:  "+address);

    }

    @Override
    public void initListener() {

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
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    public static void actionStart(Context context,String pid,String theme,String address,String detials){
        Intent intent=new Intent(context,CodePartyActivity.class);
        intent.putExtra("pid",pid);
        intent.putExtra("theme",theme);
        intent.putExtra("address",address);
        intent.putExtra("detials",detials);
        context.startActivity(intent);
    }
}
