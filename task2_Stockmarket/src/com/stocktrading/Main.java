package com.stocktrading;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static HashMap<String, Stock> market = new HashMap<>();
    private static User user;

    public static void main(String[] args) {
        initMarket();
        user = new User("Aashik", 10000.0);

        System.out.println("Welcome to Stock Trading Platform, " + user.getName());
        int choice;

        do {
            System.out.println("\n====== Main Menu ======");
            System.out.println("1. View Market Stocks");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = getIntInput();

            switch (choice) {
                case 1:
                    showMarket();
                    break;
                case 2:
                    buyStock();
                    break;
                case 3:
                    sellStock();
                    break;
                case 4:
                    viewPortfolio();
                    break;
                case 5:
                    viewTransactions();
                    break;
                case 6:
                    System.out.println(" Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }

    private static void initMarket() {
        market.put("AAPL", new Stock("AAPL", "Apple Inc.", 150.0));
        market.put("TSLA", new Stock("TSLA", "Tesla Inc.", 220.0));
        market.put("GOOGL", new Stock("GOOGL", "Alphabet Inc.", 2750.0));
        market.put("AMZN", new Stock("AMZN", "Amazon Inc.", 3400.0));
        market.put("INFY", new Stock("INFY", "Infosys", 1350.0));
    }

    private static void showMarket() {
        System.out.println("\n Available Stocks:");
        System.out.printf("%-10s %-20s %-10s\n", "Symbol", "Company", "Price");
        for (Stock stock : market.values()) {
            System.out.printf("%-10s %-20s ₹%-10.2f\n", stock.getSymbol(), stock.getName(), stock.getPrice());
        }
    }

    private static void buyStock() {
        showMarket();
        System.out.print("\nEnter stock symbol to buy: ");
        String symbol = scanner.next().toUpperCase();
        Stock stock = market.get(symbol);
        if (stock == null) {
            System.out.println(" Stock not found.");
            return;
        }

        System.out.print("Enter quantity to buy: ");
        int qty = getIntInput();
        double total = qty * stock.getPrice();

        if (user.deductBalance(total)) {
            user.addStock(symbol, qty);
            user.addTransaction(new Transaction("BUY", stock, qty, total));
            System.out.println("Bought " + qty + " shares of " + symbol);
        } else {
            System.out.println(" Insufficient balance.");
        }
    }

    private static void sellStock() {
        if (user.getPortfolio().isEmpty()) {
            System.out.println(" You don’t own any stocks.");
            return;
        }

        viewPortfolio();
        System.out.print("\nEnter stock symbol to sell: ");
        String symbol = scanner.next().toUpperCase();
        Stock stock = market.get(symbol);
        if (stock == null) {
            System.out.println(" Stock not found.");
            return;
        }

        System.out.print("Enter quantity to sell: ");
        int qty = getIntInput();

        if (user.removeStock(symbol, qty)) {
            double total = qty * stock.getPrice();
            user.addBalance(total);
            user.addTransaction(new Transaction("SELL", stock, qty, total));
            System.out.println(" Sold " + qty + " shares of " + symbol);
        } else {
            System.out.println(" Not enough shares to sell.");
        }
    }

    private static void viewPortfolio() {
        System.out.println("\n Your Portfolio:");
        System.out.println("Balance: ₹" + user.getBalance());

        if (user.getPortfolio().isEmpty()) {
            System.out.println("You don't own any stocks.");
            return;
        }

        System.out.printf("%-10s %-10s\n", "Symbol", "Quantity");
        for (Map.Entry<String, Integer> entry : user.getPortfolio().entrySet()) {
            System.out.printf("%-10s %-10d\n", entry.getKey(), entry.getValue());
        }
    }

    private static void viewTransactions() {
        ArrayList<Transaction> txs = user.getTransactions();
        if (txs.isEmpty()) {
            System.out.println("\n No transactions found.");
            return;
        }

        System.out.println("\n Transaction History:");
        for (Transaction tx : txs) {
            System.out.printf("[%s] %s %d shares of %s for ₹%.2f at %s\n",
                    tx.getType(), tx.getType(), tx.getQuantity(),
                    tx.getStock().getSymbol(), tx.getTotalAmount(),
                    tx.getDateTime().toString());
        }
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
