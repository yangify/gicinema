package io.yang.init;


import java.util.Scanner;

import static io.yang.printer.ConsolePrinter.printWriter;

/**
 * The {@code Initializer} class is a utility class designed to initialize a {@link
 * CinemaConfiguration} object based on user input. This class interacts with the user through the
 * console, prompting for input in a specific format and handling parsing and validation of the
 * input data.
 *
 * <p><b>Usage example:</b>
 *
 * <pre>
 *     Scanner scanner = new Scanner(System.in);
 *     CinemaConfiguration config = Initializer.init(scanner);
 * </pre>
 *
 * <p>This class cannot be instantiated as its constructor is private, and it is intended for static
 * use only.
 *
 * <p>The expected input format is {@code [Title] [Row] [SeatsPerRow]}, where:
 *
 * <ul>
 *   <li><b>Title:</b> A string representing the title of the movie.
 *   <li><b>Row:</b> An integer representing the number of rows in the cinema.
 *   <li><b>SeatsPerRow:</b> An integer representing the number of seats in each row.
 * </ul>
 *
 * <p>If user input is invalid, an error message will be displayed, and the user will be prompted
 * again until valid input is provided.
 */
public class Initializer {

  private static final String PROMPT =
      "Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format:";

  private Initializer() {}

  /**
   * Displays a prompt to the user and reads a single line of input from the console.
   *
   * @return The input string entered by the user
   */
  private static String prompt(Scanner scanner) {
    printWriter.println(PROMPT);
    return scanner.nextLine();
  }

  /**
   * Initializes a {@link CinemaConfiguration} object by continuously prompting the user for input
   * until the input is valid. It parses the input using the {@link
   * CinemaConfigurationParser#parse(String)} method and handles any {@link
   * IllegalArgumentException} thrown during parsing.
   *
   * @return An initialized {@link CinemaConfiguration} object based on valid user input
   */
  public static CinemaConfiguration init(Scanner scanner) {
    CinemaConfiguration cinemaConfiguration = null;
    while (cinemaConfiguration == null) {

      String input = prompt(scanner);
      try {
        cinemaConfiguration = CinemaConfigurationParser.parse(input);

      } catch (IllegalArgumentException e) {
        printWriter.println(e.getMessage());
      }
    }
    return cinemaConfiguration;
  }
}
