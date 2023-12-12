package aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.Parse;

public class Aoc2312 {

  private static long part1(String[] input) {
    if (input.length == 1) return 0;
    long r = 0;
    for (var in : input) {
      List<Integer> nums = new ArrayList<>(Parse.integers(in.split(" ")[1]));
      String s = in.split(" ")[0];
      r += numberOfWays(s, nums);
    }

    return r;
  }

  private static long part2(String[] input) {
    if (input.length == 1) return 0;
    long r = 0;
    for (var in : input) {
      List<Integer> nums = new ArrayList<>(Parse.integers(in.split(" ")[1]));
      List<Integer> newnums = new ArrayList<>();
      String s = in.split(" ")[0];
      StringBuilder ns = new StringBuilder();
      for (int i = 0; i < 5; i++) {
        ns.append(s);
        newnums.addAll(nums);
        if (i != 4) {
          ns.append('?');
        }
      }
      r += numberOfWays(ns.toString(), newnums);
    }

    return r;
  }

  static Map<String, Map<List<Integer>, Long>> mem = new HashMap<>();

  static Long get(String s, List<Integer> l) {
    return mem.containsKey(s) && mem.get(s).containsKey(l)
      ? mem.get(s).get(l)
      : -1;
  }

  static void set(String s, List<Integer> l, long r) {
    mem.putIfAbsent(s, new HashMap<>());
    mem.get(s).put(l, r);
  }

  private static long numberOfWays(String s, List<Integer> nums) {
    if (s.isEmpty()) {//string is empty, if nums are also empty that's a 1
      return nums.isEmpty() ? 1 : 0;
    }
    if (nums.isEmpty()) {//if string is not empty and nums are empty -> check if we MUST place a number
      return s.indexOf('#') == -1 ? 1 : 0;
    }
    if (get(s, nums) != -1) {
      return get(s, nums);
    }

    int n = nums.get(0);
    List<Integer> newNums = new ArrayList<>(nums);
    newNums.remove(0);

    long possiblePlacements = 0;
    int end = s.length();
    if (s.indexOf('#') != -1) {//can't avoid placing the current number if # exists
      end = s.indexOf('#') + 1;
    }
    for (int i = 0; i < end; i++) {
      if (validPosition(s, n, i)) {//consider current num as placed
        if (i + n == s.length() && newNums.isEmpty()) {//no more string to check and no more numbers
          possiblePlacements += 1;
        } else if (i + n < s.length()) {//there's some string left, recursive call
          possiblePlacements += numberOfWays(s.substring(i + n + 1), newNums);
        }
      }
    }

    set(s, nums, possiblePlacements);
    return possiblePlacements;
  }

  private static boolean validPosition(String s, int number, int idx) {
    int end = idx + number;
    if (end > s.length()) {
      return false;
    }
    for (int i = idx; i < end; i++) {
      if (s.charAt(i) == '.') {//can't place a number over empty tile
        return false;
      }
    }
    if (end <= s.length() - 1 && s.charAt(end) == '#') {//can't place the number because to the right goes #
      return false;
    }
    if (idx > 0 && s.charAt(idx - 1) == '#') {//can't place the number because to the left goes #
      return false;
    }

    return true;
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

  static int expected1 = 21;
  static int expected2 = 525152;
  static String testStr = "???.### 1,1,3\n" +
    ".??..??...?##. 1,1,3\n" +
    "?#?#?#?#?#?#?#? 1,3,1,6\n" +
    "????.#...#... 4,1,1\n" +
    "????.######..#####. 1,6,5\n" +
    "?###???????? 3,2,1";
  static String realStr = "";
}
