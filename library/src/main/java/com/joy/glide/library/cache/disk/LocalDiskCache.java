package com.joy.glide.library.cache.disk;

import android.graphics.Bitmap;

import com.joy.glide.library.cache.disk.naming.FileNameGenerator;
import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.cache.utils.FileUtil;
import com.joy.glide.library.cache.utils.IoUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by joybar on 27/05/2018.
 */

public class LocalDiskCache implements DiskCache {

	public static final String FILE_PATH = FileUtil.getSdCard() + "/cache/pics";

	private static final String TEMP_IMAGE_POSTFIX = ".tmp";
	public static final int DEFAULT_BUFFER_SIZE = 100 * 1024; // 32 Kb

	public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
	public static final int DEFAULT_COMPRESS_QUALITY = 100;

	protected final File cacheDir;
	protected final File reserveCacheDir;
	protected final FileNameGenerator fileNameGenerator;


	public LocalDiskCache(File cacheDir, File reserveCacheDir, FileNameGenerator fileNameGenerator) {
		this.cacheDir = cacheDir;
		this.reserveCacheDir = reserveCacheDir;
		this.fileNameGenerator = fileNameGenerator;
	}

	@Override
	public File getDirectory() {
		return cacheDir;
	}

	@Override
	public File get(DrawableKey key) {
		return getFile(key);
	}

	@Override
	public boolean save(DrawableKey key, InputStream imageStream, IoUtils.CopyListener listener) throws IOException {
		File imageFile = getFile(key);
		File tmpFile = new File(imageFile.getAbsolutePath() + TEMP_IMAGE_POSTFIX);
		boolean loaded = false;
		try {
			OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), DEFAULT_BUFFER_SIZE);
			try {
				loaded = IoUtils.copyStream(imageStream, os, listener, DEFAULT_BUFFER_SIZE);
			} finally {
				IoUtils.closeSilently(os);
			}
		} finally {
			if (loaded && !tmpFile.renameTo(imageFile)) {
				loaded = false;
			}
			if (!loaded) {
				tmpFile.delete();
			}
		}
		return loaded;
	}

	@Override
	public boolean save(DrawableKey key, Bitmap bitmap) throws IOException {
		File imageFile = getFile(key);
		File tmpFile = new File(imageFile.getAbsolutePath() + TEMP_IMAGE_POSTFIX);
		OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), DEFAULT_BUFFER_SIZE);
		boolean savedSuccessfully = false;
		try {
			savedSuccessfully = bitmap.compress(DEFAULT_COMPRESS_FORMAT, DEFAULT_COMPRESS_QUALITY, os);
		} finally {
			IoUtils.closeSilently(os);
			if (savedSuccessfully && !tmpFile.renameTo(imageFile)) {
				savedSuccessfully = false;
			}
			if (!savedSuccessfully) {
				tmpFile.delete();
			}
		}
		//bitmap.recycle();
		return savedSuccessfully;
	}

	@Override
	public boolean remove(DrawableKey key) {
		return getFile(key).delete();
	}

	@Override
	public void close() {
		// Nothing to do
	}

	@Override
	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files != null) {
			for (File f : files) {
				f.delete();
			}
		}
	}


	protected File getFile(DrawableKey key) {
		String fileName = fileNameGenerator.generate(key);
		File dir = cacheDir;
		if (!cacheDir.exists() && !cacheDir.mkdirs()) {
			if (reserveCacheDir != null && (reserveCacheDir.exists() || reserveCacheDir.mkdirs())) {
				dir = reserveCacheDir;
			}
		}
		return new File(dir, fileName);
	}


}
