package com.joy.glide.library.cache.key;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Created by joybar on 26/05/2018.
 */

public interface Key {
    String STRING_CHARSET_NAME = "UTF-8";

    void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException;

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}