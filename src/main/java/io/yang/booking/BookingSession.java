package io.yang.booking;

import io.yang.booking.command.Action;
import io.yang.booking.option.Option;
import io.yang.booking.display.Menu;

import java.util.Scanner;

/** Represents a booking session that continuously processes user input until it is closed. */
public class BookingSession {

  /** Indicates whether the session is still running. */
  private boolean isOpen;

  /**
   * The menu that presents the user with all available options and actions they can take during the
   * booking session.
   */
  private final Menu menu;

  /** The Scanner instance used to capture user input during the running session. */
  private final Scanner scanner;

  /**
   * Creates a new booking session with the provided options. By default, a newly created session is
   * open.
   *
   * @param options an array of options used to build the session menu
   * @param scanner the Scanner instance to read user input
   */
  public BookingSession(Option[] options, Scanner scanner) {
    this.isOpen = true;
    this.menu = new Menu(options);
    this.scanner = scanner;
  }

  /**
   * Runs the main loop of the booking session. It displays the available options, gathers user
   * input, and executes the corresponding action. The session continues until it is closed through
   * an executed action.
   */
  public void run() {
    while (isOpen) {
      menu.displayOptions();
      int selection = menu.promptForSelection(scanner);
      Action command = menu.getAction(selection);

      command.execute();
      isOpen = !command.isExit();
    }
    menu.displayExitMessage();
  }
}
