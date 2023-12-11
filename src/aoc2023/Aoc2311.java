package aoc2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.Pair;

public class Aoc2311 {

  private static long part1(String[] input) {
    Set<Integer> emptyRows = new HashSet<>();
    Set<Integer> emptyCols = new HashSet<>();

    for (int col = 0; col < input[0].length(); col++) {
      boolean containsGalaxies = false;
      for (int row = 0; row < input.length; row++) {
        if (input[row].charAt(col) == '#') {
          containsGalaxies = true;
        }
      }
      if (!containsGalaxies) {
        emptyCols.add(col);
      }
    }

    List<Pair<Integer, Integer>> galaxies = new ArrayList<>();
    List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> pairs = new ArrayList<>();

    for (int row = 0; row < input.length; row++) {
      boolean containsGalaxies = false;
      for (int col = 0; col < input[0].length(); col++) {
        if (input[row].charAt(col) == '#') {
          galaxies.add(new Pair(col, row));
          containsGalaxies = true;
        }
      }
      if (!containsGalaxies) {
        emptyRows.add(row);
      }
    }

    for (int i = 0; i < galaxies.size(); i++) {
      for (int j = i + 1; j < galaxies.size(); j++) {
        pairs.add(new Pair<>(galaxies.get(i), galaxies.get(j)));
      }
    }

    long r = 0;

    for (var pair : pairs) {
      var g1 = pair.getKey();
      var g2 = pair.getValue();
      var gx = Math.max(g1.getKey(), g2.getKey());
      var lx = Math.min(g1.getKey(), g2.getKey());
      var gy = Math.max(g1.getValue(), g2.getValue());
      var ly = Math.min(g1.getValue(), g2.getValue());
      r += (gx - lx)
        + (gy - ly)
        + emptyRows.stream().filter(e -> e > ly && e < gy).count()
        + emptyCols.stream().filter(e -> e > lx && e < gx).count();
    }

    return r;
  }

  private static long part2(String[] input) {
    Set<Integer> emptyRows = new HashSet<>();
    Set<Integer> emptyCols = new HashSet<>();

    for (int col = 0; col < input[0].length(); col++) {
      boolean containsGalaxies = false;
      for (int row = 0; row < input.length; row++) {
        if (input[row].charAt(col) == '#') {
          containsGalaxies = true;
        }
      }
      if (!containsGalaxies) {
        emptyCols.add(col);
      }
    }

    List<Pair<Integer, Integer>> galaxies = new ArrayList<>();
    List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> pairs = new ArrayList<>();

    for (int row = 0; row < input.length; row++) {
      boolean containsGalaxies = false;
      for (int col = 0; col < input[0].length(); col++) {
        if (input[row].charAt(col) == '#') {
          galaxies.add(new Pair(col, row));
          containsGalaxies = true;
        }
      }
      if (!containsGalaxies) {
        emptyRows.add(row);
      }
    }

    for (int i = 0; i < galaxies.size(); i++) {
      for (int j = i + 1; j < galaxies.size(); j++) {
        pairs.add(new Pair<>(galaxies.get(i), galaxies.get(j)));
      }
    }

    long r = 0;

    for (var pair : pairs) {
      var g1 = pair.getKey();
      var g2 = pair.getValue();
      var gx = Math.max(g1.getKey(), g2.getKey());
      var lx = Math.min(g1.getKey(), g2.getKey());
      var gy = Math.max(g1.getValue(), g2.getValue());
      var ly = Math.min(g1.getValue(), g2.getValue());
      r += (gx - lx)
        + (gy - ly)
        + emptyRows.stream().filter(e -> e > ly && e < gy).count() * 999999
        + emptyCols.stream().filter(e -> e > lx && e < gx).count() * 999999;
    }

    return r;
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

  static int expected1 = 374;
  static int expected2 = 82000210;
  static String testStr = "...#......\n" +
    ".......#..\n" +
    "#.........\n" +
    "..........\n" +
    "......#...\n" +
    ".#........\n" +
    ".........#\n" +
    "..........\n" +
    ".......#..\n" +
    "#...#.....";
  static String realStr = "";
}
