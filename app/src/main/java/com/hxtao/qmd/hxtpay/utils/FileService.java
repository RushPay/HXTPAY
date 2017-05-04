package com.hxtao.qmd.hxtpay.utils;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Cyf on 2017/3/7.
 */

public class FileService {
    public Context context;
    public FileService(Context context) {
        this.context = context;
    }
    public boolean saveToRom(String name, String pass, String fileName){
        try{
            FileOutputStream fos=context.openFileOutput(fileName, Context.MODE_APPEND);
            String result=name+":"+pass+";";
            fos.write(result.getBytes());
            fos.flush();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Map<String,String> readFile(String fileName){
        Map<String,String> map=new LinkedHashMap<>();
        try{
            FileInputStream fis=context.openFileInput(fileName);
            String value=StreamTools.getValue(fis);
            String values[]=value.split(";");
            for (int i = values.length-1; i >=values.length-4; i--) {
                String userInfo = values[i];
                String[] userSplit = userInfo.split(":");
                String userName = userSplit[0];
                map.put(userName, userSplit[1]);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }
}
