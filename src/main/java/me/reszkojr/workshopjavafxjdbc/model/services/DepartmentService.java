package me.reszkojr.workshopjavafxjdbc.model.services;

import me.reszkojr.workshopjavafxjdbc.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    public List<Department> findAll() {
        // Mocking datah

        List<Department> list = new ArrayList<>();
        list.add(new Department(1, "Books"));
        list.add(new Department(2, "Foods"));
        list.add(new Department(3, "Phones"));
        list.add(new Department(4, "Computers"));

        return list;
    }
}
