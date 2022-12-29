package projectdaojdbc.application;

import java.util.Date;

import projectdaojdbc.model.entities.Department;
import projectdaojdbc.model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Department department = new Department(1, "Books");

		Seller seller = new Seller(21, "Bruno", "bruno.silva@hotmail.com", new Date(), 3000.0, department);

		System.out.println(department);
		System.out.println(seller);
	}

}
