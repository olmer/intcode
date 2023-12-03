package aoc2015;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tools.Parse;

public class Aoc1507 {
  private static final boolean TEST = true;

  static Map<String, String> commandsMap = new HashMap<>();
  static Map<String, Integer> calculatedNodes = new HashMap<>();

  public static void main(String[] args) throws NoSuchAlgorithmException {

    Set<String> sources = new HashSet<>();
    Set<String> destinations = new HashSet<>();
    for (var val : getInput()) {
      var pair = val.split(" -> ");
      destinations.add(pair[1]);
      commandsMap.put(pair[1], pair[0]);

      var gate = pair[0].split(" ");
      if (gate.length == 1) {
      } else if (gate.length == 2) {
        sources.add(gate[1]);
      } else {
        sources.add(gate[0]);
        if (gate[0].equals("AND") || gate[0].equals("OR")) {
          sources.add(gate[2]);
        }
      }
    }

    destinations.removeAll(sources);

    for (var d : destinations) {
      evaluate(d);
    }

    System.out.println(calculatedNodes.get("x"));
  }

  private static int evaluate(String node) {
    if (calculatedNodes.containsKey(node)) {
      return calculatedNodes.get(node);
    }
    var foundInts = Parse.integers(node);
    if (!foundInts.isEmpty()) {
      calculatedNodes.put(node, foundInts.get(0));
      return calculatedNodes.get(node);
    }
    String[] gate = commandsMap.get(node).split(" ");
    if (gate.length == 1) {
      // literal
      calculatedNodes.put(node, evaluate(gate[0]));
    } else if (gate.length == 2) {
      calculatedNodes.put(node, ~evaluate(gate[1]));
    } else {
      // lshift/rshift/and/or
      if (gate[1].equals("AND")) {
        calculatedNodes.put(node, evaluate(gate[0]) & evaluate(gate[2]));
      } else if (gate[1].equals("OR")) {
        calculatedNodes.put(node, evaluate(gate[0]) | evaluate(gate[2]));
      } else if (gate[1].equals("LSHIFT")) {
        calculatedNodes.put(node, evaluate(gate[0]) << Integer.parseInt(gate[2]));
      } else {
        calculatedNodes.put(node, evaluate(gate[0]) >> Integer.parseInt(gate[2]));
      }
    }
    return calculatedNodes.get(node);
  }



  private static String[] getInput() {
    String testString =
      "123 -> x\n" +
        "456 -> y\n" +
        "x AND y -> d\n" +
        "x OR y -> e\n" +
        "x LSHIFT 2 -> f\n" +
        "y RSHIFT 2 -> g\n" +
        "NOT x -> h\n" +
        "NOT d -> hh\n" +
        "NOT y -> i";
    String realString = "";
    return (TEST ? testString : realString).split("\n");
  }
}
