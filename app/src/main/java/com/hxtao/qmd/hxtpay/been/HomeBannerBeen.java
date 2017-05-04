package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2017/2/7.
 */

public class HomeBannerBeen {

    /**
     * status : 1
     * info : 成功
     * data : ["http://172.16.0.64/Data/pic/Group1.png","http://172.16.0.64/Data/pic/Group2.png","http://172.16.0.64/Data/pic/Group3.png"]
     */

    private int status;
    private String info;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
