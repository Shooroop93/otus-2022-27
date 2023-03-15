package ru.sergeev.homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private final Deque<Customer> linkedList = new ArrayDeque<>();

    public void add(Customer customer) {
        linkedList.push(customer);
    }

    public Customer take() {
        return linkedList.pop();
    }
}