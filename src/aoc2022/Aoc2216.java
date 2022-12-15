package aoc2022;

import java.util.Arrays;

public class Aoc2216 {
  static boolean TEST = true;

  public static void main(String[] args) throws Exception {
    System.out.println(Arrays.toString(getInput()));
  }

  private static String[] getInput() {
    String testStr = "";

    String realStr = "";

    return (TEST ? testStr : realStr).split("\n");
  }
}
