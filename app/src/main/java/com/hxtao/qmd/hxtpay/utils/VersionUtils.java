package com.hxtao.qmd.hxtpay.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @ClassName: VersionUtils
 * @Description:获取版本信息
 * @Author:Cyf
 * @time: 2016/12/21  11:34
 */

public class VersionUtils {

    /**
     * @description:获取应用程序版本名
     * @param
     * @return String   eg:  1.0
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * @description:获取应用程序版本号
     * @param
     * @return int eg: 1
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * @description:获取应用程序包的信息
     * @param
     * @return
     */
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
