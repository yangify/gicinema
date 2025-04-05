package io.yang.booking.option;

import io.yang.booking.command.Action;

public abstract class Option {

  private final String message;
  private final Action action;

  Option(String message, Action action) {
    this.message = message;
    this.action = action;
  }

  public String getMessage() {
    return message;
  }

  public Action getAction() {
    return action;
  }
}
