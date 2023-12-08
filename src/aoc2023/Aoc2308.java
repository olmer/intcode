package aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tools.MathAoc;

public class Aoc2308 {

  private static int part1(String[] input) {
    if (input.length == 1) return 0;
    var directions = input[0];
    var nodes = Arrays.stream(input[1].split("\n")).collect(Collectors.toMap(
      k -> k.split(" = ")[0],
      v -> Arrays.stream(v.split(" = ")[1].substring(1, v.split(" = ")[1].length() - 1).split(", ")).toList()
    ));
    var curNode = "XGS";
    int result = 0;
    int directionIdx = 0;
    while (!curNode.equals("FDM")) {
      directionIdx %= directions.length();
      curNode = nodes.get(curNode).get(directions.charAt(directionIdx) == 'L' ? 0 : 1);
      result++;
      directionIdx++;
    }
    return result;
  }

  private static long part2(String[] input) {
    if (input.length == 1) return 0;
    var startingNodes = new ArrayList<String>();
    var numberOfStepsToFinish = new ArrayList<Long>();

    var directions = input[0];
    var nodes = parseMap(input);
    nodes.entrySet().stream().filter(e -> e.getKey().charAt(2) == 'A').forEach(e -> startingNodes.add(e.getKey()));

    for (var curNode : startingNodes) {
      long numberOfSteps = 0;
      int directionIndex = 0;
      while (curNode.charAt(2) != ('Z')) {
        directionIndex %= directions.length();
        curNode = nodes.get(curNode).get(directions.charAt(directionIndex) == 'L' ? 0 : 1);
        numberOfSteps++;
        directionIndex++;
      }
      numberOfStepsToFinish.add(numberOfSteps);
    }
    return numberOfStepsToFinish.stream().reduce(1L, MathAoc::lcm);
  }

  private static Map<String, List<String>> parseMap(String[] input) {
    return Arrays.stream(input[1].split("\n")).collect(Collectors.toMap(
      k -> k.split(" = ")[0],
      v -> Arrays.stream(v.split(" = ")[1].substring(1, v.split(" = ")[1].length() - 1).split(", ")).toList()
    ));
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
    return (isTest ? testStr : realStr).split("\n\n");
  }

  static int expected1 = 1;
  static int expected2 = 3;
  static String testStr = "LR\n" +
    "\n" +
    "XGS = (FDM, XXX)\n" +
    "11B = (XXX, 11Z)\n" +
    "11Z = (11B, XXX)\n" +
    "22A = (22B, XXX)\n" +
    "22B = (22C, 22C)\n" +
    "22C = (22Z, 22Z)\n" +
    "22Z = (22B, 22B)\n" +
    "XXX = (XXX, XXX)";
  static String realStr = "";
}
