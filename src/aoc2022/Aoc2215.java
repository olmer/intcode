package aoc2022;

import java.util.HashMap;
import java.util.Map;

import tools.Parse;

public class Aoc2215 {
  static boolean TEST = true;

  private static Map<Pair<Long, Long>, Long> getSensors() {
    Map<Pair<Long, Long>, Long> sensors = new HashMap<>();

    for (String sens : getInput()) {
      var digits = Parse.longs(sens);

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

    for (var sensor : sensors.entrySet()) {
      long sensorX = sensor.getKey().getKey();
      long sensorY = sensor.getKey().getValue();
      long sensorArea = sensor.getValue();
      long yOffset = 0;
      for (long potentialX = sensorX - sensorArea - 1; potentialX != sensorX + sensorArea + 1; potentialX++) {
        boolean isInRangeOfSensor = false;
        for (long potentialY : new long[]{sensorY + yOffset, sensorY - yOffset}) {
          for (var sensorToTest : sensors.entrySet()) {
            long sensorToTestX = sensorToTest.getKey().getKey();
            long sensorToTestY = sensorToTest.getKey().getValue();

            long distance = Math.abs(sensorToTestX - potentialX) + Math.abs(sensorToTestY - potentialY);
            if (distance <= sensorToTest.getValue()) {
              isInRangeOfSensor = true;
              break;
            }
          }

          if (!isInRangeOfSensor && potentialX >= 0 && potentialY >= 0 && potentialX < searchSpace && potentialY < searchSpace) {
            System.out.println(potentialX + " : " + potentialY);
            System.out.println(potentialX * 4000000 + potentialY);
          }
        }
        if (potentialX > sensorX) {
          yOffset++;
        } else {
          yOffset--;
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
