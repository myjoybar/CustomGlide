package com.joy.glide.library.cache.memory;

import android.graphics.Bitmap;

import com.joy.glide.library.cache.key.Key;

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

	private final Map<Key, Reference<Bitmap>> softMap = Collections.synchronizedMap(new HashMap<Key, Reference<Bitmap>>());

	@Override
	public Bitmap get(Key key) {
		Bitmap result = null;
		Reference<Bitmap> reference = softMap.get(key);
		if (reference != null) {
			result = reference.get();
		}
		return result;
	}

	@Override
	public boolean put(Key key, Bitmap value) {
		softMap.put(key, createReference(value));
		return true;
	}

	@Override
	public Bitmap remove(Key key) {
		Reference<Bitmap> bmpRef = softMap.remove(key);
		return bmpRef == null ? null : bmpRef.get();
	}

	@Override
	public Collection<Key> keys() {
		synchronized (softMap) {
			return new HashSet<Key>(softMap.keySet());
		}
	}

	@Override
	public void clear() {
		softMap.clear();
	}

	/** Creates {@linkplain Reference not strong} reference of value */
	protected abstract Reference<Bitmap> createReference(Bitmap value);
}

