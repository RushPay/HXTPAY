package com.hxtao.qmd.hxtpay.been;

/**
 * @Description:
 * @Author: Cyf on 2016/12/20.
 */

public class LoginBeen {


    /**
     * status : 1
     * info : 登录成功
     * data : {"id":"71","account":"18510871164","username":null,"money":"0","last_login_time":"0","icon":null,"mark":"","sign":"e2c420d928d4bf8ce0ff2ec19b371514","monetary_month":0.01,"party_times_month":0}
     */

    private int status;
    private String info;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 71
         * account : 18510871164
         * username : null
         * money : 0
         * last_login_time : 0
         * icon : null
         * mark :
         * sign : e2c420d928d4bf8ce0ff2ec19b371514
         * monetary_month : 0.01
         * party_times_month : 0
         */

        private String id;
        private String account;
        private Object username;
        private String money;
        private String last_login_time;
        private Object icon;
        private String mark;
        private String sign;
        private double monetary_month;
        private int party_times_month;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public Object getUsername() {
            return username;
        }

        public void setUsername(Object username) {
            this.username = username;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public double getMonetary_month() {
            return monetary_month;
        }

        public void setMonetary_month(double monetary_month) {
            this.monetary_month = monetary_month;
        }

        public int getParty_times_month() {
            return party_times_month;
        }

        public void setParty_times_month(int party_times_month) {
            this.party_times_month = party_times_month;
        }
    }
}
