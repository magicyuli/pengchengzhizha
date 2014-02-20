package com.pengchengzhizha.util;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
	public static String encode(String plaintext) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plaintext.getBytes("UTF-8"));
		byte[] b = md.digest();
		
		StringBuffer sb = new StringBuffer();
		for (int i : b) {
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(i));
		}
		return sb.toString();
	}
}
