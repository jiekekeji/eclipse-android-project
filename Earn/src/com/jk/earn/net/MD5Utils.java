package com.jk.earn.net;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	/**
	 * MD5加密
	 * 
	 * @param data
	 *            需加密的字符串
	 * @return
	 */
	public static String toMD5(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(data.getBytes());
			return toHexString(md.digest(), data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String toHexString(byte[] bytes, String separator) {
		StringBuilder hexstring = new StringBuilder();
		for (byte b : bytes) {
			String hex = Integer.toHexString(0xFF & b);
			if (hex.length() == 1) {
				hexstring.append('0');
			}
			hexstring.append(hex);
		}
		return hexstring.toString();
	}
}
