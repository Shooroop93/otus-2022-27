package ru.sergeev.homework;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> customerStringTreeMap;

    public CustomerService() {
        customerStringTreeMap = new TreeMap<>(Comparator.comparing(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> customerStringEntry = customerStringTreeMap.firstEntry();
        return new AbstractMap.SimpleEntry<>(new Customer(customerStringEntry.getKey()), customerStringEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> customerStringEntry = customerStringTreeMap.higherEntry(customer);
        if (customerStringEntry == null) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(new Customer(customerStringEntry.getKey()), customerStringEntry.getValue());
    }

    public void add(Customer customer, String data) {
        customerStringTreeMap.put(customer, data);
    }
}