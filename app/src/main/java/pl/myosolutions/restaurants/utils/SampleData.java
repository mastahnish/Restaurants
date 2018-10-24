package pl.myosolutions.restaurants.utils;

import java.util.ArrayList;
import java.util.List;

import pl.myosolutions.restaurants.entities.Customer;
import pl.myosolutions.restaurants.entities.Reservation;
import pl.myosolutions.restaurants.entities.Table;

public class SampleData {

    public static List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(0, "Jacek", "Ryś", -1));
        customers.add(new Customer(1, "Irina", "Stogova", -1));
        customers.add(new Customer(2, "Micky", "Mouse", -1));
        customers.add(new Customer(3, "Michael", "Jackson", -1));
        customers.add(new Customer(4, "Nikola", "Tesla", -1));
        return customers;
    }

    public static List<Table> getTables() {
        List<Table> tables = new ArrayList<>();
        tables.add(new Table(0, true));
        tables.add(new Table(1, true));
        tables.add(new Table(2, true));
        tables.add(new Table(3, true));
        tables.add(new Table(4, true));
        tables.add(new Table(5, true));
        tables.add(new Table(6, true));
        tables.add(new Table(7, true));
        tables.add(new Table(8, true));
        tables.add(new Table(9, true));
        return tables;
    }
}
