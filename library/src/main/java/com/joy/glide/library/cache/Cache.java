package com.joy.glide.library.cache;

/**
 * Created by joybar on 27/05/2018.
 */

public interface Cache<T, Y> {
    Y get(T key);

    void save(T key, Y value);
}
