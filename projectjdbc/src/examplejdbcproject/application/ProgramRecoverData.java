package examplejdbcproject.application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import examplejdbcproject.db.DB;
import examplejdbcproject.db.DbException;

/**
 * Main class of the program
 */
public class ProgramRecoverData {

	public static void main(String[] args) {

		Connection connection = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			connection = DB.getConnection();

			st = connection.createStatement();

			rs = st.executeQuery("SELECT * FROM SELLER");

			while (rs.next()) {
				System.out.println(rs.getInt("ID") + ", " + rs.getString("NAME"));
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}

}
