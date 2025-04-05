package io.yang.booking.command;

public class CheckBooking implements Action {

  @Override
  public boolean execute() {
    System.out.println("Checking bookings functionality executed.");
    // TODO: Add check bookings logic here
    return true;
  }
}
