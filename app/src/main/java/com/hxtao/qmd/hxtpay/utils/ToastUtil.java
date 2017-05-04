package com.hxtao.qmd.hxtpay.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hxtao.qmd.hxtpay.R;


/**
 * Created by Administrator on 2016/5/26.
 */
public class ToastUtil {

    private static ToastUtil toastUtil;
    private static Toast toast;
    private TextView text;
    private ToastUtil() {
    }

    public static ToastUtil createToastConfig() {
        if (toastUtil == null) {
            toastUtil = new ToastUtil();
        }
        return toastUtil;
    }

    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            toast.cancel();
        }
    };

    /**
     * 显示Toast
     *
     * @param context
     * @param tvString
     */

    public void ToastShow(Context context, String tvString, int duration) {
        mHandler.removeCallbacks(r);
        if (toast != null)
            text.setText(tvString);
        else {
            View layout = LayoutInflater.from(context).inflate(R.layout.toast_xml, null);
            text = (TextView) layout.findViewById(R.id.text);
            text.setText(tvString);
            toast = new Toast(context);
            toast.setGravity(Gravity.BOTTOM, 0, Uiutils.dip2px(context,80));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
        }
        mHandler.postDelayed(r, duration);
        toast.show();
    }

}
