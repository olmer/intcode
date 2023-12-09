package aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tools.Parse;

public class Aoc2309 {

  private static long part1(String[] input) {
    if (input.length == 1) return 0;
    var lines = Arrays.stream(input).map(Parse::longs).collect(Collectors.toList());
    long result = 0;
    for (var line : lines) {
      List<Long> prevlist = new ArrayList<>(line);
      long cursum = 0;
      boolean allZeroes = false;
      long lastVal = 0;
      while (!allZeroes) {
        allZeroes = true;
        List<Long> curlist = new ArrayList<>();
        for (int i = 1; i < prevlist.size(); i++) {
          long dif = prevlist.get(i) - prevlist.get(i - 1);
          if (dif != 0) {
            allZeroes = false;
          }
          curlist.add(dif);
          lastVal = prevlist.get(i);
        }
        cursum += lastVal;
        prevlist = curlist;
      }
      result += cursum;
    }
    return result;
  }

  private static long part2(String[] input) {
    if (input.length == 1) return 0;
    var lines = Arrays.stream(input).map(Parse::longs).collect(Collectors.toList());
    long result = 0;
    for (var line : lines) {
      List<Long> prevlist = new ArrayList<>(line);
      boolean allZeroes = false;
      List<Long> firstVals = new ArrayList<>();
      while (!allZeroes) {
        firstVals.add(prevlist.get(0));
        allZeroes = true;
        List<Long> curlist = new ArrayList<>();
        for (int i = 1; i < prevlist.size(); i++) {
          long dif = prevlist.get(i) - prevlist.get(i - 1);
          if (dif != 0) {
            allZeroes = false;
          }
          curlist.add(dif);
        }
        prevlist = curlist;
      }
      long currentLineResult = 0;
      for (int i = firstVals.size() - 1; i >= 0; i--) {
        currentLineResult = firstVals.get(i) - currentLineResult;
      }
      result += currentLineResult;
    }
    return result;
  }

  private static void test() {
//    var custom1 = part1(new String[]{});
//    System.out.println("Custom test: " + custom1);

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

  static int expected1 = 114;
  static int expected2 = 2;
  static String testStr = "0 3 6 9 12 15\n" +
    "1 3 6 10 15 21\n" +
    "10 13 16 21 30 45";
  static String realStr = "";
}
