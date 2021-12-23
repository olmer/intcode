import javafx.util.Pair;
import java.util.*;

public class Aoc2123 {
    static final Map<Character, Integer> costMap = new HashMap<>() {{
        put('A', 1);
        put('B', 10);
        put('C', 100);
        put('D', 1000);
    }};

    static class Node {
        static final boolean ROOM = true;
        static final boolean HALL = false;

        final boolean type;
        final boolean canStop;
        final String name;
        final List<Node> children = new ArrayList<>();
        final List<String> destinations = new ArrayList<>();

        Node(boolean type, String name, boolean canStop) {
            this.type = type;
            this.name = name;
            this.canStop = canStop;
        }

        @Override
        public String toString() {
            return "{" +
                "name='" + name + '\'' +
                ", type=" + (type ? "room" : "hall") +
                ", canStop=" + canStop +
                ", children=" + children.size() +
                '}';
        }
    }

    public static void main(String[] args) {
        /*

        #############
        #...........#
        ###D#B#D#A###
          #C#C#A#B#
          #########

         */

        var allNodes = new ArrayList<Node>();
        var root = new Node(Node.HALL, ".0", true);
        allNodes.add(root);
        var names = new String[]{"A", "B", "C", "D"};
        var prevNode = root;
        for (var i = 1; i <= 10; i++) {
            var canStop = i == 10 || i % 2 == 1;
            var newNode = new Node(Node.HALL, "." + i, canStop);
            prevNode.children.add(newNode);
            newNode.children.add(prevNode);
            prevNode = newNode;
            allNodes.add(newNode);
        }
        prevNode = null;
        for (var i = 0; i < 8; i++) {
            var newNode = new Node(Node.ROOM, names[i / 2] + i % 2, true);
            if (prevNode != null) {
                prevNode.children.add(newNode);
                newNode.children.add(prevNode);
                allNodes.get(i + 1).children.add(newNode);
                newNode.children.add(allNodes.get(i + 1));
                prevNode = null;
            } else {
                prevNode = newNode;
            }
            allNodes.add(newNode);
        }

        var currentState = new HashMap<String, Pair<Character, Node>>();
        var chars = "ABDCCBAD";
        var wrongNodes = new ArrayList<String>();

        for (var i = 0; i < allNodes.size(); i++) {
            var a = allNodes.get(i);
            if (!a.canStop) continue;
            for (var b : allNodes) {
                if (a == b || !b.canStop
                    || a.type == Node.HALL && b.type == Node.HALL
                ) continue;
                a.destinations.add(b.name);
            }
            if (i >= 11) wrongNodes.add(a.name);
        }

        for (var i = 0; i < allNodes.size(); i++) {
            var a = allNodes.get(i);
            currentState.put(a.name, new Pair<>(i >= 11 ? chars.charAt(i - 11) : Character.MIN_VALUE, a));
        }

        //end init
        var bestCost = Integer.MAX_VALUE;
        var r = moveToPossiblePlace(new HashMap<>(currentState), new ArrayList<>(wrongNodes));
        if (r > 0 && r < bestCost) bestCost = r;

        System.out.println(bestCost);
    }

    private static int moveToPossiblePlace(Map<String, Pair<Character, Node>> currentState, List<String> wrongNodes) {
        if (wrongNodes.size() == 0) return 0;
        var moveCost = 0;
        while (wrongNodes.size() > 0) {
            var moveWasSuccessful = false;
            for (var node : wrongNodes) {
                var wrongNodesClone = new ArrayList<>(wrongNodes);
                wrongNodesClone.remove(node);
                var c = currentState.get(node).getKey();
                var shouldBreak = false;
                for (var destination : currentState.get(node).getValue().destinations) {
                    var moveCostNew = moveCostIfValid(c, node, destination, currentState);
                    if (moveCostNew == -1) continue;

                    moveWasSuccessful = true;
                    moveCostNew *= costMap.get(c);

                    currentState.put(node, new Pair<>(Character.MIN_VALUE, currentState.get(node).getValue()));
                    currentState.put(destination, new Pair<>(c, currentState.get(destination).getValue()));

                    if (currentState.get(destination).getValue().type == Node.HALL) {
                        wrongNodesClone.add(destination);
                    }

                    var r = moveToPossiblePlace(new HashMap<>(currentState), wrongNodesClone);
                    moveCostNew += r;

                    moveCost += moveCostNew;
                    shouldBreak = true;
                    break;//todo check all destinations?
                }
                if (shouldBreak) break;
            }
            if (!moveWasSuccessful) return -1;
        }

        return moveCost;
    }

    private static int moveCostIfValid(char c, String start, String end, Map<String, Pair<Character, Node>> currentState) {
        var endNode = currentState.get(end).getValue();
        if (endNode.type == Node.ROOM) {
            if (c != endNode.name.charAt(0)) return -1;
            if (endNode.name.charAt(1) == '1') {
                if (currentState.get(String.valueOf(c) + "0").getKey() != c) return -1;
            }
        }

        return moveCost(
            currentState.get(start).getValue(),
            currentState.get(end).getValue(),
            new HashSet<>(),
            currentState
        );
    }

    private static int moveCost(Node a, Node b, Set<Node> visited, Map<String, Pair<Character, Node>> currentState) {
        if (a == b) return 0;
        for (var child : a.children) {
            if (visited.contains(child)) continue;
            if (currentState.get(child.name).getKey() != Character.MIN_VALUE) continue;
            visited.add(child);
            var d = moveCost(child, b, visited, currentState);
            if (d == -1) continue;
            return d + 1;
        }
        return -1;
    }
}
