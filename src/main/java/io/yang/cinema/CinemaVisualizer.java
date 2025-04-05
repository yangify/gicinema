package io.yang.cinema;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CinemaVisualizer {

  private static final int ROW_MARKER_WIDTH = 2;
  private static final int COL_MARKER_WIDTH = 3;
  private static final String SCREEN_LABEL = "S C R E E N";
  private static final String DIVIDER = "-";
  private static final String AVAILABLE_SYMBOL = ".";
  private static final String RESERVED_BY_SELF_SYMBOL = "o";
  private static final String RESERVED_BY_OTHER_SYMBOL = "#";

  private CinemaVisualizer() {}

  private static String padRight(String text, int width) {
    if (text == null) text = "";
    int padding = width - text.length();
    return text + " ".repeat(padding);
  }

  private static String padRight(char c, int width) {
    return padRight(String.valueOf(c), width);
  }

  private static String buildColumnMarkerLine(Cinema cinema) {
    int columns = cinema.getSeats()[0].length;
    StringBuilder sb = new StringBuilder(" ".repeat(ROW_MARKER_WIDTH));
    for (int col = 0; col < columns; col++) {
      sb.append(padRight(String.valueOf(col + 1), COL_MARKER_WIDTH));
    }
    return sb.toString();
  }

  private static String buildSeatLine(Seat[] seats, String bookingId) {
    return Arrays.stream(seats)
        .map(
            seat -> {
              if (seat.isAvailable()) {
                return padRight(AVAILABLE_SYMBOL, COL_MARKER_WIDTH);
              } else if (seat.getBookingId().equals(bookingId)) {
                return padRight(RESERVED_BY_SELF_SYMBOL, COL_MARKER_WIDTH);
              } else {
                return padRight(RESERVED_BY_OTHER_SYMBOL, COL_MARKER_WIDTH);
              }
            })
        .collect(Collectors.joining());
  }

  private static String buildScreenLine(int width) {
    int totalPadding = width - SCREEN_LABEL.length();
    if (totalPadding <= 0) {
      return SCREEN_LABEL;
    }
    int halfPadding = totalPadding / 2;
    String spaces = " ".repeat(halfPadding);
    // If totalPadding is odd, add an extra space after the initial chunk
    return spaces + SCREEN_LABEL + spaces + ((totalPadding % 2 == 1) ? " " : "");
  }

  /** Calculates the total width needed to display the visualization, including row markers. */
  private static int calculateTotalWidth(Cinema cinema) {
    return ROW_MARKER_WIDTH + cinema.getSeats()[0].length * COL_MARKER_WIDTH;
  }

  /**
   * Visualizes the seating layout using the given booking ID to highlight which seats are reserved
   * by the current user.
   */
  public static void visualize(Cinema cinema, String bookingId) {
    int width = calculateTotalWidth(cinema);
    System.out.println(buildScreenLine(width));
    System.out.println(DIVIDER.repeat(width));

    Seat[][] allSeats = cinema.getSeats();
    int totalRows = allSeats.length;
    for (int row = 0; row < totalRows; row++) {
      char rowChar = (char) ('A' + totalRows - row - 1);
      String line = padRight(rowChar, ROW_MARKER_WIDTH) + buildSeatLine(allSeats[row], bookingId);
      System.out.println(line);
    }
    System.out.println(buildColumnMarkerLine(cinema));
  }

  /** Visualizes the seating layout without any highlighted booking. */
  public static void visualize(Cinema cinema) {
    visualize(cinema, null);
  }
}
