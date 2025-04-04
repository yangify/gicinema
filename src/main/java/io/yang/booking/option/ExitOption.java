package io.yang.booking.option;

import io.yang.booking.BookingSession;
import io.yang.booking.command.CloseSessionCommand;

public class ExitOption extends Option {

  ExitOption(BookingSession session) {
    super("Exit", new CloseSessionCommand(session));
  }
}
