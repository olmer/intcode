package aoc2024;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.Parse;

public class Aoc2401 extends AbstractAoc {
  long part1(String[] in) {
    List<Integer> l1 = new ArrayList<>();
    List<Integer> l2 = new ArrayList<>();

    for (int i = 0; i < in.length; i++) {
      List<Integer> t = Parse.integers(in[i]);
      l1.add(t.get(0));
      l2.add(t.get(1));
    }

    long r = 0;

    l1.sort(Integer::compare);
    l2.sort(Integer::compare);

    for (int i = 0; i < l1.size(); i++) {
      r += Math.abs(l1.get(i) - l2.get(i));
    }

    return r;
  }

  long part2(String[] in) {
    Map<Integer, Integer> freq = new HashMap<>();

    for (int i = 0; i < in.length; i++) {
      List<Integer> t = Parse.integers(in[i]);
      freq.put(t.get(1), freq.getOrDefault(t.get(1), 0) + 1);
    }

    long r = 0;

    for (int i = 0; i < in.length; i++) {
      List<Integer> t = Parse.integers(in[i]);
      r += freq.getOrDefault(t.get(0), 0) * t.get(0);
    }

    return r;
  }

  public static void main(String[] args) {
    (new Aoc2401()).start();
  }

  @Override
  String getTestInput() {
    return "3   4\n" +
        "4   3\n" +
        "2   5\n" +
        "1   3\n" +
        "3   9\n" +
        "3   3";
  }

  @Override
  String getRealInput() {
    return "3   4\n" +
        "4   3\n" +
        "2   5\n" +
        "1   3\n" +
        "3   9\n" +
        "3   3";
  }

  @Override
  long getRealExpected1() {
    return 11;
  }

  @Override
  long getRealExpected2() {
    return 31;
  }
}
