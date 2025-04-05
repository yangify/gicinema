package io.yang.cinema;

/**
 * Represents a seat in a cinema.
 *
 * <p>The {@code Seat} class allows for managing the reservation state of a seat. It provides
 * methods to check whether the seat is available, reserved, or to mark it as reserved.
 */
public class Seat {
  
  
  /**
   * The booking ID associated with the seat. This field is assigned when the seat is reserved and is
   * {@code null} if the seat is not reserved.
   */
  private String bookingId;

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
   * Marks the seat as reserved with the specified booking ID. Once this method is called, 
   * {@code isAvailable()} will return {@code false}.
   *
   * @param bookingId the unique identifier for the booking associated with this seat
   */
  public void reserve(String bookingId) {
    this.reserved = true;
    this.bookingId = bookingId;
  }

  /**
   * Retrieves the booking ID associated with the seat.
   *
   * @return the booking ID of the reserved seat, or {@code null} if the seat is not reserved.
   */
  public String getBookingId() {
    return bookingId;
  }
}
