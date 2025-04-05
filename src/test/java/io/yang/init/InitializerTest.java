package io.yang.init;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class InitializerTest {

  private static Scanner scanner;
  private static MockedStatic<CinemaConfigurationParser> parser;

  @BeforeAll
  static void setUp() {
    scanner = mock(Scanner.class);
    parser = mockStatic(CinemaConfigurationParser.class);
  }

  @AfterAll
  static void tearDown() {
    parser.close();
  }

  @Test
  void testInitWithValidInput() {
    // Given
    CinemaConfiguration expected = mock(CinemaConfiguration.class);
    when(scanner.nextLine()).thenReturn("1");
    parser.when(() -> CinemaConfigurationParser.parse("1")).thenReturn(expected);

    // When
    CinemaConfiguration actual = Initializer.init(scanner);

    // Then
    parser.verify(() -> CinemaConfigurationParser.parse("1"));
    assertNotNull(actual);
  }

  @Test
  void testPromptTillValidInput() {
    // Given
    CinemaConfiguration expected = mock(CinemaConfiguration.class);
    when(scanner.nextLine()).thenReturn("somthingwong").thenReturn("2");
    parser
        .when(() -> CinemaConfigurationParser.parse("somthingwong"))
        .thenThrow(IllegalArgumentException.class);
    parser.when(() -> CinemaConfigurationParser.parse("2")).thenReturn(expected);

    // When
    CinemaConfiguration config = Initializer.init(scanner);

    // Then
    parser.verify(() -> CinemaConfigurationParser.parse("somthingwong"));
    parser.verify(() -> CinemaConfigurationParser.parse("2"));
    assertNotNull(config);
  }
}
