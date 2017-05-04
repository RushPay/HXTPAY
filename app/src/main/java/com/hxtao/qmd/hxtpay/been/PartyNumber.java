package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2017/1/5.
 */

public class PartyNumber {
    private String status;
    private String info;
    private List<NumberInfo> numberInfoList;

    public PartyNumber() {
    }

    public PartyNumber(String status, String info, List<NumberInfo> numberInfoList) {
        this.status = status;
        this.info = info;
        this.numberInfoList = numberInfoList;
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

    public List<NumberInfo> getNumberInfoList() {
        return numberInfoList;
    }

    public void setNumberInfoList(List<NumberInfo> numberInfoList) {
        this.numberInfoList = numberInfoList;
    }

    public static class NumberInfo{
        private String id;
        private String icon;
        private String username;
        private boolean choose;

        public NumberInfo() {
        }

        public NumberInfo(String id, String icon, String username, boolean choose) {
            this.id = id;
            this.icon = icon;
            this.username = username;
            this.choose = choose;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isChoose() {
            return choose;
        }

        public void setChoose(boolean choose) {
            this.choose = choose;
        }
    }
}
