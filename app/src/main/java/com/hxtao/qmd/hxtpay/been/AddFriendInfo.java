package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/28.
 */

public class AddFriendInfo {

    /**
     * status : 1
     * info : 成功
     * data : [{"id":"87","username":"秦楠","icon":"http://192.168.199.137/Data/pic/58621a14abc86.png","f_chatacter":"H","account":"18371061818"}]
     */

    private int status;
    private String info;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 87
         * username : 秦楠
         * icon : http://192.168.199.137/Data/pic/58621a14abc86.png
         * f_chatacter : H
         * account : 18371061818
         */

        private String id;
        private String username;
        private String icon;
        private String f_chatacter;
        private String account;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getF_chatacter() {
            return f_chatacter;
        }

        public void setF_chatacter(String f_chatacter) {
            this.f_chatacter = f_chatacter;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
    }
}
