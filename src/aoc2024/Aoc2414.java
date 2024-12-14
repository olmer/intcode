package aoc2024;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import tools.Parse;

public class Aoc2414 extends AbstractAoc {
  long part1(String[] in) {
    int xsize = 11;
    int ysize = 7;
    List<Robot> robots = Arrays.stream(in).map(Parse::integers)
        .map(l -> new Robot(l.get(0), l.get(1), l.get(2), l.get(3))).toList();
    long[] quadrants = new long[4];
    for (Robot robot : robots) {
      for (int i = 0; i < 100; i++) {
        robot.x = (robot.x + robot.vx + xsize) % xsize;
        robot.y = (robot.y + robot.vy + ysize) % ysize;
      }
      if (robot.x == xsize / 2 || robot.y == ysize / 2) {
        continue;
      }
      if (robot.x <= xsize / 2 && robot.y <= ysize / 2) {
        quadrants[0]++;
      } else if (robot.x > xsize / 2 && robot.y <= ysize / 2) {
        quadrants[1]++;
      } else if (robot.x <= xsize / 2 && robot.y > ysize / 2) {
        quadrants[2]++;
      } else {
        quadrants[3]++;
      }
    }

    System.out.println(Arrays.toString(quadrants));

    // return product of quadrants
    return quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3];
  }

  long part2(String[] in) {
    int xsize = 101;
    int ysize = 103;
    List<Robot> robots = Arrays.stream(in).map(Parse::integers)
        .map(l -> new Robot(l.get(0), l.get(1), l.get(2), l.get(3))).toList();
    try (PrintWriter writer = new PrintWriter("output.txt")) {
      for (long i = 0; i < 7600; i++) {
        long[] quadrants = new long[4];
        for (Robot robot : robots) {
          robot.x = (robot.x + robot.vx + xsize) % xsize;
          robot.y = (robot.y + robot.vy + ysize) % ysize;

          if (robot.x == xsize / 2 || robot.y == ysize / 2) {
            continue;
          }
          if (robot.x <= xsize / 2 && robot.y <= ysize / 2) {
            quadrants[0]++;
          } else if (robot.x > xsize / 2 && robot.y <= ysize / 2) {
            quadrants[1]++;
          } else if (robot.x <= xsize / 2 && robot.y > ysize / 2) {
            quadrants[2]++;
          } else {
            quadrants[3]++;
          }
        }
        if (i >= 0 && i <= 7700) {
          writer.println("------- " + i + " -------");
          for (int jj = 0; jj < ysize; jj++) {
            for (int ii = 0; ii < xsize; ii++) {
              int finalIi = ii;
              int finalJj = jj;
              writer.printf("%c", robots.stream().anyMatch(r -> r.x == finalIi && r.y == finalJj) ? '#' : '.');
            }
            writer.println();
          }
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static void main(String[] args) {
    (new Aoc2414()).start();
  }

  @Override
  long getTestExpected1() {
    return 12;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }

  class Robot {
    int x;
    int y;
    int vx;
    int vy;

    public Robot(int x, int y, int vx, int vy) {
      this.x = x;
      this.y = y;
      this.vx = vx;
      this.vy = vy;
    }
  }

  @Override
  String getTestInput() {
    return "p=0,4 v=3,-3\n" +
        "p=6,3 v=-1,-3\n" +
        "p=10,3 v=-1,2\n" +
        "p=2,0 v=2,-1\n" +
        "p=0,0 v=1,3\n" +
        "p=3,0 v=-2,-2\n" +
        "p=7,6 v=-1,-3\n" +
        "p=3,0 v=-1,-2\n" +
        "p=9,3 v=2,3\n" +
        "p=7,3 v=-1,2\n" +
        "p=2,4 v=2,-3\n" +
        "p=9,5 v=-3,-3";
  }

  String getRealInput() {
    return "p=0,4 v=3,-3\n" +
        "p=6,3 v=-1,-3\n" +
        "p=10,3 v=-1,2\n" +
        "p=2,0 v=2,-1\n" +
        "p=0,0 v=1,3\n" +
        "p=3,0 v=-2,-2\n" +
        "p=7,6 v=-1,-3\n" +
        "p=3,0 v=-1,-2\n" +
        "p=9,3 v=2,3\n" +
        "p=7,3 v=-1,2\n" +
        "p=2,4 v=2,-3\n" +
        "p=9,5 v=-3,-3";
  }
}
