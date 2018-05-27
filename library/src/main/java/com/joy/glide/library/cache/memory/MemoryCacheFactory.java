package com.joy.glide.library.cache.memory;

import android.util.LruCache;

import com.joy.glide.library.cache.DrawableSave;
import com.joy.glide.library.cache.key.DrawableKey;

/**
 * Created by joybar on 27/05/2018.
 */

public class MemoryCacheFactory {

    private static LruCache<DrawableKey, DrawableSave> mMemoryCache;

    public static MemoryCacheFactory getInstance() {
        return MemoryCacheFactoryHolder.INSTANCE;
    }

    private static class MemoryCacheFactoryHolder {
        private static MemoryCacheFactory INSTANCE = new MemoryCacheFactory();
    }


    public MemoryCacheFactory() {

        // 获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        // 设置图片缓存大小为程序最大可用内存的1/8
        mMemoryCache = new LruCache<DrawableKey, DrawableSave>(cacheSize) {
            @Override
            protected int sizeOf(DrawableKey key, DrawableSave drawableSave) {
                return drawableSave.getBitmap().getByteCount();
            }
        };
    }

    public void addDrawableSaveToMemoryCache(DrawableKey key, DrawableSave drawableSave) {
        if (getDrawableSaveFromMemoryCache(key) == null) {
            mMemoryCache.put(key, drawableSave);
        }
    }

    public DrawableSave getDrawableSaveFromMemoryCache(DrawableKey key) {
        return mMemoryCache.get(key);
    }


}
