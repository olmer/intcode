package aoc2024;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Aoc2424 extends AbstractAoc {
  long part1(String[] in) {
    String[] init = Arrays.stream(in).collect(Collectors.joining("\n")).split("\n\n")[0].split("\n");
    String[] gates = Arrays.stream(in).collect(Collectors.joining("\n")).split("\n\n")[1].split("\n");

    Map<String, Integer> wires = Arrays.stream(init).collect(Collectors.toMap(s -> s.split(": ")[0], s -> Integer.parseInt(s.split(": ")[1])));

    Set<Gate> initGates = new HashSet<>();

    Map<String, Set<Gate>> gatesByWire = new HashMap<>();
    Map<String, Gate> gateMap = Arrays.stream(gates).map(s -> {
      String[] parts = s.split(" ");
      Gate gate = new Gate(parts[1], parts[0], parts[2], parts[4]);
      gatesByWire.computeIfAbsent(gate.in1, _ -> new HashSet<>()).add(gate);
      gatesByWire.computeIfAbsent(gate.in2, _ -> new HashSet<>()).add(gate);
      if (wires.containsKey(gate.in1) && wires.containsKey(gate.in2)) {
        initGates.add(gate);
      }
      wires.put(gate.out, 0);
      return gate;
    }).collect(Collectors.toMap(g -> g.out, g -> g));

    ArrayDeque<Gate> gatesToProcess = new ArrayDeque<>(initGates);

    while (!gatesToProcess.isEmpty()) {
      Gate gate = gatesToProcess.poll();
      wires.put(gate.out, switch (gate.op) {
        case "AND" -> wires.get(gate.in1) & wires.get(gate.in2);
        case "OR" -> wires.get(gate.in1) | wires.get(gate.in2);
        case "XOR" -> wires.get(gate.in1) ^ wires.get(gate.in2);
        default -> throw new RuntimeException("Unknown op: " + gate.op);
      });
      if (gatesByWire.containsKey(gate.out)) {
        gatesToProcess.addAll(gatesByWire.get(gate.out));
      }
    }

    List<Entry<String, Integer>> res = wires.entrySet().stream().filter(e -> e.getKey().startsWith("z")).sorted((a, b) -> b.getKey().compareTo(a.getKey())).toList();
    long r = 0;
    for (Entry<String, Integer> e : res) {
      r = r << 1 | e.getValue();
    }
    return r;
  }

  String verifyAdd(String carry, Map<String, Set<Gate>> gatesByWire, int idx) {
    String x = "x" + String.format("%02d", idx);
    String y = "y" + String.format("%02d", idx);
    Set<Gate> xg = gatesByWire.get(x);
    Set<Gate> yg = gatesByWire.get(y);
    if (!xg.equals(yg)) {
      System.out.println("Different gates for x and y: " + xg + " " + yg);
    }
    Gate xor = xg.stream().filter(g -> g.op.equals("XOR")).findFirst().get();
    Gate and1 = xg.stream().filter(g -> g.op.equals("AND")).findFirst().get();

    if (!gatesByWire.get(xor.out).equals(gatesByWire.get(carry))) {
      System.out.println("Different gates for xor and carry: " + gatesByWire.get(xor.out) + " " + gatesByWire.get(carry));
    }

    Set<Gate> xor2 = gatesByWire.get(xor.out);
    Set<Gate> and1Children = gatesByWire.get(and1.out);

    if (xor2.size() != 2) {
      System.out.println("Wrong XOR2 size: " + xor2);
    }

    Optional<Gate> out = xor2.stream().filter(g -> g.op.equals("XOR")).findFirst();
    if (out.isEmpty() || !out.get().out.equals("z" + String.format("%02d", idx))) {
      System.out.println("No OUT in XOR2: " + xor2);
    }

    if (and1Children.size() != 1) {
      System.out.println("Wrong and1 children size: " + and1Children);
    }

    Set<Gate> and2Children = gatesByWire.get(gatesByWire.get(carry).stream().filter(g -> g.op.equals("AND")).findFirst().get().out);
    if (and2Children.size() != 1) {
      System.out.println("Wrong and2 children size: " + and2Children);
    }

    Gate or = and1Children.stream().findFirst().get();
    Gate sameOrByFromCarry = and2Children.stream().findFirst().get();

    if (!or.equals(sameOrByFromCarry)) {
      System.out.println("Different OR and sameOrButFromCarry: " + or + " " + sameOrByFromCarry);
    }

    return or.out;
  }

  long part2(String[] in) {
    String[] init = Arrays.stream(in).collect(Collectors.joining("\n")).split("\n\n")[0].split("\n");
    String[] gates = Arrays.stream(in).collect(Collectors.joining("\n")).split("\n\n")[1].split("\n");
    Map<String, Integer> wires = Arrays.stream(init).collect(Collectors.toMap(s -> s.split(": ")[0], s -> Integer.parseInt(s.split(": ")[1])));

    Set<Gate> initGates = new HashSet<>();

    Map<String, Set<Gate>> gatesByWire = new HashMap<>();
    Map<String, Gate> gateMap = Arrays.stream(gates).map(s -> {
      String[] parts = s.split(" ");
      Gate gate = new Gate(parts[1], parts[0], parts[2], parts[4]);
      gatesByWire.computeIfAbsent(gate.in1, _ -> new HashSet<>()).add(gate);
      gatesByWire.computeIfAbsent(gate.in2, _ -> new HashSet<>()).add(gate);
      wires.put(gate.out, 0);
      return gate;
    }).collect(Collectors.toMap(g -> g.out, g -> g));

    String carry = "wbd";
    for (int i = 1; i < 45; i++) {
      carry = verifyAdd(carry, gatesByWire, i);
    }
    ArrayDeque<String> gatesToProcess1 = new ArrayDeque<>();
    Set<String> visited = new HashSet<>();
    initGates = new HashSet<>();
    for (int i = 0; i < 45; i++) {
      gatesToProcess1.add("x" + String.format("%02d", i));
      visited.add("x" + String.format("%02d", i));
      initGates.addAll(gatesByWire.get("x" + String.format("%02d", i)));
    }

    while (!gatesToProcess1.isEmpty()) {
      if (!gatesByWire.containsKey(gatesToProcess1.peek())) {
        gatesToProcess1.poll();
        continue;
      }
      Set<Gate> gatess = gatesByWire.get(gatesToProcess1.poll());
      System.out.println(gatess);
      System.out.println("------");
      for (Gate g : gatess) {
        if (visited.add(g.out)) {
          gatesToProcess1.add(g.out);
        }
      }
    }

    System.out.println("Ended");

    // run simulation
    ArrayDeque<Gate> gatesToProcess = new ArrayDeque<>(initGates);
    Set<Gate> visited2 = new HashSet<>(initGates);

    while (!gatesToProcess.isEmpty()) {
      while (!gatesToProcess.isEmpty()) {
        Gate gate = gatesToProcess.poll();
        wires.put(gate.out, switch (gate.op) {
          case "AND" -> wires.get(gate.in1) & wires.get(gate.in2);
          case "OR" -> wires.get(gate.in1) | wires.get(gate.in2);
          case "XOR" -> wires.get(gate.in1) ^ wires.get(gate.in2);
          default -> throw new RuntimeException("Unknown op: " + gate.op);
        });
        if (gatesByWire.containsKey(gate.out)) {
          for (Gate toAdd : gatesByWire.get(gate.out)) {
            if (visited2.add(toAdd)) {
              gatesToProcess.add(toAdd);
            }
          }
        }
      }
    }

    List<Entry<String, Integer>> res;

    res = wires.entrySet().stream().filter(e -> e.getKey().startsWith("x")).sorted((a, b) -> b.getKey().compareTo(a.getKey())).toList();
    long xl = 0;
    for (Entry<String, Integer> e : res) {
      xl = xl << 1 | e.getValue();
    }

    res = wires.entrySet().stream().filter(e -> e.getKey().startsWith("y")).sorted((a, b) -> b.getKey().compareTo(a.getKey())).toList();
    long yl = 0;
    for (Entry<String, Integer> e : res) {
      yl = yl << 1 | e.getValue();
    }

    res = wires.entrySet().stream().filter(e -> e.getKey().startsWith("z")).sorted((a, b) -> b.getKey().compareTo(a.getKey())).toList();
    long zl = 0;
    for (Entry<String, Integer> e : res) {
      zl = zl << 1 | e.getValue();
    }

    if (xl + yl == zl) {
      System.out.println("Found solution");
      System.out.println("x: " + xl);
      System.out.println("y: " + yl);
      System.out.println("z: " + zl);
    }
    return 0;
  }

  class Gate {
    String op;
    String in1;
    String in2;
    String out;

    public Gate(String op, String in1, String in2, String out) {
      this.op = op;
      this.in1 = in1;
      this.in2 = in2;
      this.out = out;
    }

    @Override
    public String toString() {
      return in1 + " " + op + " " + in2 + " -> " + out;
    }
  }

  public static void main(String[] args) {
    (new Aoc2424()).start();
  }

  @Override
  long getTestExpected1() {
    return 0;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }

  @Override
  String getTestInput() {
    return "";
  }

  @Override
  String getRealInput() {
    return "";
  }
}
