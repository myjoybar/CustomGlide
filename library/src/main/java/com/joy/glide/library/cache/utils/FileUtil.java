package com.joy.glide.library.cache.utils;

import android.os.Build;
import android.os.Environment;

/**
 * Created by joybar on 2018/5/29.
 */

public class FileUtil {
	public static String getSdCard() {
		try {
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !isExternalStorageRemovable()) {
				return Environment.getExternalStorageDirectory().toString();

			} else {
				return Environment.getDataDirectory().toString();
			}
		} catch (Exception e) {
			return Environment.getDataDirectory().toString();
		}
	}
	private static boolean isExternalStorageRemovable() {
		if (hasGingerbread()) {
			return Environment.isExternalStorageRemovable();
		}
		return true;
	}
	//9
	private static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}
}
