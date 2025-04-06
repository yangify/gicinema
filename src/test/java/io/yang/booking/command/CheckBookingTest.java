package io.yang.booking.command;

import io.yang.cinema.Cinema;
import io.yang.cinema.CinemaVisualizer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class CheckBookingTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private PrintStream originalOut;

  @BeforeEach
  void setUpStreams() {
    originalOut = System.out;
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  void testExecute_WhenBookingIdIsEmpty() {
    Scanner scannerMock = mock(Scanner.class);
    when(scannerMock.nextLine()).thenReturn("");

    Cinema cinemaMock = mock(Cinema.class);

    CheckBooking checkBooking = new CheckBooking(cinemaMock, scannerMock);
    checkBooking.execute();

    // Verify no calls made to visualize (since booking ID is empty)
    // and confirm output does not contain the booking ID line
    assertTrue(outContent.toString().contains("Enter booking id"), "Prompt should be displayed.");
    assertTrue(
        !outContent.toString().contains("Booking id:"),
        "Should not print booking details when ID is empty.");
  }

  @Test
  void testExecute_WhenBookingIdIsNonEmpty() {
    Scanner scannerMock = mock(Scanner.class);
    when(scannerMock.nextLine()).thenReturn("ABC123");

    Cinema cinemaMock = mock(Cinema.class);

    // Use a mocked static call for CinemaVisualizer
    try (MockedStatic<CinemaVisualizer> mockedStatic = Mockito.mockStatic(CinemaVisualizer.class)) {
      CheckBooking checkBooking = new CheckBooking(cinemaMock, scannerMock);
      checkBooking.execute();

      // Verify that CinemaVisualizer.visualize was called with the correct arguments
      mockedStatic.verify(() -> CinemaVisualizer.visualize(cinemaMock, "ABC123"), times(1));
    }

    // Confirm the console output
    String output = outContent.toString();
    assertTrue(output.contains("Enter booking id"), "Prompt should be displayed.");
    assertTrue(
        output.contains("Booking id: ABC123"), "Should print booking details for non-empty ID.");
  }
}
