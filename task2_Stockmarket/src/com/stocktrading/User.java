package com.stocktrading;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String name;
    private double balance;
    private HashMap<String, Integer> portfolio; // symbol → quantity
    private ArrayList<Transaction> transactions;

    public User(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
        this.portfolio = new HashMap<>();
        this.transactions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public HashMap<String, Integer> getPortfolio() {
        return portfolio;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void addBalance(double amount) {
        balance += amount;
    }

    public boolean deductBalance(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    public void addStock(String symbol, int qty) {
        portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + qty);
    }

    public boolean removeStock(String symbol, int qty) {
        int owned = portfolio.getOrDefault(symbol, 0);
        if (qty > owned) return false;
        if (qty == owned) portfolio.remove(symbol);
        else portfolio.put(symbol, owned - qty);
        return true;
    }

    public void addTransaction(Transaction tx) {
        transactions.add(tx);
    }
}
