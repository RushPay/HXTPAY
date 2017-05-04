package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/27.
 */

public class InfoData {

    public String letter;
    public List<PersonInfo> infoBeenList;

    public InfoData() {
    }

    public InfoData(String letter, List<PersonInfo> infoBeenList) {
        this.letter = letter;
        this.infoBeenList = infoBeenList;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public List<PersonInfo> getInfoBeenList() {
        return infoBeenList;
    }

    public void setInfoBeenList(List<PersonInfo> infoBeenList) {
        this.infoBeenList = infoBeenList;
    }
}
