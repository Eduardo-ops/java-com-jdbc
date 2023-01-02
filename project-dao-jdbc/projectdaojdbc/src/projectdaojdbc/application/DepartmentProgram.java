package projectdaojdbc.application;

import java.util.List;
import java.util.Scanner;

import projectdaojdbc.model.dao.DaoFactory;
import projectdaojdbc.model.dao.DepartmentDao;
import projectdaojdbc.model.entities.Department;

public class DepartmentProgram {

	public static void main(String[] args) {

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Scanner scn = new Scanner(System.in);

		System.out.println("********* Test1 Insert **********");
		Department departmentTest1 = new Department(null, "TESTE DELETE");
		departmentDao.insert(departmentTest1);

		System.out.println("********* Test2 Update **********");
		Department departmentTest2 = departmentDao.findById(6);
		departmentTest2.setName("COMMERCIAL");
		departmentDao.update(departmentTest2);

		System.out.println("********* Test3 Delete **********");
		departmentDao.deleteById(9);

		System.out.println("********* Test4 findById **********");
		Department departmentTest4 = departmentDao.findById(4);
		System.out.println(departmentTest4);

		System.out.println("********* Tes5 findAll **********");
		List<Department> listDepartmentTest5 = departmentDao.findAll();

		for (Department department : listDepartmentTest5) {
			System.out.println(department);
		}

	}

}
