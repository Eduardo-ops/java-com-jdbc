package examplejdbcproject.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import examplejdbcproject.db.DB;

public class ProgramUpdateData {

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement pst = null;

		try {
			connection = DB.getConnection();

			pst = connection.prepareStatement(
					"UPDATE SELLER " 
					+ "SET BASESALARY = ? " 
					+ "WHERE ID = 13");

			pst.setDouble(1, 4500.0);

			int rowsAffected = pst.executeUpdate();

			System.out.println("Done!\n");
			System.out.println("Rows affected: " + rowsAffected);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(pst);
			DB.closeConnection();
		}
	}

}
