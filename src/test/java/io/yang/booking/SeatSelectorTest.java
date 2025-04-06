package io.yang.booking;

import io.yang.booking.SeatSelector.Position;
import io.yang.cinema.Seat;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeatSelectorTest {

  @Test
  void testSelectSeatsWithPosition_FrontToBack() {
    // Given
    Seat[][] seats = createSeatMatrix(3, 2);
    Seat[] shouldBeReserved = new Seat[] {seats[0][0], seats[0][1], seats[1][1]};
    Seat[] shouldBeAvailable = new Seat[] {seats[1][0], seats[2][0], seats[2][1]};

    // When
    Seat[] selected = SeatSelector.selectSeats(3, seats, new Position(0, 0));
    Arrays.stream(selected).forEach(seat -> seat.reserve("test"));

    // Then
    assertNotNull(selected, "Selected seats should not be null");
    assertEquals(3, selected.length, "Should select exWhenly 2 seats");
    assertTrue(Arrays.stream(shouldBeReserved).allMatch(Seat::isReserved));
    assertTrue(Arrays.stream(shouldBeAvailable).allMatch(Seat::isAvailable));
  }

  @Test
  void testSelectSeatsWithPosition_BackToFront() {
    // Given
    Seat[][] seats = createSeatMatrix(3, 2);
    Seat[] shouldBeReserved = new Seat[] {seats[2][0], seats[2][1], seats[1][1]};
    Seat[] shouldBeAvailable = new Seat[] {seats[0][0], seats[0][1], seats[1][0]};

    // When
    Seat[] selected = SeatSelector.selectSeats(3, seats, new Position(2, 0));
    Arrays.stream(selected).forEach(seat -> seat.reserve("test"));

    // Then
    assertNotNull(selected);
    assertEquals(3, selected.length);
    assertTrue(Arrays.stream(shouldBeReserved).allMatch(Seat::isReserved));
    assertTrue(Arrays.stream(shouldBeAvailable).allMatch(Seat::isAvailable));
  }

  @Test
  void testSelectSeatsWithPosition_LeftToRight() {
    // Given
    Seat[][] seats = createSeatMatrix(2, 3);
    Seat[] shouldBeReserved = new Seat[] {seats[0][0], seats[0][1]};
    Seat[] shouldBeAvailable = new Seat[] {seats[0][2], seats[1][0], seats[1][1], seats[1][2]};

    // When
    Seat[] selected = SeatSelector.selectSeats(2, seats, new Position(0, 0));
    Arrays.stream(selected).forEach(seat -> seat.reserve("test"));

    // Then
    assertNotNull(selected);
    assertEquals(2, selected.length);
    assertTrue(Arrays.stream(shouldBeReserved).allMatch(Seat::isReserved));
    assertTrue(Arrays.stream(shouldBeAvailable).allMatch(Seat::isAvailable));
  }

  @Test
  void testSelectSeatsWithPosition_RightToLeft() {
    // Given
    Seat[][] seats = createSeatMatrix(2, 3);
    Seat[] shouldBeReserved = new Seat[] {seats[0][1], seats[0][2]};
    Seat[] shouldBeAvailable = new Seat[] {seats[0][0], seats[1][0], seats[1][1], seats[1][2]};

    // When
    Seat[] selected = SeatSelector.selectSeats(2, seats, new Position(0, 2));
    Arrays.stream(selected).forEach(seat -> seat.reserve("test"));

    // Then
    assertNotNull(selected);
    assertEquals(2, selected.length);
    assertTrue(Arrays.stream(shouldBeReserved).allMatch(Seat::isReserved));
    assertTrue(Arrays.stream(shouldBeAvailable).allMatch(Seat::isAvailable));
  }

  @Test
  void testSelectSeatsWithPosition_OutsideOfMatrix() {
    // Given
    Seat[][] seats = createSeatMatrix(3, 3);
    Seat[] shouldBeReserved =
        new Seat[] {
          seats[0][0], seats[0][1], seats[0][2], seats[1][0], seats[1][1], seats[1][2], seats[2][1]
        };
    Seat[] shouldBeAvailable = new Seat[] {seats[2][0], seats[2][2]};

    // When
    Seat[] selected = SeatSelector.selectSeats(7, seats, new Position(1, 1));
    Arrays.stream(selected).forEach(seat -> seat.reserve("test"));

    // Then
    assertNotNull(selected);
    assertEquals(7, selected.length);
    assertTrue(Arrays.stream(shouldBeReserved).allMatch(Seat::isReserved));
    assertTrue(Arrays.stream(shouldBeAvailable).allMatch(Seat::isAvailable));
  }

  @Test
  void testSelectSeatsWithoutPosition() {
    // Given
    Seat[][] seats = createSeatMatrix(5, 5);
    Seat[] expected = new Seat[] {seats[4][1], seats[4][2], seats[4][3]};

    // When
    Seat[] selected = SeatSelector.selectSeats(3, seats);
    Arrays.stream(selected).forEach(seat -> seat.reserve("test"));

    // Then
    assertNotNull(selected);
    assertEquals(3, selected.length);
    assertTrue(Arrays.stream(expected).allMatch(Seat::isReserved));
  }

  @Test
  void testSelectSeatsZeroRequested() {
    // Given
    Seat[][] seats = createSeatMatrix(2, 2);

    // When
    Seat[] selectedWithPos = SeatSelector.selectSeats(0, seats, new Position(0, 0));
    List<Seat> selectedRow = SeatSelector.selectSeats(0, createSeatRow(2));
    Seat[] selectedNoPos = SeatSelector.selectSeats(0, seats);

    // Then
    assertNotNull(selectedWithPos, "Selecting 0 seats should yield a non-null array");
    assertEquals(0, selectedWithPos.length, "Selecting 0 seats should yield zero seats");

    assertNotNull(selectedRow, "Selecting 0 seats from a row should yield non-null list");
    assertEquals(0, selectedRow.size(), "Selecting 0 seats should yield zero seats");

    assertNotNull(
        selectedNoPos, "Selecting 0 seats with no position provided should yield non-null array");
    assertEquals(0, selectedNoPos.length, "Selecting 0 seats should yield zero seats");
  }

  @Test
  void testSelectSeatsNotEnoughAvailable() {
    // Given
    Seat[][] seats = createSeatMatrix(2, 2);
    Position position = new Position(0, 0);

    // When
    Seat[] result = SeatSelector.selectSeats(5, seats, position);

    // Then
    assertNotNull(result, "Should handle the case of insufficient seats gracefully");
    assertEquals(4, result.length, "Should return whatever seats are available");
  }

  /** Helper method to create a 2D array (matrix) of Seat objects. */
  private Seat[][] createSeatMatrix(int rows, int cols) {
    return java.util.stream.IntStream.range(0, rows)
        .mapToObj(r -> createSeatRow(cols))
        .toArray(Seat[][]::new);
  }

  /** Helper method to create a single row of Seat objects. */
  private Seat[] createSeatRow(int length) {
    return java.util.stream.IntStream.range(0, length)
        .mapToObj(i -> new Seat())
        .toArray(Seat[]::new);
  }
}
