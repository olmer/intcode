package aoc2024;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Aoc2405 extends AbstractAoc {
  long part1(String[] in) {
    int r = 0;
    boolean part = false;
    Map<String, List<String>> rules = new HashMap<>();
    for (int i = 0; i < in.length; i++) {
      if (in[i].isEmpty()) {
        part = true;
        continue;
      }
      if (!part) {
        // rules
        String[] parts = in[i].split("\\|");
        if (!rules.containsKey(parts[0])) {
          rules.put(parts[0], new ArrayList<>());
        }
        rules.get(parts[0]).add(parts[1]);
      } else {
        // updates
        String[] parts = in[i].split(",");
        Set<String> seen = new HashSet<>();
        boolean valid = true;
        int mid = 0;
        for (int j = 0; j < parts.length; j++) {
          mid = j / 2;
          // keep track of what we've seen
          if (!rules.containsKey(parts[j])) {
            seen.add(parts[j]);
            continue;
          }

          for (String s : rules.get(parts[j])) {
            // if the second number in the rule is in the seen set, the update is invalid,
            // since it should go only AFTER the first number
            if (seen.contains(s)) {
              valid = false;
              break;
            }
          }
          seen.add(parts[j]);
        }

        if (valid) {
          r += Integer.parseInt(parts[mid]);
        }
      }
    }

    return r;
  }

  long part2(String[] in) {
    int r = 0;
    boolean part = false;
    Map<String, List<String>> rules = new HashMap<>();
    for (int i = 0; i < in.length; i++) {
      if (in[i].isEmpty()) {
        part = true;
        continue;
      }
      if (!part) {
        // rules
        String[] parts = in[i].split("\\|");
        if (!rules.containsKey(parts[0])) {
          rules.put(parts[0], new ArrayList<>());
        }
        rules.get(parts[0]).add(parts[1]);
      } else {
        // updates
        String[] parts = in[i].split(",");
        Set<String> toOrder = new HashSet<>();
        boolean valid = true;
        int mid = 0;
        for (int j = 0; j < parts.length; j++) {
          mid = j / 2;
          if (!rules.containsKey(parts[j])) {
            toOrder.add(parts[j]);
            continue;
          }

          for (String s : rules.get(parts[j])) {
            if (toOrder.contains(s)) {
              valid = false;
              break;
            }
          }
          toOrder.add(parts[j]);
        }

        if (!valid) {
          // main part 2 logic (topological sort)
          Map<String, Set<String>> dependencies = new HashMap<>();

          // build dependency map
          for (Map.Entry<String, List<String>> entry : rules.entrySet()) {
            for (String s : entry.getValue()) {
              if (!toOrder.contains(s) || !toOrder.contains(entry.getKey())) {
                continue;
              }
              if (!dependencies.containsKey(s)) {
                dependencies.put(s, new HashSet<>());
              }
              dependencies.get(s).add(entry.getKey());
            }
          }

          // add all nodes without dependencies to the removal queue
          ArrayDeque<String> removalQueue = new ArrayDeque<>();
          for (String s : toOrder) {
            if (!dependencies.containsKey(s)) {
              removalQueue.push(s);
            }
          }

          // topological sort: add nodes without dependencies to the result,
          // remove them from the dependency map,
          // and add new nodes without dependencies to the removal queue
          List<String> ordered = new ArrayList<>();
          while (!removalQueue.isEmpty()) {
            String removed = removalQueue.pop();
            ordered.add(removed);
            for (Map.Entry<String, Set<String>> entry : dependencies.entrySet()) {
              if (entry.getValue().isEmpty()) {
                continue;
              }
              entry.getValue().remove(removed);
              if (entry.getValue().isEmpty()) {
                removalQueue.push(entry.getKey());
              }
            }
          }

          r += Integer.parseInt(ordered.get(mid));
        }
      }
    }


    return r;
  }

  public static void main(String[] args) {
    (new Aoc2405()).start();
  }

  @Override
  long getTestExpected1() {
    return 143;
  }

  @Override
  long getTestExpected2() {
    return 123;
  }

  @Override
  String getTestInput() {
    return "47|53\n" +
        "97|13\n" +
        "97|61\n" +
        "97|47\n" +
        "75|29\n" +
        "61|13\n" +
        "75|53\n" +
        "29|13\n" +
        "97|29\n" +
        "53|29\n" +
        "61|53\n" +
        "97|53\n" +
        "61|29\n" +
        "47|13\n" +
        "75|47\n" +
        "97|75\n" +
        "47|61\n" +
        "75|61\n" +
        "47|29\n" +
        "75|13\n" +
        "53|13\n" +
        "\n" +
        "75,47,61,53,29\n" +
        "97,61,53,29,13\n" +
        "75,29,13\n" +
        "75,97,47,61,53\n" +
        "61,13,29\n" +
        "97,13,75,29,47";
  }

  @Override
  String getRealInput() {
    return "47|53\n" +
        "97|13\n" +
        "97|61\n" +
        "97|47\n" +
        "75|29\n" +
        "61|13\n" +
        "75|53\n" +
        "29|13\n" +
        "97|29\n" +
        "53|29\n" +
        "61|53\n" +
        "97|53\n" +
        "61|29\n" +
        "47|13\n" +
        "75|47\n" +
        "97|75\n" +
        "47|61\n" +
        "75|61\n" +
        "47|29\n" +
        "75|13\n" +
        "53|13\n" +
        "\n" +
        "75,47,61,53,29\n" +
        "97,61,53,29,13\n" +
        "75,29,13\n" +
        "75,97,47,61,53\n" +
        "61,13,29\n" +
        "97,13,75,29,47";
  }
}
