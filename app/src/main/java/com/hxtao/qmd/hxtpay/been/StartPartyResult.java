package com.hxtao.qmd.hxtpay.been;

/**
 * @Description:
 * @Author: Cyf on 2016/12/27.
 */

public class StartPartyResult {

    /**
     * status : 1
     * info : 已提交好友请求
     */

    private int status;
    private String info;
    private String data;

    public StartPartyResult() {
    }

    public StartPartyResult(int status, String info, String data) {
        this.status = status;
        this.info = info;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
