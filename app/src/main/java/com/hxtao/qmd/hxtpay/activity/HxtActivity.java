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

public class HxtActivity extends BaseActivity {


    @BindView(R.id.web_hxt_act) WebView webHxtAct;
    @BindView(R.id.load_progress_hxt_act) ProgressBar loadProgressHxtAct;

    @Override
    public int layoutContentId() {
        return R.layout.activity_hxt;
    }

    @Override
    public void initView() {
        webHxtAct.loadUrl("http://www.hxtao.com/");
        webHxtAct.getSettings().setJavaScriptEnabled(true);
        webHxtAct.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webHxtAct.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress==100){
                    loadProgressHxtAct.setVisibility(View.GONE);
                }else {
                    loadProgressHxtAct.setVisibility(View.VISIBLE);
                    loadProgressHxtAct.setProgress(newProgress);
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
