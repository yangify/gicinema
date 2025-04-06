package io.yang.booking.option;

import io.yang.booking.command.CheckBooking;
import io.yang.cinema.Cinema;

import java.util.Scanner;

public class CheckOption extends Option {

  public CheckOption(Cinema cinema, Scanner scanner) {
    super("Check bookings", new CheckBooking(cinema, scanner));
  }
}
