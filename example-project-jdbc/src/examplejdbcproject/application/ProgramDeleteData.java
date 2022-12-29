package examplejdbcproject.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import examplejdbcproject.db.DB;
import examplejdbcproject.db.DbIntegrityException;

public class ProgramDeleteData {

	public static void main(String[] args) {
		Connection connection = null;
		PreparedStatement pst = null;

		try {
			connection = DB.getConnection();

			pst = connection.prepareStatement(
					"DELETE FROM DEPARTMENT " 
					+ "WHERE ID = ?");

			pst.setInt(1, 2);

			int rowsAffected = pst.executeUpdate();
			
			System.out.println("Done!\n");
			System.out.println("Rows affected: " + rowsAffected);

		} 
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
		}
	}

}
