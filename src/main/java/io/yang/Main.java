package io.yang;

import io.yang.init.Initializer;

import java.util.Scanner;

public class Main {

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    init();
  }

  public static void init() {
    try {
      Initializer.init(scanner);

    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      scanner.close();
    }
  }
}
