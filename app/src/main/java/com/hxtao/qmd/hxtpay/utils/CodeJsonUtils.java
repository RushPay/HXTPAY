package com.hxtao.qmd.hxtpay.utils;

import com.hxtao.qmd.hxtpay.been.CodeDataBeen;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Description:
 * @Author: Cyf on 2017/2/14.
 */

public class CodeJsonUtils {
    public static CodeDataBeen getCodeData(String codeString){
        CodeDataBeen codeDataBeen=null;
        try {
            JSONObject jsonObject=new JSONObject(codeString);
            JSONObject dataJsonobject = jsonObject.getJSONObject("data");
            int type = dataJsonobject.getInt("type");
            JSONObject m_infoJsonobject = dataJsonobject.getJSONObject("m_info");
            switch (type){
                case 1:
                case 2:
                    String id = m_infoJsonobject.getString("id");
                    String username = m_infoJsonobject.getString("username");
                    String icon = m_infoJsonobject.getString("icon");
                    String account = m_infoJsonobject.getString("account");
                    codeDataBeen=new CodeDataBeen(type,id,username,icon,account);
                break;
                case 3:
                    String partyid = m_infoJsonobject.getString("id");
                    String title = m_infoJsonobject.getString("title");
                    String detail = m_infoJsonobject.getString("detail");
                    String address = m_infoJsonobject.getString("address");
                    codeDataBeen=new CodeDataBeen(type,partyid,title,detail,address);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return codeDataBeen;
    }
}
