package io.yang.booking;

import io.yang.cinema.Cinema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingPrompterTest {

  private Cinema mockCinema;
  private Scanner mockScanner;
  private BookingPrompter bookingPrompter;

  @BeforeEach
  void setUp() {
    mockCinema = mock(Cinema.class);
    mockScanner = mock(Scanner.class);
    bookingPrompter = new BookingPrompter(mockScanner, mockCinema);
  }

  @Test
  void testIsValidInput_WithValidNumericInput() {
    assertTrue(bookingPrompter.isValidInput("1"));
    assertTrue(bookingPrompter.isValidInput("2"));
    assertTrue(bookingPrompter.isValidInput("3"));
  }

  @Test
  void testIsValidInput_WithInvalidNonNumericInput() {
    assertFalse(bookingPrompter.isValidInput("abc"));
    assertFalse(bookingPrompter.isValidInput(""));
    assertFalse(bookingPrompter.isValidInput(null));
  }

  @Test
  void testIsValidInput_WithOutOfBoundNumericInput() {
    assertFalse(bookingPrompter.isValidInput("4"));
    assertFalse(bookingPrompter.isValidInput("-1")); // Negative numbers are invalid
  }

  @Test
  void testIsValidOption_WithValidOptions() {
    assertTrue(bookingPrompter.isValidOption(1));
    assertTrue(bookingPrompter.isValidOption(2));
    assertTrue(bookingPrompter.isValidOption(3));
  }

  @Test
  void testIsValidOption_WithInvalidOptions() {
    assertFalse(bookingPrompter.isValidOption(0)); // Below range
    assertFalse(bookingPrompter.isValidOption(4)); // Above range
    assertFalse(bookingPrompter.isValidOption(-1)); // Negative value
  }

  @Test
  void testDisplayOptions() {
    // Given
    when(mockCinema.getMovieTitle()).thenReturn("The Matrix");
    when(mockCinema.getAvailableSeatsCount()).thenReturn(100);

    // When
    bookingPrompter.displayOptions();

    // Then
    verify(mockCinema, times(1)).getMovieTitle();
    verify(mockCinema, times(1)).getAvailableSeatsCount();
  }

  @Test
  void testPrompt_WithValidInput() {
    // Given
    when(mockScanner.nextLine())
        .thenReturn("abc") // Invalid input
        .thenReturn("5") // Out of range
        .thenReturn("3"); // Valid input

    // When
    int result = bookingPrompter.prompt();

    // Then
    assertEquals(3, result); // Final valid result after retries
    verify(mockScanner, times(3)).nextLine();
  }
}
