package io.yang.booking;

import io.yang.cinema.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatSelector {

  private SeatSelector() {}

  // TODO: solve this bug
  public static Seat[] selectSeats(int numberOfSeats, Seat[][] seats, Position startPosition) {
    List<Seat> selectedSeats = new ArrayList<>();

    int rowNum = startPosition.rowNum;
    int colNum = startPosition.colNum;
    Seat[] row = seats[rowNum];

    while (numberOfSeats > 0 && colNum < row.length) {
      if (row[colNum].isAvailable()) {
        selectedSeats.add(row[colNum]);
        numberOfSeats--;
      }
      colNum++;
    }

    while (numberOfSeats > 0) {
      rowNum++;
      if (rowNum >= seats.length) rowNum = 0;
      List<Seat> availableRowSeats = selectSeats(numberOfSeats, seats[rowNum]);
      availableRowSeats.subList(0, Math.min(numberOfSeats, availableRowSeats.size()));
      selectedSeats.addAll(availableRowSeats);
      numberOfSeats -= availableRowSeats.size();
    }

    return selectedSeats.toArray(new Seat[0]);
  }

  public static List<Seat> selectSeats(int numberOfSeats, Seat[] row) {
    List<Seat> selectedSeats = new ArrayList<>();

    int rightCursor = row.length / 2;
    int leftCursor = Math.max(rightCursor - 1, 0);

    while (numberOfSeats > 0 && (leftCursor >= 0 || rightCursor < row.length)) {
      if (rightCursor < row.length && row[rightCursor].isAvailable()) {
        selectedSeats.add(row[rightCursor]);
        numberOfSeats--;
      }

      if (numberOfSeats > 0 && leftCursor >= 0 && row[leftCursor].isAvailable()) {
        selectedSeats.add(row[leftCursor]);
        numberOfSeats--;
      }

      leftCursor--;
      rightCursor++;
    }

    return selectedSeats;
  }

  public static Seat[] selectSeats(int numberOfSeats, Seat[][] seats) {
    List<Seat> selectedSeats = new ArrayList<>();
    int rowNum = seats.length - 1;

    while (numberOfSeats > 0 && rowNum >= 0) {
      Seat[] row = seats[rowNum];
      List<Seat> availableRowSeats = selectSeats(numberOfSeats, row);

      int endIndex = Math.min(availableRowSeats.size(), numberOfSeats);
      List<Seat> seatsToReserve = availableRowSeats.subList(0, endIndex);
      selectedSeats.addAll(seatsToReserve);

      numberOfSeats -= seatsToReserve.size();
      rowNum--;
    }
    return selectedSeats.toArray(new Seat[0]);
  }

  public static class Position {
    int rowNum;
    int colNum;

    public Position(int rowNum, int colNum) {
      this.rowNum = rowNum;
      this.colNum = colNum;
    }
  }
}
