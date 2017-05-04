package com.hxtao.qmd.hxtpay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ModifyNameActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.img_more) ImageView imgMore;
    @BindView(R.id.name_ed_modify_act) EditText nameEdModifyAct;
    @BindView(R.id.diss_img_modify_act) ImageView dissImgModifyAct;


    private Map<String,String> requestMap;
    private String userNaem;

    @Override
    public int layoutContentId() {
        return R.layout.activity_modify_name;
    }

    @Override
    public void initView() {
        titleTv.setText("姓名");
        imgBack.setImageResource(R.mipmap.cancel_bg);
        imgMore.setImageResource(R.mipmap.save_normasll_bg);
        imgMore.setEnabled(false);

        nameEdModifyAct.setText(BaseApplication.getInstance().getUsername());
    }

    @Override
    public void initListener() {


        nameEdModifyAct.addTextChangedListener(new TextWatcher() {
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
                    dissImgModifyAct.setVisibility(View.VISIBLE);
                    imgMore.setImageResource(R.mipmap.save_pressed_bg);
                    imgMore.setEnabled(true);
                }else {
                    dissImgModifyAct.setVisibility(View.GONE);
                    imgMore.setImageResource(R.mipmap.save_normasll_bg);
                    imgMore.setEnabled(false);
                }
            }
        });
        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        dissImgModifyAct.setOnClickListener(this);
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
                case R.id.diss_img_modify_act:
                    nameEdModifyAct.setText("");
                    break;
                case R.id.img_more://保存修改
                    userNaem = nameEdModifyAct.getText().toString();
                    requestMap=new HashMap<>();
                    requestMap.put("sign",BaseApplication.getInstance().getSign());
                    requestMap.put("username",userNaem);
                    XutilsHttpPostUtils.hxtPostUtils(requestMap, HXTUrl.HXTHTTP_EDITUSERNAME, new IOnLoadDataListener() {
                        @Override
                        public void onSuccess(String result, String tagUrl) {
                            Gson gson=new Gson();
                            AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                            if (1==addFriendResult.getStatus()){
                                BaseApplication.getInstance().setUsername(userNaem);
                                ToastUtil.createToastConfig().ToastShow(ModifyNameActivity.this,"用户名保存成功",15000);
                                EventBus.getDefault().post(new TransferEvent(TypeEvent.TRANSFER));
                                Intent intent=new Intent();
                                intent.putExtra("name",userNaem);
                                setResult(Activity.RESULT_OK,intent);
                                finish();
                            }else {
                                ToastUtil.createToastConfig().ToastShow(ModifyNameActivity.this,"用户名保存失败",15000);
                            }
                        }
                        @Override
                        public void onError(String tagUrl) {

                        }

                        @Override
                        public void networkError(String tagUrl) {

                        }
                    });
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
