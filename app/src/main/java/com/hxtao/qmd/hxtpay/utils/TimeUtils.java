package com.hxtao.qmd.hxtpay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:时间戳转换字符串类
 * @Author: Cyf on 2017/1/11.
 */

public class TimeUtils {

    private static TimeUtils timeUtils;
    //Date()方法需要到毫秒的时间戳 但是给的是到秒的时间戳所以 *1000
    private int MULTIPLE=1000;
    private String TIMESTR="yyyy-MM-dd kk:mm:ss";
    private String MINUTESSTR="yyyy-MM-dd kk:mm";
    private String DATESTR="yyyy年MM月dd日";
    public static TimeUtils createTimeUtils() {
        if (timeUtils==null){
            timeUtils=new TimeUtils();
        }
        return timeUtils;
    }
    /**
     * @description:
     * @param
     * @return yyyy-MM-dd kk:mm:ss
     */
    public  String getTimeString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTR);
        Date date = new Date(time * MULTIPLE);
        return sdf.format(date);
    }
    public  String getMinutesString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(MINUTESSTR);
        Date date = new Date(time * MULTIPLE);
        return sdf.format(date);
    }
    public  String getDateString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATESTR);
        Date date = new Date(time * MULTIPLE);
        return sdf.format(date);
    }
}
