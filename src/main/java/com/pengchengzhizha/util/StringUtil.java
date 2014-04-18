package com.pengchengzhizha.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class StringUtil {
	private static final String SEED_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	private static final Random random = new Random();
	
	public static String getRandomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(SEED_STRING.charAt(random.nextInt(SEED_STRING.length())));
		}
		return sb.toString();
	}

	public static String hydrateTpl(String tpl, Map<String, String> mapper) {
		for (Entry<String, String> e : mapper.entrySet()) {
			tpl = tpl.replace("${" + e.getKey() + "}", e.getValue());
		}
		return tpl;
	}

}
