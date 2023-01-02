package projectdaojdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectdaojdbc.db.DB;
import projectdaojdbc.db.DbException;
import projectdaojdbc.model.dao.DepartmentDao;
import projectdaojdbc.model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection connection;

	public DepartmentDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement pst = null;
		
		try {
			pst = connection.prepareStatement(
					"INSERT INTO DEPARTMENT "
					+ "(NAME) "
					+ "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, department.getName());
			
			int rowsAffected = pst.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();
				
				if (rs.next()) {
					department.setId(rs.getInt(1));
				}
				
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected.");
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement pst = null;
		
		try {
			pst = connection.prepareStatement(
					"UPDATE DEPARTMENT "
					+ "SET NAME = ? "
					+ "WHERE ID = ?");
			
			pst.setString(1, department.getName());
			pst.setInt(2, department.getId());
			
			pst.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(pst);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement pst = null;
		
		try {
			pst = connection.prepareStatement(
					"DELETE FROM DEPARTMENT "
					+ "WHERE ID = ?");
			
			pst.setInt(1, id);
			
			int rowaAffected = pst.executeUpdate();
			
			if (rowaAffected > 0) {
				System.out.println("Delete executed with success!!!");
			} 
			else {
				System.out.println("The id does not exist.");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(pst);
		}
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = connection.prepareStatement(
					"SELECT * FROM DEPARTMENT "
					+ "WHERE ID = ?");
			
			pst.setInt(1, id);
			
			rs = pst.executeQuery();
			
			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				
				return department;
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
		
		return null;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Department> listDepartment = new ArrayList<Department>();
		
		try {
			pst = connection.prepareStatement(
					"SELECT * FROM TESTE");
			
			rs = pst.executeQuery();
			
			while (rs.next()) {
				Department department = instantiateDepartment(rs);
				listDepartment.add(department);
			}
			
			return listDepartment;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department department = new Department();
		department.setId(rs.getInt("ID"));
		department.setName(rs.getString("NAME"));
		return department;
	}

}
