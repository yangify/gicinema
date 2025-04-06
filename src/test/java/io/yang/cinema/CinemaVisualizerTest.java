package io.yang.cinema;

import io.yang.init.CinemaConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CinemaVisualizerTest {

  private PrintStream originalOut;
  private ByteArrayOutputStream outContent;

  @BeforeEach
  void setUpStreams() {
    originalOut = System.out;
    outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  void restoreStreams() {
    System.setOut(originalOut);
  }

  static Stream<Arguments> centeredScreenProvider() {
    return Stream.of(
        Arguments.of(5, "   S C R E E N   "), Arguments.of(10, "          S C R E E N           "));
  }

  @ParameterizedTest
  @MethodSource("centeredScreenProvider")
  void testVisualizeCenteredScreen(int col, String expectedScreen) {
    // Given
    Cinema cinema = new Cinema(new CinemaConfiguration("Halo", 1, col));

    // When
    CinemaVisualizer.visualize(cinema);
    String[] output = outContent.toString().split("\n");

    // Then
    assertEquals(expectedScreen, output[0], "Should print the screen label");
  }

  @Test
  void testVisualizeDivider() {
    // Given
    Cinema cinema = new Cinema(new CinemaConfiguration("Halo", 1, 2));

    // When
    CinemaVisualizer.visualize(cinema);
    String[] output = outContent.toString().split("\n");

    // Then
    assertEquals("--------", output[1], "Should print the divider");
  }

  @Test
  void testVisualizeColumnMarkers() {
    // Given
    Cinema cinema = new Cinema(new CinemaConfiguration("Halo", 1, 2));

    // When
    CinemaVisualizer.visualize(cinema);
    String[] output = outContent.toString().split("\n");

    // Then
    assertEquals("  1  2  ", output[3], "Should print the divider");
  }

  @Test
  void testVisualizeRowMarkers() {
    // Given
    Cinema cinema = new Cinema(new CinemaConfiguration("Halo", 3, 2));

    // When
    CinemaVisualizer.visualize(cinema);
    String[] output = outContent.toString().split("\n");

    // Then
    assertEquals("C .  .  ", output[2], "Should print the divider");
    assertEquals("B .  .  ", output[3], "Should print the divider");
    assertEquals("A .  .  ", output[4], "Should print the divider");
  }

  @Test
  void testVisualizeSingleRow_NoBookingId() {
    // Given
    Cinema cinema = new Cinema(new CinemaConfiguration("Halo", 1, 2));
    cinema.getSeats()[0][1].reserve("OTHER");

    // When
    CinemaVisualizer.visualize(cinema);
    String[] output = outContent.toString().split("\n");

    // Then
    assertEquals("A .  #  ", output[2], "Expected output to show '.' then '#' in single row");
  }

  @Test
  void testVisualizeMultipleRows_BookingId() {
    // Given
    Cinema cinema = new Cinema(new CinemaConfiguration("Halo", 2, 3));
    cinema.getSeats()[0][0].reserve("MYBOOKING");
    cinema.getSeats()[0][1].reserve("OTHERBOOKING");
    cinema.getSeats()[1][2].reserve("MYBOOKING");

    // When
    CinemaVisualizer.visualize(cinema, "MYBOOKING");
    String[] output = outContent.toString().split("\n");

    // Then
    assertEquals("B o  #  .  ", output[2], "Expected row B to include 'o', '#', '.' in that order");
    assertEquals(
        "A .  .  o  ", output[3], "Expected row A to include two '.' and one 'o' at the end");
  }
}
