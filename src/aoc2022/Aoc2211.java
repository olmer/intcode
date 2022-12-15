package aoc2022;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import tools.Parse;

public class Aoc2211 {
  public static void main(String[] args) throws Exception {
    var input = getInput(true);
    Map<Integer, Monkey> mons = new HashMap<>();
    long[] inspec = new long[input.length];

    for (int i = 0; i < input.length; i++) {
      var monkeyMap = Parse.map(input[i]);
      String[] items = monkeyMap.get("Starting items").split(" ");
      ArrayDeque<Long> itmslist = new ArrayDeque<>();
      for (String item : items) {
        if (item.charAt(item.length() - 1) == ',') {
          itmslist.offer(Long.parseLong(item.substring(0, item.length() - 1)));
        } else {
          itmslist.offer(Long.parseLong(item));
        }
      }
      String op = monkeyMap.get("Operation");
      String test = monkeyMap.get("Test");
      String[] tru = monkeyMap.get("If true").split(" ");
      String[] fal = monkeyMap.get("If false").split(" ");
      mons.put(i, new Monkey(
        itmslist,
        new int[]{Integer.parseInt(tru[tru.length - 1]), Integer.parseInt(fal[fal.length - 1])},
        op,
        test
        ));
    }

    long gci = 1;
    for (var mon : mons.values()){
      gci *= mon.divby;
    }

    for (int round = 0; round < 10_000; round++) {
      for (int monidx = 0; monidx < mons.size(); monidx++) {
        Monkey mon = mons.get(monidx);
        while (!mon.items.isEmpty()) {
          Long it = mon.items.poll();
          inspec[monidx]++;
          it = mon.op.call(it) % gci;
          if (it % mon.divby == 0) {
            mons.get(mon.dest[0]).items.offer(it % gci);
          } else {
            mons.get(mon.dest[1]).items.offer(it % gci);
          }
        }
      }
    }

    Arrays.sort(inspec);

    System.out.println(inspec[inspec.length - 1] * inspec[inspec.length - 2]);
  }

  static class Monkey {
    ArrayDeque<Long> items;
    int[] dest;
    int divby;
    callable op;
    public Monkey(ArrayDeque<Long> startItems, int[] dest_, String op_, String test) {
      items = startItems;
      dest = dest_;
      divby = Integer.parseInt(test.split(" ")[2]);

      op = (long p) -> {
        long s = p;
        if (!op_.split(" ")[4].equals("old")) {
          s = Long.parseLong(op_.split(" ")[4]);
        }
        return switch ((op_.split(" "))[3]) {
          case "*" -> p * s;
          case "+" -> p + s;
          default -> throw new Exception("wtf");
        };
      };
    }
  }

  interface callable {
    long call(long p) throws Exception;
  }

  private static String[] getInput(boolean dry) {
    String test = "Monkey 0:\n" +
      "  Starting items: 79, 98\n" +
      "  Operation: new = old * 19\n" +
      "  Test: divisible by 23\n" +
      "    If true: throw to monkey 2\n" +
      "    If false: throw to monkey 3\n" +
      "\n" +
      "Monkey 1:\n" +
      "  Starting items: 54, 65, 75, 74\n" +
      "  Operation: new = old + 6\n" +
      "  Test: divisible by 19\n" +
      "    If true: throw to monkey 2\n" +
      "    If false: throw to monkey 0\n" +
      "\n" +
      "Monkey 2:\n" +
      "  Starting items: 79, 60, 97\n" +
      "  Operation: new = old * old\n" +
      "  Test: divisible by 13\n" +
      "    If true: throw to monkey 1\n" +
      "    If false: throw to monkey 3\n" +
      "\n" +
      "Monkey 3:\n" +
      "  Starting items: 74\n" +
      "  Operation: new = old + 3\n" +
      "  Test: divisible by 17\n" +
      "    If true: throw to monkey 0\n" +
      "    If false: throw to monkey 1";

    String real = "";

    return (dry ? test : real).split("\n\n");
  }
}
