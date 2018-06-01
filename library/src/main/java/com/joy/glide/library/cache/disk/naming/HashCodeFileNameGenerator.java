package com.joy.glide.library.cache.disk.naming;

import com.joy.glide.library.cache.key.DrawableKey;

/**
 * Created by joybar on 2018/5/29.
 */

public class HashCodeFileNameGenerator implements FileNameGenerator {
	@Override
	public String generate(DrawableKey key) {
		return String.valueOf(key.hashCode());
	}
}
