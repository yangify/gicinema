package io.yang.booking;

import io.yang.cinema.Cinema;

import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * The {@code BookingPrompter} class is responsible for handling user interactions
 * in a cinema booking system. It displays menu options to users, accepts their input,
 * and validates menu selections.
 *
 * <p>The menu options include:
 * <ul>
 *   <li>Booking tickets for a specific movie</li>
 *   <li>Checking existing bookings</li>
 *   <li>Exiting the system</li>
 * </ul>
 *
 * <p>Instances of this class utilize {@link Scanner} for reading user input and rely
 * on a {@link Cinema} instance to fetch information about the movie title and
 * number of available seats.
 */
public class BookingPrompter {

  private static final String WELCOME_MESSAGE = "\nWelcome to GIC Cinemas";
  private static final String BOOK_OPTION_FORMAT = "[1] Book tickets for %s (%s seats available)";
  private static final String CHECK_BOOKINGS_OPTION = "[2] Check bookings";
  private static final String EXIT_OPTION = "[3] Exit";
  private static final String PROMPT_MESSAGE = "Please enter your selection:";

  private final Scanner scanner;
  private final Cinema cinema;

  /**
   * Constructs a new {@code BookingPrompter} instance that handles user interaction
   * for the cinema booking system.
   *
   * @param scanner the {@link Scanner} instance used to read user input
   * @param cinema  the {@link Cinema} instance that provides details about the
   *                movie and available seats
   */
  public BookingPrompter(Scanner scanner, Cinema cinema) {
    this.scanner = scanner;
    this.cinema = cinema;
  }

  /**
   * Validates whether the given input string is numeric and corresponds to a valid
   * menu option (1, 2, or 3).
   *
   * @param input the input string entered by the user
   * @return {@code true} if the input is numeric and represents a valid option,
   *         {@code false} otherwise
   */
  public boolean isValidInput(String input) {
    return isNumeric(input) && isValidOption(Integer.parseInt(input));
  }

  /**
   * Checks if the specified numeric menu option is within the valid range of
   * options (1 to 3).
   *
   * @param option the menu option entered by the user
   * @return {@code true} if the option is between 1 and 3 (inclusive),
   *         {@code false} otherwise
   */
  public boolean isValidOption(int option) {
    return option >= 1 && option <= 3;
  }

  /**
   * Displays the main menu of booking options to the user. The menu includes:
   * <ul>
   *   <li>An option to book tickets, displaying the movie title and the number
   *       of seats available (retrieved from the {@link Cinema} instance)</li>
   *   <li>An option to check existing bookings</li>
   *   <li>An option to exit the booking system</li>
   * </ul>
   *
   * Example:
   * <pre>
   * Welcome to GIC Cinemas
   * [1] Book tickets for Movie Title (25 seats available)
   * [2] Check bookings
   * [3] Exit
   * Please enter your selection:
   * </pre>
   */
  public void displayOptions() {
    System.out.println(WELCOME_MESSAGE);
    System.out.printf(BOOK_OPTION_FORMAT, cinema.getMovieTitle(), cinema.getAvailableSeatsCount());
    System.out.println();
    System.out.println(CHECK_BOOKINGS_OPTION);
    System.out.println(EXIT_OPTION);
    System.out.println(PROMPT_MESSAGE);
  }

  /**
   * Prompts the user to select a menu option. The method continuously asks for
   * input until a valid option (1, 2, or 3) is entered. If the input is invalid,
   * the user will see an error message followed by the menu options again.
   *
   * @return the validated menu option selected by the user as an integer (1, 2, or 3)
   */
  public int prompt() {
    displayOptions();
    String input = scanner.nextLine();

    while (!isValidInput(input)) {
      System.out.println("Invalid option. Please try again.");
      displayOptions();
      input = scanner.nextLine();
    }

    return Integer.parseInt(input);
  }

  /**
   * Displays a farewell message to the user.
   *
   * <p>This method is called when the user exits the booking system, thanking them for using the
   * service and signaling the end of their session.
   */
  public void displayExitMessage() {
    System.out.println();
    System.out.println("Thank you for using GIC Cinemas system, Bye!");
  }
}