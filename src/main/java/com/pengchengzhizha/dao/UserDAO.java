package com.pengchengzhizha.dao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pengchengzhizha.bean.PageBean;
import com.pengchengzhizha.entity.User;
import com.pengchengzhizha.exception.IllegalInputException;

public class UserDAO extends BaseDAO {
	private static final String GET_USER_BY_ID_SQL = "select * from users where id=?";
	private final static String GET_USER_BY_NAME_SQL = "select * from users where name=?";
	private static final String GET_USER_BY_EMAIL_SQL = "select * from users where email=?";
	private static final String UPDATE_SQL = "update users set name=?,password=?,email=?,is_admin=?,is_activated=?,is_news_manager=?,is_message_manager=?,hash=? where id=?";
	private static final String SAVE_USER_SQL = "insert into users (name,password,email,is_admin,is_activated,is_news_manager,is_message_manager,hash) values (?,?,?,?,?,?,?,?)";

	private static final String TABLE_NAME = "users";
	private static final String GET_USER_LIST_SQL = "select * from users where is_activated=1 limit ?,?";
	
	public UserDAO() {
		super(TABLE_NAME);
	}
	
	public User getById(int id) throws SQLException, IOException {
		return getById(id, true);
	}
	
	private User getById(int id, boolean independent) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatementBySQL(GET_USER_BY_ID_SQL);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User user = hydrateUser(rs);
				return user;
			}
			return null;
		} finally {
			if (independent) {
				closeAll();
			} else {
				closePS(ps);
			}
		}
	}

	public User getUserByName(String name) throws SQLException, IOException {
		return getUserByName(name, true);
	}
	
	private User getUserByName(String name, boolean independent) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatementBySQL(GET_USER_BY_NAME_SQL);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User user = hydrateUser(rs);
				return user;
			} else {
				return null;
			}
		} finally {
			if (independent) {
				closeAll();
			} else {
				closePS(ps);
			}
		}
	}
	
	public User getUserByEmail(String email) throws SQLException, IOException {
		return getUserByEmail(email, true);
	}

	private User getUserByEmail(String email, boolean independent) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatementBySQL(GET_USER_BY_EMAIL_SQL);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				User user = hydrateUser(rs);
				return user;
			} else {
				return null;
			}
		} finally {
			if (independent) {
				closeAll();
			} else {
				closePS(ps);
			}
		}
	}
	
	private User hydrateUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		user.setEmail(rs.getString("email"));
		user.setIsAdmin(rs.getInt("is_admin"));
		user.setIsActivated(rs.getInt("is_activated"));
		user.setIsNewsManager(rs.getInt("is_news_manager"));
		user.setIsMessageManager(rs.getInt("is_message_manager"));
		user.setHash(rs.getString("hash"));
		return user;
	}

	public int update(User user) throws IllegalInputException, SQLException, IOException {
		try{
			User userPO = buildUserPO(user);
			PreparedStatement ps = null;
			ps = prepareStatementBySQL(UPDATE_SQL);
			ps.setString(1, userPO.getName());
			ps.setString(2, userPO.getPassword());
			ps.setString(3, userPO.getEmail());
			ps.setInt(4, userPO.getIsAdmin());
			ps.setInt(5, userPO.getIsActivated());
			ps.setInt(6, userPO.getIsNewsManager());
			ps.setInt(7, userPO.getIsMessageManager());
			ps.setString(8, userPO.getHash());
			ps.setInt(9, userPO.getId());
			return ps.executeUpdate();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return 0;
	}

	private User getUser(User user) throws SQLException, IOException, IllegalInputException {
		User userPO = null;
		if (user.getId() > 0) {
			userPO = getById(user.getId(), false);
		} else if (user.getName() != null) {
			userPO = getUserByName(user.getName(), false);
		} else if (user.getEmail() != null) {
			userPO = getUserByEmail(user.getEmail(), false);
		} else {
			throw new IllegalInputException("no user identifiers set...");
		}
		return userPO;
	}

	public int save(User user) throws SQLException, IOException {
		PreparedStatement ps = null;
		try{
			ps = prepareStatementBySQL(SAVE_USER_SQL);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setInt(4, user.getIsAdmin());
			ps.setInt(5, user.getIsActivated());
			ps.setInt(6, user.getIsNewsManager());
			ps.setInt(7, user.getIsMessageManager());
			ps.setString(8, user.getHash());
			return ps.executeUpdate();
		} finally {
			closeAll();
		}
	}
	
	public PageBean<User> getPaginatedUserList(int page, int pageSize) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			List<User> users = new ArrayList<User>();
			ps = prepareStatementBySQL(GET_USER_LIST_SQL);
			ps.setInt(1, (page - 1) * pageSize);
			ps.setInt(2, pageSize);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setIsAdmin(rs.getInt("is_admin"));
				user.setIsNewsManager(rs.getInt("is_news_manager"));
				user.setIsMessageManager(rs.getInt("is_message_manager"));
				users.add(user);
			}
			PageBean<User> paginatedUserList = new PageBean<User>();
			paginatedUserList.setPageContent(users);
			paginatedUserList.setTotal(getTotalCount("where is_activated=1", false));
			return paginatedUserList;
		} finally {
			closeAll();
		}
	}

	public void batchUpdate(List<User> users) throws SQLException, IOException, IllegalInputException {
		PreparedStatement ps;
		try{
			ps = prepareStatementBySQL(UPDATE_SQL);
			conn.setAutoCommit(false);
			for (User user : users) {
				User userPO = buildUserPO(user);
				ps.setString(1, userPO.getName());
				ps.setString(2, userPO.getPassword());
				ps.setString(3, userPO.getEmail());
				ps.setInt(4, userPO.getIsAdmin());
				ps.setInt(5, userPO.getIsActivated());
				ps.setInt(6, userPO.getIsNewsManager());
				ps.setInt(7, userPO.getIsMessageManager());
				ps.setString(8, userPO.getHash());
				ps.setInt(9, userPO.getId());
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
	}

	private User buildUserPO(User user) throws SQLException, IOException,
			IllegalInputException, IllegalAccessException {
		User userPO = getUser(user);
		Field[] fields = User.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.get(user) != null) {
				field.set(userPO, field.get(user));
			}
		}
		return userPO;
	}

}
