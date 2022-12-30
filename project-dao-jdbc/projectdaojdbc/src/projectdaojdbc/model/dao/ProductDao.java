package projectdaojdbc.model.dao;

import java.util.List;

import projectdaojdbc.model.entities.Seller;

public interface ProductDao {

	void insert(Seller seller);

	void update(Seller seller);

	void deleteById(Integer id);

	Seller findById(Integer id);

	List<Seller> findAll();
}
