package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        System.out.println("==== TEST  1: department findByID ====");
        Department dep = departmentDao.findById(1);
        System.out.println(dep);

        System.out.println("==== TEST  2: seller findAll ====");
        List<Department> list = departmentDao.findAll();
        list.forEach(System.out::println);

        System.out.println("==== TEST  3: seller insert ====");
        Department tmp = new Department(null, "Hope");
        departmentDao.insert(tmp);
        System.out.println("Department inserted! id = " + tmp.getId());

        System.out.println("==== TEST  4: seller update ====");
        tmp = new Department(5, "Mudei kk");
        departmentDao.update(tmp);
        System.out.println("Department updated!");
        System.out.println(tmp);
        System.out.println("==== TEST  5: seller delete ====");


    }
}
