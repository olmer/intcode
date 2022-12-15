package aoc2022;

import tools.Parse;

public class Aoc2204 {
  public static void main(String[] args) {
    int r = 0;

    for (var s : getInput()) {
      var ranges = Parse.ranges(s);

      if (ranges.get(1).getKey() <= ranges.get(0).getValue() && ranges.get(1).getKey() >= ranges.get(0).getKey()
        || ranges.get(0).getKey() <= ranges.get(1).getValue() && ranges.get(0).getKey() >= ranges.get(1).getKey())
        r++;

    }

    System.out.println(r);
  }

  private static String[] getInput() {
    String s = "2-4,6-8\n" +
      "2-3,4-5\n" +
      "5-7,7-9\n" +
      "2-8,3-7\n" +
      "6-6,4-6\n" +
      "2-6,4-8";
    return s.split("\n");
  }
}
