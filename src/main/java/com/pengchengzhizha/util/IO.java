package com.pengchengzhizha.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class IO {
	public static String readFile(String path) throws IOException {
		String filePath = IO.class.getResource(path).getPath();
		
		System.out.println(path + " : " + filePath);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), Charset.forName("UTF-8")));
		try {
			StringBuilder sb = new StringBuilder();
			while (in.ready()) {
				sb.append(in.readLine());
			}
			return sb.toString();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String readInputStream(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		while (true) {
			int len = in.read(buffer);
			if (len <= 0) {
				break;
			} else {
				out.write(buffer, 0, len);
			}
		}
		return out.toString("UTF-8");
	} 
}
