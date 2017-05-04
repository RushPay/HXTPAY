package com.hxtao.qmd.hxtpay.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.activity.AliPayRechargeActivity;
import com.hxtao.qmd.hxtpay.activity.CodeQueryFriendActivity;
import com.hxtao.qmd.hxtpay.activity.CoinmallActivity;
import com.hxtao.qmd.hxtpay.activity.HxtActivity;
import com.hxtao.qmd.hxtpay.activity.MeiTuanActivity;
import com.hxtao.qmd.hxtpay.activity.MoneyTransferActivity;
import com.hxtao.qmd.hxtpay.activity.PartyDetailsActivity;
import com.hxtao.qmd.hxtpay.activity.ReceiptCodeActivity;
import com.hxtao.qmd.hxtpay.activity.StartPatyActivity;
import com.hxtao.qmd.hxtpay.activity.TransfeMoneyActivity;
import com.hxtao.qmd.hxtpay.adapter.BannerAdapter;
import com.hxtao.qmd.hxtpay.adapter.HomeGvAdapter;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseFragment;
import com.hxtao.qmd.hxtpay.been.CodeDataBeen;
import com.hxtao.qmd.hxtpay.been.HomeBannerBeen;
import com.hxtao.qmd.hxtpay.utils.CodeJsonUtils;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.youth.banner.Banner;
import com.zxing.activity.CaptureActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.gridview_homefragment) GridView gridviewHomefragment;
    @BindView(R.id.hxt_img_homefragment) ImageView hxtImgHomefragment;
    @BindView(R.id.coinmall_img_homefragment) ImageView coinmallImgHomefragment;
    @BindView(R.id.banner_home_fra) Banner bannerHomeFra;
    @BindView(R.id.meituan_img_homefragment) ImageView meituanImgHomefragment;
    private List<Bitmap> bitmapList = new ArrayList<>();

    private List<String> listBanner;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (msg.obj != null) {
                        listBanner = (List<String>) msg.obj;
                        bannerHomeFra.setImageLoader(new BannerAdapter());
                        bannerHomeFra.setImages(listBanner);
                        bannerHomeFra.start();
                    }
                    break;

                case 200:
                    if (msg.obj != null) {
                        CodeDataBeen codeDataBeen = (CodeDataBeen) msg.obj;
//                        Log.e("codeDataBeen.getType()", "" + codeDataBeen.getType());
                        switch (codeDataBeen.getType()) {
                            case 1://查找好友
//                                Log.e("CodeDataBeen", codeDataBeen.getIcon() + "---:" + codeDataBeen.getUsername() + "---:" + codeDataBeen.getId());
                                CodeQueryFriendActivity.actionStart(getContext(), codeDataBeen.getIcon(), codeDataBeen.getUsername(), codeDataBeen.getId());
                                break;
                            case 2://转账
//                                Log.e("CodeDataBeen", codeDataBeen.getIcon() + "---:" + codeDataBeen.getUsername() + "---:" + codeDataBeen.getId());
//                                CodeTransferActivity.actionStart(getContext(),codeDataBeen.getIcon(),codeDataBeen.getUsername(),codeDataBeen.getId());
                                Intent intent = new Intent(getActivity(), MoneyTransferActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("icon", codeDataBeen.getIcon());
                                bundle.putString("username", codeDataBeen.getUsername());
                                bundle.putString("id", codeDataBeen.getId());
                                intent.putExtra("person", bundle);
                                startActivity(intent);
                                break;
                            case 3://聚会二维码跳转
                                PartyDetailsActivity.actionStart(getContext(), codeDataBeen.getId());
                                break;
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public int layoutContentId() {
//        int fragment_home = R.layout.fragment_home;
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        //加载首页轮播图
        getBanner();

        //加载首页gridView使用的图片
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.mipmap.scancode_bg));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.mipmap.transfer_money_bg));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.mipmap.collection_bg));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.mipmap.startparty_bg));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.mipmap.safety_bg));
        bitmapList.add(BitmapFactory.decodeResource(getResources(), R.mipmap.addbank_bg));
        gridviewHomefragment.setAdapter(new HomeGvAdapter(getContext(), bitmapList));
    }

    private void getBanner() {
        final RequestParams request = new RequestParams(HXTUrl.HXTHTTP_GETBANNER);
        request.addBodyParameter("sign", BaseApplication.getInstance().getSign());
        x.http().post(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Gson gson = new Gson();
                    HomeBannerBeen homeBannerBeen = gson.fromJson(result, HomeBannerBeen.class);
                    if (1 == homeBannerBeen.getStatus()) {
                        Message message = handler.obtainMessage();
                        message.what = 100;
                        message.obj = homeBannerBeen.getData();
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
        hxtImgHomefragment.setOnClickListener(this);
        coinmallImgHomefragment.setOnClickListener(this);
        meituanImgHomefragment.setOnClickListener(this);
        gridviewHomefragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0://扫一扫
                        intent.setClass(getActivity(), CaptureActivity.class);
                        startActivityForResult(intent, 200);
                        break;
                    case 1://转账功能
                        intent.setClass(getActivity(), TransfeMoneyActivity.class);
                        startActivity(intent);
                        break;
                    case 2://收款码
                        intent.setClass(getActivity(), ReceiptCodeActivity.class);
                        startActivity(intent);
                        break;
                    case 3://发起聚会功能
                        intent.setClass(getActivity(), StartPatyActivity.class);
                        startActivity(intent);
                        break;
                    case 4://充值界面
                        intent.setClass(getActivity(), AliPayRechargeActivity.class);
                        startActivity(intent);
                    break;
                    case 5:
//                        ToastUtil.createToastConfig().ToastShow(getContext(), "工程师正在完善此功能", 1500);
                        break;
                }

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hxt_img_homefragment:
                Intent hxtIntent = new Intent(getActivity(), HxtActivity.class);
                startActivity(hxtIntent);
                break;
            case R.id.coinmall_img_homefragment:
                Intent coinmallIntent = new Intent(getActivity(), CoinmallActivity.class);
                startActivity(coinmallIntent);
                break;
            case R.id.meituan_img_homefragment:
                Intent meituanIntent = new Intent(getActivity(), MeiTuanActivity.class);
                startActivity(meituanIntent);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            String result = data.getStringExtra("result");
//            Log.e("onActivityResult", result);
            requestHttpData(result);
        }
    }

    private void requestHttpData(String codeUrl) {

        Map<String, String> entryMap = getRequestParams(codeUrl);

        RequestParams requestParams = new RequestParams(codeUrl);
        if (entryMap != null && entryMap.size() > 0) {
            for (Map.Entry<String, String> entry : entryMap.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
//                Log.e("entryMap","key:"+entry.getKey()+"---value:"+entry.getValue());
            }
        }
        requestParams.addBodyParameter("sign", BaseApplication.getInstance().getSign());
//        Log.e("sign", BaseApplication.getInstance().getSign());
//        Log.e("codeUrl", requestParams.getUri().toString());

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("requestHttpData", result);
                if (result != null) {
                    CodeDataBeen codeData = CodeJsonUtils.getCodeData(result);
                    Message message = handler.obtainMessage();
                    message.what = 200;
                    message.obj = codeData;
                    message.sendToTarget();
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

    private Map<String, String> getRequestParams(String codeUrl) {
        //codeUrl =http://qmd.hxtao.net/Api/Party/getPartyInfoById?pid=255&mid=95
        Map<String, String> entryMap = null;
        int i = codeUrl.indexOf("?");
        if (-1 != i) {
            entryMap = new HashMap<>();
            String substring = codeUrl.substring(i + 1, codeUrl.length());//pid=255&mid=9

            String[] split = substring.split("&");
            for (String s : split) {
                int equalsInt = s.indexOf("=");
                entryMap.put(s.substring(0, equalsInt), s.substring(equalsInt + 1, s.length()));
            }
        }

        return entryMap;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
