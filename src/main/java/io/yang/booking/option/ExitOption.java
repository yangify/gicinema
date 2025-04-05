package io.yang.booking.option;

import io.yang.booking.command.Exit;

public class ExitOption extends Option {

  public ExitOption() {
    super("Exit", new Exit());
  }
}
