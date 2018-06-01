package com.joy.glide.library.cache.disk.naming;

import com.joy.glide.library.cache.key.DrawableKey;

/**
 * Created by joybar on 2018/5/29.
 */

public interface FileNameGenerator {
	String generate(DrawableKey key);
}
