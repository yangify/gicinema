package io.yang.booking.command;

import io.yang.cinema.Cinema;
import io.yang.cinema.CinemaVisualizer;

import java.util.Scanner;

import static io.yang.printer.ConsolePrinter.printWriter;

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
    printWriter.println("Enter booking id, or enter blank to go back to main menu:");
    return scanner.nextLine();
  }

  private void displayBookingDetails(String bookingId) {
    printWriter.println("Booking id: " + bookingId);
    CinemaVisualizer.visualize(cinema, bookingId);
    printWriter.println();
  }
}
