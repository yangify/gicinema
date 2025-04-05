package io.yang.booking.command;

import io.yang.cinema.Cinema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookSeatTest {

  private Scanner scanner;
  private Cinema cinema;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    scanner = mock(Scanner.class);
    cinema = mock(Cinema.class);
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
    outContent.reset();
  }

  @Test
  void testExecuteReturnsBackToMainMenuWhenBlankInput() {
    // Given
    when(scanner.nextLine()).thenReturn("\n");
    BookSeat bookSeat = new BookSeat(cinema, scanner);

    // When
    boolean result = bookSeat.execute();

    // Then
    assertTrue(result, "Expected execute() to return true when the user input is blank");
  }

  static Stream<Arguments> invalidInputProvider() {
    return Stream.of(
        Arguments.of(
            Named.of("Text input", "text"), "Number of tickets must be a number greater than 0"),
        Arguments.of(
            Named.of("Negative number", "-1"), "Number of tickets must be a number greater than 0"),
        Arguments.of(Named.of("Zero", "0"), "Number of tickets must be a number greater than 0"),
        Arguments.of(
            Named.of("Not enough seats", "101"), "Sorry, there are only 10 seats available"));
  }

  @ParameterizedTest
  @MethodSource("invalidInputProvider")
  void testKeepsPromptingUntilValidResponse(String invalidInput, String expectedMessage) {
    // Given
    when(scanner.nextLine()).thenReturn(invalidInput).thenReturn("1");
    when(cinema.getAvailableSeatsCount()).thenReturn(10);
    BookSeat bookSeat = new BookSeat(cinema, scanner);

    // When
    bookSeat.execute();
    String output = outContent.toString();

    // Then
    assertTrue(output.contains(expectedMessage));
  }
}
