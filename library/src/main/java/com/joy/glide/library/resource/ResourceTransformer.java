package com.joy.glide.library.resource;

/**
 * Created by joybar on 2018/6/4.
 */

public interface ResourceTransformer<S,T> {
	T transform(S s);
}
