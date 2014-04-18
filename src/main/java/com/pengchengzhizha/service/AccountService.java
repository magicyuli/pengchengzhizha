package com.pengchengzhizha.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pengchengzhizha.bean.PageBean;
import com.pengchengzhizha.conf.Conf;
import com.pengchengzhizha.dao.UserDAO;
import com.pengchengzhizha.entity.User;
import com.pengchengzhizha.exception.IllegalInputException;
import com.pengchengzhizha.util.IO;
import com.pengchengzhizha.util.MD5;
import com.pengchengzhizha.util.MailSender;
import com.pengchengzhizha.util.StringUtil;

public class AccountService {
	private UserDAO userDao;
	
	public AccountService() {
		userDao = new UserDAO();
	}

	public int modifyPassword(int userId, String oldPassword, String newPassword) throws IllegalInputException, SQLException, IOException, NoSuchAlgorithmException, IllegalArgumentException, IllegalAccessException {
		if (isIllegalPassword(newPassword)) {
			throw new IllegalInputException("新密码过短或包含非法字符！");
		}
		if (! userDao.getById(userId).getPassword().equals(MD5.encode(oldPassword))) {
			throw new IllegalInputException("旧密码错误！");
		}
		User user = new User();
		user.setId(userId);
		user.setPassword(MD5.encode(newPassword));
		return userDao.update(user);
	}

	private boolean isIllegalPassword(String newPassword) {
		return newPassword.length() < 6 || Pattern.matches("[^0-9a-zA-Z_]", newPassword);
	}

	public void register(String name, String password, String email) throws IllegalInputException, NoSuchAlgorithmException, IOException, MessagingException, SQLException {
		if (isIllegalPassword(password)) {
			throw new IllegalInputException("密码过短或包含非法字符！");
		}
		if (isIllegalName(name)) {
			throw new IllegalInputException("用户名过短或包含非法字符！");
		}
		if (! Pattern.matches("^[0-9a-zA-Z_]+@([0-9a-zA-Z]+\\.)+[a-zA-Z]+$", email)) {
			throw new IllegalInputException("邮箱格式错误！");
		}
		User user = new User();
		user.setName(name);
		user.setPassword(MD5.encode(password));
		user.setEmail(email);
		user.setHash(StringUtil.getRandomString(10));
		user.setIsActivated(0);
		user.setIsAdmin(0);
		user.setIsMessageManager(0);
		user.setIsNewsManager(0);
		userDao.save(user);
		user = userDao.getUserByName(name);
		sendEmail(user);
	}

	private void sendEmail(User user) throws IOException, MessagingException {
		MailSender mailSender = MailSender.getHtmlMailSender(Conf.EMAIL_FROM_ADDRESS, user.getEmail());
		mailSender.setSubject("岛城鹏程账户激活");
		
		String tpl = IO.readFile("/tpl/email_activate");
		
		Map<String, String> mapper = new HashMap<String, String>(3);
		mapper.put("name", user.getName());
		mapper.put("hash", user.getHash());
		mapper.put("id", String.valueOf(user.getId()));
		
		String content = StringUtil.hydrateTpl(tpl, mapper);
		System.out.println(content);
		mailSender.setContent(content);
		mailSender.send();
	}

	private boolean isIllegalName(String name) {
		return name.length() < 4 || Pattern.matches("[^0-9a-zA-Z_]", name);
	}
	
	public boolean isNameOccupied(String name) throws SQLException, IOException {
		User user = userDao.getUserByName(name);
		return user != null;
	}
	
	public boolean isEmailOccupied(String email) throws SQLException, IOException {
		User user = userDao.getUserByEmail(email);
		return user != null;
	}

	public int activate(int id, String hash) throws SQLException, IOException, IllegalArgumentException, IllegalAccessException {
		User user = userDao.getById(id);
		if (user.isActivated()) {
			return 1;
		}
		if (! user.getHash().equals(hash)) {
			return 0;
		}
		user.setIsActivated(1);
		user.setHash("");
		try {
			return userDao.update(user);
		} catch (IllegalInputException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getUserListJSON(int page, int pageSize) throws SQLException, IOException {
		PageBean<User> paginatedUserList = userDao.getPaginatedUserList(page, pageSize);
		return new JSONObject(paginatedUserList).toString();
	}

	public void updateAuthFromJSON(String json) throws SQLException, IOException {
		JSONObject obj = new JSONObject(json);
		JSONArray authList = obj.getJSONArray("authList");
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < authList.length(); i++) {
			JSONObject item = authList.getJSONObject(i);
			User user = new User();
			user.setId(item.getInt("id"));
			user.setIsAdmin(item.getInt("isAdmin"));
			user.setIsNewsManager(item.getInt("isNewsManager"));
			user.setIsMessageManager(item.getInt("isMessageManager"));
			user.setIsActivated(1);
			users.add(user);
		}
		try {
			userDao.batchUpdate(users);
		} catch (IllegalInputException e) {
			e.printStackTrace();
		}
	}
	
}
