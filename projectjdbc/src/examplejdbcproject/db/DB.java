package examplejdbcproject.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Statement;
import java.sql.ResultSet;

public class DB {

	private static Connection connection = null;

	public static Connection getConnection() {
		if (connection == null) {
			try {
				Properties properties = LoadProperties();
				String urlDb = properties.getProperty("dburl");
				connection = DriverManager.getConnection(urlDb, properties);
			} 
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return connection;
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} 
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} 
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} 
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	private static Properties LoadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties properties = new Properties();
			properties.load(fs);

			return properties;
		} 
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
}
