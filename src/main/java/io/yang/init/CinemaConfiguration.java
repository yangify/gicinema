package io.yang.init;

/**
 * The {@code CinemaConfiguration} class represents the configuration details of a cinema. It
 * includes information about the movie being screened, and the seating arrangement in the cinema.
 */
public class CinemaConfiguration {

  /** The title of the movie being screened. */
  String movieTitle;

  /** The number of rows of seats in the cinema. */
  int rows;

  /** The number of seats per row in the cinema. */
  int seatsPerRow;

  /**
   * Constructs a new {@code CinemaConfiguration} object with the specified details.
   *
   * @param movieTitle the title of the movie
   * @param rows the number of rows in the cinema
   * @param seatsPerRow the number of seats in each row
   */
  public CinemaConfiguration(String movieTitle, int rows, int seatsPerRow) {
    this.movieTitle = movieTitle;
    this.rows = rows;
    this.seatsPerRow = seatsPerRow;
  }

  /**
   * Retrieves the title of the movie being screened.
   *
   * @return the movie title
   */
  public String getMovieTitle() {
    return movieTitle;
  }

  /**
   * Retrieves the number of rows of seats in the cinema.
   *
   * @return the number of rows
   */
  public int getRows() {
    return rows;
  }

  /**
   * Retrieves the number of seats per row in the cinema.
   *
   * @return the number of seats per row
   */
  public int getSeatsPerRow() {
    return seatsPerRow;
  }
}
