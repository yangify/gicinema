package io.yang.booking.option;

import io.yang.cinema.Cinema;
import io.yang.booking.command.BookSeat;

public class BookOption extends Option {

  private final Cinema cinema;

  public BookOption(Cinema cinema) {
    super("Book tickets for %s (%s seats available)", new BookSeat());
    this.cinema = cinema;
  }

  @Override
  public String getMessage() {
    String movieTitle = cinema.getMovieTitle();
    int availableSeats = cinema.getAvailableSeatsCount();
    return String.format(super.getMessage(), movieTitle, availableSeats);
  }
}
