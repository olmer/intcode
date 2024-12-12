package aoc2015;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Aoc1509 {

  private static long part1(String[] input) {
    Map<String, Set<String>> adjMap = new HashMap<>();
    Map<Set<String>, Integer> weights = new HashMap<>();
    for (String in : input) {
      String[] parts = in.split(" ");
      String a = parts[0];
      String b = parts[2];
      int w = Integer.parseInt(parts[4]);
      if (!adjMap.containsKey(a)) {
        adjMap.put(a, new HashSet<>());
      }
      if (!adjMap.containsKey(b)) {
        adjMap.put(b, new HashSet<>());
      }
      adjMap.get(a).add(b);
      adjMap.get(b).add(a);
      weights.put(Set.of(a, b), w);
    }
    long r = Long.MAX_VALUE;

    for (String start : adjMap.keySet()) {
      Set<String> visited = new HashSet<>();
      visited.add(start);
      r = Math.min(r, dfs(adjMap, weights, visited, start));
    }

    return r;
  }

  private static long dfs(Map<String, Set<String>> adjMap, Map<Set<String>, Integer> weights, Set<String> visited, String node) {
    if (visited.size() == adjMap.size()) {
      return 0;
    }
    long r = Long.MAX_VALUE;
    for (String next : adjMap.get(node)) {
      if (visited.contains(next)) {
        continue;
      }
      visited.add(next);
      r = Math.min(r, weights.get(Set.of(node, next)) + dfs(adjMap, weights, visited, next));
      visited.remove(next);
    }
    return r;
  }

  private static long part2(String[] input) {
    Map<String, Set<String>> adjMap = new HashMap<>();
    Map<Set<String>, Integer> weights = new HashMap<>();
    for (String in : input) {
      String[] parts = in.split(" ");
      String a = parts[0];
      String b = parts[2];
      int w = Integer.parseInt(parts[4]);
      if (!adjMap.containsKey(a)) {
        adjMap.put(a, new HashSet<>());
      }
      if (!adjMap.containsKey(b)) {
        adjMap.put(b, new HashSet<>());
      }
      adjMap.get(a).add(b);
      adjMap.get(b).add(a);
      weights.put(Set.of(a, b), w);
    }
    long r = Long.MIN_VALUE;

    for (String start : adjMap.keySet()) {
      Set<String> visited = new HashSet<>();
      visited.add(start);
      r = Math.max(r, dfsl(adjMap, weights, visited, start));
    }

    return r;
  }

  private static long dfsl(Map<String, Set<String>> adjMap, Map<Set<String>, Integer> weights, Set<String> visited, String node) {
    if (visited.size() == adjMap.size()) {
      return 0;
    }
    long r = Long.MIN_VALUE;
    for (String next : adjMap.get(node)) {
      if (visited.contains(next)) {
        continue;
      }
      visited.add(next);
      r = Math.max(r, weights.get(Set.of(node, next)) + dfsl(adjMap, weights, visited, next));
      visited.remove(next);
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

  static int expected1 = 605;
  static int expected2 = 982;

  static String testStr = "London to Dublin = 464\n" +
      "London to Belfast = 518\n" +
      "Dublin to Belfast = 141";
  static String realStr = "London to Dublin = 464\n" +
      "London to Belfast = 518\n" +
      "Dublin to Belfast = 141";
}
