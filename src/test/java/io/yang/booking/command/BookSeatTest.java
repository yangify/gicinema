package io.yang.booking.command;

import io.yang.cinema.Cinema;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class BookSeatTest {

  @Test
  void testExecuteReturnsBackToMainMenuWhenBlankInput() {
    // Given - Simulate blank input
    Scanner scanner = new Scanner("\n");
    BookSeat bookSeat = new BookSeat(mock(Cinema.class), scanner);

    // When
    boolean result = bookSeat.execute();

    // Then
    assertTrue(result, "Expected execute() to return true when the user input is blank");
  }
}
