package com.hxtao.qmd.hxtpay.been;

/**
 * @Description:
 * @Author: Cyf on 2016/12/27.
 */

public class InfoBeen {


    /**
     * status : 1
     * info : 保存成功
     * data : {"id":"358","account":"18510871239","username":"出来了","money":"0.00","last_login_time":"1488446835","icon":"http://qmd.hxtao.net/Data/pic/thumb/thumb_58b7e59129e9d.jpg","mark":"vyye","monetary_month":null,"party_times_month":"0"}
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
         * id : 358
         * account : 18510871239
         * username : 出来了
         * money : 0.00
         * last_login_time : 1488446835
         * icon : http://qmd.hxtao.net/Data/pic/thumb/thumb_58b7e59129e9d.jpg
         * mark : vyye
         * monetary_month : null
         * party_times_month : 0
         */

        private String id;
        private String account;
        private String username;
        private String money;
        private String last_login_time;
        private String icon;
        private String mark;
        private Object monetary_month;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public Object getMonetary_month() {
            return monetary_month;
        }

        public void setMonetary_month(Object monetary_month) {
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
