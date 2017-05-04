package com.hxtao.qmd.hxtpay.app;

import android.accounts.Account;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.hxtao.qmd.hxtpay.utils.ConstantUtils;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;
import com.hxtao.qmd.hxtpay.utils.SharedPreferencesUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xutils.x;

/**
 * Created by Administrator on 2016/7/20.
 */
public class BaseApplication extends Application {
	private static Context mContext;
	private static Thread mMainThread;
	private static long mMainThreadId;
	private static Looper mMainLooper;
	private static Handler mMainHandler;
	private int cartNum;

	private static IWXAPI iwxapi;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String sign;

	/**
	 * String username = (String) data.getUsername();
	 String icon = (String) data.getIcon();
	 String account = data.getAccount();
	 String mark = data.getMark();
	 String money = data.getMoney();
	 String last_login_time = data.getLast_login_time();
	 Double monetary_month =  data.getMonetary_month();
	 int party_times_month = data.getParty_times_month();*/

	public String username;
	public String icon;
	public String accounts;
	public String mark;
	public String money;
	public String id;
	public String last_login_time;
	public Double monetary_month;
	public int party_times_month;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAccounts() {
		return accounts;
	}

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}

	public Double getMonetary_month() {
		return monetary_month;
	}

	public void setMonetary_month(Double monetary_month) {
		this.monetary_month = monetary_month;
	}

	public int getParty_times_month() {
		return party_times_month;
	}

	public void setParty_times_month(int party_times_month) {
		this.party_times_month = party_times_month;
	}

	private static BaseApplication baseApplication;
	public static BaseApplication createApplication(){
		if (baseApplication==null){
			baseApplication=new BaseApplication();
		}
			return baseApplication;
	}



	@Override
	public void onCreate() {
		super.onCreate();
		x.Ext.init(this);
		x.Ext.setDebug(true);

		ImageLoadUtil.initImageLoader(this);
		//注册微信
		regToWx();

		// 上下文
		mContext = getApplicationContext();
		// 主线程
		mMainThread = Thread.currentThread();
		// mMainThreadId = mMainThread.getId();
		mMainThreadId = android.os.Process.myTid();
		mMainLooper = getMainLooper();
		// 创建主线程的handler
		mMainHandler = new Handler();
		SharedPreferencesUtils spUtil = new SharedPreferencesUtils(this, ConstantUtils.PREFERENCE_NAME);
		account = (Account) spUtil.getObject(ConstantUtils.ACCOUNT_NAME);

	}
	//注册微信
	private void regToWx() {
		iwxapi= WXAPIFactory.createWXAPI(this,null);
		iwxapi.registerApp(Constants.APP_ID);
	}


	private static BaseApplication instance;

	private Account account, oldAccount;

	public Account getOldAccount() {
		return oldAccount;
	}

	public void setOldAccount(Account oldAccount) {
		this.oldAccount = oldAccount;
	}

	public BaseApplication() {
		instance = this;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public static BaseApplication getInstance() {
		return instance;
	}

	public static Context getContext() {
		return mContext;
	}

	public int getCartNum() {
		return cartNum;
	}

	public void setCartNum(int cartNum) {
		this.cartNum = cartNum;
	}

	public static Thread getMainThread() {
		return mMainThread;
	}

	public static long getMainThreadId() {
		return mMainThreadId;
	}

	public static Looper getMainThreadLooper() {
		return mMainLooper;
	}

	public static Handler getMainHandler() {
		return mMainHandler;
	}

}
