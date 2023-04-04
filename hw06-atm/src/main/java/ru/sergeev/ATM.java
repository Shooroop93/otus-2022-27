package ru.sergeev;

import java.util.*;

public class ATM {
    private Map<Integer, CashDispenser> dispensers;

    public ATM() {
        dispensers = new HashMap<>();
    }

    public void deposit(Banknote banknote) {
        int denomination = banknote.getValue();
        CashDispenser dispenser = dispensers.getOrDefault(denomination, new CashDispenser(denomination, 0));
        dispenser.setCount(dispenser.getCount() + 1);
        dispenser.setBalance(denomination * dispenser.getCount());
        dispensers.put(denomination, dispenser);
        System.out.println("Деньги успешно внесены в банкомат. Баланс: " + getBalance());
    }

    public Map<Integer, Integer> withdraw(int amount) {
        Map<Integer, Integer> result = new HashMap<>();
        int remainingAmount = amount;
        List<Integer> denominations = new ArrayList<>(dispensers.keySet());
        denominations.sort(Collections.reverseOrder());
        for (int denomination : denominations) {
            int dispensedAmount = 0;
            CashDispenser dispenser = dispensers.get(denomination);
            if (dispenser.getCount() > 0) {
                while (remainingAmount >= denomination && dispenser.getCount() > 0) {
                    remainingAmount -= denomination;
                    dispenser.setCount(dispenser.getCount() - 1);
                    dispenser.setBalance(denomination * dispenser.getCount());
                    dispensedAmount += denomination;
                }
                if (dispensedAmount > 0) {
                    result.put(denomination, dispensedAmount / denomination);
                }
            }
            if (remainingAmount == 0) {
                System.out.println("Деньги успешно выданы из банкомата. Баланс: " + getBalance());
                return result;
            }
        }
        throw new RuntimeException("Невозможно выдать требуемое количество денег");
    }

    public int getBalance() {
        int balance = 0;
        for (CashDispenser dispenser : dispensers.values()) {
            balance += dispenser.getBalance();
        }
        System.out.println("Баланс в банкомате: " + balance);
        return balance;
    }

    public int getRemainingAmount() {
        int remainingAmount = 0;
        for (CashDispenser dispenser : dispensers.values()) {
            remainingAmount += dispenser.getCount() * dispenser.getDenomination();
        }
        System.out.println("Остаток денежных средств в банкомате: " + remainingAmount);
        return remainingAmount;
    }
}