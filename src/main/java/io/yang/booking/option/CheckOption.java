package io.yang.booking.option;

import io.yang.booking.command.CheckBookingCommand;

public class CheckOption extends Option {

  CheckOption() {
    super("Check bookings", new CheckBookingCommand());
  }
}
