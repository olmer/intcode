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
//    long min = 200000000000000L;
    long min = 7;
//    long max = 400000000000000L;
    long max = 27;
    for (int i = 0; i < in.size(); i++) {
      for (int j = i + 1; j < in.size(); j++) {
        var hs1 = in.get(i);
        var hs2 = in.get(j);
        if (hs1.m == hs2.m) continue;

        double x = (hs2.b - hs1.b) / (hs1.m - hs2.m);
        double y = hs1.m * x + hs1.b;
        if (x < min || x > max || y < min || y > max)
          continue;

        if ((x - hs1.sx) * hs1.vx >= 0 && (y - hs1.sy) * hs1.vy >= 0 &&
          (x - hs2.sx) * hs2.vx >= 0 && (y - hs2.sy) * hs2.vy >= 0
        ) {
          System.out.println(hs1 + " and " + hs2 + " intersect at x: " + x + " y: " + y);
          r++;
        }
      }
    }
    return r;
  }

  /*
  To solve this we need to create system of linear equations. There are 6 unknowns: velocities and positions X, Y and Z of the rock we throw.
  We therefore need 6 equations (for 6 unknowns)

  Start with the most important observation: for 2 object to intersect they need to be in the same place at the same time.
  We start with only one coordinate - X. Let t be time of intersection. To calculate where the object will be in time t,
  we add velocity * t to the positions: X + t * Vx

  Now let's take 2 objects, their positions should be the same after time t to intersect with each other:
  Xr + t * Vxr = Xh + t * Vxh
  where Xr is position X of the rock, t is time, Vxr is velocity X of the rock, Xh position of the hailstone, Vxh velocity X of the hailstone

  Extract t:
  t = (Xh - Xr) / (Vxr - Vxh)

  Same applies to all other coordinates, for example Y:
  t = (Yh - Yr) / (Vyr - Vyh)

  Since t is the same for all coordinates, we can equate right sides:
  (Xh - Xr) / (Vxr - Vxh) = (Yh - Yr) / (Vyr - Vyh)
  equals to
  (Xh - Xr) * (Vyr - Vyh) = (Yh - Yr) * (Vxr - Vxh)

  Now if we move all terms related to the rock left, we remain with:
  Yr * Vxr - Xr * Vyr = Yh * (Vxr - Vxh) - Xh * (Vyr - Vyh) - Xr * Vyh + Yr * Vxh

  Since starting position and velocity of the rock we throw  is the same for all the hailstones,
  Yr * Vxr - Xr * Vyr is the same for all of them.

  Therefore, we can introduce second hailstone, and equate right sides of first and second hailstones' equations:
  Yh1 * (Vxr - Vxh1) - Xh1 * (Vyr - Vyh1) - Xr * Vyh1 + Yr * Vxh1 = Yh2 * (Vxr - Vxh2) - Xh2 * (Vyr - Vyh2) - Xr * Vyh2 + Yr * Vxh2

  Now if we move to the right all known variables (hailstones positions and velocities),
  we will have unknowns and coefficients to the left.
  Vxr * (Yh2 - Yh1) + Vyr * (Xh1 - Xh2) + Xr * (Vyh1 - Vyh2) + Yr * (Vxh2 - Vxh1) = Xh1 * Vyh1 - Yh1 * Vxh1  - Xh2 * Vyh2 + Yh2 * Vxh2

  Here right side is completely known and can be calculated, and left side has parts like Vxr * (Yh2 - Yh1),
  where Vxr is unknown, and coefficient can be calculated.

  This is only 1 equation for coordinate pair X-Y, but we can do exactly the same thing for remaining 2 pairs: X-Z and Y-Z

  We have 3 equations now, but we need 6. What's left to do is pick other pair of hailstones
  (we've picked hailstone 0 and 1, so we can take 1 and 2),
  and create remaining 3 equations.

  Now having all needed 6 equations for 6 unknowns, employ Gaussian elimination to calculate the puzzle result.
  https://en.m.wikipedia.org/wiki/Gaussian_elimination

  6 equations created from problem example

  6a + b + 2x + y = 44,
  -8a + c + z = 36,
  8b + 6c + 2z = 40,
  6a - 2b + x - y = -9,
  12a - 2c + 2x - z = -2,
  -12b + 6c - 2y + z = -16

  Using Gaussian elimination, we get:

  a = -3
  b = 1
  c = 2
  x = 24
  y = 13
  z = 10

  x + y + z = 47
   */
  private static long part2(String[] input) {
    List<Hailstone> in = Arrays
      .stream(input)
      .map(Parse::doubles)
      .map(e -> new Hailstone(e.get(0), e.get(1), e.get(2), e.get(3), e.get(4), e.get(5)))
      .toList();

    double[][] matrix = new double[6][7];//6 equations with 6 unknowns plus augmented column
    for (int t = 0; t < 2; t++) {
      Hailstone hailstone1 = in.get(t);
      Hailstone hailstone2 = in.get(t + 1);
      Coords h1p = new Coords(hailstone1.sx, hailstone1.sy, hailstone1.sz);//Hailstone 1 position
      Coords h2p = new Coords(hailstone2.sx, hailstone2.sy, hailstone2.sz);//Hailstone 2 position
      Coords h1v = new Coords(hailstone1.vx, hailstone1.vy, hailstone1.vz);//Hailstone 1 velocity
      Coords h2v = new Coords(hailstone2.vx, hailstone2.vy, hailstone2.vz);//Hailstone 2 velocity

      //Right sides of the equations, can be calculated from the hailstones
      //We negate the result to be able to augment the matrix and solve using Gaussian elimination
      //So equation Vxr + Vyr + Vzr + Xr + Yr + Zr = RESULT
      //becomes matrix row
      //[Vxr, Vyr, Vzr, Xr, Yr, Zr, -RESULT]
      double xyProduct = -product(h1p.toArray(), h2p.toArray(), h1v.toArray(), h2v.toArray(), 0, 1);
      double xzProduct = -product(h1p.toArray(), h2p.toArray(), h1v.toArray(), h2v.toArray(), 0, 2);
      double yzProduct = -product(h1p.toArray(), h2p.toArray(), h1v.toArray(), h2v.toArray(), 2, 1);

      //When making a row for X and Y we put 0 for Z, and so on for others
      //Therefore for X and Y we have
      //[Vxr, Vyr, 0, Xr, Yr, 0, xyProduct]
      double[] xy = new double[]{h2p.y - h1p.y, h1p.x - h2p.x, 0, h1v.y - h2v.y, h2v.x - h1v.x, 0, xyProduct};//system for X and Y
      double[] xz = new double[]{h2p.z - h1p.z, 0, h1p.x - h2p.x, h1v.z - h2v.z, 0, h2v.x - h1v.x, xzProduct};//for X and Z
      double[] yz = new double[]{0, h1p.z - h2p.z, h2p.y - h1p.y, 0, h2v.z - h1v.z, h1v.y - h2v.y, yzProduct};//for Y and Z

      //collect into matrix
      for (int idx = 0; idx < 7; idx++) {
        matrix[t * 3][idx] = xy[idx];
        matrix[t * 3 + 1][idx] = xz[idx];
        matrix[t * 3 + 2][idx] = yz[idx];
      }
    }

    List<Double> result = gaussianElimination(matrix);

    return (long) (result.get(3) + result.get(4) + result.get(5));//only interested in positions
  }

  public static List<Double> gaussianElimination(double[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length - 1; // Exclude the augmented column

    for (int i = 0; i < rows; i++) {
      // Find pivot (largest number) for this column
      int pivotRow = i;
      for (int j = i + 1; j < rows; j++) {
        if (Math.abs(matrix[j][i]) > Math.abs(matrix[pivotRow][i])) {
          pivotRow = j;
        }
      }

      // Swap rows
      double[] temp = matrix[i];
      matrix[i] = matrix[pivotRow];
      matrix[pivotRow] = temp;

      // Make the pivot element 1
      double pivot = matrix[i][i];
      for (int j = i; j <= cols; j++) {
        matrix[i][j] /= pivot;
      }

      // Eliminate other rows
      for (int j = 0; j < rows; j++) {
        if (j != i) {
          double factor = matrix[j][i];
          for (int k = i; k <= cols; k++) {
            matrix[j][k] -= factor * matrix[i][k];
          }
        }
      }
    }
    //Take rightmost column which contains the answer
    return Arrays.stream(matrix).map(e -> -e[e.length - 1]).toList();
  }

  private static double product(double[] posA, double[] posB, double[] velA, double[] velB, int idx1, int idx2) {
    return posA[idx1] * velA[idx2] - posA[idx2] * velA[idx1] - posB[idx1] * velB[idx2] + posB[idx2] * velB[idx1];
  }

  public record Coords (double x, double y, double z) {
    public double[] toArray() {
      return new double[]{x, y, z};
    }
  }

  public record Hailstone (double sx, double sy, double sz, double vx, double vy, double vz, double m, double b) {
    //y = mx + b
    //m = dy / dx
    //b = y - mx
    public Hailstone (double sx, double sy, double sz, double vx, double vy, double vz) {
      this(sx, sy, sz, vx, vy, vz, vy / vx, sy - (vy / vx) * sx);
    }
  }

  private static void test() {
    var p1 = part1(getInput(true));
    System.out.println("Part 1 test: " + p1 + (p1 == expected1 ? " PASSED" : " FAILED"));

    var p2 = part2(getInput(true));
    System.out.println("Part 2 test: " + p2 + (p2 == expected2 ? " PASSED" : " FAILED"));
  }


  public static void main(String[] args) {
    test();
    System.out.println(part1(getInput(false)));
    System.out.println(part2(getInput(false)));
  }

  private static String[] getInput(boolean isTest) {
    return (isTest ? testStr : realStr).split("\n");
  }

  static int expected1 = 2;
  static int expected2 = 47;
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
