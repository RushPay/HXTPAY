package com.hxtao.qmd.hxtpay.utils;

import android.accounts.Account;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Handler;

import com.hxtao.qmd.hxtpay.app.BaseApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 版 权 ： 北京马里马里网络科技有限公司 (c)
 * <p/>
 * 创建时间：2016-07-21 14:22
 * <p/>
 * 类说明 ：
 */
public class Uiutils {

	private static long lastClickTime = System.currentTimeMillis();// 时间标识
	private static long time;

	public static Context getContext() {

		return BaseApplication.getContext();
	}

	public static boolean isAccountIsNull() {

		Account account = BaseApplication.getInstance().getAccount();
		if (account == null) {
			return true;
		} else {
			return false;
		}
	}

	public static Resources getResources() {
		return getContext().getResources();
	}

	public static String getString(int resId) {
		return getResources().getString(resId);
	}

	public static String[] getStringArray(int resId) {
		return getResources().getStringArray(resId);
	}

	public static String getPackageName() {
		return getContext().getPackageName();
	}

	public static int getColor(int resId) {
		return getResources().getColor(resId);
	}

	public static Handler getMainHandler() {
		return BaseApplication.getMainHandler();
	}

	public static long getMainThreadId() {

		return BaseApplication.getMainThreadId();
	}

	public static long formatDate(String dateStr) {
		Date parse = null;

		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
			parse = sf.parse(dateStr);
			time = parse.getTime();
			String format = sf.format(time);
//			LogUtils.v("format-" + format);
			String formatNet = sf.format(new Date());

		} catch (Exception e) {

		}
		return time;
	}

//	/**
//	 * 提取出城市或者县
//	 * @param city
//	 * @param district
//	 * @return
//	 */
//	public static String extractLocation(final String city, final String district){
//		return district.contains("县") ? district.substring(0, district.length() - 1) : city.substring(0, city.length() - 1);
//	}
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 判断SD卡是否挂载
	 */
	public static boolean isSDCardAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取版本信息
	 *
	 * @param context
	 * @return
	 */
	// 版本名
	public static String getVersionName(Context context) {
		return getPackageInfo(context).versionName;
	}

	public static int getVersionCode(Context context) {
		return getPackageInfo(context).versionCode;
	}

	private static PackageInfo getPackageInfo(Context context) {
		PackageInfo pi = null;

		try {
			PackageManager pm = context.getPackageManager();
			pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			// 获取软件版本号
//			versionCode = context.getPackageManager().getPackageInfo(
//					"com.home.getversioninfo", 1).versionCode;

			return pi;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pi;
	}

	/**
	 * 判断时间避免重复点击
	 *
	 * @return
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
