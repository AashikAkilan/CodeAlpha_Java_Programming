package com.hotelreservation;

public class Payment {
    public static boolean processPayment(double amount) {
        System.out.println("Processing payment of ₹" + amount + "...");
        // simulate success
        System.out.println("Payment successful!");
        return true;
    }
}
