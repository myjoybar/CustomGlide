package com.joy.glide.libasync.util;

import java.util.Locale;
import java.util.concurrent.ThreadFactory;

/**
 * Created by joybar on 2018/5/9.
 */

public class Util {
	/** Returns a {@link Locale#US} formatted {@link String}. */
	public static String format(String format, Object... args) {
		return String.format(Locale.US, format, args);
	}


	public static ThreadFactory threadFactory(final String name, final boolean daemon) {
		return new ThreadFactory() {
			@Override public Thread newThread(Runnable runnable) {
				Thread result = new Thread(runnable, name);
				result.setDaemon(daemon);
				return result;
			}
		};
	}
}
