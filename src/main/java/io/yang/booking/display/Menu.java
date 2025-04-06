package io.yang.booking.display;

import io.yang.booking.command.Action;
import io.yang.booking.option.Option;

import java.util.Scanner;

import static io.yang.printer.ConsolePrinter.printWriter;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class Menu {

  private static final String WELCOME_MESSAGE = "Welcome to GIC Cinemas";
  private static final String PROMPT_MESSAGE = "Please enter your selection:";

  private final Option[] options;

  public Menu(Option[] options) {
    this.options = options;
  }

  private boolean isValidSelection(String input) {
    return isNumeric(input)
        && Integer.parseInt(input) > 0
        && Integer.parseInt(input) <= options.length;
  }

  public void displayOptions() {
    printWriter.println(WELCOME_MESSAGE);

    for (int i = 0; i < options.length; i++) {
      printWriter.println("[" + (i + 1) + "] " + options[i].getMessage());
    }

    printWriter.println(PROMPT_MESSAGE);
  }

  public void displayExitMessage() {
    printWriter.println();
    printWriter.println("Thank you for using GIC Cinemas system, Bye!");
  }

  public int promptForSelection(Scanner scanner) {
    String input = scanner.nextLine();
    while (!isValidSelection(input)) {
      printWriter.println("Invalid option. Please try again.");
      input = scanner.nextLine();
    }

    return Integer.parseInt(input) - 1; // Offset to zero-based index
  }

  public Action getAction(int index) {
    return options[index].getAction();
  }
}
