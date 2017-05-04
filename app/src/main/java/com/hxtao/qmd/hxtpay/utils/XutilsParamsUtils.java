package com.hxtao.qmd.hxtpay.utils;

import org.xutils.http.RequestParams;

import java.util.Map;

/**
 * @Description:
 * @Author: Cyf on 2017/1/3.
 */

public class XutilsParamsUtils {
    public static RequestParams hxtParamsUtils(Map<String, String> request, final String postUrl){
        RequestParams requestParams = new RequestParams(postUrl);
        if (request!=null) {
            StringBuffer stringBuffer = new StringBuffer(postUrl);
            for (Map.Entry<String, String> entry : request.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
                stringBuffer.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            System.out.println("URL=" + stringBuffer.toString());
            return requestParams;
        }
        return requestParams;
    }
}
