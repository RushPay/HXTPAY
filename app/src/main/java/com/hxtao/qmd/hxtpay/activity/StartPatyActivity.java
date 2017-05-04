package com.hxtao.qmd.hxtpay.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.StartPartyResult;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.hxtao.qmd.hxtpay.utils.XutilsParamsUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartPatyActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.theme_ed_patystart) EditText themeEdPatystart;
    @BindView(R.id.explain_ed_patystart) EditText explainEdPatystart;
    @BindView(R.id.address_ed_patystart) EditText addressEdPatystart;
    @BindView(R.id.sure_btn_patystart) Button sureBtnPatystart;


    private String themeText;
    private String explainText;
    private String addressText;

    private Map<String, String> map = new HashMap<>();

    private Handler handler;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public int layoutContentId() {
        return R.layout.activity_start_paty;
    }

    @Override
    public void initView() {
        imgBack.setImageResource(R.mipmap.cancel_bg);
        titleTv.setText("发起聚会");

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1://发起聚会成功 邀请好友参加聚会
                        //跳转邀请好友界面
                        if (msg.obj != null) {
                            String pId = (String) msg.obj;
                            Intent intent = new Intent(StartPatyActivity.this, InviteFriendActivity.class);
                            intent.putExtra("pId",pId);
                            startActivity(intent);
                            finish();
                        }

                        break;
                    case 0://发起聚会失败
                        ToastUtil.createToastConfig().ToastShow(StartPatyActivity.this, "网络异常,请检查网络", 15000);
                        break;
                }
                sureBtnPatystart.setEnabled(true);
            }
        };
    }

    @Override
    public void initListener() {
        //设置控件点击监听事件
        imgBack.setOnClickListener(this);
        sureBtnPatystart.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_btn_patystart:
                sureBtnPatystart.setEnabled(false);
                getViewData();//获取用户输入信息 并判断输入内容
                if (!TextUtils.isEmpty(themeText) && !TextUtils.isEmpty(explainText)) {
                    Map<String, String> request = new HashMap<>();
                    request.put("sign", BaseApplication.createApplication().getSign());
                    request.put("title", themeText);
                    request.put("detail", explainText);
                    request.put("address", addressText);
                    RequestParams requestParams = XutilsParamsUtils.hxtParamsUtils(request, HXTUrl.HXTHTTP_LAUNCHPARTY);
                    x.http().post(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
//                            Log.e("StartPaty_onSuccess", result);
                            Gson gson = new Gson();
                            StartPartyResult startPartyResult = gson.fromJson(result, StartPartyResult.class);
                            int status = startPartyResult.getStatus();
                            Message message = handler.obtainMessage();
                            message.what = status;
                            message.obj = startPartyResult.getData();
                            message.sendToTarget();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
//                            Log.e("StartPaty_onError",ex.toString());
                            sureBtnPatystart.setEnabled(true);
                            ToastUtil.createToastConfig().ToastShow(StartPatyActivity.this, "网络异常,请检查网络", 15000);
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
                }
                break;
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void getViewData() {
        themeText = themeEdPatystart.getText().toString().trim();
        explainText = explainEdPatystart.getText().toString().trim();
        addressText = addressEdPatystart.getText().toString().trim();

        if (TextUtils.isEmpty(themeText)) {
            themeEdPatystart.setError("请输入聚会主题");
        }
        if (TextUtils.isEmpty(explainText)) {
            explainEdPatystart.setError("请输入聚会说明");
        }
        if (TextUtils.isEmpty(addressText)) {
            addressEdPatystart.setError("请输入聚会地址");
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("StartPaty Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


//    public void showDiglog() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("邀请好友");
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setMultiChoiceItems(items, selected, new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//
//                if (isChecked) {
//                    if (!map.containsKey(String.valueOf(which))) {
//                        //添加
//                        map.put(String.valueOf(which), "" + isChecked);
//                    }
//                } else {
//                    if (map.containsKey(String.valueOf(which))) {
//                        map.remove(String.valueOf(which));
//                    }
//                }
////                Toast.makeText(StartPatyActivity.this, items[which]+isChecked, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Toast.makeText(StartPatyActivity.this, "确定", Toast.LENGTH_SHORT).show();
//                //android会自动根据你选择的改变selected数组的值。
//                for (Map.Entry<String, String> entry : map.entrySet()) {
//                    Log.e("map", "key" + entry.getKey() + ",   value" + entry.getValue());
//                }
//            }
//        });
//        builder.create().show();
//    }

}
