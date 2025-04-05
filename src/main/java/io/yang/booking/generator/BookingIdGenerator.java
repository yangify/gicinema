package io.yang.booking.generator;

/**
 * Generates booking IDs by combining a predefined prefix with an incremented, zero-padded numerical
 * sequence.
 */
public class BookingIdGenerator {

  /** Constant prefix used at the start of each generated ID. */
  public static final String PREFIX = "GIC";

  /** Private constructor to prevent instantiation of this utility class. */
  private BookingIdGenerator() {}

  /** Tracks the current count for generated IDs. */
  private static int currentCount = 0;

  /**
   * Returns the next unique booking ID. Increments the internal counter and formats the result as a
   * zero-padded 4-digit number with the prefix.
   *
   * @return the next booking ID string
   */
  public static String nextId() {
    return PREFIX + pad(++currentCount);
  }

  /**
   * Formats a numerical value to a zero-padded string with a width of 4 digits.
   *
   * @param number the numerical value to format
   * @return the zero-padded string
   */
  private static String pad(int number) {
    return String.format("%04d", number);
  }
}
