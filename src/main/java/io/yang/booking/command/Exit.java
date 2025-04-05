package io.yang.booking.command;

public class Exit implements Action {

  @Override
  public boolean isExit() {
    return true;
  }

  @Override
  public void execute() {

  }
}
