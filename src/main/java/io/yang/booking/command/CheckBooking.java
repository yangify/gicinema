package io.yang.booking.command;

import io.yang.cinema.Cinema;
import io.yang.cinema.CinemaVisualizer;

import java.util.Scanner;

public class CheckBooking implements Action {

  private final Cinema cinema;
  private final Scanner scanner;

  public CheckBooking(Cinema cinema, Scanner scanner) {
    this.cinema = cinema;
    this.scanner = scanner;
  }

  @Override
  public void execute() {
    String bookingId = solicitForBookingId();
    if (bookingId.isEmpty()) return;
    displayBookingDetails(bookingId);
  }

  private String solicitForBookingId() {
    System.out.println("Enter booking id, or enter blank to go back to main menu:");
    return scanner.nextLine();
  }

  private void displayBookingDetails(String bookingId) {
    System.out.println("Booking id: " + bookingId);
    CinemaVisualizer.visualize(cinema, bookingId);
    System.out.println();
  }
}
