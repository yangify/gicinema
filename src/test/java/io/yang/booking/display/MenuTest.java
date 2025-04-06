package io.yang.booking.display;

import io.yang.booking.command.Action;
import io.yang.booking.option.Option;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuTest {

  private Scanner mockScanner;
  private Option mockOption1;
  private Option mockOption2;

  // Streams for capturing printed output
  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    mockScanner = mock(Scanner.class);
    mockOption1 = mock(Option.class);
    mockOption2 = mock(Option.class);

    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @AfterEach
  void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  void testDisplayOptions_capturesOutput() {
    // Arrange
    Option[] options = { mockOption1, mockOption2 };
    Menu menu = new Menu(options);

    // Act
    menu.displayOptions();
    String printedOutput = outputStreamCaptor.toString();

    // Assert
    // Verify that expected text is present in the captured output
    assertTrue(printedOutput.contains("Welcome to GIC Cinemas"),
            "Should contain welcome message");
    assertTrue(printedOutput.contains("Please enter your selection:"),
            "Should contain prompt for user selection");
    // Option lines like "[1] ..." and "[2] ..." should also appear
    assertTrue(printedOutput.contains("[1]"),
            "Should display option [1]");
    assertTrue(printedOutput.contains("[2]"),
            "Should display option [2]");
  }

  @Test
  void testDisplayExitMessage_capturesOutput() {
    // Arrange
    Option[] options = { mockOption1, mockOption2 };
    Menu menu = new Menu(options);

    // Act
    menu.displayExitMessage();
    String printedOutput = outputStreamCaptor.toString();

    // Assert
    assertTrue(printedOutput.contains("Thank you for using GIC Cinemas system, Bye!"),
            "Should display the exit message");
  }

  @Test
  void testPromptForSelection_numericValidInput() {
    // Arrange
    when(mockScanner.nextLine()).thenReturn("1");
    Option[] options = { mockOption1, mockOption2 };
    Menu menu = new Menu(options);

    // Act
    int selectionIndex = menu.promptForSelection(mockScanner);

    // Assert
    // "1" => index 0
    assertEquals(0, selectionIndex);
  }

  @Test
  void testPromptForSelection_numericInvalidInputThenValid() {
    // Arrange
    when(mockScanner.nextLine())
            .thenReturn("3") // invalid numeric (out of range)
            .thenReturn("2"); // valid
    Option[] options = { mockOption1, mockOption2 };
    Menu menu = new Menu(options);

    // Act
    int selectionIndex = menu.promptForSelection(mockScanner);

    // Assert
    // "2" => index 1
    assertEquals(1, selectionIndex);

    // Check the printed messages for invalid input warning, if desired
    String printedOutput = outputStreamCaptor.toString();
    assertTrue(printedOutput.contains("Invalid option. Please try again."),
            "Should warn about invalid option");
  }

  @Test
  void testPromptForSelection_nonNumericInputThenValid() {
    // Arrange
    when(mockScanner.nextLine())
            .thenReturn("abc") // non-numeric
            .thenReturn("1");  // valid
    Option[] options = { mockOption1, mockOption2 };
    Menu menu = new Menu(options);

    // Act
    int selectionIndex = menu.promptForSelection(mockScanner);

    // Assert
    // "1" => index 0
    assertEquals(0, selectionIndex);

    // Check the printed messages about invalid input
    String printedOutput = outputStreamCaptor.toString();
    assertTrue(printedOutput.contains("Invalid option. Please try again."),
            "Should warn about invalid option");
  }

  @Test
  void testGetAction() {
    // Arrange
    Action action1 = () -> {};
    Action action2 = () -> {};

    when(mockOption1.getAction()).thenReturn(action1);
    when(mockOption2.getAction()).thenReturn(action2);

    Option[] options = { mockOption1, mockOption2 };
    Menu menu = new Menu(options);

    // Act & Assert
    assertEquals(action1, menu.getAction(0),
            "Retrieved Action should match action1");
    assertEquals(action2, menu.getAction(1),
            "Retrieved Action should match action2");
  }
}