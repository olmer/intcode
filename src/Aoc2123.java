import javafx.util.Pair;
import java.util.*;

public class Aoc2123 {
    static class Node {
        static final boolean ROOM = true;
        static final boolean SPACE = false;

        final boolean type;
        final boolean canStop;
        final String name;
        char content;
        final List<Node> children = new ArrayList<>();
        final List<Pair<Integer, Node>> distances = new ArrayList<>();

        Node(boolean type, String name, boolean canStop) {
            this.type = type;
            this.name = name;
            this.canStop = canStop;
        }

        @Override
        public String toString() {
            return "{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", content=" + content +
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

        var costMap = new HashMap<Character, Integer>(){{
            put('A', 1);
            put('B', 10);
            put('C', 100);
            put('D', 1000);
        }};

        var allNodes = new ArrayList<Node>();
        var root = new Node(Node.SPACE, ".0", true);
        allNodes.add(root);
        var names = new String[]{"A", "B", "C", "D"};
        var prevNode = root;
        for (var i = 1; i <= 10; i++) {
            var canStop = i == 10 || i % 2 == 1;
            var newNode = new Node(Node.SPACE, "." + i, canStop);
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

        var input = "ABDCCBAD";
        for (var i = 0; i < input.length(); i++) {
            allNodes.get(i + 11).content = input.charAt(i);
        }

        for (var nodea : allNodes) {
            if (!nodea.canStop) continue;
            for (var nodeb : allNodes) {
                if (nodea == nodeb || !nodeb.canStop
                    || nodea.type == Node.SPACE && nodeb.type == Node.SPACE
                ) continue;
                nodea.distances.add( new Pair<>(distance(nodea, nodeb, new HashSet<>()), nodeb));
            }
            nodea.distances.sort(Comparator.comparing(Pair::getKey));
        }


        //end init

        Stack<Node> misplacedNodes = new Stack<>();
        misplacedNodes.addAll(allNodes.subList(11, allNodes.size()));

        var score = 0;
        while (misplacedNodes.size() > 0) {
            var nextNode = misplacedNodes.pop();
            var maybeDestination = findWhereToMove(nextNode.distances, Node.ROOM, nextNode.content);
            if (maybeDestination.isEmpty()) {
                maybeDestination = findWhereToMove(nextNode.distances, Node.SPACE, Character.MIN_VALUE);
                misplacedNodes.push(maybeDestination.orElseThrow().getValue());
            }

            var destination = maybeDestination.orElseThrow();
            score += destination.getKey() * costMap.get(nextNode.content);
            destination.getValue().content = nextNode.content;
            nextNode.content = Character.MIN_VALUE;
        }

        System.out.println(score);
    }

    private static Optional<Pair<Integer, Node>> findWhereToMove(List<Pair<Integer, Node>> list, boolean type, char name) {
        return list.stream().filter(e -> {
            var a = e.getValue().type == type;
            var b = e.getValue().content == 0;
            var c = name == 0 || e.getValue().name.charAt(0) == name;
            return a && b && c;
        }).findFirst();
    }

    private static int distance(Node a, Node b, Set<Node> visited) {
        if (a == b) return 0;
        for (var child : a.children) {
            if (visited.contains(child)) continue;
            visited.add(child);
            var d = distance(child, b, visited);
            if (d == -1) continue;
            return d + 1;
        }
        return -1;
    }
}
