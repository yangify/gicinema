package io.yang.booking.command;

import io.yang.booking.BookingSession;

public class CloseSessionCommand implements Command {

  private final BookingSession session;

  public CloseSessionCommand(BookingSession session) {
    this.session = session;
  }

  @Override
  public void execute() {
    session.close();
  }
}
