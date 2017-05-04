package com.hxtao.qmd.hxtpay.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.adapter.BillDetailsAdapter;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.PersonBillDetailsEntity;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.widgets.RefreshLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonBillActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.list_person_bill_act) ListView listPersonBillAct;
    @BindView(R.id.refresh_person_bill_act) RefreshLayout refreshPersonBillAct;

    private List<PersonBillDetailsEntity.BillData> list = new ArrayList<>();
    private BillDetailsAdapter billDetailsAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (msg.obj != null) {
                        list.clear();
                        list.addAll((List<PersonBillDetailsEntity.BillData>) msg.obj);
                        billDetailsAdapter.notifyDataSetChanged();
                    }
                    break;
                case 200:
                    if (msg.obj != null) {
                        list.addAll((List<PersonBillDetailsEntity.BillData>) msg.obj);
                        billDetailsAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    private int pageNum = 1;

    private int lodaPage=2;
    @Override
    public int layoutContentId() {
        return R.layout.activity_person_bill;
    }

    @Override
    public void initView() {
        titleTv.setText("账单详情");
        imgBack.setImageResource(R.mipmap.back_title_bg);
        getBillInfo(pageNum);

        billDetailsAdapter = new BillDetailsAdapter(PersonBillActivity.this, list);
        listPersonBillAct.setAdapter(billDetailsAdapter);

        // 设置下拉进度的主题颜色
        refreshPersonBillAct.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);



    }

    @Override
    public void initListener() {
        imgBack.setOnClickListener(this);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        refreshPersonBillAct.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新数据
                getBillInfo(1);
                //设置不刷新状态,将下拉刷新进度收起
            }        });

        //设置上拉加载更多
        refreshPersonBillAct.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                getBillInfo(lodaPage);
                lodaPage++;
            }
        });
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

    public void getBillInfo(final int page) {
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_ACCOUNTINFOLIST);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("page", String.valueOf(page));
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("onSuccess", result);
                if (result != null) {
                    Gson gson = new Gson();
                    PersonBillDetailsEntity personBillDetailsEntity = gson.fromJson(result, PersonBillDetailsEntity.class);
                    switch (personBillDetailsEntity.getStatus()) {
                        case 1:
                            Message message = handler.obtainMessage();
                            if (page==1){
                                message.what = 100;
                                message.obj = personBillDetailsEntity.getData();
                                message.sendToTarget();
                                refreshPersonBillAct.setRefreshing(false);
                            }else {
                                message.what = 200;
                                message.obj = personBillDetailsEntity.getData();
                                message.sendToTarget();
                                refreshPersonBillAct.setLoading(false);
                            }
                            break;
                        case 0:
                            ToastUtil.createToastConfig().ToastShow(PersonBillActivity.this, personBillDetailsEntity.getInfo(), 1500);
                            refreshPersonBillAct.setLoading(false);
                            break;
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(PersonBillActivity.this, "网络异常请检查网络", 1500);
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
