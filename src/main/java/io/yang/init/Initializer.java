package io.yang.init;

import java.util.Scanner;

public class Initializer {

  // Public constant for the prompt
  private static final String PROMPT =
      "Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format:";

  private Initializer() {}

  private static String prompt(Scanner scanner) {
    System.out.println(PROMPT);
    return scanner.nextLine();
  }

  public static CinemaConfiguration init(Scanner scanner) {
    CinemaConfiguration cinemaConfiguration = null;
    while (cinemaConfiguration == null) {
      String input = prompt(scanner);
      try {
        cinemaConfiguration = CinemaConfigurationParser.parse(input);
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    return cinemaConfiguration;
  }
}
