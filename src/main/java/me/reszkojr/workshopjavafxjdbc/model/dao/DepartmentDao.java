package me.reszkojr.workshopjavafxjdbc.model.dao;

import java.util.List;

import me.reszkojr.workshopjavafxjdbc.model.entities.Department;

public interface DepartmentDao {

	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
}
