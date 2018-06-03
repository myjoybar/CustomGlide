package com.joy.glide.library.cache.memory;

import android.graphics.Bitmap;

import com.joy.glide.library.cache.key.Key;

import java.util.Collection;

/**
 * Created by joybar on 2018/5/29.
 */

public interface MemoryCache {

	boolean put(Key key, Bitmap value);

	Bitmap get(Key key);

	Bitmap remove(Key key);

	Collection<Key> keys();

	void clear();
}
