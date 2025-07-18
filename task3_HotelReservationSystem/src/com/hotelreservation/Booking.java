package com.hotelreservation;

import java.time.LocalDate;

public class Booking {
    private String userName;
    private int roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Booking(String userName, int roomNumber, LocalDate checkInDate, LocalDate checkOutDate) {
        this.userName = userName;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String getUserName() { return userName; }
    public int getRoomNumber() { return roomNumber; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }

    @Override
    public String toString() {
        return "Booking: " + userName + " | Room #" + roomNumber + 
               " | " + checkInDate + " to " + checkOutDate;
    }
}
