package ru.sergeev;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.deposit(new Banknote(10));
        atm.deposit(new Banknote(50));
        atm.deposit(new Banknote(100));
        atm.deposit(new Banknote(500));
        atm.deposit(new Banknote(1000));
        atm.deposit(new Banknote(1000));
        atm.deposit(new Banknote(2000));
        atm.deposit(new Banknote(5000));
        atm.withdraw(7560);
        atm.getRemainingAmount();
        atm.withdraw(2100);
        atm.getRemainingAmount();
        atm.withdraw(10);
    }
}