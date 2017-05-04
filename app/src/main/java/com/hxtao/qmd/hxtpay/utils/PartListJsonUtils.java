package com.hxtao.qmd.hxtpay.utils;

import com.hxtao.qmd.hxtpay.been.PartDataListBeen;
import com.hxtao.qmd.hxtpay.been.PartyDetailsInfoBeen;
import com.hxtao.qmd.hxtpay.been.PartyInfoBeen;
import com.hxtao.qmd.hxtpay.been.PartyListBeen;
import com.hxtao.qmd.hxtpay.been.PartyNumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Cyf on 2016/12/29.
 */

public class PartListJsonUtils {

    public static PartyListBeen getPartData(String strJson){
        PartyListBeen partyListBeen=new PartyListBeen();
        try {
            JSONObject jsonObject=new JSONObject(strJson);
            String status = jsonObject.getString("status");
            String info = jsonObject.getString("info");

            partyListBeen.setStatus(status);
            partyListBeen.setInfo(info);
            List<PartDataListBeen> partyList =new ArrayList<>();
            boolean dataBoolean = jsonObject.isNull("data");//判断是否存在聚会的数据
            if (!dataBoolean){
                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);

                    String date = dataObject.getString("date");

                    PartDataListBeen partDataListBeen=new PartDataListBeen();
                    partDataListBeen.setDate(date);
//                partDataListBeen.setPartDataList();
//                partyList.add()
                    List<PartyInfoBeen> partyInfoBeenList=new ArrayList<>();

                    JSONArray infoArray = dataObject.getJSONArray("info");
                    for (int j = 0; j <infoArray.length() ; j++) {
                        JSONObject infoObject = infoArray.getJSONObject(j);

                        String id = infoObject.getString("id");
                        String invite_status = infoObject.getString("invite_status");
                        String title = infoObject.getString("title");
                        String detail = infoObject.getString("detail");
                        String address = infoObject.getString("address");
                        String count = infoObject.getString("count");
                        String p_status = infoObject.getString("p_status");
                        String addtime = infoObject.getString("addtime");
                        String username = infoObject.getString("username");
                        String icon = infoObject.getString("icon");

                        boolean m_arrBoolean = infoObject.isNull("m_arr");
//                    Log.e("PartListJsonUtils m_arr",""+m_arrBoolean);
                        if (!m_arrBoolean){//当存在m_arr时
//                        Log.e("PartListJsonUtils","m_arr没有这个Key马丹");
                            JSONArray m_arrArray = infoObject.getJSONArray("m_arr");
                            List<List<String>> m_arrList=new ArrayList<>();
//                        Log.e("m_arrArray.size",""+m_arrArray.length());
                            for (int k = 0; k < m_arrArray.length(); k++) {
                                JSONArray jsonArray = m_arrArray.getJSONArray(k);

                                List<String> strList=new ArrayList<>();

                                for (int h = 0; h <jsonArray.length() ; h++) {
                                    String strInfo = (String) jsonArray.get(h);
                                    strList.add(strInfo);
                                }
                                m_arrList.add(strList);
                            }
                            PartyInfoBeen  partyInfoBeen=new PartyInfoBeen(id,invite_status,title,detail,address,count,p_status,addtime,username,icon,m_arrList);
                            partyInfoBeenList.add(partyInfoBeen);
                        }else {//当不存在m_arr时
                            PartyInfoBeen  partyInfoBeen=new PartyInfoBeen(id,invite_status,title,detail,address,count,p_status,addtime,username,icon,null);
                            partyInfoBeenList.add(partyInfoBeen);
                        }

                    }
                    partDataListBeen.setPartDataList(partyInfoBeenList);

                    partyList.add(partDataListBeen);
                }
                partyListBeen.setPartyList(partyList);
                return partyListBeen;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PartyDetailsInfoBeen partyDetailsInfoUtils(String strJson){

        try {
            JSONObject jsonObject=new JSONObject(strJson);
            int status = jsonObject.getInt("status");
            String info = jsonObject.getString("info");
            JSONObject dataObject = jsonObject.getJSONObject("data");
            String id = dataObject.getString("id");
            String sponsor_id = dataObject.getString("sponsor_id");
            String title = dataObject.getString("title");
            String detail = dataObject.getString("detail");
            String address = dataObject.getString("address");
            String count = dataObject.getString("count");
            String addtime = dataObject.getString("addtime");
            String partyStatus = dataObject.getString("status");
            String username = dataObject.getString("username");
            String icon = dataObject.getString("icon");
            String is_organizer = dataObject.getString("is_organizer");
            String pay_method = dataObject.getString("pay_method");
            String luckyman=null;
            boolean luckyBoolean = dataObject.isNull("luckyman");
            if (!luckyBoolean){
                luckyman=dataObject.getString("luckyman");
            }
            boolean m_arr = dataObject.isNull("m_arr");

            if (!m_arr){
                List<List<String>> m_arrLists=new ArrayList<>();
                JSONArray m_arrArray = dataObject.getJSONArray("m_arr");

                for (int i = 0; i < m_arrArray.length(); i++) {
                    JSONArray jsonArray = m_arrArray.getJSONArray(i);
                    List<String> stringList=new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        String string = jsonArray.getString(j);
                        stringList.add(string);
                    }
                    m_arrLists.add(stringList);
                }
                PartyDetailsInfoBeen partyDetailsInfoBeen=new PartyDetailsInfoBeen(status,info,id,sponsor_id,title,detail,address,count,addtime,partyStatus,username,icon,is_organizer,luckyman,pay_method,m_arrLists);
                return partyDetailsInfoBeen;
            }else {
                PartyDetailsInfoBeen partyDetailsInfoBeen=new PartyDetailsInfoBeen(status,info,id,sponsor_id,title,detail,address,count,addtime,partyStatus,username,icon,is_organizer,luckyman,pay_method,null);
                return partyDetailsInfoBeen;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PartyNumber partyNumberInfo(String strJson){
        PartyNumber  partyNumber=null;
        try {
            JSONObject jsonObject=new JSONObject(strJson);
            partyNumber=new PartyNumber();
            String status = jsonObject.getString("status");
            String info = jsonObject.getString("info");
            partyNumber.setStatus(status);
            partyNumber.setInfo(info);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            List<PartyNumber.NumberInfo> numberInfoList=new ArrayList<>();
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                String id = dataObject.getString("id");
                String icon = dataObject.getString("icon");
                String username = dataObject.getString("username");
                numberInfoList.add(new PartyNumber.NumberInfo(id,icon,username,false));
            }
            partyNumber.setNumberInfoList(numberInfoList);
            return  partyNumber;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return partyNumber;
    }
}
