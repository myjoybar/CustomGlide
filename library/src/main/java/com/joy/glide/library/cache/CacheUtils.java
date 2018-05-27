package com.joy.glide.library.cache;

import com.joy.glide.library.cache.disk.DiskCacheFactory;
import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.cache.memory.MemoryCacheFactory;

import me.joy.async.lib.task.AsynchronousTask;

/**
 * Created by joybar on 27/05/2018.
 */

public class CacheUtils {

    public static DrawableSave get(DrawableKey key) {
        DrawableSave drawableSave = MemoryCacheFactory.getInstance()
                .getDrawableSaveFromMemoryCache(key);
        if (null == drawableSave) {
            drawableSave = DiskCacheFactory.getInstance().getDrawableSaveFromDisk(key);
        }
        return drawableSave;
    }

    public static void saveDrawableSave(final DrawableKey key, final DrawableSave drawableSave) {
        AsynchronousTask<Void, Void> asyncTask = new AsynchronousTask<Void, Void>() {

            @Override
            protected Void doInBackground() {
                MemoryCacheFactory.getInstance().addDrawableSaveToMemoryCache(key, drawableSave);
                DiskCacheFactory.getInstance().setDrawableSave2Local(key, drawableSave);
                return null;
            }
        };
        asyncTask.execute();


    }

}
