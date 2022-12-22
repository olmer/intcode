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
  static int PART = 2;
  static int Q_SIZE = 8;
  static boolean BLOCK_CALCULATED = false;

  static Map<String, Pair<Long, Integer>> knownStates = new HashMap<>();
  static Queue<short[]> lastRows = new ArrayDeque<>();

  static long END = PART == 1 ? 2022L : 1_000_000_000_000L;

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
    int prevHeight;
    long heightProduced = 0;

    while (true) {
      while (maxHeight + 10 >= field.length) {
        field = resize(field);
      }
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
          prevHeight = maxHeight;
          for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
              if (shape[i][j] > 0) {
                maxHeight = Math.max(maxHeight, cury + i + 1);
                field[cury + i][curx + j] = 1;
              }
            }
          }

          for (int i = prevHeight; i < maxHeight; i++) {
            lastRows.offer(field[i]);
          }
          while (lastRows.size() > Q_SIZE) {
            lastRows.poll();
          }
          if (lastRows.size() < Q_SIZE) {
            break;
          }
          String cacheKey = jetIdx % jetLen + "_" + rocksIdx % rocksLen + "_" + lastRows.stream().map(Arrays::toString).collect(Collectors.joining());
          if (!BLOCK_CALCULATED && knownStates.containsKey(cacheKey)) {
            BLOCK_CALCULATED = true;
            var dat = knownStates.get(cacheKey);
            long blockSizeInRocks = rocksIdx - dat.getKey();
            int blockSizeInHeight = maxHeight - 1 - dat.getValue();

            long rocksBlockRequired = (END - rocksIdx) / blockSizeInRocks;
            rocksIdx = rocksIdx + rocksBlockRequired * blockSizeInRocks;

            heightProduced = rocksBlockRequired * blockSizeInHeight;
          } else {
            knownStates.put(cacheKey, new Pair<>(rocksIdx, maxHeight - 1));
          }

          break;
        }
      }

      if (rocksIdx >= END) {
        break;
      }
    }

    System.out.println(maxHeight + heightProduced);
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
