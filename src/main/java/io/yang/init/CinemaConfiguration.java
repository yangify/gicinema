package io.yang.init;

public class CinemaConfiguration {
  String movieTitle;
  int rows;
  int seatsPerRow;

  public CinemaConfiguration(String movieTitle, int rows, int seatsPerRow) {
    this.movieTitle = movieTitle;
    this.rows = rows;
    this.seatsPerRow = seatsPerRow;
  }

  public String getMovieTitle() {
    return movieTitle;
  }

  public int getRows() {
    return rows;
  }

  public int getSeatsPerRow() {
    return seatsPerRow;
  }
}
