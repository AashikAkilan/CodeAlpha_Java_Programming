package com.stocktrading;

import java.time.LocalDateTime;

public class Transaction {
    private String type; // "BUY" or "SELL"
    private Stock stock;
    private int quantity;
    private double totalAmount;
    private LocalDateTime dateTime;

    public Transaction(String type, Stock stock, int quantity, double totalAmount) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.dateTime = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
