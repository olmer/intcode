package aoc2022;

import static aoc2022.Aoc2207.max;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.Parse;

public class Aoc2219 {

  record BP (int max, int idx, int oreRobotOreCost, int clayRobotOreCost, int obsRobotOreCost, int obsRobotClayCost, int geoRobotOreCost, int geoRobotObsCost) {}

  static boolean TEST = true;

  static int TIME = 32;

  static Map<Integer, Map<List<Integer>, Map<List<Integer>, Integer>>> dp = new HashMap<>();

  static int[] maxgeo;

  public static void main(String[] args) throws Exception {
    var in = getInput();

    BP[] bps = new BP[in.length];

    for (int i = 0; i < in.length; i++) {
      var nums = Parse.integers(in[i]);
      int m = 0;
      for (var t : nums) {
        m = Math.max(m, t);
      }
      bps[i] = new BP(max, nums.get(0), nums.get(1), nums.get(2), nums.get(3), nums.get(4), nums.get(5), nums.get(6));
    }

    long r = 1;
    for (int i = 0; i < in.length; i++) {
      maxgeo = new int[33];
      var bp = bps[i];
      dp = new HashMap<>();
      List<Integer> robots = new ArrayList<>(){{add(1);add(0);add(0);add(0);}};
      List<Integer> ores = new ArrayList<>(){{add(0);add(0);add(0);add(0);}};
      var a = helper(bp, TIME, robots, ores);
      System.out.println("BP " + (i + 1) + " : " + a);
      r *= a;
    }
    System.out.println(r);
  }

  static int helper(BP bp, int timeLeft, List<Integer> robs, List<Integer> ores) throws Exception {
    if (timeLeft <= 0 || ores.get(3) < maxgeo[timeLeft]) {
      return ores.get(3);
    }

    if (dp.containsKey(timeLeft) && dp.get(timeLeft).containsKey(robs) && dp.get(timeLeft).get(robs).containsKey(ores)) {
      return dp.get(timeLeft).get(robs).get(ores);
    }

    int max = 0;

    List<Integer> newOres;
    List<Integer> newRobs;
    for (int i = 0; i < 4; i++) {
      switch (i) {
        case 0 -> {
          newOres = new ArrayList<>(ores);
          newRobs = new ArrayList<>(robs);
          if (bp.geoRobotOreCost <= ores.get(0) && bp.geoRobotObsCost <= ores.get(2)) {
            newOres.set(0, ores.get(0) - bp.geoRobotOreCost);
            newOres.set(2, ores.get(2) - bp.geoRobotObsCost);
            newRobs.set(3, robs.get(3) + 1);
          }
          for (int j = 0; j < robs.size(); j++) {
            newOres.set(j, newOres.get(j) + robs.get(j));
          }
          max = Math.max(max, helper(bp, timeLeft - 1, newRobs, newOres));
        }
        case 1 -> {
          newOres = new ArrayList<>(ores);
          newRobs = new ArrayList<>(robs);
          if (bp.obsRobotOreCost <= ores.get(0) && bp.obsRobotClayCost <= ores.get(1)) {
            newOres.set(0, ores.get(0) - bp.obsRobotOreCost);
            newOres.set(1, ores.get(1) - bp.obsRobotClayCost);
            newRobs.set(2, robs.get(2) + 1);
          }
          for (int j = 0; j < robs.size(); j++) {
            newOres.set(j, newOres.get(j) + robs.get(j));
          }
          max = Math.max(max, helper(bp, timeLeft - 1, newRobs, newOres));
        }
        case 2 -> {
          newOres = new ArrayList<>(ores);
          newRobs = new ArrayList<>(robs);
          if (bp.clayRobotOreCost <= ores.get(0)) {
            newOres.set(0, ores.get(0) - bp.clayRobotOreCost);
            newRobs.set(1, robs.get(1) + 1);
          }
          for (int j = 0; j < robs.size(); j++) {
            newOres.set(j, newOres.get(j) + robs.get(j));
          }
          max = Math.max(max, helper(bp, timeLeft - 1, newRobs, newOres));
        }
        case 3 -> {
          newOres = new ArrayList<>(ores);
          newRobs = new ArrayList<>(robs);
          if (bp.oreRobotOreCost <= ores.get(0)) {
            newOres.set(0, ores.get(0) - bp.oreRobotOreCost);
            newRobs.set(0, robs.get(0) + 1);
          }
          for (int j = 0; j < robs.size(); j++) {
            newOres.set(j, newOres.get(j) + robs.get(j));
          }
          max = Math.max(max, helper(bp, timeLeft - 1, newRobs, newOres));
        }
        default -> throw new Exception("wtf");
      }
    }

    dp.putIfAbsent(timeLeft, new HashMap<>());
    dp.get(timeLeft).putIfAbsent(robs, new HashMap<>());
    dp.get(timeLeft).get(robs).putIfAbsent(ores, max);
    maxgeo[timeLeft] = Math.max(maxgeo[timeLeft], ores.get(3));
    return max;
  }

  private static String[] getInput() {
    String testStr = "Blueprint 1:\n" +
      "  Each ore robot costs 4 ore.\n" +
      "  Each clay robot costs 2 ore.\n" +
      "  Each obsidian robot costs 3 ore and 14 clay.\n" +
      "  Each geode robot costs 2 ore and 7 obsidian.\n" +
      "\n" +
      "Blueprint 2:\n" +
      "  Each ore robot costs 2 ore.\n" +
      "  Each clay robot costs 3 ore.\n" +
      "  Each obsidian robot costs 3 ore and 8 clay.\n" +
      "  Each geode robot costs 3 ore and 12 obsidian.";

    String realStr = "Blueprint 1: Each ore robot costs 3 ore. Each clay robot costs 3 ore. Each obsidian robot costs 2 ore and 15 clay. Each geode robot costs 3 ore and 9 obsidian.\n" +
      "Blueprint 2: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 12 clay. Each geode robot costs 4 ore and 19 obsidian.\n" +
      "Blueprint 3: Each ore robot costs 4 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 14 clay. Each geode robot costs 3 ore and 16 obsidian.\n";

    return TEST ? testStr.split("\n\n") : realStr.split("\n");
  }
}
