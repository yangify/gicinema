package io.yang.booking.command;

import io.yang.booking.SeatSelector;
import io.yang.booking.SeatSelector.Position;
import io.yang.booking.generator.BookingIdGenerator;
import io.yang.cinema.Cinema;
import io.yang.cinema.CinemaVisualizer;
import io.yang.cinema.Seat;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class BookSeat implements Action {

  private final Cinema cinema;
  private final Scanner scanner;

  public BookSeat(Cinema cinema, Scanner scanner) {
    this.cinema = cinema;
    this.scanner = scanner;
  }

  private Position convertToPosition(String input) {
    if (input == null || input.length() != 3) {
      throw new IllegalArgumentException(
          "Input must be 3 characters (letter + 2 digits, e.g. B03).");
    }

    char rowChar = input.charAt(0);
    if (!Character.isLetter(rowChar)) {
      throw new IllegalArgumentException("First character must be a letter (A-Z).");
    }
    if (!Character.isUpperCase(rowChar)) {
      throw new IllegalArgumentException("First character must be an uppercase letter (A-Z).");
    }

    // inverse input char and row index
    int row = cinema.getSeats().length - 1 - (rowChar - 'A');
    if (row < 0) {
      throw new IllegalArgumentException(
          "Row marker must be before " + (char) ('A' + cinema.getSeats().length - 1));
    }

    String colStr = input.substring(1);
    if (!isNumeric(colStr)) {
      throw new IllegalArgumentException("Last two characters must be positive digits (e.g. 03).");
    }
    int column = Integer.parseInt(colStr) - 1;
    if (column < 0) {
      throw new IllegalArgumentException("Last two characters must not be zero (e.g. 03).");
    }
    if (column >= cinema.getSeats()[row].length) {
      throw new IllegalArgumentException(
          "Column marker must be before " + cinema.getSeats()[row].length);
    }

    return new Position(row, column);
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

  private void displayErrorMessage(String message) {
    System.out.println(message);
  }

  private void displayBookingConfirmation(String bookingId) {
    System.out.printf("Booking id: %s confirmed.%n", bookingId);
    System.out.println();
  }

  private void displayBookingSummary(String bookingId, int numberOfSeats) {
    System.out.println(
        "Successfully reserved " + numberOfSeats + " " + cinema.getMovieTitle() + " tickets.");
    System.out.println("Booking id: " + bookingId);
    System.out.println("Selected seats:");
    System.out.println();

    CinemaVisualizer.visualize(cinema, bookingId);
  }

  private Optional<Position> solicitForAcceptanceOrNewPosition() {
    while (true) {
      System.out.println();
      System.out.println("Enter blank to accept seat selection, or enter new seating position:");
      String input = scanner.nextLine();

      boolean isAcceptance = input.trim().isEmpty();
      if (isAcceptance) return Optional.empty();

      try {
        return Optional.of(convertToPosition(input));

      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private Integer solicitForNumberOfSeats() {
    while (true) {
      try {
        String input = promptForSeats();
        if (isReturnToMainMenu(input)) return null;
        return parse(input);

      } catch (IllegalArgumentException e) {
        displayErrorMessage(e.getMessage());
      }
    }
  }

  private void releaseSeats(String bookingId) {
    Arrays.stream(cinema.getSeats())
        .flatMap(Arrays::stream)
        .filter(seat -> bookingId.equals(seat.getBookingId()))
        .forEach(Seat::release);
  }

  private void bookSeats(String bookingId, int numberOfSeats) {
    Seat[][] seats = cinema.getSeats();
    Seat[] selectedSeats = SeatSelector.selectSeats(numberOfSeats, seats);
    Arrays.stream(selectedSeats).forEach(seat -> seat.reserve(bookingId));
  }

  private void bookSeats(String bookingId, int numberOfSeats, Position position) {
    Seat[][] seats = cinema.getSeats();
    Seat[] selectedSeats = SeatSelector.selectSeats(numberOfSeats, seats, position);
    Arrays.stream(selectedSeats).forEach(seat -> seat.reserve(bookingId));
  }

  private boolean isReturnToMainMenu(String input) {
    return input.trim().isEmpty();
  }

  private String promptForSeats() {
    System.out.println("Enter number of seats to book, or enter blank to go back to main menu:");
    return scanner.nextLine();
  }

  @Override
  public void execute() {
    String bookingId = BookingIdGenerator.nextId();

    Integer numberOfSeats = solicitForNumberOfSeats();
    if (numberOfSeats == null) return;
    bookSeats(bookingId, numberOfSeats);
    displayBookingSummary(bookingId, numberOfSeats);

    Optional<Position> position = solicitForAcceptanceOrNewPosition();
    while (position.isPresent()) {
      releaseSeats(bookingId);
      bookSeats(bookingId, numberOfSeats, position.get());
      displayBookingSummary(bookingId, numberOfSeats);
      position = solicitForAcceptanceOrNewPosition();
    }

    displayBookingConfirmation(bookingId);
  }
}
