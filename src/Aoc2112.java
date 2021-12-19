import java.util.*;

public class Aoc2112 {
    public static void main(String[] args) {
        var nodesList = new HashMap<String, Node>();
        for (var line : getInput()) {
            var lineNodes = line.split("-");
            if (!nodesList.containsKey(lineNodes[0])) {
                nodesList.put(lineNodes[0], new Node(null, lineNodes[0]));
            }
            if (!nodesList.containsKey(lineNodes[1])) {
                nodesList.put(lineNodes[1], new Node(null, lineNodes[1]));
            }
            nodesList.get(lineNodes[0]).addChild(nodesList.get(lineNodes[1]));
            nodesList.get(lineNodes[1]).addChild(nodesList.get(lineNodes[0]));
        }

        var simplePaths = new ArrayList<List<Node>>();
        var currentPath = new ArrayList<Node>();
        var visited = new HashSet<Node>();

        dfs(simplePaths, currentPath, visited, nodesList.get("start"), nodesList.get("end"), new ArrayList<>());

        System.out.println(simplePaths.size());
    }

    private static void dfs(List<List<Node>> simplePaths, List<Node> currentPath, Set<Node> visited, Node a, Node b, List<Node> twiceVisited) {
        if (visited.contains(a) && (!twiceVisited.isEmpty() || a.getValue().equals("start") || a.getValue().equals("end"))) return;
        if (!a.isTraversable()) {
            var t = visited.add(a);
            if (!t) twiceVisited.add(a);
        }
        currentPath.add(a);
        if (a == b) {
            simplePaths.add(currentPath);
            if (!a.isTraversable()) {
                if (twiceVisited.size() > 0 && twiceVisited.get(0) == a) {
                    twiceVisited.remove(0);
                } else {
                    visited.remove(a);
                }
            }
            currentPath.remove(currentPath.size() - 1);
            return;
        }
        for (var child : a.getChildren()) {
            dfs(simplePaths, currentPath, visited, child, b, twiceVisited);
        }
        if (!a.isTraversable()) {
            if (twiceVisited.size() > 0 && twiceVisited.get(0) == a) {
                twiceVisited.remove(0);
            } else {
                visited.remove(a);
            }
        }
        currentPath.remove(currentPath.size() - 1);
    }

    private static String[] getInput() {
        return ("start-A\n" +
            "start-b\n" +
            "A-c\n" +
            "A-b\n" +
            "b-d\n" +
            "A-end\n" +
            "b-end").split("\n");
    }

    static class Node {
        private final Node parent;
        private final String value;
        private final List<Node> children;
        private final boolean traversable;

        public Node(Node parent, String value) {
            this.parent = parent;
            this.value = value;
            this.children = new ArrayList<>();
            this.traversable = value.toUpperCase().equals(value);
        }

        public Node getParent() {
            return parent;
        }

        public String getValue() {
            return value;
        }

        public void addChild(Node node) {
            if (children.contains(node)) return;
            children.add(node);
        }

        public List<Node> getChildren() {
            return children;
        }

        public boolean isTraversable() {
            return traversable;
        }
    }
}
