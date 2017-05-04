package com.hxtao.qmd.hxtpay.event;

/**
 * @Description:转账的订阅事件
 * @Author: Cyf on 2017/3/13.
 */

public class TransferEvent {
    private int type;

    public TransferEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
