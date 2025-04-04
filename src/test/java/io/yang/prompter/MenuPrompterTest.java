package io.yang.prompter;

import io.yang.booking.option.BookOption;
import io.yang.booking.option.Option;
import io.yang.booking.option.OptionCatalogue;
import io.yang.booking.prompter.MenuPrompter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuPrompterTest {

  private Scanner scannerMock;
  private OptionCatalogue optionCatalogueMock;
  private MenuPrompter bookingPrompter;

  @BeforeEach
  void setUp() {
    scannerMock = mock(Scanner.class);
    optionCatalogueMock = mock(OptionCatalogue.class);
    bookingPrompter = new MenuPrompter(scannerMock, optionCatalogueMock);
  }

  @Test
  void testPrompt_validInputOffset() {
    when(optionCatalogueMock.getOptions()).thenReturn(createMockOptions());
    when(scannerMock.nextLine()).thenReturn("1"); // A valid option

    int selectedOption = bookingPrompter.prompt();
    assertEquals(0, selectedOption);
  }

  @Test
  void testPrompt_invalidThenValidInput() {
    when(optionCatalogueMock.getOptions()).thenReturn(createMockOptions());
    when(scannerMock.nextLine())
        .thenReturn("4") // Invalid input
        .thenReturn("invalid") // Invalid input
        .thenReturn("2"); // Valid input second

    int selectedOption = bookingPrompter.prompt();
    assertEquals(1, selectedOption);

    // Verify that the user was prompted thrice
    verify(scannerMock, times(3)).nextLine();
  }

  private Option[] createMockOptions() {
    return new Option[] {mock(BookOption.class), mock(BookOption.class), mock(BookOption.class)};
  }
}
