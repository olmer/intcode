package aoc2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tools.Pair;
import tools.Parse;
import tools.Range;

public class Aoc2319 {
  public enum Operation {
    COMPARISON, ACCEPTED, REJECTED
  }
  static long LIM = 4000;
  public record Rule (char name, boolean less, String to, int val) {}
  public record Limit (Operation op, long value, char parameter, Limit ifTrue, Limit ifFalse) {}

  private static long part1(String[] input) {
    var rules = parseRules(input);
    var parts = parseParts(input);

    long r = 0;
    for (var part : parts) {
      String currentRuleName = "in";
      while (!currentRuleName.equals("A") && !currentRuleName.equals("R")) {
        for (Rule rule : rules.get(currentRuleName)) {
          if (rule.name == 'E') {
            currentRuleName = rule.to;
            break;
          }
          if (rule.less) {
            if (part.get(rule.name) < rule.val) {
              currentRuleName = rule.to;
              break;
            }
          } else if (part.get(rule.name) > rule.val) {
            currentRuleName = rule.to;
            break;
          }
        }
      }
      if (currentRuleName.equals("A")) {
        r += part.values().stream().reduce(0, Integer::sum);
      }
    }
    return r;
  }

  private static long part2(String[] input) {
    Map<String, List<Rule>> rules = parseRules(input);

    var root = buildTree("in", rules, 0);

    return calculate(root, Map.of(
      'x', new Pair<>(1L, LIM),
      'm', new Pair<>(1L, LIM),
      'a', new Pair<>(1L, LIM),
      's', new Pair<>(1L, LIM)
    ));
  }

  private static Limit buildTree(String ruleName, Map<String, List<Rule>> rules, int ruleIndex) {
    Rule rule = rules.get(ruleName).get(ruleIndex);
    if (rule.name == 'E') {
      if (rule.to.equals("A")) {
        return new Limit(Operation.ACCEPTED, 0, '0', null, null);
      }
      if (rule.to.equals("R")) {
        return new Limit(Operation.REJECTED, 0, '0', null, null);
      }
      return buildTree(rule.to, rules, 0);
    }
    return new Limit(
      Operation.COMPARISON,
      rule.less ? rule.val : rule.val + 1,
      rule.name,
      rule.less ? buildTree(rule.to, rules, 0) : buildTree(ruleName, rules, ruleIndex + 1),
      rule.less ? buildTree(ruleName, rules, ruleIndex + 1) : buildTree(rule.to, rules, 0)
    );
  }

  private static long calculate(Limit root, Map<Character, Pair<Long, Long>> ranges) {
    if (root == null) return 0;
    if (root.op == Operation.ACCEPTED) {
      return (ranges.get('x').getValue() - ranges.get('x').getKey() + 1)
        * (ranges.get('m').getValue() - ranges.get('m').getKey() + 1)
        * (ranges.get('a').getValue() - ranges.get('a').getKey() + 1)
        * (ranges.get('s').getValue() - ranges.get('s').getKey() + 1);
    } else if (root.op == Operation.REJECTED) {
      return 0;
    }
    Pair<Long, Long> ruleRangeTrue = new Pair<>(1L, root.value - 1L);
    var intersectTrue = Range.intersect(ruleRangeTrue, ranges.get(root.parameter));
    var rangesTrue = copyRanges(ranges);
    if (intersectTrue.isPresent()) {
      rangesTrue.get(root.parameter).setKey(intersectTrue.get().getKey()).setValue(intersectTrue.get().getValue());
    } else {
      rangesTrue.get(root.parameter).setKey(0L).setValue(-1L);
    }

    Pair<Long, Long> ruleRangeFalse = new Pair<>(root.value, LIM);
    var intersectFalse = Range.intersect(ruleRangeFalse, ranges.get(root.parameter));
    var rangesFalse = copyRanges(ranges);
    if (intersectFalse.isPresent()) {
      rangesFalse.get(root.parameter).setKey(intersectFalse.get().getKey()).setValue(intersectFalse.get().getValue());
    } else {
      rangesFalse.get(root.parameter).setKey(0L).setValue(-1L);
    }

    return calculate(root.ifTrue, rangesTrue) + calculate(root.ifFalse, rangesFalse);
  }

  private static Map<Character, Pair<Long, Long>> copyRanges(Map<Character, Pair<Long, Long>> ranges) {
    return new HashMap<>() {{
      put('x', new Pair<>(ranges.get('x').getKey(), ranges.get('x').getValue()));
      put('m', new Pair<>(ranges.get('m').getKey(), ranges.get('m').getValue()));
      put('a', new Pair<>(ranges.get('a').getKey(), ranges.get('a').getValue()));
      put('s', new Pair<>(ranges.get('s').getKey(), ranges.get('s').getValue()));
    }};
  }

  private static Map<String, List<Rule>> parseRules(String[] input) {
    Map<String, List<Rule>> rules = Arrays.stream(input[0].split("\n")).map(
      e -> e.split("\\{")
    ).collect(Collectors.toMap(
      k -> k[0],
      v -> Arrays.stream(v[1].split(",")).map(
        t -> t.charAt(t.length() - 1) == '}'
          ? new Rule('E', false, t.substring(0, t.length() - 1), 0)
          : new Rule(t.charAt(0), t.charAt(1) == '<', t.split(":")[1], Parse.integers(t).get(0))
      ).collect(Collectors.toList())
    ));
    rules.put("A", List.of(new Rule('E', false, "A", 0)));
    rules.put("R", List.of(new Rule('E', false, "R", 0)));
    return rules;
  }

  private static List<Map<Character, Integer>> parseParts(String[] input) {
    return Arrays.stream(input[1].split("\n")).map(e -> {
      var nums = Parse.integers(e);
      return Map.of('x', nums.get(0), 'm', nums.get(1), 'a', nums.get(2), 's', nums.get(3));
    }).collect(Collectors.toList());
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

  static int expected1 = 19114;
  static long expected2 = 167409079868000L;
  static String testStr =
    "px{a<2006:qkq,m>2090:A,rfg}\n" +
    "pv{a>1716:R,A}\n" +
    "lnx{m>1548:A,A}\n" +
    "rfg{s<537:gd,x>2440:R,A}\n" +
    "qs{s>3448:A,lnx}\n" +
    "qkq{x<1416:A,crn}\n" +
    "crn{x>2662:A,R}\n" +
    "in{s<1351:px,qqz}\n" +
    "qqz{s>2770:qs,m<1801:hdj,R}\n" +
    "gd{a>3333:R,R}\n" +
    "hdj{m>838:A,pv}\n" +
    "\n" +
    "{x=787,m=2655,a=1222,s=2876}\n" +
    "{x=1679,m=44,a=2067,s=496}\n" +
    "{x=2036,m=264,a=79,s=2244}\n" +
    "{x=2461,m=1339,a=466,s=291}\n" +
    "{x=2127,m=1623,a=2188,s=1013}";
  static String realStr =
    "px{a<2006:qkq,m>2090:A,rfg}\n" +
      "pv{a>1716:R,A}\n" +
      "lnx{m>1548:A,A}\n" +
      "rfg{s<537:gd,x>2440:R,A}\n" +
      "qs{s>3448:A,lnx}\n" +
      "qkq{x<1416:A,crn}\n" +
      "crn{x>2662:A,R}\n" +
      "in{s<1351:px,qqz}\n" +
      "qqz{s>2770:qs,m<1801:hdj,R}\n" +
      "gd{a>3333:R,R}\n" +
      "hdj{m>838:A,pv}\n" +
      "\n" +
      "{x=787,m=2655,a=1222,s=2876}\n" +
      "{x=1679,m=44,a=2067,s=496}\n" +
      "{x=2036,m=264,a=79,s=2244}\n" +
      "{x=2461,m=1339,a=466,s=291}\n" +
      "{x=2127,m=1623,a=2188,s=1013}";
}
