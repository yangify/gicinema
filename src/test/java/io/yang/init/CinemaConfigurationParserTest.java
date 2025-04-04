package io.yang.init;

import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CinemaConfigurationParserTest {

  @Test
  void testParseWithValidInput() {
    // Given
    String input = "Inception 15 25";

    // When
    CinemaConfiguration result = CinemaConfigurationParser.parse(input);

    // Then
    assertNotNull(result);
    assertEquals("Inception", result.getMovieTitle());
    assertEquals(15, result.getRows());
    assertEquals(25, result.getSeatsPerRow());
  }

  @ParameterizedTest
  @MethodSource("invalidInputProvider")
  void testParseWithInvalidInputs(String input, String expectedMessage) {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> CinemaConfigurationParser.parse(input));

    assertEquals(expectedMessage, exception.getMessage());
  }

  static Stream<Arguments> invalidInputProvider() {
    return Stream.of(
        Arguments.of(Named.of("Null input", null), "Input cannot be null or empty.\n"),
        Arguments.of(Named.of("Empty input", ""), "Input cannot be null or empty.\n"),
        Arguments.of(
            Named.of("Input with less than three parts", "Only TwoParts"),
            "Input must contain exactly 3 parts: movie title, rows, and seats per row.\n"),
        Arguments.of(Named.of("Blank movie title", " 15 25"), "Movie title cannot be empty.\n"),
        Arguments.of(
            Named.of("Rows less than 1", "Inception 0 25"),
            "Number of rows cannot be less than 1.\n"),
        Arguments.of(
            Named.of("Rows exceeding 26", "Inception 27 25"), "Number of rows cannot exceed 26.\n"),
        Arguments.of(
            Named.of("Seats per row less than 1", "Inception 15 0"),
            "Number of seats per row cannot be less than 1.\n"),
        Arguments.of(
            Named.of("Seats per row exceeding 50", "Inception 15 51"),
            "Number of seats per row cannot exceed 50.\n"));
  }
}
