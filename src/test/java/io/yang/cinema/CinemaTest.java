package io.yang.cinema;

import io.yang.init.CinemaConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CinemaTest {

  private Cinema cinema;

  @BeforeEach
  void setUp() {
    // Mock the configuration
    CinemaConfiguration mockConfig = mock(CinemaConfiguration.class);
    when(mockConfig.getMovieTitle()).thenReturn("Test Movie");
    when(mockConfig.getRows()).thenReturn(2);
    when(mockConfig.getSeatsPerRow()).thenReturn(3);

    // Create a Cinema instance
    cinema = new Cinema(mockConfig);
  }

  @Test
  void testMovieTitle() {
    assertEquals("Test Movie", cinema.getMovieTitle());
  }

  @Test
  void testInitialSeatAvailability() {
    for (Seat[] row : cinema.getSeats()) {
      for (Seat seat : row) {
        assertTrue(seat.isAvailable());
      }
    }
  }

  @Test
  void testGetSeat() {
    Optional<Seat> seat = cinema.getSeat(0, 1);
    assertTrue(seat.isPresent());
    assertTrue(seat.get().isAvailable()); // Seat should be available initially
  }

  @Test
  void testGetSeatWithInvalidIndexes() {
    // Negative indexes
    assertTrue(cinema.getSeat(-1, 1).isEmpty());
    assertTrue(cinema.getSeat(1, -1).isEmpty());

    // Out-of-bounds indexes
    assertTrue(cinema.getSeat(2, 1).isEmpty()); // Row index out of bounds
    assertTrue(cinema.getSeat(0, 3).isEmpty()); // Seat index out of bounds
  }

  @Test
  void testAvailableSeatsCount() {
    Seat[][] seats = cinema.getSeats();

    // Manually occupy some seats for testing
    seats[0][0].reserve("1"); // Reserve seat at (0,0)
    seats[1][2].reserve("2"); // Reserve seat at (1,2)

    // Verify updated count
    assertEquals(4, cinema.getAvailableSeatsCount());
  }
}
