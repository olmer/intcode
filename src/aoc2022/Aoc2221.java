package aoc2022;

public class Aoc2221 {

  static boolean TEST = false;

  public static void main(String[] args) throws Exception {}

  private static String getInput() {
    String testStr = "1\n" +
      "2\n" +
      "-3\n" +
      "3\n" +
      "-2\n" +
      "0\n" +
      "4";

    String realStr = "";

    return (TEST ? testStr : realStr);
  }
}
