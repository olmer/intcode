package aoc2022;

import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

public class Aoc2213 {
  private static int compare(Object l, Object r) {
    if (l instanceof Long && r instanceof Long) {
      return ((Long) l).compareTo((Long) r);
    }
    if (l instanceof Long) {
      return compare(new JSONArray() {{
        add(l);
      }}, r);
    }
    if (r instanceof Long) {
      return compare(l, new JSONArray() {{
        add(r);
      }});
    }

    var ll = (JSONArray) l;
    var rr = (JSONArray) r;

    for (int i = 0; i < ll.size(); i++) {
      if (i >= rr.size()) {
        return 1;
      }
      var c = compare(ll.get(i), rr.get(i));
      if (c != 0) {
        return c;
      }
    }

    return ll.size() < rr.size() ? -1 : 0;
  }

  public static void main(String[] args) throws Exception {
    var input = getInput(true);

    int res = 0;
    var tt = Arrays.stream(input).map(e -> (JSONArray) JSONValue.parse(e)).toArray();
    Arrays.sort(tt, Aoc2213::compare);

    int id0 = 0;
    int id2 = 0;

    for (int i = 0; i < tt.length; i++) {
      if (tt[i].equals(JSONValue.parse("[[2]]"))) {
        id0 = i + 1;
      }
      if (tt[i].equals(JSONValue.parse("[[6]]"))) {
        id2 = i + 1;
      }
    }

    System.out.println(id0 * id2);
  }

  private static String[] getInput(boolean dry) {
    String test = "[1,1,3,1,1]\n" +
      "[1,1,5,1,1]\n" +
      "\n" +
      "[[1],[2,3,4]]\n" +
      "[[1],4]\n" +
      "\n" +
      "[9]\n" +
      "[[8,7,6]]\n" +
      "\n" +
      "[[4,4],4,4]\n" +
      "[[4,4],4,4,4]\n" +
      "\n" +
      "[7,7,7,7]\n" +
      "[7,7,7]\n" +
      "\n" +
      "[]\n" +
      "[3]\n" +
      "\n" +
      "[[[]]]\n" +
      "[[]]\n" +
      "\n" +
      "[1,[2,[3,[4,[5,6,7]]]],8,9]\n" +
      "[1,[2,[3,[4,[5,6,0]]]],8,9]\n" +
      "[[2]]\n" +
      "[[6]]";

    String real = "";

    return (dry ? test : real).replace("\n\n", "\n").split("\n");
  }
}
