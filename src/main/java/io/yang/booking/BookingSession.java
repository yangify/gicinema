package io.yang.booking;

import io.yang.booking.command.Action;
import io.yang.booking.option.Option;
import io.yang.booking.display.Menu;

import java.util.Scanner;

/** Represents a booking session that continuously processes user input until it is closed. */
public class BookingSession {

  /** Indicates whether the session is still running. */
  private boolean isOpen;

  /** Menu of available options for the user. */
  private final Menu menu;

  /**
   * Creates a new booking session with the provided options. By default, a newly created session is
   * open.
   *
   * @param options an array of options used to build the session menu
   */
  public BookingSession(Option[] options) {
    this.isOpen = true;
    this.menu = new Menu(options);
  }

  /**
   * Runs the main loop of the booking session. It displays the available options, gathers user
   * input, and executes the corresponding action. The session continues until it is closed through
   * an executed action.
   *
   * @param scanner the Scanner instance to read user input
   */
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
