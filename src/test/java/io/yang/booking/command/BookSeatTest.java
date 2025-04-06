package io.yang.booking.command;

import io.yang.cinema.Cinema;
import io.yang.init.CinemaConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class BookSeatTest {

  private Scanner scanner;

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    scanner = mock(Scanner.class);
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
    outContent.reset();
  }

  @Test
  void testExecute_ReturnsToMainMenu() {
    // Given
    Cinema cinema = mock(Cinema.class);
    when(scanner.nextLine()).thenReturn("");

    // When
    new BookSeat(cinema, scanner).execute();

    // Then
    verifyNoMoreInteractions(cinema);
  }

  @Test
  void testExecute_InvalidNumberOfSeats() {
    // Given
    Cinema cinema = new Cinema(new CinemaConfiguration("Movie", 2, 3));
    BookSeat bookSeat = new BookSeat(cinema, scanner);
    when(scanner.nextLine())
        .thenReturn("not-a-number") // will cause isValidNumber to fail
        .thenReturn("10") // exceed capacity
        .thenReturn("") // assume done or back to main
    ;

    // When
    bookSeat.execute();
    String output = outContent.toString();

    // Then
    assertTrue(output.contains("Number of tickets must be an integer greater than 0"));
    assertTrue(output.contains("Sorry, there are only 6 seats available"));
  }

  @Test
  void testExecute_ValidNumberOfSeatsWithConfirmAndBook() {
    // Given
    Cinema cinema = new Cinema(new CinemaConfiguration("Movie", 2, 3));
    BookSeat bookSeat = new BookSeat(cinema, scanner);
    when(scanner.nextLine())
        .thenReturn("2") // number of seats
        .thenReturn("A01") // seat label or something similar
        .thenReturn("") // confirm acceptance
        .thenReturn("") // exit
    ;

    // When
    bookSeat.execute();
    String output = outContent.toString();

    // Then
    assertTrue(output.contains("Successfully reserved 2 Movie tickets."));
    assertTrue(output.matches("(?s).*Booking id: GIC\\w* confirmed.*"));
  }

  @Test
  void testExecute_InvalidPosition() {
    // Given
    Cinema cinema = new Cinema(new CinemaConfiguration("Movie", 2, 3));
    BookSeat bookSeat = new BookSeat(cinema, scanner);
    when(scanner.nextLine())
            .thenReturn("2") // number of seats
            .thenReturn("a") // seat label or something similar
            .thenReturn("") // confirm acceptance
    ;

    // When
    bookSeat.execute();
    String output = outContent.toString();

    // Then
    assertTrue(output.contains("Input must be 3 characters (letter + 2 digits, e.g. B03)."));
  }
}
