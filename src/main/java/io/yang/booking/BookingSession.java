package io.yang.booking;

import io.yang.booking.command.Action;
import io.yang.booking.option.Option;
import io.yang.booking.display.Menu;

import java.util.Scanner;

public class BookingSession {

  private boolean isOpen;
  private final Menu menu;

  public BookingSession(Option[] options) {
    this.isOpen = true;
    this.menu = new Menu(options);
  }

  public void run(Scanner scanner) {
    while (isOpen) {
      menu.displayOptions();
      int selection = menu.getInput(scanner);
      Action command = menu.getAction(selection);
      isOpen = command.execute();
    }
    menu.displayExitMessage();
  }
}
