package com.hxtao.qmd.hxtpay.hxtinterface;

/**
 * Created by Zch on 2016/6/25.
 */
public interface IOnLoadDataListener {
    /**
     * 成功时回调
     */
    void onSuccess(String result, String tagUrl);

    /**
     * 失败时回调，简单处理，没做什么
     */
    void onError(String tagUrl);

    /**
     * 网路错误回调
     */
    void networkError(String tagUrl);

}
