package com.yhjia.me.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.List;

public class SystemInfo {

	public static final String MOBILE = "3G";
	public static final String WIFI = "wifi";

	private static ConnectivityManager connMgr;

	public String getDeviceId(Context ctx) {
		String imei = null;
		TelephonyManager telephony = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
//		imei = telephony.getDeviceId();
		return "";
	}

	public String getMacid(Context ctx) {
		String macid = "";
		WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		macid = wifiInfo.getMacAddress();
		return macid;
	}

	public static String getVersion(Context ctx) {
		try {
			PackageManager manager = ctx.getPackageManager();
			PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int getVersionCode(Context ctx) {
		int versionCode = 0;
		try {
			PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			versionCode = info.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public static boolean screenLocked(Context context) {
		KeyguardManager mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		return mKeyguardManager.inKeyguardRestrictedInputMode();
	}

	public static boolean isAppOnForeground(Context context) {
		String packageName = context.getPackageName();
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// Returns a list of application processes that are running on the device
		// List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		// if (appProcesses == null) return false;
		//
		// for (RunningAppProcessInfo appProcess : appProcesses) {
		// // The name of the process that this object is associated with.
		// if (appProcess.processName.equals(packageName) && appProcess.importance ==
		// RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
		// return true;
		// }
		// }

		List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (topActivity.getPackageName().equals(packageName)) {
				return true;
			}
		}
		return false;
	}

	public String getPackageName(Context ctx) {
		String packageName = null;
		try {
			PackageInfo info = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			packageName = info.packageName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageName;
	}

	public DisplayMetrics getDisplayMetrics(Activity act) {
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		return mDisplayMetrics;
	}

	public static boolean isOnline(Context context) {
		if (connMgr == null) {
			connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isAvailable());
	}

	public String getNetworkType(Context context) {
		if (connMgr == null) {
			connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = networkInfo.isConnected();
		networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = networkInfo.isConnected();

		if (isWifiConn) {
			return WIFI;
		} else if (isMobileConn) {
			return MOBILE;
		}
		return null;
	}

	public String getNativePhoneNumber(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getLine1Number();
	}

	public String getProvidersName(Context context) {
		String ProvidersName = null;
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String IMSI = telephonyManager.getSubscriberId();
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			ProvidersName = "移动";
		} else if (IMSI.startsWith("46001")) {
			ProvidersName = "联通";
		} else if (IMSI.startsWith("46003")) {
			ProvidersName = "电信";
		}
		return ProvidersName;
	}
	
	public static ApplicationInfo getApplicationInfo(Context context){
		PackageManager manager = context.getPackageManager();
		ApplicationInfo applicationInfo = null;
		try {
			applicationInfo = manager.getApplicationInfo(context.getPackageName(), 0);
			return applicationInfo;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getAppName(Context context){
		if(getApplicationInfo(context)==null)return "unknow";
		return (String) context.getPackageManager().getApplicationLabel(getApplicationInfo(context));
	}
	
	public String getImei(Context context) {
		return   (( TelephonyManager )context.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
	}
}