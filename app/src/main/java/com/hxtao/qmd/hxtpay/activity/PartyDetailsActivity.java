package com.hxtao.qmd.hxtpay.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.adapter.PartyDetilsNumAdapter;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.been.PartyDetailsInfoBeen;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.PartListJsonUtils;
import com.hxtao.qmd.hxtpay.utils.TimeUtils;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.widgets.SelectPicPopupWindow;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PartyDetailsActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.icon_part_details_act_img) CircleImageView iconPartDetailsActImg;
    @BindView(R.id.theme_part_details_act_tv) TextView themePartDetailsActTv;
    @BindView(R.id.address_part_details_act_tv) TextView addressPartDetailsActTv;
    @BindView(R.id.message_part_details_act_tv) TextView messagePartDetailsActTv;
    @BindView(R.id.rg_partdetails_radiogroup) RadioGroup rgPartdetailsRadiogroup;
    @BindView(R.id.time_part_details_act_tv) TextView timePartDetailsActTv;
    @BindView(R.id.number_part_details_act_tv) TextView numberPartDetailsActTv;
    @BindView(R.id.start_bill_part_details_act_btn) Button startBillPartDetailsActBtn;
    @BindView(R.id.isorganizer_part_details_act_tv) TextView isorganizerPartDetailsActTv;
    @BindView(R.id.numlist_part_details_act_lv) ListView numlistPartDetailsActLv;
    @BindView(R.id.bill_part_details_act_btn) Button billPartDetailsActBtn;
    @BindView(R.id.lucky_part_details_act_tv) TextView luckyTv;
    @BindView(R.id.lucky_part_details_act_btn) Button luckBtn;
    @BindView(R.id.paylucky_part_details_act_btn) Button payluckyPartDetailsActBtn;
    @BindView(R.id.paylucky_part_details_act_ll) LinearLayout payluckyPartDetailsActLl;

    private String p_id;
    private PartyDetailsInfoBeen partyDetailsInfo;
    private List<List<String>> numList = new ArrayList<>();
    private PartyDetilsNumAdapter partyDetilsNumAdapter;

    private String detail;
    private String address;
    private String theme;

    private boolean luckBoolean = false;

    //获得当前用户的id
    String perId = BaseApplication.createApplication().getId();
    String sponsorName = null;

    private int INTENTCODE = 1;
    //自定义的弹出框类  密码输入框
    private SelectPicPopupWindow menuWindow;


    private Handler handler = new Handler() {

        private boolean luckyBoolen;
        private String payMethod;
        private int size;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (msg.obj != null) {
                        partyDetailsInfo = (PartyDetailsInfoBeen) msg.obj;
//                        Log.e("ImgUrl", partyDetailsInfo.getIcon());
                        theme = partyDetailsInfo.getTitle();
                        address = partyDetailsInfo.getAddress();
                        detail = partyDetailsInfo.getDetail();
                        themePartDetailsActTv.setText(theme);
                        addressPartDetailsActTv.setText(address);
                        messagePartDetailsActTv.setText(detail);

                        sponsorName = partyDetailsInfo.getSponsor_id();
                        payMethod = partyDetailsInfo.getPayMethod();
                        List<List<String>> m_arrList = partyDetailsInfo.getM_arrList();
                        luckyBoolen = TextUtils.isEmpty(partyDetailsInfo.getLuckyMan());
                        if (!luckyBoolen) {//判断是否有幸运人
                            String luckyMan = partyDetailsInfo.getLuckyMan();
//                            Log.e("luckyMan",luckyMan);
                            String luckuManName = null;
//                            Log.e("numList.size()",""+numList.size());
                            for (int i = 0; i < m_arrList.size(); i++) {
                                List<String> stringList = m_arrList.get(i);
                                String id = stringList.get(0);
//                                Log.e("id",id);
                                if (TextUtils.equals(luckyMan, id)) {
                                    luckuManName = stringList.get(1);
//                                    Log.e("luckuManName",luckuManName);
                                }
                            }
                            luckyTv.setText("幸运人:" + luckuManName);
                        }


                        if (m_arrList != null && m_arrList.size() > 0) {
                            numList.clear();
                            numList.addAll(m_arrList);
                            size = numList.get(0).size();
                            partyDetilsNumAdapter.notifyDataSetChanged();
                        }

                        if (TextUtils.equals("1", partyDetailsInfo.getIs_organizer())) {//判断是否是发起人
//                            Log.e("partyDetailsInfo", "  " + size);
                            switch (size) {
                                case 3:
                                    rgPartdetailsRadiogroup.setVisibility(View.VISIBLE);
                                    startBillPartDetailsActBtn.setVisibility(View.VISIBLE);
                                    break;
                                case 6:
                                    String payState = null;
                                    for (int i = 0; i < numList.size(); i++) {
                                        List<String> stringList = numList.get(i);
                                        String id = stringList.get(0);
                                        if (TextUtils.equals(perId, id)) {
                                            payState = stringList.get(5);
                                        }
                                    }
                                        //是组织者显示生产幸运人控件
                                        payluckyPartDetailsActLl.setVisibility(View.VISIBLE);
                                        rgPartdetailsRadiogroup.setVisibility(View.VISIBLE);
                                        billPartDetailsActBtn.setVisibility(View.VISIBLE);
                                        payluckyPartDetailsActBtn.setVisibility(View.VISIBLE);

                                        if (luckyBoolen){
                                            billPartDetailsActBtn.setVisibility(View.VISIBLE);
                                        }else {
                                            billPartDetailsActBtn.setEnabled(false);
                                        }



//                                    if (TextUtils.equals("3",payMethod)){
//                                        payluckyPartDetailsActLl.setVisibility(View.VISIBLE);
//                                        rgPartdetailsRadiogroup.setVisibility(View.VISIBLE);
//                                        billPartDetailsActBtn.setVisibility(View.VISIBLE);
//                                        payluckyPartDetailsActBtn.setVisibility(View.VISIBLE);
//
//                                        if (luckyBoolen){
//                                            billPartDetailsActBtn.setVisibility(View.VISIBLE);
//                                        }else {
//                                            billPartDetailsActBtn.setEnabled(false);
//                                        }
//                                    }else {
//                                        payluckyPartDetailsActLl.setVisibility(View.VISIBLE);
//                                        rgPartdetailsRadiogroup.setVisibility(View.VISIBLE);
//                                        billPartDetailsActBtn.setVisibility(View.VISIBLE);
//                                    }


//                                    if (TextUtils.equals("1", payState)) {//结账状态 1:未付款 2:已付款
//                                        rgPartdetailsRadiogroup.setVisibility(View.VISIBLE);
//                                        billPartDetailsActBtn.setVisibility(View.VISIBLE);
//                                    } else {
//                                        rgPartdetailsRadiogroup.setVisibility(View.VISIBLE);
//                                        billPartDetailsActBtn.setVisibility(View.GONE);
//                                        if (TextUtils.isEmpty(luckyTv.getText().toString().trim())) {
//                                            luckBtn.setVisibility(View.VISIBLE);
//                                        }
//                                    }
                                    break;
                            }


                            partyDetilsNumAdapter.getIsOrganizer("1", partyDetailsInfo.getSponsor_id());
                        } else {

                            switch (size) {
                                case 3:
                                    isorganizerPartDetailsActTv.setText("参与者");
                                    break;
                                case 6:
                                    String payState = null;
                                    for (int i = 0; i < numList.size(); i++) {
                                        List<String> stringList = numList.get(i);
                                        String id = stringList.get(0);

                                        if (TextUtils.equals(perId, id)) {
                                            payState = stringList.get(5);
                                        }
                                    }
                                    if (TextUtils.equals("1", payState)) {
                                        rgPartdetailsRadiogroup.setVisibility(View.GONE);
                                        payluckyPartDetailsActLl.setVisibility(View.VISIBLE);
                                        billPartDetailsActBtn.setVisibility(View.VISIBLE);

                                    } else {
                                        rgPartdetailsRadiogroup.setVisibility(View.GONE);
                                        billPartDetailsActBtn.setVisibility(View.GONE);
                                    }

                                    break;
                            }

                        }
                        timePartDetailsActTv.setText(TimeUtils.createTimeUtils().getDateString(new Long(partyDetailsInfo.getAddtime())));
                        numberPartDetailsActTv.setText(partyDetailsInfo.getCount()+"人");
                    }
                    break;
                case 0:

                    break;
            }
        }
    };


    @Override
    public int layoutContentId() {
        return R.layout.activity_party_details;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        p_id = intent.getStringExtra("p_id");
//        Log.e("PartyDetailsActivity", p_id);
        //控件数据的初始化
        initViewData();
        getPartyInfo(p_id);

        partyDetilsNumAdapter = new PartyDetilsNumAdapter(PartyDetailsActivity.this, numList, p_id);
        numlistPartDetailsActLv.setAdapter(partyDetilsNumAdapter);

    }

    private void initViewData() {
        imgBack.setImageResource(R.mipmap.back_title_bg);
        titleTv.setText("聚会");
        imgBack.setOnClickListener(this);
        startBillPartDetailsActBtn.setOnClickListener(this);
        billPartDetailsActBtn.setOnClickListener(this);
        luckBtn.setOnClickListener(this);
        rgPartdetailsRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.code_partdetails_rb://聚会二维码
                        CodePartyActivity.actionStart(PartyDetailsActivity.this, p_id, theme, address, detail);
                        rgPartdetailsRadiogroup.clearCheck();
                        break;
                    case R.id.invite_partdetails_rb://邀请朋友
                        Intent intent = new Intent(PartyDetailsActivity.this, InviteFriendActivity.class);
                        intent.putExtra("pId", p_id);
                        startActivity(intent);
                        rgPartdetailsRadiogroup.clearCheck();
                        break;
                    case R.id.transfer_partdetails_rb://组织者转让
                        Intent intentTransfer = new Intent(PartyDetailsActivity.this, TransferActivity.class);
                        intentTransfer.putExtra("pId", p_id);
                        startActivity(intentTransfer);
                        rgPartdetailsRadiogroup.clearCheck();
                        break;
                    case R.id.cancel_partdetails_rb://取消聚会
                        cancelParty(p_id);
                        rgPartdetailsRadiogroup.clearCheck();
//                        ToastUtil.createToastConfig().ToastShow(PartyDetailsActivity.this,"22222",15000);
                        break;
                }
            }
        });
    }

    @Override
    public void initListener() {
        payluckyPartDetailsActBtn.setOnClickListener(this);
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
            case R.id.start_bill_part_details_act_btn://发起结账
                Intent intent = new Intent(PartyDetailsActivity.this, BillActivity.class);
                intent.putExtra("pid", p_id);
                startActivityForResult(intent, INTENTCODE);

                break;
            case R.id.bill_part_details_act_btn://个人结账
                //获取聚会成员的id
                String perPayCode = null;
                //当前用户需要支付的金额
                String payMonery = null;
//                Log.e("personId", perId);
                for (int i = 0; i < numList.size(); i++) {
                    List<String> stringList = numList.get(i);
                    String id = stringList.get(0);
                    if (TextUtils.equals(perId, id)) {
                        perPayCode = stringList.get(3);
                        payMonery = stringList.get(4);
                    }
                }
                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(PartyDetailsActivity.this, itemsOnClick, perPayCode, payMonery);
                //显示窗口
                menuWindow.showAtLocation(PartyDetailsActivity.this.findViewById(R.id.activity_party_details),
                        Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位


                menuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //消失再次获取数据
                        getPartyInfo(p_id);
                    }
                });
                break;

            case R.id.lucky_part_details_act_btn:
                productionLuckyMan();
                break;
            case R.id.paylucky_part_details_act_btn://土豪模式时 发起者可以选择生产幸运人
                productionLuckyMan();
                break;
        }
    }

    //获取聚会信息
    private void getPartyInfo(String p_id) {
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_PARTYINFO);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("p_id", p_id);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("getPartyInfo", result);
                if (result != null) {
                    PartyDetailsInfoBeen partyDetailsInfoBeen = PartListJsonUtils.partyDetailsInfoUtils(result);
//                    Log.e("getPartyInfo", partyDetailsInfoBeen.toString());
                    if (partyDetailsInfoBeen != null) {
                        Message message = handler.obtainMessage();
                        message.what = partyDetailsInfoBeen.getStatus();
                        message.obj = partyDetailsInfoBeen;
                        message.sendToTarget();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(PartyDetailsActivity.this, "网络异常请检查网络", 15000);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //取消聚会
    private void cancelParty(String strP_id) {
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_CANCELPARTY);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("p_id", strP_id);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("cancelparty", result);
                if (result != null) {
                    Gson gson = new Gson();
                    AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                    ToastUtil.createToastConfig().ToastShow(PartyDetailsActivity.this, addFriendResult.getInfo(), 15000);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(PartyDetailsActivity.this, "网络异常请检查网络", 15000);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {

            }
        }
    };

    public void productionLuckyMan() {
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_LUCKYMAN);
        requestParams.addBodyParameter("sign", BaseApplication.getInstance().getSign());
        requestParams.addBodyParameter("p_id", p_id);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("productionLuckyMan",result);
                if (result != null) {
                    Gson gson = new Gson();
                    AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                    switch (addFriendResult.getStatus()) {
                        case 1:
                            //生成幸运人成功后 在次获取聚会详情数据 模拟数据刷新
                            getPartyInfo(p_id);
                            break;
                        case 0:
//                            Log.e("productionLuckyMan",addFriendResult.getInfo());
                            ToastUtil.createToastConfig().ToastShow(PartyDetailsActivity.this, addFriendResult.getInfo(), 15000);
                            break;

                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(PartyDetailsActivity.this, "网络异常请检查网络", 15000);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //再次获获取聚会数据
        if (requestCode==INTENTCODE&&resultCode==INTENTCODE){
            getPartyInfo(p_id);
        }

    }

    public static void actionStart(Context context, String p_id) {
        Intent intent = new Intent(context, PartyDetailsActivity.class);
        intent.putExtra("p_id", p_id);
        context.startActivity(intent);
    }
}
