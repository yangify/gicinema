package io.yang.printer;

import java.io.PrintWriter;

public class ConsolePrinter {

  private ConsolePrinter() {}

  public static final PrintWriter printWriter = new PrintWriter(System.out, true);
}
