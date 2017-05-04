package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/29.
 */

public class PartyInfoBeen {
    public String id;
    public String invite_status;
    public String title;
    public String detail;
    public String address;
    public String count;
    public String p_status;
    public String addtime;
    public String username;
    public String icon;
    public List<List<String>> numberList;

    public PartyInfoBeen() {
    }

    public PartyInfoBeen(String id, String invite_status, String title, String detail, String address, String count, String p_status, String addtime, String username, String icon, List<List<String>> numberList) {
        this.id = id;
        this.invite_status = invite_status;
        this.title = title;
        this.detail = detail;
        this.address = address;
        this.count = count;
        this.p_status = p_status;
        this.addtime = addtime;
        this.username = username;
        this.icon = icon;
        this.numberList = numberList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvite_status() {
        return invite_status;
    }

    public void setInvite_status(String invite_status) {
        this.invite_status = invite_status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getP_status() {
        return p_status;
    }

    public void setP_status(String p_status) {
        this.p_status = p_status;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<List<String>> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<List<String>> numberList) {
        this.numberList = numberList;
    }
}
