package io.yang.booking.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookingIdGeneratorTest {

  @Test
  void testNextId() {
    String firstId = BookingIdGenerator.nextId();
    assertEquals("GIC0001", firstId, "First generated ID should be 'GIC0001'");

    String secondId = BookingIdGenerator.nextId();
    assertEquals("GIC0002", secondId, "Second generated ID should be 'GIC0002'");
  }
}
