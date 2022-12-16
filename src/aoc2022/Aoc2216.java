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
  static boolean TEST = false;
  static int PART = 1;
  static int TIME_LIMIT = PART == 1 ? 30 : 26;

  static Map<String, Pair<Integer, String[]>> map;

  static List<String> debugVisited = new ArrayList<>();

  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();

    map = new HashMap<>();

    for (var s : getInput()) {
      String dest = s.split(";")[1].replace(" tunnels lead to valves ", "");
      map.put(s.substring(6, 8), new Pair<>(Parse.integers(s).stream().findFirst().get(), dest.replace(" tunnel leads to valve ", "").split(", ")));
      if (Parse.integers(s).stream().findFirst().get() > 0) {
      }
    }


    var distancesMap = generateDistancesMap();

    Set<String> visited = new HashSet<>();
    visited.add("AA");

    long r = dfs("AA", visited, TIME_LIMIT, 0, distancesMap);

    System.out.println(r);

    long end = System.currentTimeMillis();
    System.out.println((end - start) + " ms");
  }

  static long dfs(String cur, Set<String> opened, int timeLeft, long flow, Map<String, List<Pair<String, Integer>>> distancesMap) {
    if (timeLeft <= 0) {
      return 0;
    }
//    var tttt = new ArrayList<String>(){{
//      add("AA");
//      add("DD");
//      add("BB");
//      add("JJ");
//      add("HH");
//      add("EE");
//      add("CC");
//    }};
//    debugVisited.add(cur);

//    System.out.println("at " + timeLeft + " flow is: " + flow + " visited was: " + debugVisited);

    long best = flow;
    for (var next : distancesMap.get(cur)) {
      if (opened.contains(next.getKey())) {
        continue;
      }
      opened.add(next.getKey());

      best = Math.max(
        best,
        dfs(
          next.getKey(),
          opened,
          timeLeft - 1 - next.getValue(),
          flow + (long) map.get(next.getKey()).getKey() * (timeLeft - 1 - next.getValue()),
          distancesMap
        )
      );

      opened.remove(next.getKey());
    }

//    debugVisited.remove(debugVisited.size() - 1);

    return best;
  }

  static Map<String, List<Pair<String, Integer>>> generateDistancesMap() {
    Map<String, List<Pair<String, Integer>>> distances = new HashMap<>();
    for (var i : map.keySet()) {
      if (map.get(i).getKey() == 0 && !i.equals("AA")) {
        continue;
      }
      var list = new ArrayList<Pair<String, Integer>>();
      for (var j : map.keySet()) {
        if (i.equals(j) || map.get(j).getKey() == 0) {
          continue;
        }
        int distance = bfs(i, j);
        if (distance <= TIME_LIMIT) {
          list.add(new Pair<>(j, distance));
        }
      }
      distances.put(i, list);
    }
    return distances;
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
        for (var next : map.get(cur).getValue()) {
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

//    testStr = """
//      Valve AA has flow rate=0; tunnels lead to valves II, JJ
//      Valve JJ has flow rate=0; tunnels lead to valves DD
//      Valve DD has flow rate=20; tunnels lead to valves II, AA
//      Valve II has flow rate=10; tunnels lead to valves AA, DD""";

    String realStr = "Valve AV has flow rate=0; tunnels lead to valves AX, PI\n" +
      "Valve JI has flow rate=0; tunnels lead to valves VD, HF\n" +
      "Valve FF has flow rate=0; tunnels lead to valves ZL, CG\n" +
      "Valve CG has flow rate=10; tunnels lead to valves TI, SU, RV, FF, QX\n" +
      "Valve RC has flow rate=18; tunnels lead to valves EQ, WR, AD\n" +
      "Valve ZJ has flow rate=0; tunnels lead to valves GJ, WI\n" +
      "Valve GJ has flow rate=21; tunnels lead to valves TG, YJ, EU, AZ, ZJ\n" +
      "Valve VJ has flow rate=0; tunnels lead to valves UJ, AA\n" +
      "Valve ER has flow rate=0; tunnels lead to valves QO, ZK\n" +
      "Valve QO has flow rate=24; tunnels lead to valves MF, ER\n" +
      "Valve LN has flow rate=0; tunnels lead to valves ZR, TI\n" +
      "Valve SU has flow rate=0; tunnels lead to valves CG, LM\n" +
      "Valve AJ has flow rate=12; tunnels lead to valves QX, JW, TR, MK\n" +
      "Valve YJ has flow rate=0; tunnels lead to valves GJ, EQ\n" +
      "Valve JW has flow rate=0; tunnels lead to valves YI, AJ\n" +
      "Valve WI has flow rate=13; tunnels lead to valves XO, ZJ, ZL\n" +
      "Valve VS has flow rate=0; tunnels lead to valves XL, VD\n" +
      "Valve TI has flow rate=0; tunnels lead to valves LN, CG\n" +
      "Valve VD has flow rate=17; tunnels lead to valves TR, VS, JI, GQ, VO\n" +
      "Valve TX has flow rate=0; tunnels lead to valves FV, WR\n" +
      "Valve HP has flow rate=0; tunnels lead to valves AX, ET\n" +
      "Valve BK has flow rate=0; tunnels lead to valves PI, AD\n" +
      "Valve ET has flow rate=0; tunnels lead to valves ZR, HP\n" +
      "Valve VY has flow rate=0; tunnels lead to valves KU, LM\n" +
      "Valve DZ has flow rate=0; tunnels lead to valves VO, AA\n" +
      "Valve ZK has flow rate=0; tunnels lead to valves FR, ER\n" +
      "Valve TG has flow rate=0; tunnels lead to valves GJ, AX\n" +
      "Valve YI has flow rate=0; tunnels lead to valves JW, LM\n" +
      "Valve XO has flow rate=0; tunnels lead to valves ZR, WI\n" +
      "Valve ZR has flow rate=11; tunnels lead to valves KX, AZ, ET, LN, XO\n" +
      "Valve EQ has flow rate=0; tunnels lead to valves RC, YJ\n" +
      "Valve PI has flow rate=4; tunnels lead to valves BK, KX, VQ, EU, AV\n" +
      "Valve VO has flow rate=0; tunnels lead to valves VD, DZ\n" +
      "Valve WR has flow rate=0; tunnels lead to valves TX, RC\n" +
      "Valve TF has flow rate=0; tunnels lead to valves FR, KU\n" +
      "Valve FR has flow rate=22; tunnels lead to valves ZK, TF\n" +
      "Valve MK has flow rate=0; tunnels lead to valves AJ, YW\n" +
      "Valve AZ has flow rate=0; tunnels lead to valves GJ, ZR\n" +
      "Valve TC has flow rate=0; tunnels lead to valves KU, RO\n" +
      "Valve GQ has flow rate=0; tunnels lead to valves MF, VD\n" +
      "Valve YW has flow rate=0; tunnels lead to valves MK, KU\n" +
      "Valve AA has flow rate=0; tunnels lead to valves RO, EI, VJ, VQ, DZ\n" +
      "Valve MF has flow rate=0; tunnels lead to valves QO, GQ\n" +
      "Valve ZL has flow rate=0; tunnels lead to valves WI, FF\n" +
      "Valve LM has flow rate=3; tunnels lead to valves YI, SU, UJ, VY, HF\n" +
      "Valve KU has flow rate=9; tunnels lead to valves XL, TC, TF, VY, YW\n" +
      "Valve FV has flow rate=23; tunnels lead to valves KV, TX\n" +
      "Valve EU has flow rate=0; tunnels lead to valves PI, GJ\n" +
      "Valve KV has flow rate=0; tunnels lead to valves FV, OF\n" +
      "Valve QX has flow rate=0; tunnels lead to valves AJ, CG\n" +
      "Valve RO has flow rate=0; tunnels lead to valves AA, TC\n" +
      "Valve TR has flow rate=0; tunnels lead to valves VD, AJ\n" +
      "Valve VQ has flow rate=0; tunnels lead to valves AA, PI\n" +
      "Valve HF has flow rate=0; tunnels lead to valves JI, LM\n" +
      "Valve RV has flow rate=0; tunnels lead to valves EI, CG\n" +
      "Valve KX has flow rate=0; tunnels lead to valves PI, ZR\n" +
      "Valve UJ has flow rate=0; tunnels lead to valves LM, VJ\n" +
      "Valve AX has flow rate=5; tunnels lead to valves TG, AV, HP\n" +
      "Valve XL has flow rate=0; tunnels lead to valves KU, VS\n" +
      "Valve AD has flow rate=0; tunnels lead to valves BK, RC\n" +
      "Valve EI has flow rate=0; tunnels lead to valves RV, AA\n" +
      "Valve OF has flow rate=19; tunnel leads to valve KV";

    return (TEST ? testStr : realStr).split("\n");
  }
}
