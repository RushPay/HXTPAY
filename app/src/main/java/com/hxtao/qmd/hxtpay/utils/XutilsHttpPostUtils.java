package com.hxtao.qmd.hxtpay.utils;

import com.hxtao.qmd.hxtpay.hxtinterface.IOnLoadDataListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * @Description:
 * @Author: Cyf on 2016/12/31.
 */

public class XutilsHttpPostUtils {
    public static void hxtPostUtils(Map<String, String> request, final String postUrl, final IOnLoadDataListener listener){

        if (request!=null){
            RequestParams requestParams=new RequestParams(postUrl);
            StringBuffer stringBuffer = new StringBuffer(postUrl);
            for (Map.Entry<String, String> entry:request.entrySet()){
                requestParams.addBodyParameter(entry.getKey(),entry.getValue());
                stringBuffer.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
           System.out.println("URL=" + stringBuffer.toString());
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (!result.isEmpty()) {
                        listener.onSuccess(result, postUrl);
                    } else {
                        listener.onError(postUrl);
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    listener.networkError(postUrl);
                    System.out.println("onError=" + ex);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }else {
            RequestParams params = new RequestParams(postUrl);
            System.out.println("URL=" + postUrl);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (!result.isEmpty()) {
                        listener.onSuccess(result, postUrl);
                    } else {
                        listener.onError(postUrl);
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    listener.networkError(postUrl);
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
}
