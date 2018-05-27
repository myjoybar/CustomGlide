package com.joy.glide.library.cache.key;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * Created by joybar on 26/05/2018.
 */

public class DrawableKey implements Key {

    private final String id;
    private final int width;
    private final int height;
    private final Key signature;

    private String stringKey;
    private int hashCode;
    private Key originalKey;

    public String getId() {
        return id;
    }

    public DrawableKey(String id, int width, int height, Key signature) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.signature = signature;
    }

    public Key getOriginalKey() {
        if (originalKey == null) {
            originalKey = new OriginalKey(id, signature);
        }
        return originalKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DrawableKey engineKey = (DrawableKey) o;

        if (!id.equals(engineKey.id)) {
            return false;
        } else if (!signature.equals(engineKey.signature)) {
            return false;
        } else if (height != engineKey.height) {
            return false;
        } else if (width != engineKey.width) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = id.hashCode();
            hashCode = 31 * hashCode + signature.hashCode();
            hashCode = 31 * hashCode + width;
            hashCode = 31 * hashCode + height;
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (stringKey == null) {
            stringKey = new StringBuilder()
                    .append("EngineKey{")
                    .append(id)
                    .append('+')
                    .append(signature)
                    .append("+[")
                    .append(width)
                    .append('x')
                    .append(height)
                    .append("]+")
                    .append('\'')
                    .append('}')
                    .toString();
        }
        return stringKey;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        byte[] dimensions = ByteBuffer.allocate(8)
                .putInt(width)
                .putInt(height)
                .array();
        signature.updateDiskCacheKey(messageDigest);
        messageDigest.update(id.getBytes(STRING_CHARSET_NAME));
        messageDigest.update(dimensions);
    }
}
