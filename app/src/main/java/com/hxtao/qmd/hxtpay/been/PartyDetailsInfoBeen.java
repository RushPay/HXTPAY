package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2017/1/4.
 */

public class PartyDetailsInfoBeen {
    public int status;
    public String info;
    public String id;
    public String sponsor_id;
    public String title;
    public String detail;
    public String address;
    public String count;
    public String addtime;
    public String partyStatus;
    public String username;
    public String icon;
    public String is_organizer;
    public String luckyMan;
    public String payMethod;

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public PartyDetailsInfoBeen(int status, String info, String id, String sponsor_id, String title, String detail, String address, String count, String addtime, String partyStatus, String username, String icon, String is_organizer, String luckyMan, String payMethod, List<List<String>> m_arrList) {
        this.status = status;
        this.info = info;
        this.id = id;
        this.sponsor_id = sponsor_id;
        this.title = title;
        this.detail = detail;
        this.address = address;
        this.count = count;
        this.addtime = addtime;
        this.partyStatus = partyStatus;
        this.username = username;
        this.icon = icon;
        this.is_organizer = is_organizer;
        this.luckyMan = luckyMan;
        this.payMethod = payMethod;
        this.m_arrList = m_arrList;
    }

    public String getLuckyMan() {
        return luckyMan;
    }

    public void setLuckyMan(String luckyMan) {
        this.luckyMan = luckyMan;
    }

//    public PartyDetailsInfoBeen(int status, String info, String id, String sponsor_id, String title, String detail, String address, String count, String addtime, String partyStatus, String username, String icon, String is_organizer, String luckyMan, List<List<String>> m_arrList) {
//        this.status = status;
//        this.info = info;
//        this.id = id;
//        this.sponsor_id = sponsor_id;
//        this.title = title;
//        this.detail = detail;
//        this.address = address;
//        this.count = count;
//        this.addtime = addtime;
//        this.partyStatus = partyStatus;
//        this.username = username;
//        this.icon = icon;
//        this.is_organizer = is_organizer;
//        this.luckyMan = luckyMan;
//        this.m_arrList = m_arrList;
//    }

    public List<List<String>> m_arrList;

    public PartyDetailsInfoBeen() {
    }

    public PartyDetailsInfoBeen(int status, String info, String id, String sponsor_id, String title, String detail, String address, String count, String addtime, String partyStatus, String username, String icon, String is_organizer, List<List<String>> m_arrList) {
        this.status = status;
        this.info = info;
        this.id = id;
        this.sponsor_id = sponsor_id;
        this.title = title;
        this.detail = detail;
        this.address = address;
        this.count = count;
        this.addtime = addtime;
        this.partyStatus = partyStatus;
        this.username = username;
        this.icon = icon;
        this.is_organizer = is_organizer;
        this.m_arrList = m_arrList;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSponsor_id() {
        return sponsor_id;
    }

    public void setSponsor_id(String sponsor_id) {
        this.sponsor_id = sponsor_id;
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

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPartyStatus() {
        return partyStatus;
    }

    public void setPartyStatus(String partyStatus) {
        this.partyStatus = partyStatus;
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

    public String getIs_organizer() {
        return is_organizer;
    }

    public void setIs_organizer(String is_organizer) {
        this.is_organizer = is_organizer;
    }

    public List<List<String>> getM_arrList() {
        return m_arrList;
    }

    public void setM_arrList(List<List<String>> m_arrList) {
        this.m_arrList = m_arrList;
    }

    @Override
    public String toString() {
        return "PartyDetailsInfoBeen{" +
                "status=" + status +
                ", info='" + info + '\'' +
                ", id='" + id + '\'' +
                ", sponsor_id='" + sponsor_id + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", address='" + address + '\'' +
                ", count='" + count + '\'' +
                ", addtime='" + addtime + '\'' +
                ", partyStatus='" + partyStatus + '\'' +
                ", username='" + username + '\'' +
                ", icon='" + icon + '\'' +
                ", is_organizer='" + is_organizer + '\'' +
                ", m_arrList=" + m_arrList +
                '}';
    }
}
