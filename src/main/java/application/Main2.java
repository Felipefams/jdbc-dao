package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        System.out.println("==== TEST  1: department findByID ====");
        Department dep = departmentDao.findById(1);
        System.out.println(dep);

        System.out.println("==== TEST  2: seller findByDepartment ====");


        System.out.println("==== TEST  3: seller findAll ====");


        System.out.println("==== TEST  4: seller insert ====");


        System.out.println("==== TEST  5: seller insert ====");



        System.out.println("==== TEST  6: seller delete ====");


    }
}
