package io.yang.init;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InitializerTest {

  @Mock private Scanner scanner;

  @Test
  void testInitWithValidInput() {
    // Given
    String validInput = "MyMovie 5 10"; // Example valid format
    when(scanner.nextLine()).thenReturn(validInput);
    CinemaConfiguration expectedConfig = new CinemaConfiguration("MyMovie", 5, 10);

    try (var mockedParser = mockStatic(CinemaConfigurationParser.class)) {
      mockedParser
          .when(() -> CinemaConfigurationParser.parse(validInput))
          .thenReturn(expectedConfig);

      // When
      CinemaConfiguration result = Initializer.init(scanner);

      // Then
      assertNotNull(result);
      assertEquals(expectedConfig, result);
    }
  }

  @Test
  void testInitWithInvalidThenValidInput() {
    // Given
    String invalidInput = "Invalid Input";
    String validInput = "ValidMovie 3 7"; // Example valid format
    when(scanner.nextLine()).thenReturn(invalidInput, validInput);

    CinemaConfiguration expectedConfig = new CinemaConfiguration("ValidMovie", 3, 7);

    try (var mockedParser = mockStatic(CinemaConfigurationParser.class)) {
      mockedParser
          .when(() -> CinemaConfigurationParser.parse(invalidInput))
          .thenThrow(new IllegalArgumentException("Invalid input format."));
      mockedParser
          .when(() -> CinemaConfigurationParser.parse(validInput))
          .thenReturn(expectedConfig);

      // When
      CinemaConfiguration result = Initializer.init(scanner);

      // Then
      assertNotNull(result);
      assertEquals(expectedConfig, result);
    }
  }

  @Test
  void testInitHandlesMultipleInvalidInputsBeforeValid() {
    // Given
    String invalidInput1 = "Invalid1";
    String invalidInput2 = "Invalid2";
    String validInput = "AnotherMovie 8 12"; // Example valid format
    when(scanner.nextLine()).thenReturn(invalidInput1, invalidInput2, validInput);

    CinemaConfiguration expectedConfig = new CinemaConfiguration("AnotherMovie", 8, 12);

    try (var mockedParser = mockStatic(CinemaConfigurationParser.class)) {
      mockedParser
          .when(() -> CinemaConfigurationParser.parse(invalidInput1))
          .thenThrow(new IllegalArgumentException("Invalid input 1."));
      mockedParser
          .when(() -> CinemaConfigurationParser.parse(invalidInput2))
          .thenThrow(new IllegalArgumentException("Invalid input 2."));
      mockedParser
          .when(() -> CinemaConfigurationParser.parse(validInput))
          .thenReturn(expectedConfig);

      // When
      CinemaConfiguration result = Initializer.init(scanner);

      // Then
      assertNotNull(result);
      assertEquals(expectedConfig, result);
    }
  }
}
