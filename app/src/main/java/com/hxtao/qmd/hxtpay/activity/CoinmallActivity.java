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

public class CoinmallActivity extends BaseActivity {


    @BindView(R.id.load_progress_acinmall_act) ProgressBar loadProgressAcinmallAct;
    @BindView(R.id.web_acinmall_act) WebView webAcinmallAct;

    @Override
    public int layoutContentId() {
        return R.layout.activity_coinmall;
    }

    @Override
    public void initView() {
        webAcinmallAct.loadUrl("http://www.1yundou.com/");
        webAcinmallAct.getSettings().setJavaScriptEnabled(true);
        webAcinmallAct.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webAcinmallAct.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress==100){
                    loadProgressAcinmallAct.setVisibility(View.GONE);
                }else {
                    loadProgressAcinmallAct.setVisibility(View.VISIBLE);
                    loadProgressAcinmallAct.setProgress(newProgress);
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
