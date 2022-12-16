package aoc2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import tools.Parse;

public class Aoc2216 {
  static boolean TEST = true;
  static int TIME_LIMIT = 30;

  static Map<String, Pair<Integer, String[]>> map;

  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();

    map = parse();

    var distancesMap = generateDistancesMap();

    Set<String> visited = new HashSet<>();
    visited.add("AA");

    long r = dfs("AA", visited, TIME_LIMIT, 0, distancesMap);

    long end = System.currentTimeMillis();
    System.out.println((end - start) + " ms");

    System.out.println(r);
  }

  static long dfs(String cur, Set<String> opened, int timeLeft, long flow, Map<String, List<Pair<String, Integer>>> distancesMap) {
    if (timeLeft <= 0)
      return 0;

//    debugVisited.add(cur);
    long best = flow;
    for (Pair<String, Integer> next : distancesMap.get(cur)) {
      if (opened.contains(next.getKey()))
        continue;

      opened.add(next.getKey());

      long nextTotalFlow = flow + (long) map.get(next.getKey()).getKey() * (timeLeft - 1 - next.getValue());
      best = Math.max(
        best,
        dfs(next.getKey(), opened, timeLeft - 1 - next.getValue(), nextTotalFlow, distancesMap)
      );

      opened.remove(next.getKey());
    }
//    debugVisited.remove(debugVisited.size() - 1);
    return best;
  }

  static int bfs(String from, String to) {
    Queue<String> q = new ArrayDeque<>();
    Set<String> visited = new HashSet<>();
    q.offer(from);
    visited.add(from);
    int distance = 0;

    while (!q.isEmpty()) {
      int size = q.size();
      while (size-- > 0) {
        String cur = q.poll();
        if (cur.equals(to)) {
          return distance;
        }
        for (String next : map.get(cur).getValue()) {
          if (visited.contains(next)) {
            continue;
          }
          visited.add(next);
          q.offer(next);
        }
      }
      distance++;
    }

    return -1;
  }

  static Map<String, List<Pair<String, Integer>>> generateDistancesMap() {
    Map<String, List<Pair<String, Integer>>> distances = new HashMap<>();
    for (String valve1 : map.keySet()) {
      if (map.get(valve1).getKey() == 0 && !valve1.equals("AA")) {
        continue;
      }
      List<Pair<String, Integer>> list = new ArrayList<>();
      for (String valve2 : map.keySet()) {
        if (valve1.equals(valve2) || map.get(valve2).getKey() == 0) {
          continue;
        }
        int distance = bfs(valve1, valve2);
        if (distance <= TIME_LIMIT) {
          list.add(new Pair<>(valve2, distance));
        }
      }
      distances.put(valve1, list);
    }
    return distances;
  }

  static private Map<String, Pair<Integer, String[]>> parse() {
    Map<String, Pair<Integer, String[]>> map = new HashMap<>();

    for (String s : getInput()) {
      String dest = s.split(";")[1].replace(" tunnels lead to valves ", "");
      map.put(s.substring(6, 8), new Pair<>(Parse.integers(s).stream().findFirst().get(), dest.replace(" tunnel leads to valve ", "").split(", ")));
    }
    return map;
  }

  static List<String> debugVisited = new ArrayList<>();

  private static String[] getInput() {
    String testStr = "Valve AA has flow rate=0; tunnels lead to valves DD, II, BB\n" +
      "Valve BB has flow rate=13; tunnels lead to valves CC, AA\n" +
      "Valve CC has flow rate=2; tunnels lead to valves DD, BB\n" +
      "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE\n" +
      "Valve EE has flow rate=3; tunnels lead to valves FF, DD\n" +
      "Valve FF has flow rate=0; tunnels lead to valves EE, GG\n" +
      "Valve GG has flow rate=0; tunnels lead to valves FF, HH\n" +
      "Valve HH has flow rate=22; tunnel leads to valve GG\n" +
      "Valve II has flow rate=0; tunnels lead to valves AA, JJ\n" +
      "Valve JJ has flow rate=21; tunnel leads to valve II";

    String realStr = "";

    return (TEST ? testStr : realStr).split("\n");
  }
}
