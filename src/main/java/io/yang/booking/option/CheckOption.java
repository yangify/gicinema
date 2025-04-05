package io.yang.booking.option;

import io.yang.booking.command.CheckBooking;
import io.yang.cinema.Cinema;

public class CheckOption extends Option {

  private final Cinema cinema;

  public CheckOption(Cinema cinema) {
    super("Check bookings", new CheckBooking());
    this.cinema = cinema;
  }
}
