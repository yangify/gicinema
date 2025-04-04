package io.yang.init;

public class CinemaConfigurationParser {

  private CinemaConfigurationParser() {}

  private static String parseMovieTitle(String input) {
    String movieTitle = input.trim();
    if (movieTitle.isEmpty()) {
      throw new IllegalArgumentException("Movie title cannot be empty.\n");
    }
    return movieTitle;
  }

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

  private static String[] extractParts(String input) {
    String[] parts = input.split(" ");
    if (parts.length != 3) {
      throw new IllegalArgumentException(
          "Input must contain exactly 3 parts: movie title, rows, and seats per row.\n");
    }
    return parts;
  }

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
