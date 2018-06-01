package com.joy.glide.library.cache.memory;

import android.graphics.Bitmap;

import com.joy.glide.library.cache.key.DrawableKey;

import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by joybar on 2018/5/29.
 */

public abstract class BaseMemoryCache implements MemoryCache {

	private final Map<DrawableKey, Reference<Bitmap>> softMap = Collections.synchronizedMap(new HashMap<DrawableKey, Reference<Bitmap>>());

	@Override
	public Bitmap get(DrawableKey key) {
		Bitmap result = null;
		Reference<Bitmap> reference = softMap.get(key);
		if (reference != null) {
			result = reference.get();
		}
		return result;
	}

	@Override
	public boolean put(DrawableKey key, Bitmap value) {
		softMap.put(key, createReference(value));
		return true;
	}

	@Override
	public Bitmap remove(DrawableKey key) {
		Reference<Bitmap> bmpRef = softMap.remove(key);
		return bmpRef == null ? null : bmpRef.get();
	}

	@Override
	public Collection<DrawableKey> keys() {
		synchronized (softMap) {
			return new HashSet<DrawableKey>(softMap.keySet());
		}
	}

	@Override
	public void clear() {
		softMap.clear();
	}

	/** Creates {@linkplain Reference not strong} reference of value */
	protected abstract Reference<Bitmap> createReference(Bitmap value);
}

