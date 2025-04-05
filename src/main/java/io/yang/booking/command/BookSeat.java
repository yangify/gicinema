package io.yang.booking.command;

import io.yang.cinema.Cinema;

import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class BookSeat implements Action {

  private final Cinema cinema;
  private final Scanner scanner;

  public BookSeat(Cinema cinema, Scanner scanner) {
    this.cinema = cinema;
    this.scanner = scanner;
  }

  private boolean isValidInput(String input) {
    return isNumeric(input.trim()) && Integer.parseInt(input) > 0;
  }

  private int parse(String input) {
    while (!isValidInput(input)) {
      System.out.println("Number of tickets must be a number greater than 0");
      input = prompt();
    }
    return Integer.parseInt(input);
  }

  private boolean bookSeats(String input) {
    int numberOfSeats = parse(input);
    return bookSeats(numberOfSeats);
  }

  private boolean bookSeats(int numberOfSeats) {
    return true;
  }

  private boolean isReturnToMainMenu(String input) {
    return input.trim().isEmpty();
  }

  private String prompt() {
    System.out.println();
    System.out.println("Enter number of seats to book, or enter blank to go back to main menu:");
    return scanner.nextLine();
  }

  @Override
  public boolean execute() {
    String input = prompt();
    return isReturnToMainMenu(input) || bookSeats(input);
  }
}
