package io.yang.booking;

import io.yang.booking.option.Option;
import io.yang.booking.option.OptionCatalogue;
import io.yang.cinema.Cinema;

import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * The {@code BookingPrompter} class is a user interaction handler for the cinema booking system.
 *
 * <p>It is responsible for displaying menu options, processing user input, and validating choices.
 * The menu dynamically updates based on available options provided by the {@link OptionCatalogue}.
 *
 * <p>Main responsibilities include:
 *
 * <ul>
 *   <li>Presenting booking, booking management, and exit choices.
 *   <li>Validating user input for correctness.
 *   <li>Coordinating with {@link Cinema} and {@link OptionCatalogue} for dynamic menu generation.
 * </ul>
 *
 * <p>This class ensures a smooth workflow for users, from reserving cinema tickets to managing
 * existing bookings or exiting the system.
 *
 * <p>Example usage:
 *
 * <pre>
 * Scanner scanner = new Scanner(System.in);
 * Cinema cinema = new Cinema();
 * OptionCatalogue catalogue = new OptionCatalogue(cinema);
 * BookingPrompter prompter = new BookingPrompter(scanner, catalogue);
 * int selectedOption = prompter.prompt();
 * </pre>
 *
 * @see Option
 * @see OptionCatalogue
 * @see Cinema
 */
public class BookingPrompter {

  private static final String WELCOME_MESSAGE = "Welcome to GIC Cinemas";
  private static final String PROMPT_MESSAGE = "Please enter your selection:";

  private final Scanner scanner;
  private final OptionCatalogue optionCatalogue;

  /**
   * Constructs a new {@code BookingPrompter} to manage user interactions for the cinema booking
   * system.
   *
   * @param scanner the {@link Scanner} used to read user input
   * @param optionCatalogue the {@link OptionCatalogue} providing dynamic menu options
   * @see Cinema
   */
  public BookingPrompter(Scanner scanner, OptionCatalogue optionCatalogue) {
    this.scanner = scanner;
    this.optionCatalogue = optionCatalogue;
  }

  /**
   * Validates whether the user's input is valid.
   *
   * <p>This method ensures that:
   *
   * <ul>
   *   <li>The input is numeric.
   *   <li>The input matches a valid menu option.
   * </ul>
   *
   * @param input the raw input entered by the user
   * @return {@code true} if the input is numeric and corresponds to a valid option; {@code false}
   *     otherwise
   */
  private boolean isValidInput(String input) {
    return isNumeric(input)
        && Integer.parseInt(input) > 0
        && Integer.parseInt(input) <= optionCatalogue.getOptions().length;
  }

  /**
   * Displays the main menu of options to the user.
   *
   * <p>The menu is dynamically populated based on available options provided by the {@link
   * OptionCatalogue}. Each option includes:
   *
   * <ul>
   *   <li>A booking option with the movie title and available seats.
   *   <li>An option to check current bookings.
   *   <li>An option to exit the system.
   * </ul>
   *
   * <p>Example output:
   *
   * <pre>
   * Welcome to GIC Cinemas
   * [1] Book tickets for Movie Title (25 seats available)
   * [2] Check bookings
   * [3] Exit
   * Please enter your selection:
   * </pre>
   */
  private void displayOptions() {
    System.out.println();
    System.out.println(WELCOME_MESSAGE);

    Option[] options = optionCatalogue.getOptions();
    for (int i = 0; i < options.length; i++) {
      System.out.println("[" + (i + 1) + "] " + options[i].getMessage());
    }

    System.out.println(PROMPT_MESSAGE);
  }

  /**
   * Displays a farewell message to the user.
   *
   * <p>This method is called to thank users for utilizing the system when they choose to exit. It
   * provides a clear end to the session.
   *
   * <p>Example:
   *
   * <pre>
   * Thank you for using GIC Cinemas system, Bye!
   * </pre>
   */
  public void displayExitMessage() {
    System.out.println();
    System.out.println("Thank you for using GIC Cinemas system, Bye!");
  }

  /**
   * Prompts the user to select a menu option.
   *
   * <p>This method repeatedly asks the user for input until a valid numeric option is entered. When
   * an invalid input is provided, the user is presented with an error message and the menu options
   * again. This ensures that only valid selections are processed.
   *
   * @return the validated menu option selected by the user as an integer
   */
  public int prompt() {
    displayOptions();
    String input = scanner.nextLine();

    while (!isValidInput(input)) {
      System.out.println("Invalid option. Please try again.");
      displayOptions();
      input = scanner.nextLine();
    }

    return Integer.parseInt(input) - 1; // Offset to zero-based index
  }
}
