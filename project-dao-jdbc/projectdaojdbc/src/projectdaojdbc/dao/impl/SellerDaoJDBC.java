package projectdaojdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projectdaojdbc.db.DB;
import projectdaojdbc.db.DbException;
import projectdaojdbc.model.dao.SellerDao;
import projectdaojdbc.model.entities.Department;
import projectdaojdbc.model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection connection;

	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(
					"INSERT INTO SELLER "
					+ "(NAME, EMAIL, BIRTHDATE, BASESALARY, DEPARTMENTID) " 
					+ "VALUES " 
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			pst.setString(1, seller.getName());
			pst.setString(2, seller.getEmail());
			pst.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			pst.setDouble(4, seller.getBaseSalary());
			pst.setInt(5, seller.getDepartment().getId());

			int rowsAffected = pst.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = pst.getGeneratedKeys();

				if (rs.next()) {
					int sellerId = rs.getInt(1);
					seller.setId(sellerId);
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
	public void update(Seller seller) {
		PreparedStatement pst = null;

		try {
			pst = connection.prepareStatement(
					"UPDATE SELLER "
					+ "SET NAME = ?, EMAIL = ?, BIRTHDATE = ?, BASESALARY = ?, DEPARTMENTID = ? " 
					+ "WHERE ID = ?");

			pst.setString(1, seller.getName());
			pst.setString(2, seller.getEmail());
			pst.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			pst.setDouble(4, seller.getBaseSalary());
			pst.setInt(5, seller.getDepartment().getId());
			pst.setInt(6, seller.getId());

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
					"DELETE FROM SELLER " 
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
	public Seller findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(
					"SELECT SELLER.*,DEPARTMENT.NAME AS DEPNAME " 
					+ "FROM SELLER INNER JOIN DEPARTMENT "
					+ "ON SELLER.DEPARTMENTID = DEPARTMENT.ID " 
					+ "WHERE SELLER.ID = ?");

			pst.setInt(1, id);

			rs = pst.executeQuery();

			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, department);
				return seller;
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
	public List<Seller> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(
					"SELECT SELLER.*,DEPARTMENT.NAME AS DEPNAME " 
					+ "FROM SELLER INNER JOIN DEPARTMENT "
					+ "ON SELLER.DEPARTMENTID = DEPARTMENT.ID " 
					+ "ORDER BY NAME");

			rs = pst.executeQuery();

			List<Seller> listSeller = new ArrayList<Seller>();
			Map<Integer, Department> departmentMap = new HashMap<>();

			while (rs.next()) {
				Department department = departmentMap.get(rs.getInt("DEPARTMENTID"));
				
				if (department == null) {
					department = instantiateDepartment(rs);
					departmentMap.put(rs.getInt("DEPARTMENTID"), department);
				}
				
				Seller seller = instantiateSeller(rs, department);
				listSeller.add(seller);
			}

			return listSeller;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		List<Seller> listSeller = new ArrayList<Seller>();
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(
					"SELECT SELLER.*,DEPARTMENT.NAME AS DEPNAME " 
					+ "FROM SELLER INNER JOIN DEPARTMENT "
					+ "ON SELLER.DEPARTMENTID = DEPARTMENT.ID " 
					+ "WHERE DEPARTMENTID = ? " 
					+ "ORDER BY NAME");

			pst.setInt(1, department.getId());

			rs = pst.executeQuery();
			
			if (rs.first()) {
				Department department2 = instantiateDepartment(rs);
				rs.beforeFirst();
				
				while (rs.next()) {
					Seller seller = instantiateSeller(rs, department2);
					listSeller.add(seller);
				}

				return listSeller;
			}
			else {
				System.out.println("N?o foi encontrado nenhum dado com o departamento informado.");
			}
			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
		
		return listSeller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department department = new Department();
		department.setId(rs.getInt("DEPARTMENTID"));
		department.setName(rs.getString("DEPNAME"));
		return department;
	}

	private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("ID"));
		seller.setName(rs.getString("NAME"));
		seller.setEmail(rs.getString("EMAIL"));
		seller.setBirthDate(rs.getDate("BIRTHDATE"));
		seller.setBaseSalary(rs.getDouble("BASESALARY"));
		seller.setDepartment(department);
		return seller;
	}
}
