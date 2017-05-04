package com.hxtao.qmd.hxtpay.been;

/**
 * @Description:
 * @Author: Cyf on 2017/2/14.
 */

public class CodeDataBeen {
    private int type;
    private String id;
    private String username;
    private String icon;
    private String account;

    public CodeDataBeen() {
    }

    public CodeDataBeen(int type, String id, String username, String icon, String account) {
        this.type = type;
        this.id = id;
        this.username = username;
        this.icon = icon;
        this.account = account;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
