package com.qwer.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.qwer.contants.Contants;

public class DatabaseUtils {

	private Connection connection;

	public Connection connect() {
		String JDBC_URL = "jdbc:postgresql://" + Contants.DB_HOST + ":" + Contants.DB_PORT + "/" + Contants.DB_NAME;

		try {
			connection = DriverManager.getConnection(JDBC_URL, Contants.DB_USER, Contants.DB_PASSWORD);
			System.out.println("✅ Connect to DB successfully!");
		} catch (SQLException e) {
			System.err.println("❌ Cannot to Connect to DB: " + e.getMessage());
		}
		return connection;
	}

	/**
	 * INSERT/UPDATE/DELETE
	 */
	public int executeQuery(String sql) {
		if (connection == null) {
			System.err.println("❌ Chưa kết nối DB.");
			return -1;
		}

		try (Statement stmt = connection.createStatement()) {
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("❌ Lỗi khi thực thi UPDATE: " + e.getMessage());
			return -1;
		}
	}

	/**
	 * Thực thi câu SELECT và trả về List<Map<String, String>>
	 */
	public List<Map<String, String>> getDataRows(String sql) {
		List<Map<String, String>> rows = new ArrayList<>();

		if (connection == null) {
			System.err.println("❌ Chưa kết nối DB.");
			return rows;
		}

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();

			while (rs.next()) {
				Map<String, String> row = new LinkedHashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = meta.getColumnLabel(i);
					String value = rs.getString(i);
					row.put(columnName, value);
				}
				rows.add(row);
			}

		} catch (SQLException e) {
			System.err.println("❌ Lỗi khi thực thi SELECT: " + e.getMessage());
		}

		return rows;
	}

	public List<Map<String, String>> executeQuery(String sql, Object... params) {
		List<Map<String, String>> rows = new ArrayList<>();
		if (connection == null) {
			System.err.println("❌ Chưa kết nối DB.");
			return rows;
		}
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			// Gán tham số
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			try (ResultSet rs = ps.executeQuery()) {
				ResultSetMetaData meta = rs.getMetaData();
				int columnCount = meta.getColumnCount();

				while (rs.next()) {
					Map<String, String> row = new LinkedHashMap<>();
					for (int i = 1; i <= columnCount; i++) {
						String colName = meta.getColumnLabel(i);
						String value = rs.getString(i);
						row.put(colName, value);
					}
					rows.add(row);
				}
			}

		} catch (SQLException e) {
			System.err.println("❌ Lỗi khi thực thi SELECT có tham số: " + e.getMessage());
		}
		return rows;
	}

	/**
	 * Đóng kết nối DB
	 */
	public void disconnect() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("✅ Đã đóng kết nối DB.");
			}
		} catch (SQLException e) {
			System.err.println("❌ Lỗi khi đóng DB: " + e.getMessage());
		}
	}
}