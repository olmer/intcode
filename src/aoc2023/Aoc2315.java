package aoc2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.Pair;

public class Aoc2315 {

  private static long part1(String[] input) {
    long r = 0;
    for (var in : input) {
      long cur = 0;
      for (var c : in.toCharArray()) {
        cur += Integer.valueOf(c);
        cur *= 17;
        cur %= 256;
      }
      r += cur;
    }
    return r;
  }

  private static long part2(String[] input) {
    long r = 0;
    Map<Integer, Pair<List<Pair<String, Integer>>, Map<String, Pair<String, Integer>>>> map = new HashMap<>();

    for (var in : input) {
      var t = in.split("=");
      if (t.length == 1) {
        t = in.split("-");
      }
      int hash = 0;
      for (var c : t[0].toCharArray()) {
        hash += Integer.valueOf(c);
        hash *= 17;
        hash %= 256;
      }
      map.putIfAbsent(hash, new Pair<>(new ArrayList<>(), new HashMap<>()));

      if (t.length == 1) {//minus
        var pair = map.get(hash).getValue().get(t[0]);
        if (pair != null) {
          map.get(hash).getKey().remove(pair);
          map.get(hash).getValue().remove(t[0]);
        }
      } else {//add
        Pair<String, Integer> pair = map.get(hash).getValue().get(t[0]);
        if (pair == null) {
          pair = new Pair<>(t[0], Integer.parseInt(t[1]));
          map.get(hash).getKey().add(pair);
          map.get(hash).getValue().put(t[0], pair);
        } else {
          pair.setValue(Integer.parseInt(t[1]));
        }
      }
    }

    for (var e : map.entrySet()) {
      for (int i = 0; i < e.getValue().getKey().size(); i++) {
        r += (e.getKey() + 1) * (i + 1) * (e.getValue().getKey().get(i).getValue());
      }
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
    return (isTest ? testStr : realStr).split(",");
  }

  static int expected1 = 1320;
  static int expected2 = 145;
  static String testStr = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";
  static String realStr = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";
}
