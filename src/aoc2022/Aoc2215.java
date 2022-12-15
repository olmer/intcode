package aoc2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Aoc2215 {
  static boolean TEST = true;

  private static Map<Pair<Long, Long>, Long> getSensors() {
    Map<Pair<Long, Long>, Long> sensors = new HashMap<>();

    for (String sens : getInput()) {
      var pattern = Pattern.compile("-?\\d+");
      var matcher = pattern.matcher(sens);
      var digits = new ArrayList<Long>();
      while (matcher.find()) {
        digits.add(Long.valueOf(matcher.group()));
      }

      long distance = Math.abs(digits.get(0) - digits.get(2)) + Math.abs(digits.get(1) - digits.get(3));
      sensors.put(new Pair<>(digits.get(0), digits.get(1)), distance);
    }

    return sensors;
  }

  public static void main(String[] args) throws Exception {
    int searchSpace = 20;
    if (!TEST)
      searchSpace = 4_000_000;

    Map<Pair<Long, Long>, Long> sensors = getSensors();

    System.out.println(sensors);

    for (var sen : sensors.entrySet()) {
      long x = sen.getKey().getKey();
      long y = sen.getKey().getValue();
      long d = sen.getValue();
      long yyy = 0;
      for (long xx = x - d - 1; xx != x + d + 1; xx++) {
        long yy1 = y + yyy;
        long yy2 = y - yyy;

        boolean inrange = false;

        for (long yy : new long[]{yy1, yy2}) {
          for (var e : sensors.entrySet()) {
            long sx = e.getKey().getKey();
            long sy = e.getKey().getValue();

            long di = Math.abs(sx - xx) + Math.abs(sy - yy);
            if (di <= e.getValue()) {
              inrange = true;
              break;
            }
          }

          if (!inrange && xx >= 0 && yy >= 0 && xx < searchSpace && yy < searchSpace) {
            System.out.println(xx + " : " + yy);
            System.out.println(xx * 4000000 + yy);
          }
        }
        if (xx > x) {
          yyy++;
        } else {
          yyy--;
        }
      }
    }
  }

  private static String[] getInput() {
    String testStr = "Sensor at x=2, y=18: closest beacon is at x=-2, y=15\n" +
      "Sensor at x=9, y=16: closest beacon is at x=10, y=16\n" +
      "Sensor at x=13, y=2: closest beacon is at x=15, y=3\n" +
      "Sensor at x=12, y=14: closest beacon is at x=10, y=16\n" +
      "Sensor at x=10, y=20: closest beacon is at x=10, y=16\n" +
      "Sensor at x=14, y=17: closest beacon is at x=10, y=16\n" +
      "Sensor at x=8, y=7: closest beacon is at x=2, y=10\n" +
      "Sensor at x=2, y=0: closest beacon is at x=2, y=10\n" +
      "Sensor at x=0, y=11: closest beacon is at x=2, y=10\n" +
      "Sensor at x=20, y=14: closest beacon is at x=25, y=17\n" +
      "Sensor at x=17, y=20: closest beacon is at x=21, y=22\n" +
      "Sensor at x=16, y=7: closest beacon is at x=15, y=3\n" +
      "Sensor at x=14, y=3: closest beacon is at x=15, y=3\n" +
      "Sensor at x=20, y=1: closest beacon is at x=15, y=3";

    String realStr = "";

    return (TEST ? testStr : realStr).split("\n");
  }
}
