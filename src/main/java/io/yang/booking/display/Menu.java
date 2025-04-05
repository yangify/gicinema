package io.yang.booking.display;

import io.yang.booking.command.Action;
import io.yang.booking.option.Option;

import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class Menu {

  private static final String WELCOME_MESSAGE = "Welcome to GIC Cinemas";
  private static final String PROMPT_MESSAGE = "Please enter your selection:";

  private final Option[] options;

  public Menu(Option[] options) {
    this.options = options;
  }

  private boolean isValidInput(String input) {
    return isNumeric(input)
        && Integer.parseInt(input) > 0
        && Integer.parseInt(input) <= options.length;
  }

  public void displayOptions() {
    System.out.println();
    System.out.println(WELCOME_MESSAGE);

    for (int i = 0; i < options.length; i++) {
      System.out.println("[" + (i + 1) + "] " + options[i].getMessage());
    }

    System.out.println(PROMPT_MESSAGE);
  }

  public void displayExitMessage() {
    System.out.println();
    System.out.println("Thank you for using GIC Cinemas system, Bye!");
  }

  public int getInput(Scanner scanner) {
    String input = scanner.nextLine();
    while (!isValidInput(input)) {
      System.out.println("Invalid option. Please try again.");
      input = scanner.nextLine();
    }

    return Integer.parseInt(input) - 1; // Offset to zero-based index
  }

  public Action getAction(int index) {
    return options[index].getAction();
  }
}
