package aoc2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.Pair;

public class Aoc2411 extends AbstractAoc {
  Map<Pair<String, Integer>, Long> cache = new HashMap<>();

  long part1(String[] in) {
    return Arrays.stream(in[0].split(" ")).map(s -> doSteps(s, 25)).mapToLong(Long::longValue).sum();
  }

  long part2(String[] in) {
    return Arrays.stream(in[0].split(" ")).map(s -> doSteps(s, 75)).mapToLong(Long::longValue).sum();
  }

  long doSteps(String s, int stepsRemaining) {
    Pair<String, Integer> cacheKey = new Pair<>(s, stepsRemaining);
    if (cache.containsKey(cacheKey)) {
      return cache.get(cacheKey);
    }
    long result = 0;
    if (stepsRemaining == 0) {
      result = 1;
    } else {
      for (String st : splitStone(s)) {
        result += doSteps(st, stepsRemaining - 1);
      }
    }
    cache.put(cacheKey, result);
    return result;
  }

  List<String> splitStone(String s) {
    if (s.matches("0*")) {
      return List.of("1");
    }
    if (s.length() % 2 == 0) {
      return List.of(s.substring(0, s.length() / 2), s.substring(s.length() / 2).replaceFirst("^0+", ""));
    }
    return List.of(String.valueOf(Long.parseLong(s) * 2024));
  }

  public static void main(String[] args) {
    (new Aoc2411()).start();
  }

  @Override
  long getTestExpected1() {
    return 55312;
  }

  @Override
  long getTestExpected2() {
    return 65601038650482L;
  }

  @Override
  String getTestInput() {
    return "125 17";
  }

  @Override
  String getRealInput() {
    return "125 17";
  }
}
