package aoc2024;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class Aoc2423 extends AbstractAoc {
  long part1(String[] in) {
    Map<String, Set<String>> adjMap = new HashMap<>();
    for (String s : in) {
      String[] split = s.split("-");
      adjMap.computeIfAbsent(split[0], k -> new HashSet<>()).add(split[1]);
      adjMap.computeIfAbsent(split[1], k -> new HashSet<>()).add(split[0]);
    }
    Set<Set<String>> groups = new HashSet<>();
    for (Map.Entry<String, Set<String>> e : adjMap.entrySet()) {
      for (String next : e.getValue()) {
        for (String next2 : adjMap.get(next)) {
          if (e.getValue().contains(next2)) {
            Set<String> group = new HashSet<>();
            group.add(e.getKey());
            group.add(next);
            group.add(next2);
            groups.add(group);
          }
        }
      }
    }

    return groups.stream().filter(g -> g.stream().anyMatch(s -> s.startsWith("t"))).count();
  }

  long part2(String[] in) {
    Map<String, Set<String>> adjMap = new HashMap<>();
    for (String s : in) {
      String[] split = s.split("-");
      adjMap.computeIfAbsent(split[0], k -> new HashSet<>()).add(split[1]);
      adjMap.computeIfAbsent(split[1], k -> new HashSet<>()).add(split[0]);
    }

    Set<String> allNodes = new HashSet<>(adjMap.keySet());
    Map<String, Set<String>> groups = new HashMap();

    for (String nodeToInsertIfConnectedToAll : allNodes) {
      groups.computeIfAbsent(nodeToInsertIfConnectedToAll, _ -> new HashSet<>());
      for (Entry<String, Set<String>> next : groups.entrySet()) {
        if (next.getKey() == nodeToInsertIfConnectedToAll) {
          continue;
        }
        boolean isConnectedToAll = true;
        for (String n : next.getValue()) {
          if (!adjMap.get(nodeToInsertIfConnectedToAll).contains(n)) {
            isConnectedToAll = false;
            break;
          }
        }
        if (isConnectedToAll && adjMap.get(next.getKey()).contains(nodeToInsertIfConnectedToAll)) {
          next.getValue().add(nodeToInsertIfConnectedToAll);
        }
      }
    }

    for (String nodeToInsertIfConnectedToAll : allNodes) {
      groups.computeIfAbsent(nodeToInsertIfConnectedToAll, _ -> new HashSet<>());
      for (Entry<String, Set<String>> next : groups.entrySet()) {
        if (next.getKey() == nodeToInsertIfConnectedToAll) {
          continue;
        }
        boolean isConnectedToAll = true;
        for (String n : next.getValue()) {
          if (!adjMap.get(nodeToInsertIfConnectedToAll).contains(n)) {
            isConnectedToAll = false;
            break;
          }
        }
        if (isConnectedToAll && adjMap.get(next.getKey()).contains(nodeToInsertIfConnectedToAll)) {
          next.getValue().add(nodeToInsertIfConnectedToAll);
        }
      }
    }

    int maxSize = 0;
    Set<String> res = new TreeSet<>(String::compareTo);
    for (Entry<String, Set<String>> group : groups.entrySet()) {
      if (group.getValue().size() > maxSize) {
        group.getValue().add(group.getKey());
        res = group.getValue();
        maxSize = Math.max(maxSize, group.getValue().size() - 1);
      }
    }

    System.out.println(res);

    return String.join(",", res).length();
  }

  public static void main(String[] args) {
    (new Aoc2423()).start();
  }

  @Override
  long getTestExpected1() {
    return 7;
  }

  @Override
  long getTestExpected2() {
    return 11;
  }

  @Override
  String getTestInput() {
    return "kh-tc\n" +
        "qp-kh\n" +
        "de-cg\n" +
        "ka-co\n" +
        "yn-aq\n" +
        "qp-ub\n" +
        "cg-tb\n" +
        "vc-aq\n" +
        "tb-ka\n" +
        "wh-tc\n" +
        "yn-cg\n" +
        "kh-ub\n" +
        "ta-co\n" +
        "de-co\n" +
        "tc-td\n" +
        "tb-wq\n" +
        "wh-td\n" +
        "ta-ka\n" +
        "td-qp\n" +
        "aq-cg\n" +
        "wq-ub\n" +
        "ub-vc\n" +
        "de-ta\n" +
        "wq-aq\n" +
        "wq-vc\n" +
        "wh-yn\n" +
        "ka-de\n" +
        "kh-ta\n" +
        "co-tc\n" +
        "wh-qp\n" +
        "tb-vc\n" +
        "td-yn";
  }

  @Override
  String getRealInput() {
    return "kh-tc\n" +
        "qp-kh\n" +
        "de-cg\n" +
        "ka-co\n" +
        "yn-aq\n" +
        "qp-ub\n" +
        "cg-tb\n" +
        "vc-aq\n" +
        "tb-ka\n" +
        "wh-tc\n" +
        "yn-cg\n" +
        "kh-ub\n" +
        "ta-co\n" +
        "de-co\n" +
        "tc-td\n" +
        "tb-wq\n" +
        "wh-td\n" +
        "ta-ka\n" +
        "td-qp\n" +
        "aq-cg\n" +
        "wq-ub\n" +
        "ub-vc\n" +
        "de-ta\n" +
        "wq-aq\n" +
        "wq-vc\n" +
        "wh-yn\n" +
        "ka-de\n" +
        "kh-ta\n" +
        "co-tc\n" +
        "wh-qp\n" +
        "tb-vc\n" +
        "td-yn";
  }
}
