package com.hxtao.qmd.hxtpay.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Cyf on 2016/12/20.
 */

public class PostRequest {

    public static void main(){
        //向Map集合中把请求参数添加进去
        final Map<String, String> params = new HashMap<String, String>();
        params.put("account", "");
        params.put("password","" );
        params.put("sign", "");
        params.put("role", "");

        new Thread(){
            @Override
            public void run() {
                super.run();

                Log.e("Thread","Thread.start()");
//                getGetHttp(params,"UTF-8");
                Log.e("Thread","Thread.stop()");
            }
        }.start();
    }


    public static void getGetHttp(Map<String, String> params, String strUrl,BitmapCallBack callBack){
        String cc="http://192.168.199.137/api/member/register?";

        byte[] data = getRequestData(params, "UTF-8").toString().getBytes();
        try {
            URL url=new URL(strUrl);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            connection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);

            //设置请求体的类型是文本类型
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data);

            int responseCode = connection.getResponseCode();
            if (responseCode==200){
//                InputStreamReader input=new InputStreamReader();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                callBack.bitmapCall(strUrl,bitmap);
//                BufferedReader bf=new BufferedReader(input);
//                String line;
//                StringBuilder stringBuilder=new StringBuilder();
//                while ((line=bf.readLine())!=null){
//                    stringBuilder.append(line);
//                }
//                Log.e("getGetHttp",String.valueOf(stringBuilder));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //请求体的封装
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("stringBuffer---URL:++",stringBuffer.toString());

        return stringBuffer;
    }
}
