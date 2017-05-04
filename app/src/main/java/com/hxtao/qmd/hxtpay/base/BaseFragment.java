package com.hxtao.qmd.hxtpay.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.hxtinterface.UiOperation;
import com.hxtao.qmd.hxtpay.utils.NetUtil;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.widgets.LoadingView;

/**
 * @Description:
 * @Author: Cyf on 2016/12/23.
 */

public abstract class BaseFragment extends Fragment implements UiOperation {


    private View mViewNetFail,mView;
    private ViewStub mVbNetError;
    private FrameLayout mContent;
    private ToastUtil toastUtil;
    private Button btnLoadAgain;
    private View layout_failure;
    private LoadingView loadingView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_base_module,container,false);
        mVbNetError = (ViewStub)mView.findViewById(R.id.fragmentlayout_load_failure);
        mContent = (FrameLayout)mView.findViewById(R.id.fragmentfg_content);
        mContent.removeAllViews();
        mContent.addView(inflater.inflate(layoutContentId(), container,false));
//        int i = layoutContentId();
        toastUtil = ToastUtil.createToastConfig();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLoading();
        initView();
        initListener();
    }

    private void initLoading() {
        if (mVbNetError!=null){
            mViewNetFail=mVbNetError.inflate();
            btnLoadAgain = (Button)mViewNetFail.findViewById(R.id.btn_load_again);
            layout_failure = mViewNetFail.findViewById(R.id.layout_failure);
            layout_failure.setVisibility(View.GONE);
            loadingView = (LoadingView) mViewNetFail.findViewById(R.id.layout_loading);
            loadingView.setVisibility(View.GONE);
            btnLoadAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!NetUtil.isConnected(getActivity())) {
                        toastUtil.ToastShow(getActivity(), getResources().getString(R.string.net_error), 1500);
                        return;
                    }
                    mViewNetFail.setVisibility(View.GONE);
                    layout_failure.setVisibility(View.GONE);
                    loadingView.setVisibility(View.GONE);
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
        if (mViewNetFail != null) {
            mViewNetFail.setVisibility(View.VISIBLE);
            layout_failure.setVisibility(View.VISIBLE);
            loadingView.setVisibility(View.GONE);
        }
    }

    /**
     * 加载完成
     */
    public void loadAgainComplete() {
        if (mViewNetFail != null) {
            mViewNetFail.setVisibility(View.GONE);
            layout_failure.setVisibility(View.GONE);
            loadingView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示加载中动画
     */
    public void showLoadingView() {
        if (mViewNetFail != null) {
            mViewNetFail.setVisibility(View.VISIBLE);
            layout_failure.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Toast
     *
     * @param tvString
     *            传入内容 时间默认1500
     */
    public void showToast(String tvString) {
        showToast(tvString, 1500);
    }

    /**
     * Toast
     *
     * @param tvString
     *            内容
     * @param duration
     *            显示时长
     */
    public void showToast(String tvString, int duration) {
        showToast(getContext(), tvString, duration);
    }

    public void showToast(Context context, String tvString, int duration) {
        toastUtil.ToastShow(context, tvString, duration);
    }

}
