package io.yang.booking.command;

public interface Action {

  default boolean isExit() {
    return false;
  }

  void execute();
}
