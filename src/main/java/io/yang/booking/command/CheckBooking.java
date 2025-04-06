package io.yang.booking.command;

import static io.yang.printer.ConsolePrinter.printWriter;

public class CheckBooking implements Action {

  @Override
  public void execute() {
    printWriter.println("Checking bookings functionality executed.");
    // TODO: Add check bookings logic here
  }
}
