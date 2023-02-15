package application;

import db.DB;
import model.dao.DaoFactory;
import model.entities.Department;
import model.entities.Seller;
import model.dao.SellerDao;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao(); //hides implementation from the main program
        System.out.println("==== TEST  1: seller findByID ====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
    }
}