package com.joy.glide.library.cache.memory;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.LruCache;

import com.joy.glide.library.cache.key.Key;
import com.joy.glide.library.utils.GLog;

import java.lang.ref.SoftReference;
import java.util.Collection;

/**
 * Created by joybar on 27/05/2018.
 */

public class MemoryLruCache implements MemoryCache {

    private static LruCache<Key, SoftReference<Bitmap>> mMemoryCache;

    private int availableMemoryPercent;

    public MemoryLruCache() {
        this(50);
    }

    public MemoryLruCache(int availableMemoryPercent) {
        if (availableMemoryPercent <= 0 || availableMemoryPercent >= 100) {
            throw new IllegalArgumentException("availableMemoryPercent must be in range (0 < % < " +
                    "" + "100)");
        }
        GLog.printInfo("availableMemoryPercent= " + availableMemoryPercent);
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory * availableMemoryPercent / 100;
        mMemoryCache = new LruCache<Key, SoftReference<Bitmap>>(cacheSize) {
            @Override
            protected int sizeOf(Key key, SoftReference<Bitmap> bitmap) {
                return bitmap.get().getByteCount();
            }
        };
    }

    @Override
    public boolean put(Key key, Bitmap value) {
        if (get(key) == null) {
            mMemoryCache.put(key, new SoftReference<Bitmap>(value));
            return true;
        }
        return false;

    }

    @Override
    public Bitmap get(Key key) {
        if (key != null) {
            return mMemoryCache.get(key).get();
        }
        return null;
    }

    @Override
    public Bitmap remove(Key key) {
        if (key != null) {
            return mMemoryCache.remove(key).get();
        }
        return null;
    }

    @Override
    public Collection<Key> keys() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void clear() {
        mMemoryCache.trimToSize(-1);
    }


}
