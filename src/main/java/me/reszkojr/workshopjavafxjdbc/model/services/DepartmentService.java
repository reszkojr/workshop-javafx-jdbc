package me.reszkojr.workshopjavafxjdbc.model.services;

import me.reszkojr.workshopjavafxjdbc.model.dao.DaoFactory;
import me.reszkojr.workshopjavafxjdbc.model.dao.DepartmentDao;
import me.reszkojr.workshopjavafxjdbc.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        return dao.findAll();
    }
}
