package aoc2024;

import java.util.List;

import tools.Parse;

public class Aoc2403 extends AbstractAoc {
  long part1(String[] in) {
    long r = 0;
    for (int i = 0; i < in.length; i++) {
      for (String s : Parse.regex(in[i], "mul\\((\\d+),(\\d+)\\)")) {
        r += Parse.integers(s).get(0) * Parse.integers(s).get(1);
      }
    }
    return r;
  }

  long part2(String[] in) {
    long r = 0;
    boolean enabled = true;
    for (int i = 0; i < in.length; i++) {
      List<String> parsed = Parse.regex(in[i], "don\\'t\\(\\)|do\\(\\)|mul\\((\\d+),(\\d+)\\)");
      for (String s : parsed) {
        if (s.equals("don't()")) {
          enabled = false;
        } else if (s.equals("do()")) {
          enabled = true;
        } else if (enabled) {
          r += Parse.integers(s).get(0) * Parse.integers(s).get(1);
        }
      }
    }
    return r;
  }

  public static void main(String[] args) {
    (new Aoc2403()).start();
  }

  @Override
  long getTestExpected1() {
    return 161;
  }

  @Override
  long getTestExpected2() {
    return 48;
  }

  @Override
  String getTestInput() {
    return "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
  }

  @Override
  String getRealInput() {
    return "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
  }
}
