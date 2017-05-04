package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/29.
 */

public class PartDataListBeen {
    public String date;

    public List<PartyInfoBeen> partDataList;

    public PartDataListBeen() {
    }

    public PartDataListBeen(String date, List<PartyInfoBeen> partDataList) {
        this.date = date;
        this.partDataList = partDataList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<PartyInfoBeen> getPartDataList() {
        return partDataList;
    }

    public void setPartDataList(List<PartyInfoBeen> partDataList) {
        this.partDataList = partDataList;
    }
}
