package aoc2022;

import java.util.HashMap;
import java.util.Map;

public class Aoc2214 {
  public static void main(String[] args) throws Exception {
    var input = getInput(true);

    Map<Integer, Map<Integer, Integer>> map = new HashMap<>();

    int lowesty = 0;

    for (String path : input) {
      var cds = path.split(" -> ");

      int fromx = Integer.parseInt(cds[0].split(",")[0]);
      int fromy = Integer.parseInt(cds[0].split(",")[1]);

      for (int i = 1; i < cds.length; i++) {
        int tox = Integer.parseInt(cds[i].split(",")[0]);
        int toy = Integer.parseInt(cds[i].split(",")[1]);
        int difx = Integer.compare(tox, fromx);
        int dify = Integer.compare(toy, fromy);
        while (fromx != tox || fromy != toy) {
          map.putIfAbsent(fromx, new HashMap<>());
          map.get(fromx).put(fromy, 1);
          fromx += difx;
          fromy += dify;
          lowesty = Math.max(lowesty, fromy);
        }
        map.putIfAbsent(fromx, new HashMap<>());
        map.get(fromx).put(fromy, 1);
        lowesty = Math.max(lowesty, fromy);
      }
    }

    int r = 0;
    while (true) {
      int x = 500;
      int y = 0;

      if (map.containsKey(x) && map.get(x).containsKey(y)) {
        break;
      }

      while (true) {
        if ((!map.containsKey(x) || !map.get(x).containsKey(y + 1)) && y < lowesty + 1) {
          y++;
        } else if ((!map.containsKey(x - 1) || !map.get(x - 1).containsKey(y + 1)) && y < lowesty + 1) {
          y++;
          x--;
        } else if ((!map.containsKey(x + 1) || !map.get(x + 1).containsKey(y + 1)) && y < lowesty + 1) {
          y++;
          x++;
        } else {
          map.putIfAbsent(x, new HashMap<>());
          map.get(x).put(y, 1);
          break;
        }
      }

      r++;
    }

    System.out.println(r);
  }

  private static String[] getInput(boolean dry) {
    String test = "498,4 -> 498,6 -> 496,6\n" +
      "503,4 -> 502,4 -> 502,9 -> 494,9";

    String real = "";

    return (dry ? test : real).split("\n");
  }
}
