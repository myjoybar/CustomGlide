package com.joy.glide.library.cache.disklrucache;

/**
 * Created by joybar on 26/05/2018.
 */

import com.joy.glide.library.cache.key.Key;

import java.io.File;

public interface DiskCache {


    interface Factory {

        /**
         * 250 MB of cache.
         */
        int DEFAULT_DISK_CACHE_SIZE = 250 * 1024 * 1024;
        String DEFAULT_DISK_CACHE_DIR = "image_manager_disk_cache";

        DiskCache build();
    }


    interface Writer {

        boolean write(File file);
    }


    File get(Key key);


    void put(Key key, Writer writer);


    void delete(Key key);


    void clear();
}

