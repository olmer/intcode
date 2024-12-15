package aoc2024;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import tools.Pair;

public class Aoc2415 extends AbstractAoc {
  void print(char[][] grid) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        System.out.print(grid[i][j]);
      }
      System.out.println();
    }
  }

  long part1(String[] in) {
    // join all the lines into one string
    String[] tgrid = String.join("\n", in).split("\n\n")[0].split("\n");
    String path = String.join("\n", in).split("\n\n")[1];
    int[][] grid = new int[tgrid.length][tgrid[0].length()];
    int posx = 0;
    int posy = 0;
    for (int i = 0; i < tgrid.length; i++) {
      for (int j = 0; j < tgrid[i].length(); j++) {
        grid[i][j] = tgrid[i].charAt(j) == '#' ? 1 : (tgrid[i].charAt(j) == 'O' ? 2 : 0);
        if (tgrid[i].charAt(j) == '@') {
          posx = j;
          posy = i;
        }
      }
    }
    for (int i = 0; i < path.length(); i++) {
      char c = path.charAt(i);
      int[] dir = {0, 0};
      if (c == '^') {
        dir[1] = -1;
      } else if (c == 'v') {
        dir[1] = 1;
      } else if (c == '<') {
        dir[0] = -1;
      } else if (c == '>') {
        dir[0] = 1;
      }
      int tx = posx;
      int ty = posy;
      while (grid[ty + dir[1]][tx + dir[0]] == 2) {
        tx += dir[0];
        ty += dir[1];
      }
      if (grid[ty + dir[1]][tx + dir[0]] == 1) {
        continue;
      }
      grid[ty + dir[1]][tx + dir[0]] = 2;
      grid[posy][posx] = 0;
      grid[posy + dir[1]][posx + dir[0]] = 3;
      posx += dir[0];
      posy += dir[1];
//      System.out.println(c);
    }
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
//        System.out.print(grid[i][j] == 1 ? '#' : (grid[i][j] == 2 ? 'O' : (grid[i][j] == 3 ? '@' : '.')));
      }
//      System.out.println();
    }

    // result
    long r = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == 2) {
          r += 100 * i + j;
        }
      }
    }
    return r;
  }

  long part2(String[] in) {
    // join all the lines into one string
    String[] tgrid = String.join("\n", in).split("\n\n")[0].split("\n");
    String path = String.join("\n", in).split("\n\n")[1];
    char[][] grid = new char[tgrid.length][tgrid[0].length() * 2];
    int posx = 0;
    int posy = 0;
    for (int i = 0; i < tgrid.length; i++) {
      for (int j = 0; j < tgrid[i].length(); j++) {
        grid[i][j * 2] = tgrid[i].charAt(j);
        grid[i][j * 2 + 1] = tgrid[i].charAt(j);
        if (tgrid[i].charAt(j) == '@') {
          posx = j * 2;
          posy = i;
          grid[i][j * 2 + 1] = '.';
        } else if (tgrid[i].charAt(j) == 'O') {
          grid[i][j * 2] = '[';
          grid[i][j * 2 + 1] = ']';
        }
      }
    }
//    print(grid);
    for (int i = 0; i < path.length(); i++) {
      char c = path.charAt(i);
      int[] dir = {0, 0};
      if (c == '^') {
        dir[1] = -1;
      } else if (c == 'v') {
        dir[1] = 1;
      } else if (c == '<') {
        dir[0] = -1;
      } else if (c == '>') {
        dir[0] = 1;
      }
      if (grid[posy + dir[1]][posx + dir[0]] == '#') {
        continue;
      }
      int tx = posx;
      int ty = posy;

      if (dir[1] == 0) {
        // if we go left or right
        while (grid[ty + dir[1]][tx + dir[0]] == '[' || grid[ty + dir[1]][tx + dir[0]] == ']') {
          tx += dir[0];
          ty += dir[1];
        }
        if (grid[ty + dir[1]][tx + dir[0]] == '#') {
          continue;
        }
        while (tx != posx - dir[0] || ty != posy - dir[1]) {
          grid[ty + dir[1]][tx + dir[0]] = grid[ty][tx];
          tx -= dir[0];
          ty -= dir[1];
        }
        grid[posy][posx] = '.';
      } else {
        // if we go up or down
        //
        Set<Pair<Integer, Integer>> boxes = new HashSet<>();
        boolean newBoxesAdded = false;
        if (grid[ty + dir[1]][tx + dir[0]] == '[') {
          boxes.add(new Pair<>(ty + dir[1], tx + dir[0]));
          newBoxesAdded = true;
        } else if (grid[ty + dir[1]][tx + dir[0]] == ']') {
          boxes.add(new Pair<>(ty + dir[1], tx + dir[0] - 1));
          newBoxesAdded = true;
        }
        boolean blocked = false;
        while (newBoxesAdded && !blocked) {
          newBoxesAdded = false;
          Set<Pair<Integer, Integer>> newBoxes = new HashSet<>(boxes);
          for (Pair<Integer, Integer> box : boxes) {
            if (grid[box.getKey() + dir[1]][box.getValue() + dir[0]] == '#' || grid[box.getKey() + dir[1]][box.getValue() + dir[0] + 1] == '#') {
              blocked = true;
              break;
            }
            if (grid[box.getKey() + dir[1]][box.getValue() + dir[0]] == '[') {
              if (newBoxes.add(new Pair<>(box.getKey() + dir[1], box.getValue() + dir[0]))) {
                newBoxesAdded = true;
              }
            }
            if (grid[box.getKey() + dir[1]][box.getValue() + dir[0]] == ']') {
              if (newBoxes.add(new Pair<>(box.getKey() + dir[1], box.getValue() + dir[0] - 1))) {
                newBoxesAdded = true;
              }
            }
            if (grid[box.getKey() + dir[1]][box.getValue() + dir[0] + 1] == '[') {
              if (newBoxes.add(new Pair<>(box.getKey() + dir[1], box.getValue() + dir[0] + 1))) {
                newBoxesAdded = true;
              }
            }
            if (grid[box.getKey() + dir[1]][box.getValue() + dir[0] + 1] == ']') {
              if (newBoxes.add(new Pair<>(box.getKey() + dir[1], box.getValue() + dir[0] - 1 + 1))) {
                newBoxesAdded = true;
              }
            }
          }
          boxes = newBoxes;
        }

        if (blocked) {
          continue;
        }

        char[][] newgrid = new char[grid.length][grid[0].length];
        for (int j = 0; j < grid.length; j++) {
          newgrid[j] = Arrays.copyOf(grid[j], grid[j].length);
        }

        PriorityQueue<Pair<Integer, Integer>> boxics = new PriorityQueue<>((a, b) ->
            dir[1] > 0 ? b.getKey() - a.getKey() : a.getKey() - b.getKey());

        boxics.addAll(boxes);

        while (boxics.size() > 0) {
          Pair<Integer, Integer> box = boxics.poll();
          newgrid[box.getKey() + dir[1]][box.getValue() + dir[0]] = '[';
          newgrid[box.getKey() + dir[1]][box.getValue() + dir[0] + 1] = ']';
          newgrid[box.getKey()][box.getValue()] = '.';
          newgrid[box.getKey()][box.getValue() + 1] = '.';
        }
        grid = newgrid;
        grid[posy + dir[1]][posx + dir[0]] = grid[posy][posx];
        grid[posy][posx] = '.';
      }
      posx += dir[0];
      posy += dir[1];
//      print(grid);
    }

    // result
    long r = 0;
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        if (grid[i][j] == '[') {
          r += 100 * i + j;
        }
      }
    }
    return r;
  }

  public static void main(String[] args) {
    (new Aoc2415()).start();
  }

  @Override
  long getTestExpected1() {
    return 0;
  }

  @Override
  long getTestExpected2() {
    return 0;
  }

  @Override
  String getTestInput() {
    return "##########\n" +
        "#..O..O.O#\n" +
        "#......O.#\n" +
        "#.OO..O.O#\n" +
        "#..O@..O.#\n" +
        "#O#..O...#\n" +
        "#O..O..O.#\n" +
        "#.OO.O.OO#\n" +
        "#....O...#\n" +
        "##########\n" +
        "\n" +
        "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^\n" +
        "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v\n" +
        "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<\n" +
        "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^\n" +
        "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><\n" +
        "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^\n" +
        ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^\n" +
        "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>\n" +
        "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>\n" +
        "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^";
  }

  @Override
  String getRealInput() {
    return "##########\n" +
        "#..O..O.O#\n" +
        "#......O.#\n" +
        "#.OO..O.O#\n" +
        "#..O@..O.#\n" +
        "#O#..O...#\n" +
        "#O..O..O.#\n" +
        "#.OO.O.OO#\n" +
        "#....O...#\n" +
        "##########\n" +
        "\n" +
        "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^\n" +
        "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v\n" +
        "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<\n" +
        "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^\n" +
        "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><\n" +
        "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^\n" +
        ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^\n" +
        "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>\n" +
        "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>\n" +
        "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^";
  }
}
