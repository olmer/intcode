package aoc2022;

public class Aoc2225 {
  static boolean TEST = true;

  static char snafuValue(int val) throws Exception {
    return switch (val) {
      case 5, 0, -5 -> '0';
      case 4, -1 -> '-';
      case 3, -2 -> '=';
      case 2, -3 -> '2';
      case 1, -4 -> '1';
      default -> throw new Exception("wtf");
    };
  }
  static int snafuCarry(int val) throws Exception {
    return switch (val) {
      case 5, 4, 3 -> 1;
      case 2, 1, 0, -1, -2 -> 0;
      case -3, -4, -5 -> -1;
      default -> throw new Exception("wtf");
    };
  }
  static int value(char c) {
    return switch (c) {
      case '-' -> -1;
      case '=' -> -2;
      default -> c - '0';
    };
  }

  public static void main(String[] args) throws Exception {
    String right = "";

    for (var left : getInput()) {
      int carry = 0;
      StringBuilder sum = new StringBuilder();
      for (int i = 0; carry != 0 || i < Math.max(right.length(), left.length()); i++) {
        int curr = carry;
        if (i < right.length()) curr += value(right.charAt(right.length() - 1 - i));
        if (i < left.length()) curr += value(left.charAt(left.length() - 1 - i));
        carry = snafuCarry(curr);
        sum.append(snafuValue(curr));
      }
      right = sum.reverse().toString();
    }

    System.out.println(right);
  }

  private static String[] getInput() {
    String testStr = "1=-0-2\n" +
      "12111\n" +
      "2=0=\n" +
      "21\n" +
      "2=01\n" +
      "111\n" +
      "20012\n" +
      "112\n" +
      "1=-1=\n" +
      "1-12\n" +
      "12\n" +
      "1=\n" +
      "122";

    String realStr = "";

    return (TEST ? testStr : realStr).split("\n");
  }
}
