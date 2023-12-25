package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KargersAlgorithm {

  // Class to represent a graph edge
  static class Edge {
    int src, dest;

    Edge(int src, int dest) {
      this.src = src;
      this.dest = dest;
    }
  }

  // Class to represent a connected, undirected graph with vertices and edges
  public static class Graph {
    int vertices, edges;
    List<Edge> edgeList;

    public Graph(int vertices, int edges) {
      this.vertices = vertices;
      this.edges = edges;
      this.edgeList = new ArrayList<>();
    }

    public void addEdge(int src, int dest) {
      edgeList.add(new Edge(src, dest));
    }
  }

  // Function to find the root of the subset containing a given element
  public static int find(int[] parent, int i) {
    if (parent[i] == -1)
      return i;
    return find(parent, parent[i]);
  }

  // Function to perform union of two subsets
  static void union(int[] parent, int x, int y) {
    int xRoot = find(parent, x);
    int yRoot = find(parent, y);
    parent[xRoot] = yRoot;
  }

  // Function to apply Karger's algorithm and find the minimum cut
  public static Pair<int[], Integer> kargerMinCut(Graph graph) {
    int vertices = graph.vertices;
    int edges = graph.edges;

    // Create an array to represent subsets and initialize each subset as a single element set
    int[] parent = new int[vertices];
    Arrays.fill(parent, -1);

    // Keep contracting the graph until only two vertices remain
    while (vertices > 2) {
      // Pick a random edge
      int randomEdge = new Random().nextInt(edges);

      // Find the subsets of the source and destination vertices
      int subset1 = find(parent, graph.edgeList.get(randomEdge).src);
      int subset2 = find(parent, graph.edgeList.get(randomEdge).dest);

      // If the two vertices are not in the same subset, contract the edge
      if (subset1 != subset2) {
        // Decrement the number of vertices
        vertices--;

        // Union the subsets
        union(parent, subset1, subset2);
      }
    }

    // Count the number of crossing edges between the two subsets
    int cutCount = 0;
    for (int i = 0; i < edges; i++) {
      int src = find(parent, graph.edgeList.get(i).src);
      int dest = find(parent, graph.edgeList.get(i).dest);
      if (src != dest) {
        cutCount++;
      }
    }

    return new Pair<>(parent, cutCount);
  }

  public static void main(String[] args) {
    // Create a sample graph
    Graph graph = new Graph(4, 5);
    graph.addEdge(0, 1);
    graph.addEdge(0, 2);
    graph.addEdge(0, 3);
    graph.addEdge(1, 3);
    graph.addEdge(2, 3);

    // Run Karger's algorithm
    var minCut = kargerMinCut(graph);

    // Display the result
    System.out.println("Minimum Cut: " + minCut);
  }
}
