package io.yang.booking.command;

public class Exit implements Action {

  @Override
  public boolean execute() {
    return false;
  }
}
