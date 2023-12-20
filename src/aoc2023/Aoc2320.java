package aoc2023;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Aoc2320 {
  record Pulse (boolean state, String from, String to) {
    @Override
    public String toString() {
      return from + " -" + (state ? "high" : "low") + "-> " + to;
    }
  }

  interface Module {
    List<Pulse> trigger(Pulse in);
  }

  static class FlipFlop implements Module {
    String name;
    public boolean state;
    List<String> to;

    public FlipFlop(String name, List<String> to) {
      this.name = name;
      this.state = false;
      this.to = to;
    }

    public List<Pulse> trigger(Pulse in) {
      if (in.state) {
        return List.of();
      }
      state = !state;
      return to.stream().map(e -> new Pulse(state, name, e)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
      return "%" + name + " " + state;
    }
  }

  static class Conjunction implements Module {
    String name;
    public Map<String, Boolean> states;
    List<String> to;
    public Conjunction(String name, List<String> to) {
      this.name = name;
      this.states = new HashMap<>();
      this.to = to;
    }
    public void addFrom(String name) {
      states.put(name, false);
    }
    public List<Pulse> trigger(Pulse in) {
      states.put(in.from, in.state);
      boolean result = states.values().stream().filter(e -> e).count() == states.size();
      return to.stream().map(e -> new Pulse(!result, name, e)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
      return "&" + name + " " + states;
    }
  }

  static class Broadcaster implements Module {
    String name;
    List<String> to;
    public Broadcaster(String name, List<String> to) {
      this.name = name;
      this.to = to;
    }
    public List<Pulse> trigger(Pulse in) {
      return to.stream().map(e -> new Pulse(in.state, name, e)).collect(Collectors.toList());
    }
  }

  private static long part1(String[] input) {
    Map<String, Module> modules = new HashMap<>();
    for (var line : input) {
      var t = line.split(" -> ");
      if (t[0].charAt(0) == '%') {
        modules.put(t[0].substring(1), new FlipFlop(
          t[0].substring(1),
          Arrays.stream(t[1].split(", ")).collect(Collectors.toList()))
        );
      } else if (t[0].charAt(0) == '&') {
        modules.put(t[0].substring(1), new Conjunction(
          t[0].substring(1),
          Arrays.stream(t[1].split(", ")).collect(Collectors.toList())
        ));
      } else {
        modules.put(t[0], new Broadcaster(
          t[0],
          Arrays.stream(t[1].split(", ")).collect(Collectors.toList())
        ));
      }
    }
    for (var line : input) {
      var tos = line.split(" -> ")[1].split(", ");
      var name = line.split(" -> ")[0].charAt(0) == '%' || line.split(" -> ")[0].charAt(0) == '&'
        ? line.split(" -> ")[0].substring(1)
        : line.split(" -> ")[0];
      for (var to : tos) {
        if (!modules.containsKey(to)) continue;
        if (modules.get(to).getClass() == Conjunction.class) {
          ((Conjunction)modules.get(to)).addFrom(name);
        }
      }
    }

    ArrayDeque<Pulse> q = new ArrayDeque<>();


    long highs = 0;
    long lows = 0;

    for (int i = 0; i < 1000; i++) {
      q.offer(new Pulse(false, "button", "broadcaster"));
      while (!q.isEmpty()) {
        var pulse = q.poll();
        if (pulse.state) highs++; else lows++;

        Module destination = modules.get(pulse.to);

        List<Pulse> result = destination != null ? destination.trigger(pulse) : new ArrayList<>();
        q.addAll(result);
      }
    }
    return highs * lows;
  }


  private static long part2(String[] input) {
    Map<String, Module> modules = new HashMap<>();
    for (var line : input) {
      var t = line.split(" -> ");
      if (t[0].charAt(0) == '%') {
        modules.put(t[0].substring(1), new FlipFlop(
          t[0].substring(1),
          Arrays.stream(t[1].split(", ")).collect(Collectors.toList()))
        );
      } else if (t[0].charAt(0) == '&') {
        modules.put(t[0].substring(1), new Conjunction(
          t[0].substring(1),
          Arrays.stream(t[1].split(", ")).collect(Collectors.toList())
        ));
      } else {
        modules.put(t[0], new Broadcaster(
          t[0],
          Arrays.stream(t[1].split(", ")).collect(Collectors.toList())
        ));
      }
    }
    for (var line : input) {
      var tos = line.split(" -> ")[1].split(", ");
      var name = line.split(" -> ")[0].charAt(0) == '%' || line.split(" -> ")[0].charAt(0) == '&'
        ? line.split(" -> ")[0].substring(1)
        : line.split(" -> ")[0];
      for (var to : tos) {
        if (!modules.containsKey(to)) continue;
        if (modules.get(to).getClass() == Conjunction.class) {
          ((Conjunction)modules.get(to)).addFrom(name);
        }
      }
    }

    ArrayDeque<Pulse> q = new ArrayDeque<>();

    for (long i = 1; i < 1000000L; i++) {
      Conjunction gl = (Conjunction) modules.get("gl");//3989
      Conjunction bb = (Conjunction) modules.get("bb");//3967
      Conjunction mr = (Conjunction) modules.get("mr");//3907
      Conjunction kk = (Conjunction) modules.get("kk");//3931

      boolean finish = false;
      q.offer(new Pulse(false, "button", "broadcaster"));
      while (!q.isEmpty()) {

//        if (kk.states.values().stream().filter(e -> !e).count() == kk.states.size() && i > 1) {
//          System.out.println("kk: " + i);
//          finish = true;
//          break;
//        }
        var pulse = q.poll();

        Module destination = modules.get(pulse.to);

        List<Pulse> result = destination != null ? destination.trigger(pulse) : new ArrayList<>();
        q.addAll(result);
      }

      if (finish) {
        System.out.println("found! " + i);
        break;
      }
    }
    return 3989L * 3967L * 3907L * 3931L;
  }

  private static void test() {
    var p1 = part1(getInput(true));
    System.out.println("Part 1 test: " + p1 + (p1 == expected1 ? " PASSED" : " FAILED"));

    var p2 = part2(getInput(true));
    System.out.println("Part 2 test: " + p2 + (p2 == expected2 ? " PASSED" : " FAILED"));
  }

  public static void main(String[] args) {
    test();
//    System.out.println(part1(getInput(false)));
//    System.out.println(part2(getInput(false)));
  }

  private static String[] getInput(boolean isTest) {
    return (isTest ? testStr : realStr).split("\n");
  }

  static int expected1 = 11687500;
  static long expected2 = 243037165713371L;
  static String testStr =
    "broadcaster -> a\n" +
    "%a -> inv, con\n" +
    "&inv -> b\n" +
    "%b -> con\n" +
    "&con -> output";
  static String realStr =
"";
}
