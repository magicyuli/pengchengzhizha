package com.pengchengzhizha.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pengchengzhizha.db.ConnectionPool;

public class BaseDAO {
	protected PreparedStatement ps;
	protected ResultSet rs;
	protected Connection conn;
	
	protected void prepareStatementBySQL(String SQL) throws SQLException, IOException {
		if (conn == null || conn.isClosed()) {
			conn = ConnectionPool.getInstance().getConnection();
		}
		if (ps != null) {
			ps.close();
		}
		ps =  conn.prepareStatement(SQL);
	}
	
	protected void closeAll() throws SQLException {
		if (ps != null) {
			ps.close();
		}
		if (conn != null) {
			conn.close();
		}
	}
}
