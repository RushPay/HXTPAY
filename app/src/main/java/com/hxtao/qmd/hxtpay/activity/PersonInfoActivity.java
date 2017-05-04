package com.hxtao.qmd.hxtpay.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.AliReacharge;
import com.hxtao.qmd.hxtpay.event.TransferEvent;
import com.hxtao.qmd.hxtpay.event.TypeEvent;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hxtao.qmd.hxtpay.activity.AddInforActivity.TO_SELECT_PHOTO;

public class PersonInfoActivity extends BaseActivity {
    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.icon_rl_personinfo_act) RelativeLayout iconRlPersoninfoAct;
    @BindView(R.id.name_rl_personinfo_act) RelativeLayout nameRlPersoninfoAct;
    @BindView(R.id.phone_rl_personinfo_act) RelativeLayout phoneRlPersoninfoAct;
    @BindView(R.id.pwd_rl_personinfo_act) RelativeLayout pwdRlPersoninfoAct;
    @BindView(R.id.code_rl_personinfo_act) RelativeLayout codeRlPersoninfoAct;

    @BindView(R.id.icon_img_personinfo_ac) CircleImageView iconImgPersoninfoAc;
    @BindView(R.id.name_personinfo_act) TextView namePersoninfoAct;
    @BindView(R.id.phone_personinfo_act) TextView phonePersoninfoAct;
    @BindView(R.id.pay_rl_personinfo_act) RelativeLayout payRlPersoninfoAct;
    private String iconUrl;
    private String userName;
    private String userPhone;

    private String picPath;

    @Override
    public int layoutContentId() {
        return R.layout.activity_person_info;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        iconUrl = intent.getStringExtra("iconUrl");
        userName = intent.getStringExtra("userName");
        userPhone = intent.getStringExtra("userPhone");

        titleTv.setText("账户信息");
        ImageLoadUtil.disPlayImage(iconUrl, iconImgPersoninfoAc);
        namePersoninfoAct.setText(userName);
        phonePersoninfoAct.setText(userPhone);

        imgBack.setOnClickListener(this);

        //修改登录密码监听
        pwdRlPersoninfoAct.setOnClickListener(this);
        //查看个人二维码监听
        codeRlPersoninfoAct.setOnClickListener(this);
        //修改头像监听
        iconRlPersoninfoAct.setOnClickListener(this);
        //修改用户名监听
        nameRlPersoninfoAct.setOnClickListener(this);
        //修改支付密码监听
        payRlPersoninfoAct.setOnClickListener(this);
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
            case R.id.pwd_rl_personinfo_act://修改个人密码
                //ForgetPwdActivity
                Intent forgetintent = new Intent(PersonInfoActivity.this, ForgetPwdActivity.class);
                //EditerLoginPwdActivity
                startActivity(forgetintent);
                break;
            case R.id.code_rl_personinfo_act://二维码展示
                PersonCodeActivity.actionStart(PersonInfoActivity.this, iconUrl, userName, userPhone);
                break;
            case R.id.icon_rl_personinfo_act://修改头像
                Intent iconIntent = new Intent(PersonInfoActivity.this, SelectPicActivity.class);
                startActivityForResult(iconIntent, TO_SELECT_PHOTO);
                break;
            case R.id.name_rl_personinfo_act://修改用户名
                Intent nameIntent = new Intent(PersonInfoActivity.this, ModifyNameActivity.class);
                startActivityForResult(nameIntent, 200);
                break;
            case R.id.pay_rl_personinfo_act://修改支付密码
                Intent pwdIntent = new Intent(PersonInfoActivity.this, EditerPayActivity.class);
                startActivity(pwdIntent);
                break;
        }
    }

    public static void actionStart(Context context, String iconUrl, String userName, String userPhone) {
        Intent intent = new Intent(context, PersonInfoActivity.class);
        intent.putExtra("iconUrl", iconUrl);
        intent.putExtra("userName", userName);
        intent.putExtra("userPhone", userPhone);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
            String strPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
            if (!TextUtils.isEmpty(strPath)) {
                picPath = strPath;
                Log.e("onActivityResult", "最终选择的图片=" + picPath);
                ImageLoadUtil.disPlaySdCard(picPath, iconImgPersoninfoAc);
                //在此把图片上传
                editIcon(strPath);
            }
        }

        if (resultCode == Activity.RESULT_OK && requestCode == 200) {
            String name = data.getStringExtra("name");
//            Log.e("resultCode", name);
            userName=name;
            namePersoninfoAct.setText(userName);
        }
    }

    private void editIcon(String strPath) {
//        Log.e("strPath", strPath);
        final RequestParams re = new RequestParams(HXTUrl.HXTHTTP_EDITICON);
        re.addBodyParameter("sign", BaseApplication.getInstance().getSign());
        re.addBodyParameter("icon", new File(strPath));
        x.http().post(re, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("editIcon", result);
                Gson gson = new Gson();
                AliReacharge aliReacharge = gson.fromJson(result, AliReacharge.class);
                if (1 == aliReacharge.getStatus()) {
                    BaseApplication.getInstance().setIcon(aliReacharge.getData());
                    EventBus.getDefault().post(new TransferEvent(TypeEvent.TRANSFER));
                    ToastUtil.createToastConfig().ToastShow(PersonInfoActivity.this, "头像保存成功", 15000);
                } else {
                    ToastUtil.createToastConfig().ToastShow(PersonInfoActivity.this, "头像保存失败", 15000);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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
