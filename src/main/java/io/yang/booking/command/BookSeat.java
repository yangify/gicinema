package io.yang.booking.command;

import io.yang.cinema.Cinema;

import java.util.Scanner;

public class BookSeat implements Action {

  private final Cinema cinema;
  private final Scanner scanner;

  public BookSeat(Cinema cinema, Scanner scanner) {
    this.cinema = cinema;
    this.scanner = scanner;
  }

  private boolean isReturnToMainMenu(String response) {
    return response.trim().isEmpty();
  }

  private String prompt() {
    System.out.println();
    System.out.println("Enter number of seats to book, or enter blank to go back to main menu:");
    return scanner.nextLine();
  }

  @Override
  public boolean execute() {
    String response = prompt();
    return isReturnToMainMenu(response);
  }
}
