package io.yang.cinema.converter;

import io.yang.cinema.Cinema;
import io.yang.init.CinemaConfiguration;
import io.yang.booking.SeatSelector.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionConverterTest {

  @Test
  void testConvert_ValidInput() {
    // Given a cinema with 3 rows and 3 seats per row
    CinemaConfiguration config = new CinemaConfiguration("Movie", 3, 3);
    Cinema cinema = new Cinema(config);

    // "A01" -> rowChar='A' => reversed index => 2; col=0
    Position pos = PositionConverter.convert("A01", cinema);
    assertEquals(2, pos.getRowNum());
    assertEquals(0, pos.getColNum());
  }

  @Test
  void testConvert_NullInput() {
    // Given a cinema
    CinemaConfiguration config = new CinemaConfiguration("Movie", 1, 1);
    Cinema cinema = new Cinema(config);

    // Expect exception for null
    assertThrows(IllegalArgumentException.class, () -> PositionConverter.convert(null, cinema));
  }

  @Test
  void testConvert_InvalidLength() {
    // Given a cinema
    CinemaConfiguration config = new CinemaConfiguration("Movie", 1, 1);
    Cinema cinema = new Cinema(config);

    // Length not equal to 3
    assertThrows(IllegalArgumentException.class, () -> PositionConverter.convert("AB", cinema));
    assertThrows(IllegalArgumentException.class, () -> PositionConverter.convert("ABCD", cinema));
  }

  @Test
  void testConvert_FirstCharNotLetter() {
    // Given a cinema
    CinemaConfiguration config = new CinemaConfiguration("Movie", 2, 2);
    Cinema cinema = new Cinema(config);

    // First char is not a letter
    assertThrows(IllegalArgumentException.class, () -> PositionConverter.convert("1AA", cinema));
  }

  @Test
  void testConvert_FirstCharLowercase() {
    // Given a cinema
    CinemaConfiguration config = new CinemaConfiguration("Movie", 2, 2);
    Cinema cinema = new Cinema(config);

    // Row should be uppercase
    assertThrows(IllegalArgumentException.class, () -> PositionConverter.convert("a01", cinema));
  }

  @Test
  void testConvert_RowOutOfRange() {
    // Given a cinema with 2 rows -> valid row chars: A, B
    CinemaConfiguration config = new CinemaConfiguration("Movie", 2, 3);
    Cinema cinema = new Cinema(config);

    // "C01" => rowChar='C' -> beyond range
    assertThrows(IllegalArgumentException.class, () -> PositionConverter.convert("C01", cinema));
  }

  @Test
  void testConvert_ColumnNotNumeric() {
    // Given a cinema
    CinemaConfiguration config = new CinemaConfiguration("Movie", 2, 3);
    Cinema cinema = new Cinema(config);

    // Non-numeric, e.g. 'A0x'
    assertThrows(IllegalArgumentException.class, () -> PositionConverter.convert("A0x", cinema));
  }

  @Test
  void testConvert_ColumnZeroOrNegative() {
    // Given a cinema
    CinemaConfiguration config = new CinemaConfiguration("Movie", 2, 3);
    Cinema cinema = new Cinema(config);

    // "A00" -> parseInt("00") - 1 = -1
    assertThrows(IllegalArgumentException.class, () -> PositionConverter.convert("A00", cinema));
  }

  @Test
  void testConvert_ColumnExceedsLimit() {
    // Given a cinema with 1 row, 3 columns -> col indices: 0..2
    CinemaConfiguration config = new CinemaConfiguration("Movie", 1, 3);
    Cinema cinema = new Cinema(config);

    // "A04" -> parseInt("04") - 1 = 3 => out of bounds
    assertThrows(IllegalArgumentException.class, () -> PositionConverter.convert("A04", cinema));
  }

  @Test
  void testConvert_AnotherValidCase() {
    // Given a cinema with 3 rows, 4 seats per row
    // Valid row chars: A->2, B->1, C->0
    CinemaConfiguration config = new CinemaConfiguration("Movie", 3, 4);
    Cinema cinema = new Cinema(config);

    // "B03" -> reversed row index => 1; col => 2
    Position pos = PositionConverter.convert("B03", cinema);
    assertEquals(1, pos.getRowNum());
    assertEquals(2, pos.getColNum());
  }
}
