package aoc2023;

import java.util.Arrays;

import tools.Parse;

public class Aoc2306 {
  static private final boolean TEST = true;

  public static void main(String[] args) {
    System.out.println(part1());
  }

  private static int part1() {
    var input = Arrays.stream(getInput()).map(Parse::longs).toList();
    int r = 1;

    for (int i = 0; i < input.get(0).size(); i++) {
      int rr = 0;
      long time = input.get(0).get(i);
      long dist = input.get(1).get(i);
      for (int j = 1; j < time; j++) {
        if ((time - j) * j > dist) {
          rr++;
        }
      }
      r *= rr;
    }

    return r;
  }

  private static String[] getInput() {
    String testStr = "Time:      71530\n" +
      "Distance:  940200";
    String realStr = "";
    return (TEST ? testStr : realStr).split("\n");
  }
}
