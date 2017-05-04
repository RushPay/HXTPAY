package com.hxtao.qmd.hxtpay.been;

/**
 * @Description:
 * @Author: Cyf on 2016/12/27.
 */

public class PersonInfo {
    public String id;
    public String username;
    public String icon;
    public String f_chatacter;
    public String account;

    public PersonInfo() {
    }

    public PersonInfo(String id, String username, String icon, String f_chatacter, String account) {
        this.id = id;
        this.username = username;
        this.icon = icon;
        this.f_chatacter = f_chatacter;
        this.account = account;
    }

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
