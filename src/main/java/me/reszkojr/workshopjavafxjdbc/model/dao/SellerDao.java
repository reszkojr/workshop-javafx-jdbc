package me.reszkojr.workshopjavafxjdbc.model.dao;

import java.util.List;

import me.reszkojr.workshopjavafxjdbc.model.entities.Department;
import me.reszkojr.workshopjavafxjdbc.model.entities.Seller;

public interface SellerDao {

	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);
}
