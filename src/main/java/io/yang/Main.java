package io.yang;

import io.yang.booking.BookingSession;
import io.yang.booking.option.BookOption;
import io.yang.booking.option.CheckOption;
import io.yang.booking.option.ExitOption;
import io.yang.booking.option.Option;
import io.yang.cinema.Cinema;
import io.yang.init.CinemaConfiguration;
import io.yang.init.Initializer;

import java.util.Scanner;

public class Main {

  private static final Scanner scanner = new Scanner(System.in);

  private static void init() {
    CinemaConfiguration config = Initializer.init(scanner);
    Cinema cinema = new Cinema(config);

    Option[] options =
        new Option[] {new BookOption(cinema, scanner), new CheckOption(cinema), new ExitOption()};
    new BookingSession(options).run(scanner);
  }

  public static void main(String[] args) {
    try {
      init();

    } finally {
      scanner.close();
    }
  }
}
