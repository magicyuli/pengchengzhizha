package com.pengchengzhizha.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.pengchengzhizha.db.ConnectionPool;

public abstract class BaseDAO {
	private static final Logger logger = Logger.getLogger(BaseDAO.class.getName());
	private String GET_TOTAL_COUNT_SQL = "select count(*) as total from ${table_name}";
	private String DELETE_BY_ID_SQL = "delete from ${table_name} where id=?";
	
	protected Set<PreparedStatement> psSet;
	protected Connection conn;
	
	protected BaseDAO(String tableName) {
		psSet = new HashSet<PreparedStatement>(1);
		GET_TOTAL_COUNT_SQL = GET_TOTAL_COUNT_SQL.replace("${table_name}", tableName);
		DELETE_BY_ID_SQL = DELETE_BY_ID_SQL.replace("${table_name}", tableName);
	}
	
	protected PreparedStatement prepareStatementBySQL(String SQL) throws SQLException, IOException {
		if (conn == null || conn.isClosed()) {
			conn = ConnectionPool.getInstance().getConnection();
		}
		PreparedStatement ps = conn.prepareStatement(SQL);
		psSet.add(ps);
		return ps;
	}
	
	protected void closeAll() {
		try {
			for (PreparedStatement ps : psSet) {
				if (ps != null) {
					ps.close();
				}
			}
			psSet.clear();
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.warning("mysql resources might not be approriately closed. " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected void closePS(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			psSet.remove(ps);
		}
	}
	
	public int getTotalCount() throws SQLException, IOException {
		return getTotalCount("");
	}
	
	protected int getTotalCount(boolean independent) throws SQLException, IOException {
		return getTotalCount("", independent);
	}
	
	public int getTotalCount(String whereClause) throws SQLException, IOException {
		return getTotalCount(whereClause, true);
	}
	
	protected int getTotalCount(String whereClause, boolean independent) throws SQLException, IOException {
		PreparedStatement ps = null;
		try {
			ps = prepareStatementBySQL(GET_TOTAL_COUNT_SQL + " " + whereClause);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("total");
			}
			return 0;
		} finally {
			if (independent) {
				closeAll();
			} else {
				closePS(ps);
			}
		}
	}

	public int deleteById(int id) throws SQLException, IOException {
		try {	
			PreparedStatement ps = prepareStatementBySQL(DELETE_BY_ID_SQL);
			ps.setInt(1, id);
			return ps.executeUpdate();
		} finally {
			closeAll();
		}
	}
}
