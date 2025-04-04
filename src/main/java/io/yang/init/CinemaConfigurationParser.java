package io.yang.init;

/**
 * A utility class for parsing cinema configuration inputs into {@link CinemaConfiguration} objects.
 * This class provides methods to validate and parse input data such as movie titles, number of
 * rows, and seats per row in a cinema.
 */
public class CinemaConfigurationParser {

  /** Private constructor to prevent instantiation of this utility class. */
  private CinemaConfigurationParser() {}

  /**
   * Parses and validates the movie title from the given input.
   *
   * @param input The input string containing the movie title.
   * @return A sanitized movie title.
   * @throws IllegalArgumentException if the movie title is null, empty, or contains only
   *     whitespace.
   */
  private static String parseMovieTitle(String input) {
    String movieTitle = input.trim();
    if (movieTitle.isEmpty()) {
      throw new IllegalArgumentException("Movie title cannot be empty.\n");
    }
    return movieTitle;
  }

  /**
   * Parses and validates the number of rows from the given input.
   *
   * @param input The input string containing the number of rows.
   * @return The parsed number of rows as an integer.
   * @throws IllegalArgumentException if the number of rows is less than 1 or greater than 26.
   */
  private static int parseRows(String input) {
    int rows = Integer.parseInt(input.trim());
    if (rows < 1) {
      throw new IllegalArgumentException("Number of rows cannot be less than 1.\n");
    }
    if (rows > 26) {
      throw new IllegalArgumentException("Number of rows cannot exceed 26.\n");
    }
    return rows;
  }

  /**
   * Parses and validates the number of seats per row from the given input.
   *
   * @param input The input string containing the number of seats per row.
   * @return The parsed number of seats per row as an integer.
   * @throws IllegalArgumentException if the number of seats per row is less than 1 or greater than
   *     50.
   */
  private static int parseSeatsPerRow(String input) {
    int seatsPerRow = Integer.parseInt(input.trim());
    if (seatsPerRow < 1) {
      throw new IllegalArgumentException("Number of seats per row cannot be less than 1.\n");
    }
    if (seatsPerRow > 50) {
      throw new IllegalArgumentException("Number of seats per row cannot exceed 50.\n");
    }
    return seatsPerRow;
  }

  /**
   * Extracts and validates the components of the input string. The input must contain exactly three
   * parts: movie title, number of rows, and seats per row.
   *
   * @param input The input string containing the cinema configuration data.
   * @return An array of strings containing the extracted parts.
   * @throws IllegalArgumentException if the input does not contain exactly three parts.
   */
  private static String[] extractParts(String input) {
    String[] parts = input.split(" ");
    if (parts.length != 3) {
      throw new IllegalArgumentException(
          "Input must contain exactly 3 parts: movie title, rows, and seats per row.\n");
    }
    return parts;
  }

  /**
   * Parses the given input string to construct a {@link CinemaConfiguration} object. The input
   * string must be in the format: "movieTitle rows seatsPerRow".
   *
   * @param input The input string containing the configuration data.
   * @return A {@link CinemaConfiguration} object with the parsed data.
   * @throws IllegalArgumentException if the input is null, empty, or invalid.
   */
  public static CinemaConfiguration parse(String input) throws IllegalArgumentException {
    // Check for null or empty input
    if (input == null || input.isEmpty()) {
      throw new IllegalArgumentException("Input cannot be null or empty.\n");
    }

    // Break input into 3 parts
    String[] parts = extractParts(input);

    // Extract movie title and sanitize it
    String movieTitle = parseMovieTitle(parts[0]);
    int rows = parseRows(parts[1]);
    int seatsPerRow = parseSeatsPerRow(parts[2]);

    return new CinemaConfiguration(movieTitle, rows, seatsPerRow);
  }
}
