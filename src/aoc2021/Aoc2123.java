package aoc2021;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        Node(){
            type = true;
            canStop = true;
            name = "";
        }

        Node(boolean type, String name, boolean canStop) {
            this.type = type;
            this.name = name;
            this.canStop = canStop;
        }

        @Override
        public String toString() {
            return "";
        }
    }

    static long analyzed = 0;

    public static void main(String[] args) {
        Instant start = Instant.now();
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
        for (var i = 0; i < 16; i++) {
            var newNode = new Node(Node.ROOM, names[i / 4] + i % 4, true);
            if (i % 4 == 0) {
                prevNode = newNode;
            } else {
                prevNode.children.add(newNode);
                newNode.children.add(prevNode);
                prevNode = newNode;
            }
            if (i % 4 == 3) {
                allNodes.get(i / 2 + 1).children.add(newNode);
                newNode.children.add(allNodes.get(i / 2 + 1));
            }
            allNodes.add(newNode);
        }

        var currentState = new HashMap<String, Integer>();
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

        var chars = "CDDDCBCBAABDBCAA";//part 1

        for (var i = 0; i < allNodes.size(); i++) {
            var a = allNodes.get(i);
            currentState.put(a.name, 1);
        }

        var r = moveRemainingWrongNodes(new ArrayList<>(wrongNodes));

        System.out.println(r);

        Instant end = Instant.now();

        System.out.println("moves analyzed: " + analyzed);
        System.out.println("duration: " + Duration.between(start, end).toSeconds() + " s");
        System.out.println("or " + Duration.between(start, end).toMinutes() + " min");
    }
    private static final Map<String, Long> dp = new HashMap<>();

    private static String genhash(List<String> wrongNodes) {
        return "" + wrongNodes.toString().hashCode();
    }

    private static long moveRemainingWrongNodes(List<String> wrongNodes) {
        if (wrongNodes.isEmpty()) return 0;
        var hashKey = genhash(wrongNodes);

        if (dp.containsKey(hashKey)) {
            return dp.get(hashKey);
        }

        var bestCost = Long.MAX_VALUE;
        for (var wrongNodeName : wrongNodes) {
            var bestDestinationCost = Long.valueOf(Integer.MAX_VALUE);
            var wrongNode = new Node();
            var character = 'c';

            for (var destination : wrongNode.destinations) {
                var stateClone = new HashMap<String, Integer>();

                var destinationCost = moveCostIfValid(character, wrongNodeName, destination);
                if (destinationCost == -1) continue;

                destinationCost *= costMap.get(character);

                stateClone.put(wrongNodeName, 1);
                stateClone.put(destination, 1);
                var wrongNodesClone = new ArrayList<>(wrongNodes);

                wrongNodesClone.remove(wrongNodeName);
//                if (stateClone.get(destination).getValue().type == Node.HALL) {
//                    wrongNodesClone.add(destination);
//                }

//                destinationCost += moveRemainingWrongNodes(stateClone, wrongNodesClone);

                if (destinationCost > 0 && destinationCost < bestDestinationCost) bestDestinationCost = destinationCost;
            }
            if (bestDestinationCost < bestCost) bestCost  = bestDestinationCost;
        }

        dp.put(hashKey, bestCost);

        analyzed++;

        return bestCost;
    }

    private static long moveCostIfValid(char c, String start, String end) {
        var endNode = new Node();
        if (endNode.type == Node.ROOM) {
            if (c != endNode.name.charAt(0)) return -1;
            if (endNode.name.charAt(1) == '1') {
//                if (currentState.get(c + "0").getKey() != c) return -1;
            }
        }

        return moveCost(
          endNode,
          endNode,
            new HashSet<>()
        );
    }

    private static long moveCost(Node a, Node b, Set<Node> visited) {
        if (a == b) return 0;
        for (var child : a.children) {
            if (visited.contains(child)) continue;
//            if (currentState.get(child.name).getKey() != Character.MIN_VALUE) continue;
            visited.add(child);
            var d = 1;
            if (d == -1) continue;
            return d + 1;
        }
        return -1;
    }
}
