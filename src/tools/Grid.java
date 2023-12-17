package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Grid {
  public static List<Character> getValidNeighbours(int x, int y, String[] data) {
    List<Character> r = new ArrayList<>();
    for (var coords : getValidNeighbourCoordinates(x, y, data)) {
      r.add(data[coords.getValue()].charAt(coords.getKey()));
    }
    return r;
  }
  public static List<Pair<Character, Pair<Integer, Integer>>> getValidNeighboursWithCoords(int x, int y, String[] data) {
    return getValidSpecificNeighboursWithCoords(x, y, data, Direction.values());
  }

  public static List<Pair<Character, Pair<Integer, Integer>>> getValidSpecificNeighboursWithCoords(int x, int y, String[] data, Direction... dirs) {
    List<Pair<Character, Pair<Integer, Integer>>> r = new ArrayList<>();
    for (var coords : getValidSpecificNeighbourCoordinates(x, y, data, dirs)) {
      r.add(new Pair<>(data[coords.getValue()].charAt(coords.getKey()), coords));
    }
    return r;
  }

  public static Map<Direction, Pair<Character, Pair<Integer, Integer>>> getValidSpecificNeighboursWithCoordsMapped(int x, int y, String[] data, Direction... dirs) {
    Map<Direction, Pair<Character, Pair<Integer, Integer>>> r = new HashMap<>();
    for (var entry : getValidSpecificNeighbourCoordinatesMapped(x, y, data, dirs).entrySet()) {
      r.put(entry.getKey(), new Pair<>(data[entry.getValue().getValue()].charAt(entry.getValue().getKey()), entry.getValue()));
    }
    return r;
  }

  private static List<Pair<Integer, Integer>> getValidNeighbourCoordinates(int x, int y, String[] data) {
    return getValidSpecificNeighbourCoordinates(x, y, data, Direction.values());
  }

  private static List<Pair<Integer, Integer>> getValidSpecificNeighbourCoordinates(int x, int y, String[] data, Direction... dirs) {
    List<Pair<Integer, Integer>> r = new ArrayList<>();
    for (Direction dir : dirs) {
      int i = NEIGHBOURS.get(dir).getKey();
      int j = NEIGHBOURS.get(dir).getValue();
      int ii = x + i;
      int jj = y + j;
      if (i == 0 && j == 0 || ii < 0 || jj < 0 || ii >= data[0].length() || jj >= data.length) {
        continue;
      }
      r.add(new Pair<>(ii, jj));
    }
    return r;
  }

  public static Map<Direction, Pair<Integer, Integer>> getValidSpecificNeighbourCoordinatesMapped(int x, int y, String[] data, Direction... dirs) {
    Map<Direction, Pair<Integer, Integer>> r = new HashMap<>();
    if (dirs == null) return r;
    for (Direction dir : dirs) {
      int i = NEIGHBOURS.get(dir).getKey();
      int j = NEIGHBOURS.get(dir).getValue();
      int ii = x + i;
      int jj = y + j;
      if (i == 0 && j == 0 || ii < 0 || jj < 0 || ii >= data[0].length() || jj >= data.length) {
        continue;
      }
      r.put(dir, new Pair<>(ii, jj));
    }
    return r;
  }

  public static Map<Direction, List<Pair<Character, Pair<Integer, Integer>>>> getBeams(int x, int y, String[] data, int fromIncl, int toExcl, Direction... dirs) {
    Map<Direction, List<Pair<Character, Pair<Integer, Integer>>>> r = new HashMap<>();
    for (Direction dir : dirs) {
      r.put(dir, getBeam(x, y, data, fromIncl, toExcl, dir));
    }
    return r;
  }

  public static Map<Direction, List<Pair<Character, Pair<Integer, Integer>>>> getBeams(int x, int y, String[] data, int len, Direction... dirs) {
    return getBeams(x, y, data, 0, len, dirs);
  }

  public static List<Pair<Character, Pair<Integer, Integer>>> getBeam(int x, int y, String[] data, int fromIncl, int toExcl, Direction dir) {
    List<Pair<Character, Pair<Integer, Integer>>> r = new ArrayList<>();
    int newx = x;
    int newy = y;
    for (int i = 0; i < toExcl; i++) {
      newx += NEIGHBOURS.get(dir).getKey();
      newy += NEIGHBOURS.get(dir).getValue();
      if (i < fromIncl) {
        continue;
      }
      if (newx < 0 || newy < 0 || newx >= data[0].length() || newy >= data.length) {
        continue;
      }
      r.add(new Pair<>(data[newy].charAt(newx), new Pair<>(newx, newy)));
    }
    return r;
  }

  public enum Direction {
    NW, N, NE, W, E, SW, S, SE
  }

  public static final Direction[] CARDINAL = {Direction.N, Direction.W, Direction.E, Direction.S};

  public static Map<Direction, Direction> OPPOSITES = new HashMap<>(){{
    put(Direction.N, Direction.S);
    put(Direction.S, Direction.N);
    put(Direction.E, Direction.W);
    put(Direction.W, Direction.E);
  }};

  public static Map<Direction, Direction[]> POSSIBLE_DIRS_LEFT_RIGHT = Arrays
    .stream(Grid.CARDINAL)
    .collect(Collectors.toMap(
      ignored -> ignored,
      cardinalDir -> Arrays.stream(Grid.CARDINAL)
        .filter(ee -> ee != cardinalDir && ee != OPPOSITES.get(cardinalDir)).toArray(Direction[]::new)
    ));

  public static final Map<Direction, Pair<Integer, Integer>> NEIGHBOURS = new HashMap<>() {{
    put(Direction.NW, new Pair<>(-1, -1));
    put(Direction.N, new Pair<>(0, -1));
    put(Direction.NE, new Pair<>(1, -1));
    put(Direction.W, new Pair<>(-1, 0));
    put(Direction.E, new Pair<>(1, 0));
    put(Direction.SW, new Pair<>(-1, 1));
    put(Direction.S, new Pair<>(0, 1));
    put(Direction.SE, new Pair<>(1, 1));
  }};
}
