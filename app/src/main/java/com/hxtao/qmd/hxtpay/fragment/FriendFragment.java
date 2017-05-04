package com.hxtao.qmd.hxtpay.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.activity.QueryFriendActivity;
import com.hxtao.qmd.hxtpay.activity.ShowAddActivity;
import com.hxtao.qmd.hxtpay.adapter.LetterListAdapter;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseFragment;
import com.hxtao.qmd.hxtpay.been.AliReacharge;
import com.hxtao.qmd.hxtpay.been.FriendLetterListBeen;
import com.hxtao.qmd.hxtpay.been.FriendListBeen;
import com.hxtao.qmd.hxtpay.hxtinterface.IOnLoadDataListener;
import com.hxtao.qmd.hxtpay.utils.FriendListGson;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.utils.XutilsHttpPostUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.badgeview.QBadgeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment {


    @BindView(R.id.title_tv) TextView titleTv;

    @BindView(R.id.list_lv_friendFra) ListView listLvFriendFra;

    public List<FriendLetterListBeen> infoList;
    @BindView(R.id.showadd_ll_friendFra) LinearLayout showaddLlFriendFra;
    @BindView(R.id.img_more) ImageView imgMore;
    @BindView(R.id.swiperefrseh_friend_fra) SwipeRefreshLayout swiperefrsehFriendFra;

    //设置消息未读的提示
    @BindView(R.id.badge_rl_friend_fra) RelativeLayout badgeRlFriendFra;

    private LetterListAdapter letterListAdapter;

    private String code = "1";

    private Map<String,String> massageMap=null;

    private int messageCount=-1;

    private QBadgeView qBadgeView=null;
    @Override
    public int layoutContentId() {
        return R.layout.fragment_friend;
    }


    public Handler handler = new Handler() {




        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (msg.obj != null) {
                        FriendListBeen friendListBeen = (FriendListBeen) msg.obj;
                        String status = friendListBeen.getStatus();
                        if (TextUtils.equals(code, status)) {
                            swiperefrsehFriendFra.setRefreshing(false);
                            infoList.clear();
                            infoList.addAll(friendListBeen.getFriendLetterListBeenList());
                            letterListAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.createToastConfig().ToastShow(getContext(), "网络异常获取好友列表失败,请重试", 15000);
                        }

                    }
                    break;
                case 200:
                    messageCount= (Integer) msg.obj;

                    if (0!=messageCount){
                        Log.e("messageCount",""+messageCount);
                        qBadgeView.bindTarget(badgeRlFriendFra).setBadgeNumber(messageCount).setGravityOffset(0, 0, true);
                    }else {
                        Log.e("messageCount",""+messageCount);
                        qBadgeView.hide(true);
                    }
                    break;
            }
        }
    };

    @Override
    public void initView() {
        titleTv.setText("朋友");
        imgMore.setImageResource(R.mipmap.addfriend_bg);
        infoList = new ArrayList<>();
        letterListAdapter = new LetterListAdapter(getContext(), infoList);
        listLvFriendFra.setAdapter(letterListAdapter);

        qBadgeView=new QBadgeView(getContext());

        //访问网络获取好友列表数据
        getFriendList();

        new Runnable(){
            @Override
            public void run() {
//                getAddMessage();
                handler.postDelayed(this,5000);
            }
        }.run();

    }

    private void getAddMessage(){
        massageMap=new HashMap<>();
        massageMap.put("sign",BaseApplication.getInstance().getSign());
        XutilsHttpPostUtils.hxtPostUtils(massageMap, HXTUrl.HXTHTTP_ADDFRIENDMESSAGECOUNT, new IOnLoadDataListener() {
            @Override
            public void onSuccess(String result, String tagUrl) {
                Log.e("Success", result);
                Gson gson=new Gson();
                AliReacharge aliReacharge = gson.fromJson(result, AliReacharge.class);
                if (1==aliReacharge.getStatus()){
                    Message message=handler.obtainMessage();
                    message.what=200;
                    message.obj= Integer.valueOf(aliReacharge.getData());
                    message.sendToTarget();
                }
            }

            @Override
            public void onError(String tagUrl) {

            }

            @Override
            public void networkError(String tagUrl) {

            }
        });
    }




    private void getFriendList() {
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_FRIENDLIST);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("Success", result);
                if (!TextUtils.isEmpty(result)) {
                    FriendListBeen jsonData = FriendListGson.getJsonData(result);
                    if (jsonData != null) {
                        Message message = handler.obtainMessage();
                        message.what = 100;
                        message.obj = jsonData;
                        message.sendToTarget();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.e("onError", ex.toString());
                swiperefrsehFriendFra.setRefreshing(false);
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
        showaddLlFriendFra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看是否有新的朋友添加
                Intent intent = new Intent(getContext(), ShowAddActivity.class);
                startActivity(intent);
            }
        });

        imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查找好友
                Intent intent = new Intent(getContext(), QueryFriendActivity.class);
                startActivity(intent);
            }
        });

        swiperefrsehFriendFra.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.font_color_blue);
        swiperefrsehFriendFra.setSize(SwipeRefreshLayout.LARGE);
        swiperefrsehFriendFra.setProgressBackgroundColor(R.color.colorPrimaryDark);
        swiperefrsehFriendFra.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFriendList();
            }
        });


        //长按删除好友

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
