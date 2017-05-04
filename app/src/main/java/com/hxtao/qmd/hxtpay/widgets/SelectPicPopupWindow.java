package com.hxtao.qmd.hxtpay.widgets;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.event.TransferEvent;
import com.hxtao.qmd.hxtpay.event.TypeEvent;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;
import com.jungly.gridpasswordview.GridPasswordView;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * @Description:
 * @Author: Cyf on 2017/1/8.
 */

public class SelectPicPopupWindow extends PopupWindow {

    private final Button backBtn;
    private final TextView moneryTv;
    private final GridPasswordView pswView;
    private Context mcontext;
    private View view;
    private String monery;
    private String payid;
    private TextView logTv;

    public SelectPicPopupWindow(final Context mcontext, final String mid,final String monery){
        this.mcontext=mcontext;
        this.view= LayoutInflater.from(mcontext).inflate(R.layout.pup_layout,null);
        backBtn = (Button)view.findViewById(R.id.back_btn);
        moneryTv = (TextView)view.findViewById(R.id.pay_money_tv);
        logTv = (TextView)view.findViewById(R.id.logo_tv);
        pswView = (GridPasswordView)view.findViewById(R.id.pswView);
        logTv.setText("转账");
        moneryTv.setText("￥"+monery+"元");


        //取消按钮
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        //LinearLayout.LayoutParams.MATCH_PAREN
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        //LinearLayout.LayoutParams.WRAP_CONTENT
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.anim.pop_enter_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

        pswView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length()==6){
                    transferMoney(mid,monery,psw);
//                    payRequest(payid,psw);
                }
            }

            @Override
            public void onInputFinish(String psw) {

            }
        });
    }

    public SelectPicPopupWindow(final Context mcontext, View.OnClickListener itemsOnClick,String order_id,String monery){
        this.mcontext=mcontext;
        this.view= LayoutInflater.from(mcontext).inflate(R.layout.pup_layout,null);
        payid=order_id;
        this.monery=monery;
        backBtn = (Button)view.findViewById(R.id.back_btn);
        moneryTv = (TextView)view.findViewById(R.id.pay_money_tv);
        pswView = (GridPasswordView)view.findViewById(R.id.pswView);
        moneryTv.setText("￥"+monery+"元");
        //取消按钮
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.anim.pop_enter_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

        pswView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                if (psw.length()==6){

                    payRequest(payid,psw);
                }
            }

            @Override
            public void onInputFinish(String psw) {

            }
        });
    }

    private void transferMoney(String mid, String money,String pwd) {
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_TRANSFER);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("mid", mid);
        requestParams.addBodyParameter("money", money);
        requestParams.addBodyParameter("pwd",pwd);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("Success", result);
                if (result!=null){
                    Gson gson=new Gson();
                    AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);

                    ToastUtil.createToastConfig().ToastShow(mcontext, addFriendResult.getInfo(), 1500);
                    if (1==addFriendResult.getStatus()){//
                       EventBus.getDefault().post(new TransferEvent(TypeEvent.TRANSFER));
                    }
                    dismiss();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(mcontext, "网络异常请检查网络", 1500);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void payRequest(String order_id,String trade_password){
        RequestParams requestParams=new RequestParams(HXTUrl.HXTHTTP_PAY);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("order_id",order_id);
        requestParams.addBodyParameter("trade_password",trade_password);
//        Log.e("order_id",order_id);
//        Log.e("trade_password",trade_password);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("order_id",result);
              if (result!=null){
                  Gson gson=new Gson();
                  AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                  switch (addFriendResult.getStatus()){
                      case 1:
//                          ToastUtil.createToastConfig().ToastShow(mcontext, addFriendResult.getInfo(), 15000);
//                          Intent intent=new Intent("REFUSHBROADCASTRECEIVER");
//                          mcontext.sendBroadcast(intent);
                          EventBus.getDefault().post(new TransferEvent(TypeEvent.TRANSFER));
                          dismiss();
                          break;
                      case 0:
                          ToastUtil.createToastConfig().ToastShow(mcontext, addFriendResult.getInfo(), 15000);
                          break;
                  }
              }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(mcontext, "网络异常请检查网络", 15000);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


}
