package io.yang.cinema.converter;

import io.yang.booking.SeatSelector.Position;
import io.yang.cinema.Cinema;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class PositionConverter {

  private PositionConverter() {}

  public static Position convert(String input, Cinema cinema) {
    if (input == null || input.length() != 3) {
      throw new IllegalArgumentException(
          "Input must be 3 characters (letter + 2 digits, e.g. B03).");
    }

    char rowChar = input.charAt(0);
    if (!Character.isLetter(rowChar)) {
      throw new IllegalArgumentException("First character must be a letter (A-Z).");
    }
    if (!Character.isUpperCase(rowChar)) {
      throw new IllegalArgumentException("First character must be an uppercase letter (A-Z).");
    }

    // inverse input char and row index
    int row = cinema.getSeats().length - 1 - (rowChar - 'A');
    if (row < 0) {
      throw new IllegalArgumentException(
          "Row marker must be before " + (char) ('A' + cinema.getSeats().length - 1));
    }

    String colStr = input.substring(1);
    if (!isNumeric(colStr)) {
      throw new IllegalArgumentException(
          "Last two characters must be positive integers (e.g. 03).");
    }
    int column = Integer.parseInt(colStr) - 1;
    if (column < 0) {
      throw new IllegalArgumentException("Last two characters must not be zero (e.g. 03).");
    }
    if (column >= cinema.getSeats()[row].length) {
      throw new IllegalArgumentException(
          "Column marker must not exceed " + cinema.getSeats()[row].length);
    }

    return new Position(row, column);
  }
}
