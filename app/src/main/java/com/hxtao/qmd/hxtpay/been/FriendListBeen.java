package com.hxtao.qmd.hxtpay.been;

import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/28.
 */

public class FriendListBeen {
    /**
     * status : 1
     * info : 成功
     * data : [{"letter":"A","info":[{"id":"97","username":null,"icon":"http://192.168.199.137/Data/pic/58621a14abc86.png","f_chatacter":null,"account":"13814369257"},{"id":"98","username":null,"icon":"http://192.168.199.137","f_chatacter":null,"account":"18634567890"}]},{"letter":"A","info":[{"id":"97","username":null,"icon":"http://192.168.199.137/Data/pic/58621a14abc86.png","f_chatacter":null,"account":"13814369257"},{"id":"98","username":null,"icon":"http://192.168.199.137","f_chatacter":null,"account":"18634567890"}]}]
     */
    private String status;
    private String info;
    private List<FriendLetterListBeen> friendLetterListBeenList;

    public FriendListBeen() {
    }

    public FriendListBeen(String status, String info, List<FriendLetterListBeen> friendLetterListBeenList) {
        this.status = status;
        this.info = info;
        this.friendLetterListBeenList = friendLetterListBeenList;
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

    public List<FriendLetterListBeen> getFriendLetterListBeenList() {
        return friendLetterListBeenList;
    }

    public void setFriendLetterListBeenList(List<FriendLetterListBeen> friendLetterListBeenList) {
        this.friendLetterListBeenList = friendLetterListBeenList;
    }
}
