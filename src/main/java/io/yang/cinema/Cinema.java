package io.yang.cinema;

import io.yang.init.CinemaConfiguration;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Represents a Cinema instance, which manages information about a particular movie screening and
 * the booked or available seats within the cinema hall.
 */
public class Cinema {

  /** The title of the movie being shown in this cinema. */
  private final String movieTitle;

  /**
   * A 2D array representing the seating arrangement in the cinema, organized by rows and seats per
   * row.
   */
  private final Seat[][] seats;

  /**
   * Constructs a Cinema instance using the provided {@link CinemaConfiguration}. Initializes the
   * movie title and seating arrangement based on the configuration.
   *
   * @param config The configuration object containing movie title, number of rows, and number of
   *     seats per row.
   */
  public Cinema(CinemaConfiguration config) {
    movieTitle = config.getMovieTitle();
    seats = initSeats(config.getRows(), config.getSeatsPerRow());
  }

  /**
   * Initializes the seating arrangement for the cinema.
   *
   * @param rows The number of rows in the cinema.
   * @param seatsPerRow The number of seats in each row.
   * @return A 2D array representing the initialized seating arrangement.
   */
  private Seat[][] initSeats(int rows, int seatsPerRow) {
    return IntStream.range(0, rows)
        .mapToObj(ignored -> initRow(seatsPerRow))
        .toArray(Seat[][]::new);
  }

  /**
   * Initializes a single row within the cinema, creating the specified number of seats.
   *
   * @param seatsPerRow The number of seats to create in the row.
   * @return An array representing a row of seats.
   */
  private Seat[] initRow(int seatsPerRow) {
    return IntStream.range(0, seatsPerRow).mapToObj(ignored -> new Seat()).toArray(Seat[]::new);
  }

  /**
   * Retrieves the title of the movie being shown in this cinema.
   *
   * @return The title of the movie.
   */
  public String getMovieTitle() {
    return movieTitle;
  }

  /**
   * Retrieves the seating arrangement of the cinema.
   *
   * @return A 2D array of {@link Seat} objects, representing the seating arrangement.
   */
  public Seat[][] getSeats() {
    return seats;
  }

  /**
   * Retrieves a specific seat in the cinema by its row and seat number.
   *
   * @param row The row index of the seat.
   * @param seatNum The seat index within the specified row.
   * @return An {@link Optional} containing the {@link Seat} at the given row and seat index, or an
   *     empty {@link Optional} if the row or seat index is out of bounds.
   */
  public Optional<Seat> getSeat(int row, int seatNum) {
    return row < 0 || row >= seats.length || seatNum < 0 || seatNum >= seats[row].length
        ? Optional.empty()
        : Optional.of(seats[row][seatNum]);
  }

  /**
   * Calculates the number of available seats in the cinema by checking all seats.
   *
   * @return The number of seats that are currently available (not occupied).
   */
  public int getAvailableSeatsCount() {
    return (int) Arrays.stream(seats).flatMap(Arrays::stream).filter(Seat::isAvailable).count();
  }
  
  
  /**
   * Checks if the cinema has enough available seats to accommodate a specific number of seats.
   *
   * @param numberOfSeats The number of seats requested.
   * @return {@code true} if there are enough available seats; {@code false} otherwise.
   */
  public boolean hasEnoughSeats(int numberOfSeats) {
    return numberOfSeats <= getAvailableSeatsCount();
  }

  /**
   * Releases all seats associated with a specific booking ID, making them available again.
   *
   * @param bookingId The ID of the booking whose seats should be released.
   */
  public void releaseSeats(String bookingId) {
    Arrays.stream(seats)
        .flatMap(Arrays::stream)
        .filter(seat -> bookingId.equals(seat.getBookingId()))
        .forEach(Seat::release);
  }
}
