package com.joy.glide.library.cache;

import android.content.Context;

import com.joy.glide.library.cache.disk.LocalDiskCache;
import com.joy.glide.library.cache.disk.naming.FileNameGenerator;
import com.joy.glide.library.cache.disk.naming.HashCodeFileNameGenerator;
import com.joy.glide.library.cache.disk.naming.Md5FileNameGenerator;
import com.joy.glide.library.cache.memory.MemoryLruCache;
import com.joy.glide.library.cache.utils.StorageUtils;

import java.io.File;

/**
 * Created by joybar on 2018/5/29.
 */

public class DefaultConfigurationFactory {
	public static FileNameGenerator createFileNameGenerator() {
		return new HashCodeFileNameGenerator();
	}

	public static LocalDiskCache createDiskCache(Context context) {
		File cacheDir = StorageUtils.getCacheDirectory(context);
		File reserveCacheDir = createReserveDiskCacheDir(context);
		FileNameGenerator fileNameGenerator = new Md5FileNameGenerator();
		LocalDiskCache diskCache = new LocalDiskCache(cacheDir, reserveCacheDir, fileNameGenerator);
		return diskCache;
	}

	private static File createReserveDiskCacheDir(Context context) {
		File cacheDir = StorageUtils.getCacheDirectory(context, false);
		File individualDir = new File(cacheDir, "uil-images");
		if (individualDir.exists() || individualDir.mkdir()) {
			cacheDir = individualDir;
		}
		return cacheDir;
	}

	public static MemoryLruCache createMemoryLruCache() {
		MemoryLruCache memoryLruCache = new MemoryLruCache();
		return memoryLruCache;
	}




}
