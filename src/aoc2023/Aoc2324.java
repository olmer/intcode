package aoc2023;

import java.util.Arrays;
import java.util.List;

import tools.Parse;

public class Aoc2324 {

  private static long part1(String[] input) {
    //y = mx + b
    //m = dy / dx
    //b = y - mx
    List<Hailstone> in = Arrays
      .stream(input)
      .map(Parse::doubles)
      .map(e -> new Hailstone(e.get(0), e.get(1), e.get(2), e.get(3), e.get(4), e.get(5)))
      .toList();
    long r = 0;
    long min = 200000000000000L;
    long max = 400000000000000L;
    for (int i = 0; i < in.size(); i++) {
      for (int j = i + 1; j < in.size(); j++) {
        var hs1 = in.get(i);
        var hs2 = in.get(j);
        double a1 = hs1.a, b1 = hs1.b, c1 = hs1.c;
        double a2 = hs2.a, b2 = hs2.b, c2 = hs2.c;
        if (a1 * b2 == b1 * a2)
          continue;

        double x = (c1 * b2 - c2 * b1) / (a1 * b2 - a2 * b1);
        double y = (c2 * a1 - c1 * a2) / (a1 * b2 - a2 * b1);
        if (x < min || x > max || y < min || y > max)
          continue;

        if ((x - hs1.sx) * hs1.vx >= 0 && (y - hs1.sy) * hs1.vy >= 0 &&
          (x - hs2.sx) * hs2.vx >= 0 && (y - hs2.sy) * hs2.vy >= 0
        ) {
          r++;
        }
      }
    }
    return r;
  }

  public record Hailstone (double sx, double sy, double sz, double vx, double vy, double vz, double a, double b, double c) {
    public Hailstone (double sx, double sy, double sz, double vx, double vy, double vz) {
      this(sx, sy, sz, vx, vy, vz,
        vy,
        -vx,
        vy * sx - vx * sy
      );
    }
  }

  private static long part2(String[] input) {
    //Solution by /r/Smooth-Aide-1751
    long r = 0;
      List<Hailstone> hailstones = Arrays
        .stream(input)
        .map(Parse::doubles)
        .map(e -> new Hailstone(e.get(0), e.get(1), e.get(2), e.get(3), e.get(4), e.get(5)))
        .toList();

      Hailstone h1 = hailstones.get(0);
      Hailstone h2 = hailstones.get(1);

      int range = 500;
      for (int vx = -range; vx <= range; vx++) {
        for (int vy = -range; vy <= range; vy++) {
          for (int vz = -range; vz <= range; vz++) {

            if (vx == 0 || vy == 0 || vz == 0) {
              continue;
            }

            // Find starting point for rock that will intercept first two hailstones (x,y) on this trajectory

            // simultaneous linear equation (from part 1):
            // H1:  x = A + a*t   y = B + b*t
            // H2:  x = C + c*u   y = D + d*u
            //
            //  t = [ d ( C - A ) - c ( D - B ) ] / ( a * d - b * c )
            //
            // Solve for origin of rock intercepting both hailstones in x,y:
            //     x = A + a*t - vx*t   y = B + b*t - vy*t
            //     x = C + c*u - vx*u   y = D + d*u - vy*u

            long A = (long) h1.sx, a = (long) (h1.vx - vx);
            long B = (long) h1.sy, b = (long) h1.vy - vy;
            long C = (long) h2.sx, c = (long) h2.vx - vx;
            long D = (long) h2.sy, d = (long) h2.vy - vy;

            // skip if division by 0
            if (c == 0 || (a * d) - (b * c) == 0) {
              continue;
            }

            // Rock intercepts H1 at time t
            long t = (d * (C - A) - c * (D - B)) / ((a * d) - (b * c));

            // Calculate starting position of rock from intercept point
            long x = (long) (h1.sx + h1.vx * t - vx * t);
            long y = (long) (h1.sy + h1.vy * t - vy * t);
            long z = (long) (h1.sz + h1.vz * t - vz * t);


            // check if this rock throw will hit all hailstones

            boolean hitall = true;
            for (int i = 0; i < hailstones.size(); i++) {

              Hailstone h = hailstones.get(i);
              long u;
              if (h.vx != vx) {
                u = (long) ((x - h.sx) / (h.vx - vx));
              } else if (h.vy != vy) {
                u = (long) ((y - h.sy) / (h.vy - vy));
              } else if (h.vz != vz) {
                u = (long) ((z - h.sz) / (h.vz - vz));
              } else {
                throw new RuntimeException();
              }

              if ((x + u * vx != h.sx + u * h.vx) || (y + u * vy != h.sy + u * h.vy) || ( z + u * vz != h.sz + u * h.vz)) {
                hitall = false;
                break;
              }
            }

            if (hitall) {
              r = x + y + z;
              System.out.printf("%d %d %d   %d %d %d   %d %n", x, y, z, vx, vy, vz, x + y + z);
            }
          }
        }
      }
      return r;
    }

  private static void test() {
    var p1 = part1(getInput(true));
    System.out.println("Part 1 test: " + p1 + (p1 == expected1 ? " PASSED" : " FAILED"));

    var p2 = part2(getInput(true));
    System.out.println("Part 2 test: " + p2 + (p2 == expected2 ? " PASSED" : " FAILED"));
  }

  public static void main(String[] args) {
//    test();
//    System.out.println(part1(getInput(false)));
    System.out.println(part2(getInput(false)));
  }

  private static String[] getInput(boolean isTest) {
    return (isTest ? testStr : realStr).split("\n");
  }

  static int expected1 = 0;
  static int expected2 = 0;
  static String testStr =
    "19, 13, 30 @ -2,  1, -2\n" +
    "18, 19, 22 @ -1, -1, -2\n" +
    "20, 25, 34 @ -2, -2, -4\n" +
    "12, 31, 28 @ -1, -2, -1\n" +
    "20, 19, 15 @  1, -5, -3";
  static String realStr =
    "19, 13, 30 @ -2,  1, -2\n" +
      "18, 19, 22 @ -1, -1, -2\n" +
      "20, 25, 34 @ -2, -2, -4\n" +
      "12, 31, 28 @ -1, -2, -1\n" +
      "20, 19, 15 @  1, -5, -3";
}
