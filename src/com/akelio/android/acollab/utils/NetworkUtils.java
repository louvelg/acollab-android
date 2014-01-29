package com.akelio.android.acollab.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
	public static boolean isNetworkReachable(Context context) {
		ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo current = mManager.getActiveNetworkInfo();
		if (current == null) { return false; }
		return (current.getState() == NetworkInfo.State.CONNECTED);
	}

	public static boolean isWifiReachable(Context context) {
		ConnectivityManager mManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo current = mManager.getActiveNetworkInfo();
		if (current == null) { return false; }
		return (current.getType() == ConnectivityManager.TYPE_WIFI);
	}
}
