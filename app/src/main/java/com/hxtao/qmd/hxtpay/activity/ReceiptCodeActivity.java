package com.hxtao.qmd.hxtpay.activity;

import android.os.Bundle;
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

public class ReceiptCodeActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.code_img_receiptcode_act) ImageView codeImgReceiptcodeAct;

    @Override
    public int layoutContentId() {
        return R.layout.activity_receipt_code;
    }

    @Override
    public void initView() {
        imgBack.setOnClickListener(this);
        titleTv.setText("我的收款码");

        ImageLoadUtil.disPlayImage(HXTUrl.HXTHTTP_GETOWNMONEYQRCODE+ BaseApplication.getInstance().getSign(),codeImgReceiptcodeAct);
//        final Map<String ,String> map=new HashMap<>();
//        map.put("sign", BaseApplication.getInstance().getSign());
//       new Thread(){
//           @Override
//           public void run() {
//               super.run();
//               PostRequest.getGetHttp(map, HXTUrl.HXTHTTP_GETOWNMONEYQRCODE, new BitmapCallBack() {
//                   @Override
//                   public void bitmapCall(String url, Bitmap bitmap) {
//                       Log.e("bitmapCall","bitmapCall");
//                       codeImgReceiptcodeAct.setImageBitmap(bitmap);
//                   }
//               });
//           }
//       }.start();
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

}
