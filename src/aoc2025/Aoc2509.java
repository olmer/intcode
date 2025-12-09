package aoc2025;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import tools.Parse;

public class Aoc2509 extends AbstractAoc {
  long part1(String[] in) {
    List<List<Integer>> points = new ArrayList<>();
    for (String s : in) {
      points.add(Parse.integers(s));
    }
    long best = 0;
    for (int i = 0; i < points.size(); i++) {
      List<Integer> p1 = points.get(i);
      for (int j = 0; j < points.size(); j++) {
        if (i == j) {
          continue;
        }
        List<Integer> p2 = points.get(j);
        long area = (Math.abs(p1.get(0) - p2.get(0)) + 1L) * (Math.abs(p1.get(1) - p2.get(1)) + 1L);
        if (area > best) {
          best = area;
        }
      }
    }
    return best;
  }

  long part2(String[] in) {
    List<List<Integer>> points = new ArrayList<>();
    for (String s : in) {
      points.add(Parse.integers(s));
    }

    Coordinate[] polyCoords = points.stream()
        .map(p -> new Coordinate(p.get(0), p.get(1)))
        .toArray(Coordinate[]::new);

    GeometryFactory gf = new GeometryFactory();

    Polygon poly = gf.createPolygon(polyCoords);

    long best = 0;
    for (int i = 0; i < points.size(); i++) {
      List<Integer> p1 = points.get(i);
      for (int j = 0; j < points.size(); j++) {
        if (i == j) {
          continue;
        }
        List<Integer> p2 = points.get(j);

        long area = (Math.abs(p1.get(0) - p2.get(0)) + 1L) * (Math.abs(p1.get(1) - p2.get(1)) + 1L);

        Polygon rect = gf.createPolygon(new Coordinate[] {
            new Coordinate(Math.min(p1.get(0), p2.get(0)), Math.min(p1.get(1), p2.get(1))),
            new Coordinate(Math.max(p1.get(0), p2.get(0)), Math.min(p1.get(1), p2.get(1))),
            new Coordinate(Math.max(p1.get(0), p2.get(0)), Math.max(p1.get(1), p2.get(1))),
            new Coordinate(Math.min(p1.get(0), p2.get(0)), Math.max(p1.get(1), p2.get(1))),
            new Coordinate(Math.min(p1.get(0), p2.get(0)), Math.min(p1.get(1), p2.get(1)))
        });

        if (!poly.contains(rect)) {
//          System.out.println("Not contained: " + rect + " with area " + area);
          continue;
        }

        if (area > best) {
//          System.out.println("New best: " + area + " for polygon " + rect);
          best = area;
        }
      }
    }

    return best;
  }

  public static void main(String[] args) {
    (new Aoc2509()).start();
  }

  @Override
  String getTestInput() {
    return "7,1\n" +
        "11,1\n" +
        "11,7\n" +
        "9,7\n" +
        "9,5\n" +
        "2,5\n" +
        "2,3\n" +
        "7,3\n" +
        "7,1";
  }

  @Override
  long getTestExpected1() {
    return 50;
  }

  @Override
  long getTestExpected2() {
    return 24;
  }

  @Override
  String getRealInput() {
    return "7,1\n" +
        "11,1\n" +
        "11,7\n" +
        "9,7\n" +
        "9,5\n" +
        "2,5\n" +
        "2,3\n" +
        "7,3\n" +
        "7,1";
  }
}
