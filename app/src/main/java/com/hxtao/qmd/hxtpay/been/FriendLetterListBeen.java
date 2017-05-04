package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/28.
 */

public class FriendLetterListBeen {
    public String leter;
    public List<PersonInfo> personInfoList;

    public FriendLetterListBeen(String leter, List<PersonInfo> personInfoList) {
        this.leter = leter;
        this.personInfoList = personInfoList;
    }

    public FriendLetterListBeen() {
    }

    public String getLeter() {
        return leter;
    }

    public void setLeter(String leter) {
        this.leter = leter;
    }

    public List<PersonInfo> getPersonInfoList() {
        return personInfoList;
    }

    public void setPersonInfoList(List<PersonInfo> personInfoList) {
        this.personInfoList = personInfoList;
    }
}
