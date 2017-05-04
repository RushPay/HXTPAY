package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/29.
 */

public class PartyListBeen {
    public String status;
    public String info;
    public List<PartDataListBeen> partyList;

    public PartyListBeen() {
    }

    public PartyListBeen(String status, String info, List<PartDataListBeen> partyList) {
        this.status = status;
        this.info = info;
        this.partyList = partyList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<PartDataListBeen> getPartyList() {
        return partyList;
    }

    public void setPartyList(List<PartDataListBeen> partyList) {
        this.partyList = partyList;
    }
}
