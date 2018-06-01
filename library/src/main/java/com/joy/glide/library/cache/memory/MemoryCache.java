package com.joy.glide.library.cache.memory;

import android.graphics.Bitmap;

import com.joy.glide.library.cache.key.DrawableKey;

import java.util.Collection;

/**
 * Created by joybar on 2018/5/29.
 */

public interface MemoryCache {

	boolean put(DrawableKey key, Bitmap value);

	Bitmap get(DrawableKey key);

	Bitmap remove(DrawableKey key);

	Collection<DrawableKey> keys();

	void clear();
}
