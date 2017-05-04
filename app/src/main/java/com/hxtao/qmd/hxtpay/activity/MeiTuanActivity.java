package com.hxtao.qmd.hxtpay.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeiTuanActivity extends BaseActivity {


    @BindView(R.id.load_progress_meituan_act) ProgressBar loadProgressMeituanAct;
    @BindView(R.id.web_meituan_act) WebView webMeituanAct;

    @Override
    public int layoutContentId() {
        return R.layout.activity_mei_tuan;
    }

    @Override
    public void initView() {
        webMeituanAct.loadUrl("http://www.meituan.com/");
        webMeituanAct.getSettings().setJavaScriptEnabled(true);
        webMeituanAct.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webMeituanAct.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress==100){
                    loadProgressMeituanAct.setVisibility(View.GONE);
                }else {
                    loadProgressMeituanAct.setVisibility(View.VISIBLE);
                    loadProgressMeituanAct.setProgress(newProgress);
                }
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
