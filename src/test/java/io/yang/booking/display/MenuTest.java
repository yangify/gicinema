package io.yang.booking.display;

import io.yang.booking.option.Option;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuTest {

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
  void shouldDisplayOptions() {
    // Given
    Option option1 = mock(Option.class);
    when(option1.getMessage()).thenReturn("Option1");
    Option option2 = mock(Option.class);
    when(option2.getMessage()).thenReturn("Option2");
    Menu menu = new Menu(new Option[] {option1, option2});

    // When
    menu.displayOptions();
    String output = outContent.toString();

    // Then
    assertTrue(output.contains("Welcome to GIC Cinemas"), "Should contain welcome message");
    assertTrue(output.contains("Please enter your selection:"), "Should contain prompt");
    assertTrue(output.contains("[1] Option1"), "Should display option 1");
    assertTrue(output.contains("[2] Option2"), "Should display option 2");
  }

  @Test
  void shouldDisplayExitMessage() {
    // Given
    Option[] options = {};
    Menu menu = new Menu(options);

    // When
    menu.displayExitMessage();
    String output = outContent.toString();

    // Then
    assertTrue(
        output.contains("Thank you for using GIC Cinemas system, Bye!"),
        "Should display exit message");
  }

  @Test
  void shouldGetValidInput() {
    // Given
    Option option1 = mock(Option.class);
    when(option1.getMessage()).thenReturn("Option1");
    Option option2 = mock(Option.class);
    when(option2.getMessage()).thenReturn("Option2");
    Menu menu = new Menu(new Option[] {option1, option2});

    String userInput = "2";
    System.setIn(new ByteArrayInputStream(userInput.getBytes()));
    Scanner scanner = new Scanner(System.in);

    // When
    int selectionIndex = menu.promptForSelection(scanner);

    // Then
    // "2" means index 1 as the method subtracts 1 internally
    assertEquals(1, selectionIndex);
  }

  @Test
  void shouldRepromptForInvalidInput() {
    // Given
    Option option1 = mock(Option.class);
    when(option1.getMessage()).thenReturn("Option1");
    Option option2 = mock(Option.class);
    when(option2.getMessage()).thenReturn("Option2");
    Menu menu = new Menu(new Option[] {option1, option2});

    // Give a series of invalid input "hello", "0" and "4" followed by valid "2"
    String userInput = "hello\n0\n4\n2\n";
    System.setIn(new ByteArrayInputStream(userInput.getBytes()));
    Scanner scanner = new Scanner(System.in);

    // When
    int selectionIndex = menu.promptForSelection(scanner);

    // Then
    assertEquals(1, selectionIndex);
    String output = outContent.toString();
    assertTrue(
        output.contains("Invalid option. Please try again."),
        "Should prompt again for invalid input");
  }
}
