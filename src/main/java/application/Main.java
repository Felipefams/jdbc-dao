package application;

import db.DB;
import model.dao.DaoFactory;
import model.entities.Department;
import model.entities.Seller;
import model.dao.SellerDao;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao(); //hides implementation from the main program

        System.out.println("==== TEST  1: seller findByID ====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("==== TEST  2: seller findByDepartment ====");
        Department dep = new Department(2,null);
        List<Seller> list = sellerDao.findByDepartment(dep);
        for(var x : list) System.out.println(x);

        System.out.println("==== TEST  3: seller findAll ====");
        list = sellerDao.findAll();
        list.forEach(System.out::println);

        System.out.println("==== TEST  4: seller insert ====");
        Seller ns = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, dep);
        sellerDao.insert(ns);
        System.out.println("inserted! new id = " + ns.getId());

        System.out.println("==== TEST  5: seller insert ====");
        seller = sellerDao.findById(1);
        System.out.println("Old seller:");
        System.out.println(seller);
        seller.setName("Martha Waine");
        sellerDao.update(seller);
        System.out.println("Update completed! ");
        System.out.println(seller);

    }
}