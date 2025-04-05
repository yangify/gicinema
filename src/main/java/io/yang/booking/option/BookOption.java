package io.yang.booking.option;

import io.yang.cinema.Cinema;
import io.yang.booking.command.BookSeat;

import java.util.Scanner;

public class BookOption extends Option {

  private final Cinema cinema;

  public BookOption(Cinema cinema, Scanner scanner) {
    super("Book tickets for %s (%s seats available)", new BookSeat(cinema, scanner));
    this.cinema = cinema;
  }

  @Override
  public String getMessage() {
    String movieTitle = cinema.getMovieTitle();
    int availableSeats = cinema.getAvailableSeatsCount();
    return String.format(super.getMessage(), movieTitle, availableSeats);
  }
}
