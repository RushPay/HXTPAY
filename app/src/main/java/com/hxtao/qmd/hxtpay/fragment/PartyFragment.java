package com.hxtao.qmd.hxtpay.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.adapter.PartyListAdapter;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseFragment;
import com.hxtao.qmd.hxtpay.been.PartDataListBeen;
import com.hxtao.qmd.hxtpay.been.PartyListBeen;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PartyFragment extends BaseFragment {


    @BindView(R.id.list_partlist_act_lv) ListView listPartlistActLv;
    @BindView(R.id.part_swiperefresh) SwipeRefreshLayout partSwiperefresh;

    private List<PartDataListBeen> datePartyList;
    private PartyListAdapter partyListAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (msg.obj != null) {
                        PartyListBeen partyBeen = (PartyListBeen) msg.obj;
                        datePartyList.clear();
                        datePartyList.addAll(partyBeen.getPartyList());
                        partSwiperefresh.setRefreshing(false);
                        partyListAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    @Override
    public int layoutContentId() {
        return R.layout.fragment_party;
    }

    @Override
    public void initView() {

        datePartyList = new ArrayList<>();
        partyListAdapter = new PartyListAdapter(getContext(), datePartyList);
        listPartlistActLv.setAdapter(partyListAdapter);
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_ALLPARTLIST);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
//        Log.e("PartyFragment", BaseApplication.createApplication().getSign());
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("PartyFragment", result);
                if (result != null) {
                    PartyListBeen partData = PartListJsonUtils.getPartData(result);
                    if (partData != null) {
                        Message message = handler.obtainMessage();
                        String status = partData.getStatus();
                        message.what = Integer.valueOf(status);
                        message.obj = partData;
                        message.sendToTarget();
                    }

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                partSwiperefresh.setRefreshing(false);
                ToastUtil.createToastConfig().ToastShow(getContext(), "网络异常请检查网络", 15000);
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


        partSwiperefresh.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary,R.color.font_color_blue);
        partSwiperefresh.setSize(SwipeRefreshLayout.LARGE);
        partSwiperefresh.setProgressBackgroundColor(R.color.colorPrimaryDark);
        partSwiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_ALLPARTLIST);
                requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
//                        Log.e("onSuccess", result);
                        if (result != null) {
                            PartyListBeen partData = PartListJsonUtils.getPartData(result);
                            if (partData != null) {
                                Message message = handler.obtainMessage();
                                String status = partData.getStatus();
                                message.what = Integer.valueOf(status);
                                message.obj = partData;
                                message.sendToTarget();
                            }
                        }
                     }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ToastUtil.createToastConfig().ToastShow(getContext(), "网络异常请检查网络", 15000);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
