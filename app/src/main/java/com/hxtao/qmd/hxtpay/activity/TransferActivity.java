package com.hxtao.qmd.hxtpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.adapter.TransferAdapter;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.been.PartyNumber;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.PartListJsonUtils;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransferActivity extends BaseActivity {

    @BindView(R.id.choose_tranfer_btn) Button chooseTranferBtn;
    @BindView(R.id.party_number_transfer_lv) ListView partyNumberTransferLv;
    private String p_id;
    private List<PartyNumber.NumberInfo> numberInfoList;
    private TransferAdapter transferAdapter;
    private String numPostion;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (msg.obj != null) {
                        numberInfoList.clear();
                        numberInfoList.addAll((List<PartyNumber.NumberInfo>) msg.obj);
                        transferAdapter.notifyDataSetChanged();
                    }
                    break;
                case 200:
                    finish();
                    break;
            }
        }
    };

    @Override
    public int layoutContentId() {
        return R.layout.activity_transfer;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        p_id = intent.getStringExtra("pId");

        //访问网络获取聚会内成员信息
        getPartyNumData(p_id);
        numberInfoList=new ArrayList<>();
        transferAdapter=new TransferAdapter(TransferActivity.this,numberInfoList);
        partyNumberTransferLv.setAdapter(transferAdapter);


    }

    @Override
    public void initListener() {
        partyNumberTransferLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i <numberInfoList.size() ; i++) {

                    if (i==position){
                        PartyNumber.NumberInfo numberInfo = numberInfoList.get(i);
                        numberInfo.setChoose(true);
                        numPostion = numberInfo.getId();
                        ToastUtil.createToastConfig().ToastShow(TransferActivity.this, numPostion, 15000);
                    }else {
                        PartyNumber.NumberInfo numberInfo = numberInfoList.get(i);
                        numberInfo.setChoose(false);
                    }
                }

                transferAdapter.notifyDataSetChanged();
            }
        });

        chooseTranferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tranferPartyNumData(p_id,numPostion);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    //初始化加载聚会内成员数据
    private void getPartyNumData(String partyId) {
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_PARTYMEMBERLIST);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("p_id", partyId);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("getPartyNumData", result);
                if (result != null) {
                    PartyNumber partyNumber = PartListJsonUtils.partyNumberInfo(result);
                    if (TextUtils.equals("1", partyNumber.getStatus())) {
                        Message message = handler.obtainMessage();
                        message.what = 100;
                        message.obj = partyNumber.getNumberInfoList();
                        message.sendToTarget();
                    } else {
                        ToastUtil.createToastConfig().ToastShow(TransferActivity.this, partyNumber.getInfo(), 15000);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(TransferActivity.this, "网络异常请检查网络", 15000);
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

    //转让聚会到聚会某人的方法
    private void tranferPartyNumData(String partyId,String numId) {
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_TRANSFERORGANIZER);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("p_id", partyId);
        requestParams.addBodyParameter("mid",numId);

//        Log.e("tranferPartyNumData"," p_id:"+p_id+"   mid:"+numId);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("getPartyNumData", result);
                if (result != null) {
                    Gson gson=new Gson();
                    AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                    if (TextUtils.equals("1",addFriendResult.getInfo())){
                        Message message=handler.obtainMessage();
                        message.what=200;
                        message.sendToTarget();
                    }else {
                        ToastUtil.createToastConfig().ToastShow(TransferActivity.this,addFriendResult.getInfo(), 15000);

                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(TransferActivity.this, "网络异常请检查网络", 15000);
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
