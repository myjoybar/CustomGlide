package com.joy.glide.library.utils;

import android.util.Log;

/**
 * Created by joybar on 2018/5/17.
 */

public class GLog {
	private static boolean enable = true;
	private static final String TAG = "Glide";

	public static void setEnable(boolean enable) {
		GLog.enable = enable;
	}

	public static void print(String msg) {
		if (enable) {
			Log.d(TAG, msg);
		}
	}

}