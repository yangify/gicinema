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

  private boolean hasEnoughSeats(int numberOfSeats) {
    return numberOfSeats <= cinema.getAvailableSeatsCount();
  }

  private boolean isValidNumber(String input) {
    return isNumeric(input.trim()) && Integer.parseInt(input) > 0;
  }

  private int parse(String input) {
    if (!isValidNumber(input)) {
      String message = "Number of tickets must be a number greater than 0";
      throw new IllegalArgumentException(message);
    }

    int numberOfSeats = Integer.parseInt(input);
    if (!hasEnoughSeats(numberOfSeats)) {
      int availableSeats = cinema.getAvailableSeatsCount();
      String message = "Sorry, there are only " + availableSeats + " seats available";
      throw new IllegalArgumentException(message);
    }

    return numberOfSeats;
  }

  private int getNumberOfSeats(String input) {
    while (true) {
      try {
        return parse(input);

      } catch (IllegalArgumentException e) {
        input = promptWithErrorMessage(e.getMessage());
      }
    }
  }

  private boolean bookSeats(String input) {
    int numberOfSeats = getNumberOfSeats(input);
    return bookSeats(numberOfSeats);
  }

  private boolean bookSeats(int numberOfSeats) {
    return true;
  }

  private boolean isReturnToMainMenu(String input) {
    return input.trim().isEmpty();
  }

  private String promptWithErrorMessage(String message) {
    System.out.println();
    System.out.println(message);
    return prompt();
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
