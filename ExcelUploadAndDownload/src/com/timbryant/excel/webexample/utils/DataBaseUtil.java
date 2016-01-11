package com.timbryant.excel.webexample.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC数据库操作工具
 * 
 * @author liuxf
 * 
 */
public class DataBaseUtil {
	public static Connection createConn() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/drdc", "root", "passw0rd");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static PreparedStatement prepare(Connection conn, String sql) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

	public static void close(ResultSet resultSet) {
		try {
			resultSet.close();
			resultSet = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
