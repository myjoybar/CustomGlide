package com.joy.glide.library.cache.disk.naming;

import com.joy.glide.library.cache.key.DrawableKey;
import com.joy.glide.library.utils.GLog;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by joybar on 2018/5/29.
 */

public class Md5FileNameGenerator implements FileNameGenerator {

	private static final String HASH_ALGORITHM = "MD5";
	private static final int RADIX = 10 + 26; // 10 digits + 26 letters

	@Override
	public String generate(DrawableKey key) {
		byte[] md5 = getMD5(key.toString().getBytes());
		BigInteger bi = new BigInteger(md5).abs();
		return bi.toString(RADIX);
	}

	private byte[] getMD5(byte[] data) {
		byte[] hash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(data);
			hash = digest.digest();
		} catch (NoSuchAlgorithmException e) {
			GLog.printError("NoSuchAlgorithmException error:" + e.getMessage());
		}
		return hash;
	}
}
