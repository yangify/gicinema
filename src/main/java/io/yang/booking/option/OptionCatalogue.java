package io.yang.booking.option;

import io.yang.booking.BookingSession;
import io.yang.cinema.Cinema;

public class OptionCatalogue {

  private final Option[] options;

  public OptionCatalogue(BookingSession session) {
    Cinema cinema = session.getCinema();
    options = new Option[] {new BookOption(cinema), new CheckOption(), new ExitOption(session)};
  }

  public Option[] getOptions() {
    return options;
  }
}
