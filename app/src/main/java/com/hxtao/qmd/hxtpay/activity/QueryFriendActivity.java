package com.hxtao.qmd.hxtpay.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.adapter.QueryUserAdapter;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.been.QueryUserBeen;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QueryFriendActivity extends BaseActivity {


    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.title_tv) TextView titleTv;
    @BindView(R.id.img_more) ImageView imgMore;
    @BindView(R.id.query_que_ed) EditText queryQueEd;
    @BindView(R.id.query_que_btn) Button queryQueBtn;
    @BindView(R.id.show_que_lv) ListView showQueLv;

    private String accountStr;

    private QueryUserBeen queryUser;
    private List<QueryUserBeen.DataBean> queryUserList=new ArrayList<>();
    private QueryUserAdapter queryUserAdapter;
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100://查询失败
                    if (msg.obj!=null){
                        queryUser= (QueryUserBeen) msg.obj;
                        ToastUtil.createToastConfig().ToastShow(QueryFriendActivity.this,queryUser.getInfo(),15000);
                    }
                    break;
                case 200://查询成功
                    if (msg.obj!=null){
                        queryUser= (QueryUserBeen) msg.obj;
                        queryUserList.clear();
                        queryUserList.add(queryUser.getData());
                        queryUserAdapter.notifyDataSetChanged();
                    }
                    break;
                case 300://查询无账号
                    if (msg.obj!=null){
                        queryUser= (QueryUserBeen) msg.obj;
                        ToastUtil.createToastConfig().ToastShow(QueryFriendActivity.this,queryUser.getInfo(),15000);

                    }
                    break;
            }
        }
    };
    @Override
    public int layoutContentId() {
        return R.layout.activity_query_friend;
    }

    @Override
    public void initView() {
        titleTv.setText("添加朋友");
        imgBack.setImageResource(R.mipmap.back_title_bg);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //创建adapter实例
        queryUserAdapter=new QueryUserAdapter(QueryFriendActivity.this,queryUserList);
        //showQueLv设置适配器
        showQueLv.setAdapter(queryUserAdapter);

        queryQueBtn.setOnClickListener(new View.OnClickListener() {//查询好友
            @Override
            public void onClick(View v) {

                getAccount();
                if (!TextUtils.isEmpty(accountStr)){

                    RequestParams request=new RequestParams(HXTUrl.HXTHTTP_QUERYUSER);
                    request.addBodyParameter("sign", BaseApplication.createApplication().getSign());

                    request.addBodyParameter("mobile",accountStr);
//                    Log.e("sign",BaseApplication.createApplication().getSign());
//                    Log.e("mobile",accountStr);

                    x.http().post(request, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.e("onSuccess",result);
                            Gson gson=new Gson();
                            QueryUserBeen queryUserBeen = gson.fromJson(result, QueryUserBeen.class);
                            if (queryUserBeen!=null){
                                int status = queryUserBeen.getStatus();
                                Message message=handler.obtainMessage();
                                switch (status){
                                    case 0:
                                        message.what=100;
                                    break;
                                    case 1:
                                        message.what=200;
                                        break;
                                    case 2:
                                        message.what=300;
                                        break;
                                }
                                message.obj=queryUserBeen;
                                message.sendToTarget();
                            }

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                           Log.e("onError",ex.toString());
                            ToastUtil.createToastConfig().ToastShow(QueryFriendActivity.this,"网络异常请查看网络",15000);
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });

                }else {
                    queryQueEd.setError("请输入你想要查询的帐号");
                }
            }
        });
    }
    public void getAccount(){

        accountStr = queryQueEd.getText().toString().trim();

    }
    @Override
    public void initListener() {
        queryQueEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length==0){
                    queryQueBtn.setEnabled(false);
                    queryQueBtn.setBackgroundResource(R.color.colorGray);
                }else {
                    queryQueBtn.setEnabled(true);
                    queryQueBtn.setBackgroundResource(R.color.login_bg);
                }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
