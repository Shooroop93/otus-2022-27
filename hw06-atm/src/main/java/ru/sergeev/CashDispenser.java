package ru.sergeev;

public class CashDispenser {

    private int balance;
    private int denomination;
    private int count;

    public CashDispenser(int denomination, int count) {
        this.denomination = denomination;
        this.count = count;
        this.balance = denomination * count;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}