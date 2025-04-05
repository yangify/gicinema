package io.yang.booking.command;

public class BookSeat implements Action {

  @Override
  public boolean execute() {
    System.out.println("Booking functionality executed.");
    // TODO: Add booking logic here
    return true;
  }
}
