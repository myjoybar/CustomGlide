package com.joy.glide.library.cache.key;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * Created by joybar on 26/05/2018.
 */

public final class EmptySignature implements Key {
    private static final EmptySignature EMPTY_KEY = new EmptySignature();

    public static EmptySignature obtain() {
        return EMPTY_KEY;
    }

    private EmptySignature() {
        // Empty.
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        // Do nothing.
    }

    @Override
    public Key getOriginalKey() {
        return null;
    }
}
