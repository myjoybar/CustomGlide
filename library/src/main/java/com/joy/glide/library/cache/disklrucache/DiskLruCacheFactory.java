package com.joy.glide.library.cache.disklrucache;

/**
 * Created by joybar on 26/05/2018.
 */

import java.io.File;

public class DiskLruCacheFactory implements DiskCache.Factory {

    private final int diskCacheSize;
    private final CacheDirectoryGetter cacheDirectoryGetter;

    /**
     * Interface called out of UI thread to get the cache folder.
     */
    public interface CacheDirectoryGetter {
        File getCacheDirectory();
    }

    public DiskLruCacheFactory(final String diskCacheFolder, int diskCacheSize) {
        this(new CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                return new File(diskCacheFolder);
            }
        }, diskCacheSize);
    }

    public DiskLruCacheFactory(final String diskCacheFolder, final String diskCacheName, int diskCacheSize) {
        this(new CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                return new File(diskCacheFolder, diskCacheName);
            }
        }, diskCacheSize);
    }

    /**
     * When using this constructor {@link CacheDirectoryGetter#getCacheDirectory()} will be called out of UI thread,
     * allowing to do I/O access without performance impacts.
     *
     * @param cacheDirectoryGetter Interface called out of UI thread to get the cache folder.
     * @param diskCacheSize        Desired max bytes size for the LRU disk cache.
     */
    public DiskLruCacheFactory(CacheDirectoryGetter cacheDirectoryGetter, int diskCacheSize) {
        this.diskCacheSize = diskCacheSize;
        this.cacheDirectoryGetter = cacheDirectoryGetter;
    }

    @Override
    public DiskCache build() {
        File cacheDir = cacheDirectoryGetter.getCacheDirectory();

        if (cacheDir == null) {
            return null;
        }

        if (!cacheDir.mkdirs() && (!cacheDir.exists() || !cacheDir.isDirectory())) {
            return null;
        }

        return DiskLruCacheWrapper.get(cacheDir, diskCacheSize);
    }
}

