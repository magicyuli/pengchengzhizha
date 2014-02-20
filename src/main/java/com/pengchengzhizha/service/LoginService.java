package com.pengchengzhizha.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.pengchengzhizha.db.ConnectionPool;
import com.pengchengzhizha.entity.User;
import com.pengchengzhizha.util.MD5;

public class LoginService {
	private final static String SITE_MARK = "com.pengchengzhizha";
	private final static String AUTHENTICATING_SQL = "select * from users where name=? and password=?";
	private final static String USER_INFO_QUERY_SQL = "select * from users where name=?";
	
	public User authenticate(String name, String password) throws IOException, SQLException, NoSuchAlgorithmException {
		Connection conn = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(AUTHENTICATING_SQL);
		ps.setString(1, name);
		ps.setString(2, MD5.encode(password));
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setIsAdmin(rs.getInt("is_admin"));
			return user;
		} else {
			return null;
		}
	}

	public String getRemeberMeCookieValue(String name, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String checkingMD5 = MD5.encode(name + MD5.encode(password) + SITE_MARK);
		String finalString = name + ":" + checkingMD5;
		BASE64Encoder e = new BASE64Encoder();
		return e.encode(finalString.getBytes("UTF-8"));
	}

	public User getRemeberedName(String remeberMeCookieValue) throws UnsupportedEncodingException, IOException, SQLException, NoSuchAlgorithmException {
		BASE64Decoder d = new BASE64Decoder();
		String plainString = new String(d.decodeBuffer(remeberMeCookieValue), "UTF-8");
		String[] nameAndCheckingMD5 = plainString.split(":");
		
		Connection conn = ConnectionPool.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement(USER_INFO_QUERY_SQL);
		ps.setString(1, nameAndCheckingMD5[0]);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			String testingMD5 = MD5.encode(rs.getString("name") + rs.getString("password") + SITE_MARK);
			if (testingMD5.equals(nameAndCheckingMD5[1])) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setIsAdmin(rs.getInt("is_admin"));
				return user;
			}
		}
		return null;
	}

}
