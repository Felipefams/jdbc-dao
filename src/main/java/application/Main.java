package application;

import model.dao.DaoFactory;
import model.entities.Department;
import model.entities.Seller;
import model.dao.SellerDao;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Department obj = new Department(1, "books");

        Seller seller = new Seller(21, "bob", "bob@gmail.com", new Date(), 3000.0, obj);
        SellerDao selerDao = DaoFactory.createSellerDao(); //hides implementation from the main program
        System.out.println("DEU");
        System.out.println(seller);
    }
}