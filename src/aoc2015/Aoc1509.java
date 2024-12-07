package aoc2015;

public class Aoc1508 {

  private static long part1(String[] input) {
    long r = 0;
    for (String in : input) {
      r += in.length();
      int l = 0;
      for (int i = 0; i < in.length(); i++) {
        switch (in.charAt(i)) {
          case '\\' -> {
            switch (in.charAt(i + 1)) {
              case 'x' -> {
                l++;
                i += 3;
              }
              default -> {
                l++;
                i++;
              }
            }
          }
          case '"' -> {}
          default -> l++;
        }
      }
      r -= l;
    }
    return r;
  }

  private static long part2(String[] input) {
    long r = 0;
    long f = 0;
    for (String in : input) {
      r += in.length();
      f += in.length();
      for (int i = 0; i < in.length(); i++) {
        switch (in.charAt(i)) {
          case '\\' -> {
            f++;
            switch (in.charAt(i + 1)) {
              case 'x' -> {
              }
              default -> {
                f++;
                i++;
              }
            }
          }
          case '"' -> {f += 2;}
          default -> {}
        }
      }
    }
    return f - r;
  }

  private static void test() {
    var p1 = part1(getInput(true));
    System.out.println("Part 1 test: " + p1 + (p1 == expected1 ? " PASSED" : " FAILED"));

    var p2 = part2(getInput(true));
    System.out.println("Part 2 test: " + p2 + (p2 == expected2 ? " PASSED" : " FAILED"));
  }

  public static void main(String[] args) {
    test();
    System.out.println(part1(getInput(false)));
    System.out.println(part2(getInput(false)));
  }

  private static String[] getInput(boolean isTest) {
    return (isTest ? testStr : realStr).split("\n");
  }

  static int expected1 = 2;
  static int expected2 = 47;

  static String testStr = "\"\"\n" +
    "\"abc\"\n" +
    "\"aaa\\\"aaa\"\n" +
    "\"\\x27\"";
  static String realStr = "\"\"\n" +
    "\"abc\"\n" +
    "\"aaa\\\"aaa\"\n" +
    "\"\\x27\"";
}
