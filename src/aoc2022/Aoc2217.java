package aoc2022;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

import tools.Pair;

public class Aoc2217 {
  static boolean TEST = true;
  static boolean DEBUG = false;
  static int PART = 2;

  static int QLIM = 3000;

  static long END = PART == 1 ? 2022L : 1_000_000_000_000L;
  static long RESULT = 0;
  static long BLOCK = 0;
  static boolean DONE = false;

  static void print(short[][] field, long idx, int maxHeight) {
    int rows = 10;
    System.out.println("Shape: " + idx);
    for (int i = maxHeight; i >= 0 && i >= (maxHeight - rows); i--) {
      for (int j = 0; j < field[0].length; j++) {
        System.out.print(field[i][j] > 0 ? '#' : '.');
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
  }

  public static void main(String[] args) throws Exception {
    String jets = getInput();
    short[][][] shapes = new short[][][]{new short[][]{
      new short[]{1, 1, 1, 1},
      new short[]{0, 0, 0, 0},
      new short[]{0, 0, 0, 0},
      new short[]{0, 0, 0, 0},
    },
      new short[][]{
        new short[]{0, 1, 0, 0},
        new short[]{1, 1, 1, 0},
        new short[]{0, 1, 0, 0},
        new short[]{0, 0, 0, 0},
      },
      new short[][]{
        new short[]{1, 1, 1, 0},
        new short[]{0, 0, 1, 0},
        new short[]{0, 0, 1, 0},
        new short[]{0, 0, 0, 0},
      },
      new short[][]{
        new short[]{1, 0, 0, 0},
        new short[]{1, 0, 0, 0},
        new short[]{1, 0, 0, 0},
        new short[]{1, 0, 0, 0},
      },
      new short[][]{
        new short[]{1, 1, 0, 0},
        new short[]{1, 1, 0, 0},
        new short[]{0, 0, 0, 0},
        new short[]{0, 0, 0, 0},
      },
    };


    long rocksIdx = 0;
    int jetIdx = 0;
    long rocksLen = shapes.length;
    int jetLen = jets.length();

    short[][] field = new short[10][7];
    int maxHeight = 0;
    int lastHeight = 0;
    long maxHeightEnd = 0;

    Queue<short[]> q = new ArrayDeque<>();

    Map<String, Pair<Integer, Long>> states = new HashMap<>();

    boolean loopFound = false;

    while (true) {
      while (maxHeight + 10 >= field.length) {
        field = resize(field);
      }
//      print(field, rocksIdx, maxHeight);
      int curx = 2;
      int cury = maxHeight + 3;

      short[][] shape = shapes[(int) (rocksIdx++ % rocksLen)];

      while (true) {
        int dir = jets.charAt(jetIdx++ % jetLen) == '>' ? 1 : -1;

        boolean canShift = canShift(shape, field, dir, 0, curx, cury);

        if (canShift) {
          curx += dir;
        }

        canShift = canShift(shape, field, 0, -1, curx, cury);

        if (canShift) {
          cury--;
        } else {
          for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
              if (shape[i][j] > 0) {
                maxHeight = Math.max(maxHeight, cury + i + 1);
                field[cury + i][curx + j] = 1;
              }
            }
          }

          if (maxHeight >= QLIM) {
            for (int i = lastHeight; i < maxHeight; i++) {
              short[] r = new short[field[0].length];
              System.arraycopy(field[i], 0, r, 0, field[0].length);
              q.offer(r);
            }
            while (q.size() > QLIM) {
              q.poll();
            }

            String cache = (rocksIdx % rocksLen) + "," + (jetIdx % jetLen) + q.stream().map(Arrays::toString).collect(Collectors.joining());

            lastHeight = maxHeight;

            if (!loopFound && !DONE) {
              loopFound = states.containsKey(cache);
              if (loopFound) {
                BLOCK = maxHeight - states.get(cache).getKey();
              } else {
                states.put(cache, new Pair<>(maxHeight, rocksIdx));
              }
            }

            if (loopFound && !DONE) {
              if (DEBUG) {
                System.out.println("Next loop iteration " + maxHeight);
                System.out.println("Prev idx was " + states.get(cache));
                System.out.println("Block size " + BLOCK);
              }

              long prevRockIdx = states.get(cache).getValue();
              long loopsNum = END / (rocksIdx - prevRockIdx);
              maxHeightEnd = loopsNum * BLOCK;
              DONE = true;
            }
          }
          break;
        }
      }

      if (rocksIdx + maxHeightEnd >= END) {
        break;
      }
    }

    System.out.println(maxHeight);
    System.out.println(maxHeightEnd);
    System.out.println(rocksLen + maxHeightEnd);
    System.out.println(RESULT);
  }

  static boolean canShift(short[][] shape, short[][] field, int dirx, int diry, int curx, int cury) {
    boolean canShift = true;
    for (int i = 0; i < shape.length; i++) {
      if (!canShift) break;
      for (int j = 0; j < shape[0].length; j++) {
        if (shape[i][j] > 0) {
          if (curx + j + dirx >= field[0].length || curx + j + dirx < 0 || cury + i + diry < 0) {
            canShift = false;
            break;
          }
          if (field[cury + i + diry][curx + j + dirx] != 0) {
            canShift = false;
            break;
          }
        }
      }
    }
    return canShift;
  }

  static private short[][] resize(short[][] arr) {
    short[][] newAr = new short[arr.length * 2][7];
    for (int i = 0; i < arr.length; i++) {
      newAr[i] = Arrays.copyOf(arr[i], arr[i].length);
    }
    return newAr;
  }

  private static String getInput() {
    String testStr = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";

    String realStr = "";

    return (TEST ? testStr : realStr);
  }
}
