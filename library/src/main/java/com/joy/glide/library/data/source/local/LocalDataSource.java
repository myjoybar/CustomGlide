package com.joy.glide.library.data.source.local;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import com.joy.glide.library.cache.DefaultConfigurationFactory;
import com.joy.glide.library.cache.disk.LocalDiskCache;
import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.cache.memory.MemoryLruCache;
import com.joy.glide.library.data.DataSource;
import com.joy.glide.library.utils.GLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import me.joy.async.lib.task.AsynchronousTask;

/**
 * Created by joybar on 2018/5/30.
 */

public class LocalDataSource<R> implements DataSource<R> {

    private LocalDiskCache diskCache;
    private MemoryLruCache memoryLruCache;
    private MemoryCacheStrategy memoryCacheStrategy;
    private DiskCacheStrategy diskCacheStrategy;
    private static final int DEFAULT_AVAILABLE_MEMORY_PERCENT = 40;

    private Exception exception;

    public static class MemoryCacheStrategy {
        private boolean isMemoryCacheEnable;
        private int availableMemoryPercent;

        public MemoryCacheStrategy(boolean isMemoryCacheEnable, int availableMemoryPercent) {
            this.isMemoryCacheEnable = isMemoryCacheEnable;
            this.availableMemoryPercent = availableMemoryPercent;
        }
    }

    public static class DiskCacheStrategy {
        private boolean isCacheSource;
        private boolean isCacheResult;

        public DiskCacheStrategy(boolean isCacheSource, boolean isCacheResult) {
            this.isCacheSource = isCacheSource;
            this.isCacheResult = isCacheResult;
        }
    }


    public Exception getException(String msg) {
        if (null == exception) {
            exception = new Exception(msg);
        }
        return exception;
    }

    public LocalDataSource(Context context) {
        memoryLruCache = DefaultConfigurationFactory.createMemoryLruCache();
        diskCache = DefaultConfigurationFactory.createDiskCache(context.getApplicationContext());
    }


    public void setMemoryCacheStrategy(MemoryCacheStrategy memoryCacheStrategy) {
        if (null == memoryCacheStrategy) {
            this.memoryCacheStrategy = new MemoryCacheStrategy(true,
                    DEFAULT_AVAILABLE_MEMORY_PERCENT);
        }
        this.memoryCacheStrategy = memoryCacheStrategy;
    }

    public void setDiskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
        if (null == diskCacheStrategy) {
            this.diskCacheStrategy = new DiskCacheStrategy(true, true);
        }
        this.diskCacheStrategy = diskCacheStrategy;
    }

    @Override
    public void saveData(@NonNull final DrawableKey key, final R resource) {
        AsynchronousTask<Void, Void> asyncTask = new AsynchronousTask<Void, Void>() {

            @Override
            protected Void doInBackground() {
                if (memoryCacheStrategy.isMemoryCacheEnable) {
                    GLog.printInfo("save to memory");
                    memoryLruCache.put(key, (Bitmap) resource);

                }
                if (diskCacheStrategy.isCacheResult) {
                    try {
                        GLog.printInfo("save result to disk");
                        diskCache.save(key, (Bitmap) resource);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (diskCacheStrategy.isCacheSource) {
                    try {
                        GLog.printInfo("save source to disk");
                        diskCache.save(key.getOriginalKey(), (Bitmap)resource);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        asyncTask.execute();
    }

    @Override
    public R getData(@NonNull DrawableKey key) {

        Bitmap bitmap = null;
        if (memoryCacheStrategy.isMemoryCacheEnable) {
            GLog.printInfo("start load form memory data");
            bitmap = memoryLruCache.get(key);
            if (null != bitmap) {
                return (R)bitmap;
            }
        }
        if (diskCacheStrategy.isCacheResult) {
            GLog.printInfo("start load form disk result data");
            File imageFile = diskCache.get(key);
            if (imageFile != null && imageFile.exists() && imageFile.length() > 0) {
                // to to opt
                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
                    if (null != bitmap) {
                        return (R)bitmap;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (diskCacheStrategy.isCacheSource) {
            GLog.printInfo("start load form disk result data");
            File imageFile = diskCache.get(key.getOriginalKey());
            if (imageFile != null && imageFile.exists() && imageFile.length() > 0) {
                // to to opt
                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
                    if (null != bitmap) {
                        return (R)bitmap;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void getDataAsync(@NonNull DrawableKey key) {

    }

    @Override
    public void getDataAsync(@NonNull final DrawableKey key, final LoadDataListener
            loadDataCallback) {

        AsynchronousTask<Void, Bitmap> asynchronousTask = new AsynchronousTask<Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground() {

                Bitmap bitmap = null;
                if (memoryCacheStrategy.isMemoryCacheEnable) {
                    GLog.printInfo("start load form memory data");
                    bitmap = memoryLruCache.get(key);
                    if (null != bitmap) {
                        GLog.printInfo("load from memoryLruCache onSuccess");
                        return bitmap;
                    }
                }
                if (diskCacheStrategy.isCacheResult) {
                    GLog.printInfo("start load form disk data");
                    File imageFile = diskCache.get(key);
                    if (imageFile != null && imageFile.exists() && imageFile.length() > 0) {
                        // to to opt
                        try {
                            bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (null != bitmap) {
                            GLog.printInfo("load from diskCache onSuccess");
                            return bitmap;
                        }
                    }

                }
                return null;

            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (null != bitmap) {
                    loadDataCallback.onDataLoaded(bitmap);
                } else {
                    loadDataCallback.onDataLoadedError(getException("bitmap is null"));
                }
            }
        };

        asynchronousTask.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void deleteAll() {
        memoryLruCache.clear();
    }

    @Override
    public void delete(@NonNull DrawableKey key) {
        diskCache.clear();
        memoryLruCache.remove(key);
    }

    @Override
    public void cancel() {
        //nothing to do
    }


}
