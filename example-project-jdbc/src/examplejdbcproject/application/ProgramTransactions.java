package examplejdbcproject.application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import examplejdbcproject.db.DB;
import examplejdbcproject.db.DbException;

public class ProgramTransactions {

	public static void main(String[] args) {
		Connection connection = null;
		Statement st = null;

		try {
			connection = DB.getConnection();

			connection.setAutoCommit(false);

			st = connection.createStatement();

			int rowsAffected1 = st.executeUpdate(
					"UPDATE SELLER " 
					+ "SET BASESALARY = 2090 " 
					+ "WHERE DEPARTMENTID = 1");

			int x = 1;

			if (x < 2) {
				throw new SQLException("Fake error");
			}

			int rowsAffected2 = st.executeUpdate(
					"UPDATE SELLER " 
					+ "SET BASESALARY = 3090 " 
					+ "WHERE DEPARTMENTID = 2");

			connection.commit();

			System.out.println("rows1: " + rowsAffected1);
			System.out.println("rows2: " + rowsAffected2);
		} 
		catch (SQLException e1) {
			try {
				connection.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e1.getMessage());
			} 
			catch (SQLException e2) {
				throw new DbException("Error trying to rollback! Caused by: " + e2.getMessage());
			}
		} 
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}

}
