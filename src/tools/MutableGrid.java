package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MutableGrid {
  private static char EMPTY_TILE = '.';
  private static Set<Character> WALKABLE = Set.of('.');
  private static Set<Character> NOT_WALKABLE = Set.of('O', '#');

  private char[][] data;

  public MutableGrid(String[] input) {
    data = Arrays.stream(input).map(String::toCharArray).toArray(char[][]::new);
  }

  public Pair<Integer, Integer> move(Pair<Integer, Integer> coord, Direction dir) {
    var offset = NEIGHBOURS.get(dir);
    int newx = coord.getKey() + offset.getKey();
    int newy = coord.getValue() + offset.getValue();
    if (newx < 0 || newy < 0 || newy >= data.length || newx >= data[newy].length) {
      return coord;
    }
    if (NOT_WALKABLE.contains(data[newy][newx])) {
      return coord;
    }
    char c = data[coord.getValue()][coord.getKey()];
    data[coord.getValue()][coord.getKey()] = EMPTY_TILE;
    data[newy][newx] = c;
    return new Pair<>(newx, newy);
  }

  public List<Pair<Integer, Integer>> find(char c) {
    List<Pair<Integer, Integer>> r = new ArrayList<>();
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        if (data[i][j] == c) {
          r.add(new Pair<>(j, i));
        }
      }
    }

    return r;
  }

  public List<Character> getValidNeighbours(int x, int y) {
    List<Character> r = new ArrayList<>();
    for (var coords : getValidNeighbourCoordinates(x, y)) {
      r.add(data[coords.getValue()][coords.getKey()]);
    }
    return r;
  }

  public List<Pair<Character, Pair<Integer, Integer>>> getValidNeighboursWithCoords(int x, int y, Direction... dirs) {
    List<Pair<Character, Pair<Integer, Integer>>> r = new ArrayList<>();
    for (var coords : getValidNeighbourCoordinates(x, y, dirs)) {
      r.add(new Pair<>(data[coords.getValue()][coords.getKey()], coords));
    }
    return r;
  }

  private List<Pair<Integer, Integer>> getValidNeighbourCoordinates(int x, int y) {
    return getValidNeighbourCoordinates(x, y, Direction.values());
  }

  private List<Pair<Integer, Integer>> getValidNeighbourCoordinates(int x, int y, Direction... dirs) {
    List<Pair<Integer, Integer>> r = new ArrayList<>();
    for (Direction dir : dirs) {
      int i = NEIGHBOURS.get(dir).getKey();
      int j = NEIGHBOURS.get(dir).getValue();
      int ii = x + i;
      int jj = y + j;
      if (i == 0 && j == 0 || ii < 0 || jj < 0 || ii >= this.rows() || jj >= this.cols()) {
        continue;
      }
      r.add(new Pair<>(ii, jj));
    }
    return r;
  }

  public enum Direction {
    NW, N, NE, W, E, SW, S, SE
  }

  public static final Direction[] CARDINAL = {Direction.N, Direction.W, Direction.E, Direction.S};

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

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (var i = 0; i < data.length; i++) {
      for (var j = 0; j < data[i].length; j++) {
        s.append(data[i][j]);
      }
      s.append('\n');
    }
    return s.toString();
  }

  public int cols() {
    return data[0].length;
  }
  public int rows() {
    return data.length;
  }
  public char get(int x, int y) {
    return data[y][x];
  }
  public void set(int x, int y, char c) {
    data[y][x] = c;
  }
  public void set(Pair<Integer, Integer> coord, char c) {
    set(coord.getKey(), coord.getValue(), c);
  }
}
