package aoc2023;

import java.util.Arrays;
import java.util.List;

import tools.Parse;

public class Aoc2301 {
  static private final boolean TEST = true;

  public static void main(String[] args) {
    long r =
      Arrays.stream(getInput()).map(s -> {
        var p = "(?=(one|two|three|four|five|six|seven|eight|nine|zero|\\d))";
        List<Integer> nums = Parse.regexWithGroups(s, p).stream().map(e ->
          Integer.parseInt(e
            .replace("one", "1")
            .replace("two", "2")
            .replace("three", "3")
            .replace("four", "4")
            .replace("five", "5")
            .replace("six", "6")
            .replace("seven", "7")
            .replace("eight", "8")
            .replace("nine", "9")
            .replace("zero", "0")
          )
        ).toList();
        long rr = (long) (nums.get(0)) * 10 + (long) (nums.get(nums.size() - 1));
        return rr;
      }).reduce(0L, Long::sum);

    System.out.println(r);
  }

  private static String[] getInput() {
    String testStr = "oneight\n" +
      "eightwothree\n" +
      "abcone2threexyz\n" +
      "xtwone3four\n" +
      "4nineeightseven2\n" +
      "zoneight234\n" +
      "7pqrstsixteen";
    String realStr = "";
    return (TEST ? testStr : realStr).split("\n");
  }
}
