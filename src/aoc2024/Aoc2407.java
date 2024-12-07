package aoc2024;

import java.util.List;

import tools.Parse;

public class Aoc2407 extends AbstractAoc {
  enum Op {
    ADD, MUL, CONCAT
  }

  long part1(String[] in) {
    long r = 0;

    Op[] ops = {Op.MUL, Op.ADD};

    for (String s : in) {
      List<Long> nums = Parse.longs(s);
      long target = nums.removeFirst();
      if (backtrack(0, nums, target, 0, ops)) {
        r += target;
      }
    }

    return r;
  }

  long part2(String[] in) {
    long result = 0;

    Op[] ops = {Op.MUL, Op.ADD, Op.CONCAT};

    for (String s : in) {
      List<Long> nums = Parse.longs(s);
      long target = nums.removeFirst();
      if (backtrack(0, nums, target, 0, ops)) {
        result += target;
      }
    }

    return result;
  }

  boolean backtrack(int i, List<Long> nums, long target, long curSum, Op[] ops) {
    if (i == nums.size()) {
      return curSum == target;
    }
    if (curSum > target) {
      return false;
    }
    for (int o = 0; o < ops.length; o++) {
      if (ops[o] == Op.ADD) {
        if (backtrack(i + 1, nums, target, curSum + nums.get(i), ops)) {
          return true;
        }
      } else if (ops[o] == Op.MUL) {
        if (backtrack(i + 1, nums, target, curSum * nums.get(i), ops)) {
          return true;
        }
      } else if (ops[o] == Op.CONCAT) {
        if (backtrack(i + 1, nums, target, Long.parseLong(String.valueOf(curSum) + nums.get(i)), ops)) {
          return true;
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    (new Aoc2407()).start();
  }

  @Override
  long getTestExpected1() {
    return 3749;
  }

  @Override
  long getTestExpected2() {
    return 11387;
  }

  @Override
  String getTestInput() {
    return "190: 10 19\n" +
        "3267: 81 40 27\n" +
        "83: 17 5\n" +
        "156: 15 6\n" +
        "7290: 6 8 6 15\n" +
        "161011: 16 10 13\n" +
        "192: 17 8 14\n" +
        "21037: 9 7 18 13\n" +
        "292: 11 6 16 20";
  }

  @Override
  String getRealInput() {
    return "190: 10 19\n" +
        "3267: 81 40 27\n" +
        "83: 17 5\n" +
        "156: 15 6\n" +
        "7290: 6 8 6 15\n" +
        "161011: 16 10 13\n" +
        "192: 17 8 14\n" +
        "21037: 9 7 18 13\n" +
        "292: 11 6 16 20";
  }
}
