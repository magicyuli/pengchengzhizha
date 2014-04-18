package com.pengchengzhizha.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.pengchengzhizha.dao.UserDAO;
import com.pengchengzhizha.entity.User;
import com.pengchengzhizha.util.MD5;

public class LoginService {
	private final static String SITE_MARK = "com.pengchengzhizha";
	
	private UserDAO userDao;
	
	public LoginService() {
		userDao = new UserDAO();
	}
	
	public User authenticate(String name, String password) throws IOException, SQLException, NoSuchAlgorithmException {
		User user = userDao.getUserByName(name);
		if (user == null || ! user.getPassword().equals(MD5.encode(password))) {
			return null;
		}
		return user;
	}

	public String getRemeberMeCookieValue(String name, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String checkingMD5 = MD5.encode(name + MD5.encode(password) + SITE_MARK);
		String finalString = name + ":" + checkingMD5;
		BASE64Encoder e = new BASE64Encoder();
		return e.encode(finalString.getBytes("UTF-8"));
	}

	public User getRemeberedUser(String remeberMeCookieValue) throws UnsupportedEncodingException, IOException, SQLException, NoSuchAlgorithmException {
		BASE64Decoder d = new BASE64Decoder();
		String plainString = new String(d.decodeBuffer(remeberMeCookieValue), "UTF-8");
		String[] nameAndCheckingMD5 = plainString.split(":");
		
		return userDao.getUserByName(nameAndCheckingMD5[0]);
	}

}
