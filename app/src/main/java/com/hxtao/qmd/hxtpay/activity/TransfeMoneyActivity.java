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

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.adapter.TransferMoneyAdapter;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.PersonEntity;
import com.hxtao.qmd.hxtpay.utils.FriendListGson;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransfeMoneyActivity extends BaseActivity {


    @BindView(R.id.transfer_money_btn) Button transferMoneyBtn;
    @BindView(R.id.list_transfr_money_act) ListView listTransfrMoneyAct;

    private TransferMoneyAdapter transferMoneyAdapter;
    private List<PersonEntity> list=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    if (msg.obj!=null){//获取解析后的数据
                        list.clear();
                        list.addAll((List<PersonEntity>)msg.obj);
                        transferMoneyAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };
    @Override
    public int layoutContentId() {
        return R.layout.activity_transfe_money;
    }

    @Override
    public void initView() {

        //获取好友信息
        getFriendList();
        transferMoneyAdapter=new TransferMoneyAdapter(TransfeMoneyActivity.this,list);
        listTransfrMoneyAct.setAdapter(transferMoneyAdapter);
    }

    @Override
    public void initListener() {

        //listTransfrMoneyAct 点击事件 进行转账

        listTransfrMoneyAct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonEntity personEntity = list.get(position);
                Intent intent=new Intent(TransfeMoneyActivity.this,MoneyTransferActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",personEntity.getId());
                bundle.putString("username",personEntity.getUsername());
                bundle.putString("icon",personEntity.getIcon());
                intent.putExtra("person",bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.transfer_money_btn://确认转账按钮

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private void getFriendList() {
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_FRIENDLIST);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("Success", result);
                if (!TextUtils.isEmpty(result)) {
                    List<PersonEntity> transferList = FriendListGson.getTransferList(result);
                    if (transferList!=null&&transferList.size()>0){
                        Message message=handler.obtainMessage();
                        message.what=100;
                        message.obj=transferList;
                        message.sendToTarget();
                    }
                }else {
                    ToastUtil.createToastConfig().ToastShow(TransfeMoneyActivity.this,"网络异常请检查网络",1500);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(TransfeMoneyActivity.this,"网络异常请检查网络",1500);
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
