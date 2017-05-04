package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2017/1/19.
 */

public class PersonBillDetailsEntity {

    /**
     * status : 1
     * info : 操作成功
     * data : [{"id":"52","mid":"95","r_or_e":"2","type":"3","money":"250.00","addtime":"1484709894"},{"id":"51","mid":"95","r_or_e":"2","type":"3","money":"555.00","addtime":"1484709843"},{"id":"50","mid":"95","r_or_e":"2","type":"3","money":"250.00","addtime":"1484708247"},{"id":"49","mid":"95","r_or_e":"2","type":"3","money":"25.00","addtime":"1484701328"},{"id":"47","mid":"95","r_or_e":"2","type":"3","money":"2.50","addtime":"1484643664"},{"id":"45","mid":"95","r_or_e":"2","type":"3","money":"1.00","addtime":"1484642553"},{"id":"43","mid":"95","r_or_e":"2","type":"3","money":"14.00","addtime":"1484642417"},{"id":"42","mid":"95","r_or_e":"2","type":"3","money":"33.00","addtime":"1484642185"},{"id":"41","mid":"95","r_or_e":"2","type":"3","money":"6.00","addtime":"1484641884"},{"id":"40","mid":"95","r_or_e":"2","type":"3","money":"3.00","addtime":"1484641040"}]
     */

    private int status;
    private String info;
    private List<BillData> data;

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

    public List<BillData> getData() {
        return data;
    }

    public void setData(List<BillData> data) {
        this.data = data;
    }

    public static class BillData {
        /**
         * id : 52
         * mid : 95
         * r_or_e : 2
         * type : 3
         * money : 250.00
         * addtime : 1484709894
         */

        private String id;
        private String mid;
        private String r_or_e;
        private String type;
        private String money;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getR_or_e() {
            return r_or_e;
        }

        public void setR_or_e(String r_or_e) {
            this.r_or_e = r_or_e;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
