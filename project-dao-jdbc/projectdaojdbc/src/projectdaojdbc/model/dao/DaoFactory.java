package projectdaojdbc.model.dao;

import projectdaojdbc.dao.impl.SellerDaoJDBC;
import projectdaojdbc.db.DB;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
}
