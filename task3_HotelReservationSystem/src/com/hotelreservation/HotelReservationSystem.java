package com.hotelreservation;

import java.time.LocalDate;
import java.util.*;

public class HotelReservationSystem {
    private static List<Room> rooms = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initializeRooms();

        int choice;
        do {
            System.out.println("\n Hotel Reservation System");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. View All Bookings");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> viewAvailableRooms();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> viewAllBookings();
                case 0 -> System.out.println("Exiting system. Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Standard", true, 2000));
        rooms.add(new Room(102, "Deluxe", true, 3000));
        rooms.add(new Room(103, "Suite", true, 5000));
        rooms.add(new Room(104, "Standard", true, 2000));
        rooms.add(new Room(105, "Deluxe", true, 3000));
    }

    private static void viewAvailableRooms() {
        System.out.println("\n Available Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    private static void bookRoom() {
        sc.nextLine(); // consume newline
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        User user = new User(name, email);

        viewAvailableRooms();
        System.out.print("Enter room number to book: ");
        int roomNumber = sc.nextInt();

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println(" Room not available or doesn't exist.");
            return;
        }

        sc.nextLine(); // consume newline
        System.out.print("Enter check-in date (yyyy-mm-dd): ");
        LocalDate checkIn = LocalDate.parse(sc.nextLine());
        System.out.print("Enter check-out date (yyyy-mm-dd): ");
        LocalDate checkOut = LocalDate.parse(sc.nextLine());

        // Payment simulation
        if (Payment.processPayment(selectedRoom.getPrice())) {
            selectedRoom.setAvailable(false);
            bookings.add(new Booking(user.getName(), selectedRoom.getRoomNumber(), checkIn, checkOut));
            System.out.println("✅ Booking successful for " + user.getName());
        } else {
            System.out.println(" Payment failed. Booking canceled.");
        }
    }

    private static void cancelBooking() {
        sc.nextLine(); // consume newline
        System.out.print("Enter your name to cancel booking: ");
        String name = sc.nextLine();

        Booking toCancel = null;
        for (Booking booking : bookings) {
            if (booking.getUserName().equalsIgnoreCase(name)) {
                toCancel = booking;
                break;
            }
        }

        if (toCancel != null) {
            bookings.remove(toCancel);
            for (Room room : rooms) {
                if (room.getRoomNumber() == toCancel.getRoomNumber()) {
                    room.setAvailable(true);
                    break;
                }
            }
            System.out.println(" Booking canceled.");
        } else {
            System.out.println(" No booking found under this name.");
        }
    }

    private static void viewAllBookings() {
        System.out.println("\n All Bookings:");
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }
}
