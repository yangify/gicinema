package io.yang.booking.option;

import io.yang.booking.command.Command;

public abstract class Option {

  private final String message;
  private final Command command;

  Option(String message, Command command) {
    this.message = message;
    this.command = command;
  }

  public String getMessage() {
    return message;
  }

  public Command getCommand() {
    return command;
  }
}
