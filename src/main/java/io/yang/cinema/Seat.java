package io.yang.cinema;

/**
 * Represents a seat in a cinema.
 *
 * <p>The {@code Seat} class allows for managing the reservation state of a seat. It provides
 * methods to check whether the seat is available, reserved, or to mark it as reserved.
 */
public class Seat {

  /**
   * Indicates whether the seat is reserved. This field is {@code true} if the seat is reserved;
   * {@code false} otherwise.
   */
  private boolean reserved;

  /**
   * Checks whether the seat is available for reservation.
   *
   * @return {@code true} if the seat is not reserved; {@code false} otherwise.
   */
  public boolean isAvailable() {
    return !reserved;
  }

  /**
   * Checks whether the seat is currently reserved.
   *
   * @return {@code true} if the seat is reserved; {@code false} otherwise.
   */
  public boolean isReserved() {
    return reserved;
  }

  /**
   * Marks the seat as reserved. Once this method is called, {@code isAvailable()} will return
   * {@code false}.
   */
  public void reserve() {
    reserved = true;
  }
}
