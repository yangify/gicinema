package io.yang.booking.command;

import io.yang.booking.SeatSelector;
import io.yang.booking.SeatSelector.Position;
import io.yang.booking.generator.BookingIdGenerator;
import io.yang.cinema.Cinema;
import io.yang.cinema.CinemaVisualizer;
import io.yang.cinema.Seat;
import io.yang.cinema.converter.PositionConverter;

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

  private boolean isValidNumber(String input) {
    return isNumeric(input.trim()) && Integer.parseInt(input) > 0;
  }

  private int parse(String input) {
    if (!isValidNumber(input)) {
      String message = "Number of tickets must be an integer greater than 0";
      throw new IllegalArgumentException(message);
    }

    int numberOfSeats = Integer.parseInt(input);
    if (!cinema.hasEnoughSeats(numberOfSeats)) {
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
    System.out.println();
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
        return Optional.of(PositionConverter.convert(input, cinema));

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
    System.out.println();
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
      cinema.releaseSeats(bookingId);
      bookSeats(bookingId, numberOfSeats, position.get());
      displayBookingSummary(bookingId, numberOfSeats);
      position = solicitForAcceptanceOrNewPosition();
    }

    displayBookingConfirmation(bookingId);
  }
}
