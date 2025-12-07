package aoc2025;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Aoc2507 extends AbstractAoc {
  long part1(String[] in) {
    Set<Integer> beams = new HashSet<>();
    beams.add(in[0].length() / 2);
    int r = 0;
    for (String s : in) {
      for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '^' && beams.contains(i)) {
          beams.add(i - 1);
          beams.add(i + 1);
          beams.remove(i);
          r++;
        }
      }
    }
    return r;
  }

  long part2(String[] in) {
    long[][] dp = new long[in.length][in[0].length()];
    dp[0][in[0].length() / 2] = 1;
    for (int i = 0; i < in.length; i++) {
      for (int j = 0; j < in[i].length(); j++) {
        if (i != 0 && in[i].charAt(j) != '^') {
          dp[i][j] += dp[i - 1][j];
        }
        if (j > 0 && in[i].charAt(j - 1) == '^') {
          dp[i][j] += dp[i - 1][j - 1];
        }
        if (j < in[i].length() - 1 && in[i].charAt(j + 1) == '^') {
          dp[i][j] += dp[i - 1][j + 1];
        }
      }
    }
    return Arrays.stream(dp[dp.length - 1]).sum();
  }

  public static void main(String[] args) {
    (new Aoc2507()).start();
  }

  @Override
  String getTestInput() {
    return ".......S.......\n" +
        "...............\n" +
        ".......^.......\n" +
        "...............\n" +
        "......^.^......\n" +
        "...............\n" +
        ".....^.^.^.....\n" +
        "...............\n" +
        "....^.^...^....\n" +
        "...............\n" +
        "...^.^...^.^...\n" +
        "...............\n" +
        "..^...^.....^..\n" +
        "...............\n" +
        ".^.^.^.^.^...^.\n" +
        "...............";
  }

  @Override
  String getRealInput() {
    return ".......S.......\n" +
        "...............\n" +
        ".......^.......\n" +
        "...............\n" +
        "......^.^......\n" +
        "...............\n" +
        ".....^.^.^.....\n" +
        "...............\n" +
        "....^.^...^....\n" +
        "...............\n" +
        "...^.^...^.^...\n" +
        "...............\n" +
        "..^...^.....^..\n" +
        "...............\n" +
        ".^.^.^.^.^...^.\n" +
        "...............";
  }

  @Override
  long getTestExpected1() {
    return 0;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }
}
