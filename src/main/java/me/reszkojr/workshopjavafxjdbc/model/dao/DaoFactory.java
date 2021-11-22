package me.reszkojr.workshopjavafxjdbc.model.dao;

import me.reszkojr.workshopjavafxjdbc.db.DB;
import me.reszkojr.workshopjavafxjdbc.model.dao.impl.DepartmentDaoJDBC;
import me.reszkojr.workshopjavafxjdbc.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
