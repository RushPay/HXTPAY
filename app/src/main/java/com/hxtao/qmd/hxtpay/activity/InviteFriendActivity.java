package com.hxtao.qmd.hxtpay.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.adapter.InviteAdapter;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.PersonInfo;
import com.hxtao.qmd.hxtpay.utils.FriendListGson;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteFriendActivity extends BaseActivity {

    @BindView(R.id.invite_btn_act) Button inviteBtnAct;
    @BindView(R.id.invite_act_listView) ListView inviteActListView;


    public InviteAdapter inviteAdapter;
    public List<PersonInfo> friendList;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    if (msg.obj!=null){
                        friendList.clear();
                        List<PersonInfo> list= (List<PersonInfo>) msg.obj;
                        friendList.addAll(list);
                        inviteAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
    private String pId;

    @Override
    public int layoutContentId() {
        return R.layout.activity_invite_friend;
    }

    @Override
    public void initView() {

        pId = getIntent().getStringExtra("pId");

        friendList=new ArrayList<>();
        inviteAdapter=new InviteAdapter(InviteFriendActivity.this,friendList);
        inviteActListView.setAdapter(inviteAdapter);

        inviteBtnAct.setOnClickListener(this);

        //获取好友列表
        RequestParams requestParams=new RequestParams(HXTUrl.HXTHTTP_FRIENDLIST);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result!=null){
                    List<PersonInfo> personList = FriendListGson.getFriendList(result);
                    if (personList!=null&&personList.size()>0){
                        Message message=handler.obtainMessage();
                        message.what=100;
                        message.obj=personList;
                        message.sendToTarget();
                    }
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

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invite_btn_act:

                inviteBtnAct.setEnabled(false);
                if (!inviteAdapter.getCheckMap().isEmpty()) {
                    Map<String, String> checkMap = inviteAdapter.getCheckMap();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Map.Entry<String, String> entry : checkMap.entrySet()) {
//                    Log.e("InviteFriendActivity","Key:"+entry.getKey()+"---Value:"+entry.getValue());
                        stringBuffer.append(entry.getValue()).append(",");
                    }
                    String id_str = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
//                Log.e("stringBuffer",substring);
                    RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_INVITE);
                    requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
                    requestParams.addBodyParameter("p_id", pId);
                    requestParams.addBodyParameter("id_str", id_str);


                    x.http().post(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("InviteFriendActivity", result);
                            finish();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
//                        Log.e("onSuccess",ex.toString());
                            inviteBtnAct.setEnabled(true);
                            ToastUtil.createToastConfig().ToastShow(InviteFriendActivity.this, "网络异常请检查网络", 15000);
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                    break;
                }else {
                    finish();
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
