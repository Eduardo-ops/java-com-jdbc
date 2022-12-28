package examplejdbcproject.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import examplejdbcproject.db.DB;

/**
 * Main class of the program
 */
public class ProgramInsertData {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection connection = null;
		PreparedStatement pst = null;

		try {
			connection = DB.getConnection();

			pst = connection.prepareStatement(
					"INSERT INTO seller "
					+ "(NAME, EMAIL, BIRTHDATE, BASESALARY, DEPARTMENTID)" 
					+ "VALUES " + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, "Carol Pompeo");
			pst.setString(2, "carol.pompeo@hotmail.com");
			pst.setDate(3, new java.sql.Date(sdf.parse("22/04/1995").getTime()));
			pst.setDouble(4, 2200.0);
			pst.setInt(5, 4);

			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();

				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done!!!\n");
					System.out.println("Rows affected: " + rowsAffected);
					System.out.println("Id: " + id);
				}
			} 
			else {
				System.out.println("No rown affected!");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		catch (ParseException e) {
			e.printStackTrace();
		} 
		finally {
			DB.closeStatement(pst);
			DB.closeConnection();
		}
	}

}
