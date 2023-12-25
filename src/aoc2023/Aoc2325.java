package aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.KargersAlgorithm;
import tools.KargersAlgorithm.Graph;
import tools.Pair;

public class Aoc2325 {

  private static long part1(String[] input) {
    Map<String, Integer> map = new HashMap<>();
    List<Pair<String, String>> wires = new ArrayList<>();
    int id = 0;
    for (var in : input) {
      var from = in.split(": ")[0];
      if (!map.containsKey(from)) {
        map.put(from, id++);
      }
      var v = in.split(": ")[1];
      for (String to : v.split(" ")) {
        if (!map.containsKey(to)) {
          map.put(to, id++);
        }
        wires.add(new Pair<>(from, to));
      }
    }

    while (true) {
      var graph = new Graph(map.size(), wires.size());
      for (var wire : wires) {
        graph.addEdge(map.get(wire.getKey()), map.get(wire.getValue()));
      }
      var r = KargersAlgorithm.kargerMinCut(graph);
      if (r.getValue() == 3) {
        System.out.println(Arrays.toString(r.getKey()));
        int a1 = 0;
        int a2 = 0;
        for (int i = 0; i < r.getKey().length; i++) {
          if (KargersAlgorithm.find(r.getKey(), i) == KargersAlgorithm.find(r.getKey(), 0))
            a1++;
          else
            a2++;

          System.out.print(KargersAlgorithm.find(r.getKey(), i) + " ");
        }
        System.out.println();
        return (long) a1 * a2;
      }
    }
  }

  private static void test() {
    var p1 = part1(getInput(true));
    System.out.println("Part 1 test: " + p1 + (p1 == expected1 ? " PASSED" : " FAILED"));
  }

  public static void main(String[] args) {
    test();
    System.out.println(part1(getInput(false)));
  }

  private static String[] getInput(boolean isTest) {
    return (isTest ? testStr : realStr).split("\n");
  }

  static int expected1 = 54;
  static String testStr =
    "jqt: rhn xhk nvd\n" +
    "rsh: frs pzl lsr\n" +
    "xhk: hfx\n" +
    "cmg: qnr nvd lhk bvb\n" +
    "rhn: xhk bvb hfx\n" +
    "bvb: xhk hfx\n" +
    "pzl: lsr hfx nvd\n" +
    "qnr: nvd\n" +
    "ntq: jqt hfx bvb xhk\n" +
    "nvd: lhk\n" +
    "lsr: lhk\n" +
    "rzs: qnr cmg lsr rsh\n" +
    "frs: qnr lhk lsr";
  static String realStr =
    "jqt: rhn xhk nvd\n" +
      "rsh: frs pzl lsr\n" +
      "xhk: hfx\n" +
      "cmg: qnr nvd lhk bvb\n" +
      "rhn: xhk bvb hfx\n" +
      "bvb: xhk hfx\n" +
      "pzl: lsr hfx nvd\n" +
      "qnr: nvd\n" +
      "ntq: jqt hfx bvb xhk\n" +
      "nvd: lhk\n" +
      "lsr: lhk\n" +
      "rzs: qnr cmg lsr rsh\n" +
      "frs: qnr lhk lsr";
}
