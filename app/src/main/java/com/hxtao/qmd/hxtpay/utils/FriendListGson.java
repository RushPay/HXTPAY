package com.hxtao.qmd.hxtpay.utils;

import android.text.TextUtils;
import android.util.Log;

import com.hxtao.qmd.hxtpay.been.FriendLetterListBeen;
import com.hxtao.qmd.hxtpay.been.FriendListBeen;
import com.hxtao.qmd.hxtpay.been.PersonEntity;
import com.hxtao.qmd.hxtpay.been.PersonInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/27.
 */

public class FriendListGson {


    public static FriendListBeen getJsonData(String strJson){
        FriendListBeen friendListBeen=new FriendListBeen();
        try {
            JSONObject jsonObject=new JSONObject(strJson);
            String status = jsonObject.getString("status");
            String info = jsonObject.getString("info");
            friendListBeen.setStatus(status);
            friendListBeen.setInfo(info);
            JSONArray dataArray = jsonObject.getJSONArray("data");
//            FriendLetterListBeen letterListBeen=null;
            List<FriendLetterListBeen> friendLetterList=new ArrayList<>();
            for (int i = 0; i < dataArray.length(); i++) {
//                letterListBeen=new FriendLetterListBeen();
                JSONObject dataObject= dataArray.getJSONObject(i);
                String leter = dataObject.getString("leter");
//                letterListBeen.setLeter(leter);
                JSONArray userInfoArray = dataObject.getJSONArray("info");
                List<PersonInfo> personInfoList=new ArrayList<>();
                for (int j = 0; j <userInfoArray.length() ; j++) {
                    JSONObject userObject = userInfoArray.getJSONObject(j);
                    String id = userObject.getString("id");
                    String username = userObject.getString("username");
                    String icon = userObject.getString("icon");
                    String f_chatacter = userObject.getString("f_chatacter");
                    String account = userObject.getString("account");
//                    PersonInfo personInfo=new PersonInfo(id,username,icon,f_chatacter,account);
                    personInfoList.add(new PersonInfo(id,username,icon,f_chatacter,account));
                }
                FriendLetterListBeen letterListBeen=new FriendLetterListBeen(leter,personInfoList);
//                letterListBeen.setPersonInfoList(personInfoList);
                friendLetterList.add(letterListBeen);
            }
            friendListBeen.setFriendLetterListBeenList(friendLetterList);
            return friendListBeen;
        } catch (JSONException e) {
            Log.e("FriendListGson","解析异常");
            e.printStackTrace();
        }
        return null;
    }

    public static List<PersonInfo> getFriendList(String strJson){
        List<PersonInfo> personInfoList=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(strJson);
            String status = jsonObject.getString("status");
            if (TextUtils.equals("1",status)){
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    JSONArray infoArray = dataObject.getJSONArray("info");
                    for (int j = 0; j < infoArray.length(); j++) {
                        JSONObject infoObject = infoArray.getJSONObject(j);
                        String id = infoObject.getString("id");
                        String username = infoObject.getString("username");
                        String icon = infoObject.getString("icon");
                        String f_chatacter = infoObject.getString("f_chatacter");
                        String account = infoObject.getString("account");
                        personInfoList.add(new PersonInfo(id,username,icon,f_chatacter,account));
                    }
                }

                return personInfoList;
            }else {
                return personInfoList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return personInfoList;
    }


    public static List<PersonEntity> getTransferList(String strJson){
        List<PersonEntity> list=null;
        try {
            JSONObject jsonObject=new JSONObject(strJson);
            int status = jsonObject.getInt("status");
           if (status==1){
                list=new ArrayList<>();
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    JSONArray infoArray = dataObject.getJSONArray("info");
                    for (int j = 0; j < infoArray.length(); j++) {
                        JSONObject infoObject = infoArray.getJSONObject(j);
                        String id = infoObject.getString("id");
                        String username = infoObject.getString("username");
                        String icon = infoObject.getString("icon");
                        String f_chatacter = infoObject.getString("f_chatacter");
                        String account = infoObject.getString("account");
                        list.add( new PersonEntity(id,username,icon,f_chatacter,account,false));
                    }
                }
               return list;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
