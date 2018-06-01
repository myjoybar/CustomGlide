package com.joy.glide.library.cache.disk;

import android.graphics.Bitmap;

import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.cache.utils.IoUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by joybar on 2018/5/29.
 */

public interface DiskCache {

	File getDirectory();


	File get(DrawableKey key);


	boolean save(DrawableKey key, InputStream imageStream, IoUtils.CopyListener listener) throws IOException;


	boolean save(DrawableKey key, Bitmap bitmap) throws IOException;


	boolean remove(DrawableKey key);

	void close();

	void clear();
}
