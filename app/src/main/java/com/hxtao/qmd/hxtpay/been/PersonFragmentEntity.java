package com.hxtao.qmd.hxtpay.been;

/**
 * @Description:
 * @Author: Cyf on 2017/1/17.
 */

public class PersonFragmentEntity {


    /**
     * status : 1
     * info : 操作成功
     * data : {"username":"fjjj","icon":"/Data/pic/58621a14abc86.png","account":"13814912325","money":"5555528.00","count":"23"}
     */

    private int status;
    private String info;
    private InfoData data;

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

    public InfoData getData() {
        return data;
    }

    public void setData(InfoData data) {
        this.data = data;
    }

    public static class InfoData {
        /**
         * username : fjjj
         * icon : /Data/pic/58621a14abc86.png
         * account : 13814912325
         * money : 5555528.00
         * count : 23
         */

        private String username;
        private String icon;
        private String account;
        private String money;
        private String count;

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

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }
}
