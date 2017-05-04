package com.hxtao.qmd.hxtpay.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.activity.KitingActivity;
import com.hxtao.qmd.hxtpay.activity.PersonBillActivity;
import com.hxtao.qmd.hxtpay.activity.PersonInfoActivity;
import com.hxtao.qmd.hxtpay.activity.PersonSetActivity;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.app.Constants;
import com.hxtao.qmd.hxtpay.base.BaseFragment;
import com.hxtao.qmd.hxtpay.been.PersonFragmentEntity;
import com.hxtao.qmd.hxtpay.event.TransferEvent;
import com.hxtao.qmd.hxtpay.event.TypeEvent;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.utils.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends BaseFragment {


    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.icon_person_fra) CircleImageView iconPersonFra;
    @BindView(R.id.name_person_fra) TextView namePersonFra;
    @BindView(R.id.phone_person_fra) TextView phonePersonFra;
    @BindView(R.id.balance_person_btn_frg) Button balancePersonBtnFrg;
    @BindView(R.id.bill_person_btn_frg) Button billPersonBtnFrg;
    @BindView(R.id.img_more) ImageView imgMoreSet;
    @BindView(R.id.img_back) ImageView imgMessage;
    @BindView(R.id.rl_person_fra) RelativeLayout rlPersonFra;
    @BindView(R.id.share_person_rl_frg) RelativeLayout sharePersonRlFrg;
    @BindView(R.id.kiting_btn_person_fra) Button kitingBtnPersonFra;

    private PersonFragmentEntity.InfoData infoData = null;

    private PopupWindow sharePop;
    private View popView = null;
    private IWXAPI api = null;
    private String iconUrl = null;
    private String userName = null;
    private String userPhone = null;
    private ImageView wxShareImg;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (msg.obj != null) {
                        infoData = (PersonFragmentEntity.InfoData) msg.obj;
                        iconUrl = BaseApplication.createApplication().getIcon();
                        ImageLoadUtil.disPlayImage(iconUrl, iconPersonFra);
                        if (!TextUtils.isEmpty(infoData.getUsername())) {
                            userName = infoData.getUsername();
                            namePersonFra.setText(userName);
                        }
                        if (!TextUtils.isEmpty(infoData.getAccount())) {
                            userPhone = infoData.getAccount();
                            phonePersonFra.setText(userPhone);
                        }
                        if (!TextUtils.isEmpty(infoData.getMoney())) {
                            balancePersonBtnFrg.setText(infoData.getMoney() + "元");
                        }
                        if (!TextUtils.isEmpty(infoData.getCount())) {
                            billPersonBtnFrg.setText(infoData.getCount() + "笔");
                        }
                    }
                    break;
            }
        }
    };
    private ImageView wxfShareImg;
    private TextView canclePopTv;

    @Override
    public int layoutContentId() {
        return R.layout.fragment_person;
    }

    @Override
    public void initView() {
//        ShareSDK.initSDK(getContext(),"1d4507ee3a150");

        titleTv.setText("我的");
        imgMoreSet.setImageResource(R.mipmap.person_set_bg);
        imgMessage.setImageResource(R.mipmap.person_message_bg);
        imgMoreSet.setOnClickListener(this);
        rlPersonFra.setOnClickListener(this);
        getPersonInfo();

        api = WXAPIFactory.createWXAPI(getContext(), Constants.APP_ID);
        popView = LayoutInflater.from(getContext()).inflate(R.layout.share_pup_layout, null);
        wxShareImg = (ImageView) popView.findViewById(R.id.wx_share_img);
        wxfShareImg = (ImageView) popView.findViewById(R.id.wxf_share_img);
        canclePopTv = (TextView) popView.findViewById(R.id.cancle_share_tv);
    }

    @Override
    public void initListener() {
        billPersonBtnFrg.setOnClickListener(this);

        //分享
        sharePersonRlFrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSharePop(v);
            }
        });

        wxShareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享给微信朋友
                wxShareMethod();
            }
        });
        wxfShareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享到微信朋友圈
                wxFShareMethod();
            }
        });


        canclePopTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePop.dismiss();
            }
        });

        //提现
        kitingBtnPersonFra.setOnClickListener(this);
    }


    private void initSharePop(View v) {
        sharePop = new PopupWindow();
        sharePop.setContentView(popView);
        //设置宽高
        sharePop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        sharePop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        sharePop.setBackgroundDrawable(new ColorDrawable(0xffffffff));
        sharePop.setOutsideTouchable(false);
        sharePop.setFocusable(true);
//                sharePop.setAnimationStyle(R.style.mypopwindow_anim_style);
        sharePop.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    private void wxShareMethod() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.rushtopay.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title =getResources().getString(R.string.share_title);
        msg.description = getResources().getString(R.string.share_description);
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
        msg.thumbData = Util.bmpToByteArray(thumb, true);  // 设置缩略图
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        //SendMessageToWX.Req.WXSceneTimeline   分享到朋友圈
        //  SendMessageToWX.Req.WXSceneSession  分享给个人或群组
        req.scene = SendMessageToWX.Req.WXSceneSession;
//                req.openId = getOpenId();
        api.sendReq(req);
    }

    private void wxFShareMethod() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.rushtopay.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title =getResources().getString(R.string.share_title);
        msg.description = getResources().getString(R.string.share_description);
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.appicon);
        msg.thumbData = Util.bmpToByteArray(thumb, true);  // 设置缩略图
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        //SendMessageToWX.Req.WXSceneTimeline   分享到朋友圈
        //  SendMessageToWX.Req.WXSceneSession  分享给个人或群组
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//                req.openId = getOpenId();
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_person_fra://个人信息详情
                PersonInfoActivity.actionStart(getContext(), iconUrl, userName, userPhone);
                break;
            case R.id.bill_person_btn_frg://账单详情
                Intent intent = new Intent(getActivity(), PersonBillActivity.class);
                startActivity(intent);
                break;
            case R.id.img_more://跳转设置界面
                Intent intentSet = new Intent(getActivity(), PersonSetActivity.class);
                startActivity(intentSet);
                break;
            case R.id.img_back://跳转消息界面

                break;
            case R.id.kiting_btn_person_fra://tixian
                KitingActivity.startAction(getContext());
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        //注册事件
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getPersonInfo() {
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_MEMBERINFO);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("getPersonInfo", result);
                if (result != null) {
                    Gson gson = new Gson();
                    PersonFragmentEntity personFragmentEntity = gson.fromJson(result, PersonFragmentEntity.class);
                    switch (personFragmentEntity.getStatus()) {
                        case 1:
                            Message message = handler.obtainMessage();
                            message.what = 100;
                            message.obj = personFragmentEntity.getData();
                            message.sendToTarget();
                            break;
                        case 0:
                            ToastUtil.createToastConfig().ToastShow(getContext(), personFragmentEntity.getInfo(), 1500);
                            break;
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(getContext(), "网络异常请检查网络", 1500);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateInfo(TransferEvent transferEvent) {
        if (TypeEvent.TRANSFER == transferEvent.getType()) {
            getPersonInfo();
        }
    }
}
