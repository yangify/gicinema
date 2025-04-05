package io.yang.booking;

import io.yang.cinema.Seat;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeatSelectorTest {

  @Test
  void testSelectSeat() {
    // Given
    Seat[][] seats =
        range(0, 3)
            .mapToObj(i -> range(0, 3).mapToObj(j -> new Seat()).toArray(Seat[]::new))
            .toArray(Seat[][]::new);

    // When
    Seat[] seat = SeatSelector.selectSeats(1, seats);

    // Then
    assertEquals(1, seat.length, "Should return an array with 1 seat");
  }

  @Test
  void testSelectFurthestRowMidColSeat() {
    // Given
    Seat[][] seats =
        range(0, 3)
            .mapToObj(i -> range(0, 3).mapToObj(j -> new Seat()).toArray(Seat[]::new))
            .toArray(Seat[][]::new);

    // When
    Seat[] seat = SeatSelector.selectSeats(1, seats);
    seat[0].reserve("BOOKED");

    // Then
    assertTrue(seats[2][1].isReserved(), "Should reserve the last row seat");
    assertTrue(
        seats[2][0].isAvailable() && seats[2][1].isReserved(),
        "Should only reserve the mid col seat");
  }

  @Test
  void testSelectAllFromFurthestRowSeatFirst() {
    // Given
    Seat[][] seats =
        range(0, 4)
            .mapToObj(i -> range(0, 4).mapToObj(j -> new Seat()).toArray(Seat[]::new))
            .toArray(Seat[][]::new);
    seats[3][2].reserve("ALREADY_BOOKED");
    Seat[] reserveExpected = {seats[3][0], seats[3][1], seats[3][3]};

    // When
    Seat[] seat = SeatSelector.selectSeats(3, seats);
    Arrays.stream(seat).forEach(s -> s.reserve("NEWLY_BOOKED"));

    // Then
    assertEquals(3, seat.length, "Should only return 3 seats");
    assertTrue(
        Arrays.stream(reserveExpected).allMatch(Seat::isReserved),
        "Should only reserve the last row seat");
    assertTrue(
        Arrays.stream(reserveExpected).allMatch(s -> s.getBookingId().equals("NEWLY_BOOKED")),
        "Should only reserve the last row seat");
  }

  @Test
  void testSelectFromNextClosestRowWhenPreviousRowSeatIsFullyReserved() {
    // Given
    Seat[][] seats =
        range(0, 4)
            .mapToObj(i -> range(0, 4).mapToObj(j -> new Seat()).toArray(Seat[]::new))
            .toArray(Seat[][]::new);
    seats[3][2].reserve("ALREADY_BOOKED");
    Seat[] reserveExpected = {seats[3][0], seats[3][1], seats[3][3], seats[2][2]};

    // When
    Seat[] seat = SeatSelector.selectSeats(4, seats);
    Arrays.stream(seat).forEach(s -> s.reserve("NEWLY_BOOKED"));

    // Then
    assertEquals(4, seat.length, "Should only return 4 seats");
    assertTrue(Arrays.stream(reserveExpected).allMatch(Seat::isReserved));
    assertTrue(
        Arrays.stream(reserveExpected).allMatch(s -> s.getBookingId().equals("NEWLY_BOOKED")));
  }

  @Test
  void testSelectFromNextClosestRowWhenPreviousRowSeatIsFullyReservedOddSizeCinema() {
    // Given
    Seat[][] seats =
        range(0, 3)
            .mapToObj(i -> range(0, 3).mapToObj(j -> new Seat()).toArray(Seat[]::new))
            .toArray(Seat[][]::new);
    seats[2][1].reserve("ALREADY_BOOKED");
    Seat[] reserveExpected = {seats[2][0], seats[2][2], seats[1][1]};

    // When
    Seat[] seat = SeatSelector.selectSeats(3, seats);
    Arrays.stream(seat).forEach(s -> s.reserve("NEWLY_BOOKED"));

    // Then
    assertEquals(3, seat.length, "Should only return 3 seats");
    assertTrue(Arrays.stream(reserveExpected).allMatch(Seat::isReserved));
    assertTrue(
        Arrays.stream(reserveExpected).allMatch(s -> s.getBookingId().equals("NEWLY_BOOKED")));
  }

  @Test
  void testSelectFurthestRowMidColSeatForEvenSizeCinema() {
    // Given
    Seat[][] seats =
        range(0, 4)
            .mapToObj(i -> range(0, 4).mapToObj(j -> new Seat()).toArray(Seat[]::new))
            .toArray(Seat[][]::new);
    Seat reserveExpected = seats[3][2];
    Seat[] availableExpected =
        new Seat[] {seats[0][1], seats[0][2], seats[1][1], seats[1][2], seats[3][0], seats[3][1]};

    // When
    Seat[] seat = SeatSelector.selectSeats(1, seats);
    seat[0].reserve("BOOKED");

    // Then
    assertTrue(reserveExpected.isReserved(), "Should only reserve the last row seat");
    assertTrue(
        Arrays.stream(availableExpected).allMatch(Seat::isAvailable),
        "Should only reserve the last row seat");
  }

  @Test
  void testSelectNearestMiddleSeat() {
    // Given
    Seat[][] seats =
        range(0, 4)
            .mapToObj(i -> range(0, 4).mapToObj(j -> new Seat()).toArray(Seat[]::new))
            .toArray(Seat[][]::new);
    seats[3][1].reserve("ALREADY_BOOKED");
    Seat reserveExpected = seats[3][2];

    // When
    Seat[] seat = SeatSelector.selectSeats(1, seats);
    seat[0].reserve("BOOKED");

    // Then
    assertTrue(reserveExpected.isReserved(), "Should only reserve the last row seat");
    assertEquals("BOOKED", reserveExpected.getBookingId(), "Should only reserve the last row seat");
  }

  @Test
  void testSelectUpToMaxAvailableSeat() {
    // Given
    Seat[][] seats =
        range(0, 4)
            .mapToObj(i -> range(0, 4).mapToObj(j -> new Seat()).toArray(Seat[]::new))
            .toArray(Seat[][]::new);
    seats[3][1].reserve("ALREADY_BOOKED");

    // When
    Seat[] seat = SeatSelector.selectSeats(16, seats);

    // Then
    assertEquals(15, seat.length, "Should only return 15 seats, 1 was booked");
  }
}
