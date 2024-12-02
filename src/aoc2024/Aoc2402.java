package aoc2024;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.Parse;

public class Aoc2402 extends AbstractAoc {
  long part1(String[] in) {
    Set<Integer> safe = new HashSet<>();
    for (int i = 0; i < in.length; i++) {
      List<Integer> row = Parse.integers(in[i]);
      if (check(row, 0, 1) || check(row, 1, 0))
        safe.add(i);
    }

    return safe.size();
  }

  boolean check(List<Integer> row, int a, int b) {
    for (int j = 1; j < row.size(); j++) {
      if (row.get(j - a) - row.get(j - b) < 1 || row.get(j - a) - row.get(j - b) > 3) {
        return false;
      }
    }

    return true;
  }

  long part2(String[] in) {
    Set<Integer> safe = new HashSet<>();
    for (int i = 0; i < in.length; i++) {
      List<Integer> row = Parse.integers(in[i]);
      if (check(row, 0, 1) || check(row, 1, 0))
        safe.add(i);

      for (int j = 0; j < row.size(); j++) {
        List<Integer> newrow = new ArrayList<>(row);
        newrow.remove(j);
        if (check(newrow, 0, 1) || check(newrow, 1, 0))
          safe.add(i);
      }
    }

    return safe.size();
  }

  public static void main(String[] args) {
    (new Aoc2402()).start();
  }

  @Override
  long getTestExpected1() {
    return 2;
  }

  @Override
  long getTestExpected2() {
    return 4;
  }

  @Override
  String getTestInput() {
    return "7 6 4 2 1\n" +
        "1 2 7 8 9\n" +
        "9 7 6 2 1\n" +
        "1 3 2 4 5\n" +
        "8 6 4 4 1\n" +
        "1 3 6 7 9";
  }

  @Override
  String getRealInput() {
    return "7 6 4 2 1\n" +
        "1 2 7 8 9\n" +
        "9 7 6 2 1\n" +
        "1 3 2 4 5\n" +
        "8 6 4 4 1\n" +
        "1 3 6 7 9";
  }
}
