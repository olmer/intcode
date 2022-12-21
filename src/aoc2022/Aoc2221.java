package aoc2022;

import java.util.HashMap;
import java.util.Map;

import tools.Parse;

public class Aoc2221 {
  static boolean TEST = true;

  public static void main(String[] args) throws Exception {
    for (var m : getInput()) {
      map.put(m.split(": ")[0], new Mon(m.split(": ")[0], m.split(": ")[1]));
    }


    map.get("root").get();
  }
  static class Humn extends Exception {}

  static Map<String, Mon> map = new HashMap<>();

  static class Mon {
    boolean isNum = true;
    int num = 0;
    String id;
    String[] children = new String[2];
    char op = '+';

    public Mon(String id_, String op_) {
      id = id_;
      var p = Parse.integers(op_);
      if (p.size() > 0) {
        num = p.get(0);
      } else {
        isNum = false;
        var t = op_.split(" ");
        children[0] = t[0];
        children[1] = t[2];
        op = t[1].charAt(0);
      }
    }

    public long get(long expected) throws Exception {
      if (id.equals("humn")) {
        System.out.println(expected);
        return expected;
      }
      switch (op) {
        case '+':
          try {
            var ee = map.get(children[1]).get();
            return map.get(children[0]).get(expected - ee);
          } catch (Humn e) {
            var ee = map.get(children[0]).get();
            return map.get(children[1]).get(expected - ee);
          }
        case '-':
          try {
            var ee = map.get(children[1]).get();
            return map.get(children[0]).get(expected + ee);
          } catch (Humn e) {
            var ee = map.get(children[0]).get();
            return map.get(children[1]).get(ee - expected);
          }
        case '*':
          try {
            var ee = map.get(children[1]).get();
            return map.get(children[0]).get(expected / ee);
          } catch (Humn e) {
            var ee = map.get(children[0]).get();
            return map.get(children[1]).get(expected / ee);
          }
        case '/':
          try {
            var ee = map.get(children[1]).get();
            return map.get(children[0]).get(expected * ee);
          } catch (Humn e) {
            var ee = map.get(children[0]).get();
            return map.get(children[1]).get(ee / expected);
          }
        default:
          throw new Exception("wtf");
      }
    }

    public long get() throws Exception {
      if (id.equals("root")) {
        try {
          var expected = map.get(children[1]).get();
          return map.get(children[0]).get(expected);
        } catch (Humn e) {
          var expected = map.get(children[0]).get();
          return map.get(children[1]).get(expected);
        }
      }
      if (id.equals("humn")) {
        throw new Humn();
      }
      if (isNum) {
        return num;
      } else {
        switch (op) {
          case '+':
            return map.get(children[0]).get() + map.get(children[1]).get();
          case '-':
            return map.get(children[0]).get() - map.get(children[1]).get();
          case '*':
            return map.get(children[0]).get() * map.get(children[1]).get();
          case '/':
            return map.get(children[0]).get() / map.get(children[1]).get();
          default:
            throw new Exception("wtf");
        }
      }
    }
  }

  private static String[] getInput() {
    String testStr = "root: pppw + sjmn\n" +
      "dbpl: 5\n" +
      "cczh: sllz + lgvd\n" +
      "zczc: 2\n" +
      "ptdq: humn - dvpt\n" +
      "dvpt: 3\n" +
      "lfqf: 4\n" +
      "humn: 5\n" +
      "ljgn: 2\n" +
      "sjmn: drzm * dbpl\n" +
      "sllz: 4\n" +
      "pppw: cczh / lfqf\n" +
      "lgvd: ljgn * ptdq\n" +
      "drzm: hmdt - zczc\n" +
      "hmdt: 32";

    String realStr = "";

    return (TEST ? testStr : realStr).split("\n");
  }
}
