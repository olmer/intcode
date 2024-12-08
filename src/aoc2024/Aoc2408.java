package aoc2024;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tools.Pair;

public class Aoc2408 extends AbstractAoc {
  long part1(String[] in) {
    Map<Character, Set<Pair<Integer, Integer>>> antennas = new HashMap<>();
    Set<Pair<Integer, Integer>> res = new HashSet<>();
    for (int y = 0; y < in.length; y++) {
      for (int x = 0; x < in[y].length(); x++) {
        char c = in[y].charAt(x);
        if (c != '.') {
          for (Pair<Integer, Integer> antenna : antennas.getOrDefault(c, new HashSet<>())) {
            int diffx = antenna.getKey() - x;
            int diffy = antenna.getValue() - y;
            int zonex = x - diffx;
            int zoney = y - diffy;
            if (zonex >= 0 && zonex < in[y].length() && zoney >= 0 && zoney < in.length) {
              res.add(new Pair<>(zonex, zoney));
            }
            int zonex2 = antenna.getKey() + diffx;
            int zoney2 = antenna.getValue() + diffy;
            if (zonex2 >= 0 && zonex2 < in[y].length() && zoney2 >= 0 && zoney2 < in.length) {
              res.add(new Pair<>(zonex2, zoney2));
            }
          }
          antennas.computeIfAbsent(c, _ -> new HashSet<>()).add(new Pair<>(x, y));
        }
      }
    }
    return res.size();
  }

  long part2(String[] in) {
    Map<Character, Set<Pair<Integer, Integer>>> antennas = new HashMap<>();
    Set<Pair<Integer, Integer>> res = new HashSet<>();
    for (int y = 0; y < in.length; y++) {
      for (int x = 0; x < in[y].length(); x++) {
        char c = in[y].charAt(x);
        if (c != '.') {
          for (Pair<Integer, Integer> antenna : antennas.getOrDefault(c, new HashSet<>())) {
            res.add(new Pair<>(x, y));
            res.add(antenna);

            int diffx = antenna.getKey() - x;
            int diffy = antenna.getValue() - y;

            int zonex = x - diffx;
            int zoney = y - diffy;
            while (zonex >= 0 && zonex < in[y].length() && zoney >= 0 && zoney < in.length) {
              res.add(new Pair<>(zonex, zoney));
              zonex -= diffx;
              zoney -= diffy;
            }
            int zonex2 = antenna.getKey() + diffx;
            int zoney2 = antenna.getValue() + diffy;
            while (zonex2 >= 0 && zonex2 < in[y].length() && zoney2 >= 0 && zoney2 < in.length) {
              res.add(new Pair<>(zonex2, zoney2));
              zonex2 += diffx;
              zoney2 += diffy;
            }
          }
          antennas.computeIfAbsent(c, _ -> new HashSet<>()).add(new Pair<>(x, y));
        }
      }
    }
    return res.size();
  }

  public static void main(String[] args) {
    (new Aoc2408()).start();
  }

  @Override
  long getTestExpected1() {
    return 14;
  }

  @Override
  long getTestExpected2() {
    return 34;
  }

  @Override
  String getTestInput() {
    return "............\n" +
        "........0...\n" +
        ".....0......\n" +
        ".......0....\n" +
        "....0.......\n" +
        "......A.....\n" +
        "............\n" +
        "............\n" +
        "........A...\n" +
        ".........A..\n" +
        "............\n" +
        "............";
  }

  @Override
  String getRealInput() {
    return "............\n" +
        "........0...\n" +
        ".....0......\n" +
        ".......0....\n" +
        "....0.......\n" +
        "......A.....\n" +
        "............\n" +
        "............\n" +
        "........A...\n" +
        ".........A..\n" +
        "............\n" +
        "............";
  }
}
