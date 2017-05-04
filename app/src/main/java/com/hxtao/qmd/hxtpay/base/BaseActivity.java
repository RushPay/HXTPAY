package com.hxtao.qmd.hxtpay.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.hxtinterface.UiOperation;
import com.hxtao.qmd.hxtpay.manager.ActivityManager;
import com.hxtao.qmd.hxtpay.utils.NetUtil;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.widgets.LoadingView;

import butterknife.ButterKnife;

/**
 * @Description:
 * @Author: Cyf on 2016/12/21.
 */

public abstract class BaseActivity extends AppCompatActivity implements UiOperation {


    private ToastUtil toastUtil;
    private BaseActivity activity;

    //加载  加载布局  布局中包括加载中动画(LoadingView loadingView)  和加载失败的显示(View layout_failure)
    private ViewStub viewStub;

    //填充viewStub的  加载布局
    private View layout_load_failure;
    //加载失败的布局
    private View layout_failure;

    private Button btn_load_again;
    //自定义的加载中动画
    private LoadingView loadingView;


    MyReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutContentId());
        //初始化 ButterKnife
        ButterKnife.bind(this);

        //接口中的初始化数据
        initView();
        activity = this;
        ActivityManager.push(activity);//将Activity加入到管理者中
        toastUtil = ToastUtil.createToastConfig();
        //接口中的点击监听
        initListener();
    }


    @Override
    protected void onStart() {
        super.onStart();

        //初始化加载
        initLoading();
        registerBroadcast();
    }

    protected  void initLoading(){
        if (viewStub==null){
            viewStub=(ViewStub)findViewById(R.id.layout_load_failure);
        }
        if (viewStub!=null&&layout_load_failure==null){
            layout_load_failure=((ViewStub)findViewById(R.id.layout_load_failure)).inflate();

            layout_failure = layout_load_failure.findViewById(R.id.layout_failure);
            layout_failure.setVisibility(View.GONE);


            btn_load_again = (Button)layout_load_failure.findViewById(R.id.btn_load_again);

            loadingView = (LoadingView)layout_load_failure.findViewById(R.id.layout_loading);
            loadingView.setVisibility(View.GONE);

            btn_load_again.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!NetUtil.isConnected(activity)) {
                        toastUtil.ToastShow(activity, getResources().getString(R.string.net_error), 1500);
                        return;
                    }
                    layout_failure.setVisibility(View.GONE);
                    loadingView.setVisibility(View.VISIBLE);
                    loadAgain();
                }
            });
        }
    }

    /**
     * 重试
     */
    public void loadAgain() {

    }

    /**
     * 加载失败
     */
    public void loadFailure() {
        if (layout_load_failure != null) {
            layout_load_failure.setVisibility(View.VISIBLE);
            layout_failure.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
        }
    }

    /**
     * 加载完成
     */
    public void loadAgainComplete() {
        if (layout_load_failure != null) {
            layout_load_failure.setVisibility(View.GONE);
            layout_failure.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示加载中动画
     */
    public void showLoadingView() {
        if (layout_load_failure != null) {
            layout_load_failure.setVisibility(View.VISIBLE);
            layout_failure.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private void registerBroadcast() {
        // 注册广播接收者
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("exit_app");
        registerReceiver(receiver,filter);
    }
    /**
     * @description:finish掉继承BaseActivity的Acitvity已达到退出登录的效果
     * @param
     * @return
     */
    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("exit_app")){
                finish();
            }
        }
    }
}
