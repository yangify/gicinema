package io.yang.booking;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.yang.booking.option.ExitOption;
import io.yang.booking.option.Option;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookingSessionTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
    outContent.reset();
  }

  @Test
  void testRunStopsWhenCommandReturnsFalse() {
    // Given
    Option[] options = {new ExitOption()};
    Scanner scanner = new Scanner("1\n");

    // When
    new BookingSession(options, scanner).run();
    String output = outContent.toString();

    // If the session loop ends without issues, the test passes
    assertTrue(
        output.contains("Thank you for using GIC Cinemas system, Bye!"),
        "Should display exit message");
  }
}
