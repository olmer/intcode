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
        wrongNodes.remove("A0");
        wrongNodes.remove("C0");
        var r = moveRemainingWrongNodes(new HashMap<>(currentState), new ArrayList<>(wrongNodes));

        System.out.println(r);
    }

    private static Map<String, Integer> dp = new HashMap<>();

    private static int moveRemainingWrongNodes(Map<String, Pair<Character, Node>> currentState, List<String> wrongNodes) {
        var hashKey = currentState.hashCode() + " " + wrongNodes.hashCode();
        if (dp.containsKey(hashKey)) return dp.get(hashKey);

        var cost = 0;
        for (var wrongNodeName : wrongNodes) {
            var bestDestinationCost = Long.valueOf(Integer.MAX_VALUE);
            var wrongNode = currentState.get(wrongNodeName).getValue();
            var character = currentState.get(wrongNodeName).getKey();

            for (var destination : wrongNode.destinations) {
                var stateClone = new HashMap<>(currentState);

                var destinationCost = moveCostIfValid(character, wrongNodeName, destination, stateClone);
                if (destinationCost == -1) continue;

                destinationCost *= costMap.get(character);

                stateClone.put(wrongNodeName, new Pair<>(Character.MIN_VALUE, stateClone.get(wrongNodeName).getValue()));
                stateClone.put(destination, new Pair<>(character, stateClone.get(destination).getValue()));
                var wrongNodesClone = new ArrayList<>(wrongNodes);

                wrongNodesClone.remove(wrongNodeName);
                if (stateClone.get(destination).getValue().type == Node.HALL) {
                    wrongNodesClone.add(destination);
                }

                destinationCost += moveRemainingWrongNodes(stateClone, wrongNodesClone);

                if (destinationCost == 14460) {
                    System.out.println(wrongNodes + ": " + destinationCost);
                }


                if (destinationCost > 0 && destinationCost < bestDestinationCost) bestDestinationCost = destinationCost;
            }
            if (bestDestinationCost != Integer.MAX_VALUE) cost += bestDestinationCost;
        }

        if (cost == 14460) {
            System.out.println(wrongNodes.size() + ":!!! " + cost);
        }

        dp.put(hashKey, cost);

        return cost;
    }

    private static long moveCostIfValid(char c, String start, String end, Map<String, Pair<Character, Node>> currentState) {
        var endNode = currentState.get(end).getValue();
        if (endNode.type == Node.ROOM) {
            if (c != endNode.name.charAt(0)) return -1;
            if (endNode.name.charAt(1) == '1') {
                if (currentState.get(c + "0").getKey() != c) return -1;
            }
        }

        return moveCost(
            currentState.get(start).getValue(),
            currentState.get(end).getValue(),
            new HashSet<>(),
            currentState
        );
    }

    private static long moveCost(Node a, Node b, Set<Node> visited, Map<String, Pair<Character, Node>> currentState) {
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
