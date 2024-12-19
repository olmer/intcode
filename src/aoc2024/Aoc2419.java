package aoc2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Aoc2419 extends AbstractAoc {
  long part1(String[] in) {
    Set<String> towels = Set.of(Arrays.stream(in).collect(Collectors.joining("\n")).split("\n\n")[0].split(", "));
    List<String> designs = List.of(Arrays.stream(in).collect(Collectors.joining("\n")).split("\n\n")[1].split("\n"));
    int r = 0;
    for (String design : designs) {
      if (isPossible(design, towels, 0, design.length())) {
        r++;
      }
    }
    return r;
  }

  Map<String, Map<Integer, Map<Integer, Boolean>>> cache = new HashMap<>();
  Map<String, Long> cacheNumberOfWays;

  long part2(String[] in) {
    cacheNumberOfWays = new HashMap<>();
    Set<String> towels = Set.of(Arrays.stream(in).collect(Collectors.joining("\n")).split("\n\n")[0].split(", "));
    List<String> designs = List.of(Arrays.stream(in).collect(Collectors.joining("\n")).split("\n\n")[1].split("\n"));
    long r = 0;
    for (String design : designs) {
      r += numberOfWays(design, towels);
    }
    return r;
  }

  boolean isPossible(String design, Set<String> towels, int startIncl, int endExcl) {
    String substr = design.substring(startIncl, endExcl);
    if (cache.containsKey(design)
        && cache.get(design).containsKey(startIncl)
        && cache.get(design)
        .get(startIncl).containsKey(endExcl)) {
      return cache.get(design).get(startIncl).get(endExcl);
    }
    if (startIncl == endExcl) {
      return putCache(design, startIncl, endExcl, false);
    }
    if (towels.contains(substr)) {
      return putCache(design, startIncl, endExcl, true);
    }
    for (int i = startIncl + 1; i < endExcl; i++) {
      if (isPossible(design, towels, startIncl, i) && isPossible(design, towels, i, endExcl)) {
        return putCache(design, startIncl, endExcl, true);
      }
    }
    return putCache(design, startIncl, endExcl, false);
  }

  long numberOfWays(String design, Set<String> towels) {
    if (design.isEmpty()) {
      return 0;
    }
    if (cacheNumberOfWays.containsKey(design)) {
      return cacheNumberOfWays.get(design);
    }
    long r = 0;
    if (towels.contains(design)) {
      r++;
    }
    for (int i = 1; i < design.length(); i++) {
      long left = towels.contains(design.substring(0, i)) ? 1 : 0;
      long right = numberOfWays(design.substring(i), towels);
      r += left * right;
    }
    cacheNumberOfWays.put(design, r);
    return r;
  }

  boolean putCache(String design, int startIncl, int endExcl, boolean value) {
    if (!cache.containsKey(design)) {
      cache.put(design, new HashMap<>());
    }
    if (!cache.get(design).containsKey(startIncl)) {
      cache.get(design).put(startIncl, new HashMap<>());
    }
    cache.get(design).get(startIncl).put(endExcl, value);
    return value;
  }

  public static void main(String[] args) {
    (new Aoc2419()).start();
  }

  @Override
  long getTestExpected1() {
    return 6;
  }

  @Override
  long getTestExpected2() {
    return 16;
  }

  String getTestInput() {
    return "r, wr, b, g, bwu, rb, gb, br\n" +
        "\n" +
        "brwrr\n" +
        "bggr\n" +
        "gbbr\n" +
        "rrbgbr\n" +
        "ubwu\n" +
        "bwurrg\n" +
        "brgr\n" +
        "bbrgwb";
  }

  @Override
  String getRealInput() {
    return "r, wr, b, g, bwu, rb, gb, br\n" +
        "\n" +
        "brwrr\n" +
        "bggr\n" +
        "gbbr\n" +
        "rrbgbr\n" +
        "ubwu\n" +
        "bwurrg\n" +
        "brgr\n" +
        "bbrgwb";
  }
}
