package com.joy.glide.library.cache.disk;

import android.graphics.Bitmap;

import com.joy.glide.library.cache.key.Key;
import com.joy.glide.library.cache.utils.IoUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by joybar on 2018/5/29.
 */

public interface DiskCache<R> {

	File getDirectory();


	File get(Key key);


	boolean save(Key key, InputStream imageStream, IoUtils.CopyListener listener) throws IOException;


	boolean save(Key key, Bitmap bitmap) throws IOException;


	boolean remove(Key key);

	void close();

	void clear();
}
