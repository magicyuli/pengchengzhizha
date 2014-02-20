package com.pengchengzhizha.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {
	private static ConnectionPool instance;
	
	private ComboPooledDataSource dataSource;
	
	public ConnectionPool() throws IOException {
		dataSource = new ComboPooledDataSource();
	}
	
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("cannot connect to the DB...", e);
		}
	}
	
	public static ConnectionPool getInstance() throws IOException {
		if (instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
}
