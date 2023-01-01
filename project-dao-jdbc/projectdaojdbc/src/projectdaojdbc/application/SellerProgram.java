package projectdaojdbc.application;

import java.util.Date;
import java.util.List;

import projectdaojdbc.model.dao.DaoFactory;
import projectdaojdbc.model.dao.SellerDao;
import projectdaojdbc.model.entities.Department;
import projectdaojdbc.model.entities.Seller;

public class SellerProgram {

	public static void main(String[] args) {

		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("********** TEST 1: Seller findById **********");
		Seller sellerTest1 = sellerDao.findById(3);
		System.out.println("Result of find: \n" + sellerTest1);

		System.out.println("********** TEST 2: Seller findByDepartment **********");
		Department departmentTest2 = new Department(2, null);
		List<Seller> listSellerTest2 = sellerDao.findByDepartment(departmentTest2);

		for (Seller sellerObj : listSellerTest2) {
			System.out.println(sellerObj);
		}

		System.out.println("********** TEST 3: Seller findAll **********");
		List<Seller> listSellerTest3 = sellerDao.findAll();

		for (Seller sellerObj : listSellerTest3) {
			System.out.println(sellerObj);
		}

		System.out.println("********** TEST 4: Seller Insert **********");
		Department departmentTest4 = new Department(1, null);
		Seller sellerTest4 = new Seller(null, "Eduardo Isidoro Gonçalves", "eduardo.goncalves@hotmail.com", new Date(),
				2600.0, departmentTest4);
		sellerDao.insert(sellerTest4);

		System.out.println("********** TEST 5: Seller Update **********");
		Seller sellerTest5 = sellerDao.findById(17);
		sellerTest5.setName("Carlos Henrique Cardoso");
		sellerTest5.setEmail("carlos.cardoso@hotmail.com");
		sellerTest5.setBaseSalary(4200.0);
		sellerDao.update(sellerTest5);
		System.out.println("Update completed!!!");

		System.out.println("********** TEST 6: Seller Delete **********");
		Seller sellerTest6 = sellerDao.findById(19);
		int id = 20;
		sellerDao.deleteById(id);
	}

}
