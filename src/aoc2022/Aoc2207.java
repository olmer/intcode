package aoc2022;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Aoc2207 {

  static Node root;

  static int max = 70_000_000;
  static int unused = 30_000_000;

  public static void main(String[] args) throws Exception {
    root = new Node("/", Type.Folder, null);
    Node curdir = root;

    String[] in = getInput();

    for (String s : in) {
      String[] com = s.split(" ");

      if (com[0].equals("$")) {
        if (com[1].equals("cd")) {
          if (com[2].equals("/")) {
            curdir = root;
          } else if (com[2].equals("..")) {
            curdir = curdir.parent;
          } else {
            if (!curdir.children.containsKey(com[2]))
              curdir.children.put(com[2], new Node(com[2], Type.Folder, 0, curdir));

            curdir = curdir.children.get(com[2]);
          }
        } else if (com[1].equals("ls")) {
          //just skip, we parse ls output in else
        } else {
          throw new Exception("no such command after $");
        }
      } else {
        if (com[0].equals("dir")) {
          if (!curdir.children.containsKey(com[1])) {
            curdir.children.put(com[1], new Node(com[1], Type.Folder, 0, curdir));
          }
        } else {
          if (!curdir.children.containsKey(com[1])) {
            curdir.children.put(com[1], new Node(com[1], Type.File, Integer.parseInt(com[0]), curdir));
          }
        }
      }
    }

    ArrayDeque<Node> q = new ArrayDeque<>();
    PriorityQueue<Integer> smallestSize = new PriorityQueue<>();
    smallestSize.offer(root.getSize());
    q.offer(root);

    while (!q.isEmpty()) {
      Node next = q.poll();
      smallestSize.offer(next.getSize());
      for (Node child : next.children.values()) {
        if (child.type == Type.Folder)
          q.offer(child);
      }
    }

    System.out.println("needed folder threshold: " + (max - root.getSize()));

    while (!smallestSize.isEmpty()) {
      int next = smallestSize.poll();
      int free = max - root.getSize();
      if (next + free >= unused) {
        System.out.println(next);
        break;
      }
    }
  }

  private static String[] getInput() {
    String s = "$ cd /\n" +
      "$ ls\n" +
      "dir a\n" +
      "14848514 b.txt\n" +
      "8504156 c.dat\n" +
      "dir d\n" +
      "$ cd a\n" +
      "$ ls\n" +
      "dir e\n" +
      "29116 f\n" +
      "2557 g\n" +
      "62596 h.lst\n" +
      "$ cd e\n" +
      "$ ls\n" +
      "584 i\n" +
      "$ cd ..\n" +
      "$ cd ..\n" +
      "$ cd d\n" +
      "$ ls\n" +
      "4060174 j\n" +
      "8033020 d.log\n" +
      "5626152 d.ext\n" +
      "7214296 k";
    return s.split("\n");
  }

  enum Type {
    Folder,
    File
  }
  static class Node {
    int size = 0;
    String name;

    Type type;

    Map<String, Node> children = new HashMap<>();
    Node parent;
    Node(String _name, Type _type, Node _parent) {name = _name; type = _type; parent = _parent;}
    Node(String _name, Type _type, int _size, Node _parent) {name = _name; type = _type; size = _size; parent = _parent;}

    public int getSize() {
      if (type == Type.File)
        return size;

      int s = 0;
      for (Node node : children.values())
        s += node.getSize();

      return s;
    }

    public String toString() {
      return name + " size: " + getSize() + " children: " + children.values().stream().map(e -> e.name).toList();
    }
  }
}
