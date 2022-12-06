package aoc2021;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Aoc2118 {
    private static boolean explode(List<Node> list) {
        for (var i = 0; i < list.size(); i++) {
            if (list.get(i).depth != 4) continue;
            var left = list.get(i);
            var newDepth = left.depth - 1;
            if (i != 0) {
                list.get(i - 1).value += left.value;
            }
            var right = list.get(i + 1);
            if (i < list.size() - 2) {
                list.get(i + 2).value += right.value;
            }
            list.remove(i + 1);
            list.remove(i);
            list.add(i, new Node(newDepth));
            return true;
        }
        return false;
    }

    private static boolean split(List<Node> list) {
        for (var i = 0; i < list.size(); i++) {
            if (list.get(i).value > 9) {
                var nodeToSplit = list.get(i);
                var splitDepth = nodeToSplit.depth + 1;
                var splitValue = (float)nodeToSplit.value;
                list.remove(i);
                list.add(i, new Node(splitDepth, (int)Math.floor(splitValue / 2)));
                list.add(i + 1, new Node(splitDepth, (int)Math.ceil(splitValue / 2)));
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        var lines = getInput();

        var largestMagnitude = Integer.MIN_VALUE;

        for (var lineIdx1 = 0; lineIdx1 < lines.length; lineIdx1++) {
            for (var lineIdx2 = 0; lineIdx2 < lines.length; lineIdx2++) {
                if (lineIdx1 == lineIdx2) continue;

                List<Node> sumResult = performSum(parseLine(lines[lineIdx1]), parseLine(lines[lineIdx2]));

                var reduced = false;
                do {
                    reduced = explode(sumResult);
                    if (reduced) continue;
                    reduced = split(sumResult);
                } while (reduced);

                var magnitude = magnitude(sumResult);

                if (magnitude > largestMagnitude) largestMagnitude = magnitude;
            }
        }

        System.out.println(largestMagnitude);
    }

    private static List<Node> performSum(List<Node> operandA, List<Node> operandB) {
        if (operandA.isEmpty()) return operandB;
        operandA.addAll(operandB);
        operandA = operandA.stream().peek(e -> e.depth++).collect(Collectors.toList());
        return operandA;
    }

    private static int magnitude(List<Node> list) {
        for (var depth = 3; depth >= 0; depth--) {
            var reduced = false;
            do {
                reduced = false;
                for (var j = 0; j < list.size() - 1; j++) {
                    var left = list.get(j);
                    var right = list.get(j + 1);
                    if (left.depth != depth) continue;
                    list.remove(j + 1);
                    list.remove(j);
                    list.add(j, new Node(depth - 1, 3 * left.value + 2 * right.value));
                    reduced = true;
                    break;
                }
            } while (reduced);
        }
        return list.get(0).value;
    }

    private static List<Node> parseLine(String line) {
        var list = new ArrayList<Node>();
        var depth = -1;
        for (var cc : line.toCharArray()) {
            switch (cc) {
                case '[':
                    depth++;
                    break;
                case ']':
                    depth--;
                    break;
                case ',':
                    break;
                default:
                    var value = Integer.parseInt(String.valueOf(cc));
                    var node = new Node();
                    node.depth = depth;
                    node.value = value;
                    list.add(node);
                    break;
            }
        }
        return list;
    }

    static class Node {
        int value = 0;
        int depth;

        public Node() {
        }
        public Node(int depth) {
            this.depth = depth;
        }
        public Node(int depth, int value) {
            this.depth = depth;
            this.value = value;
        }

        @Override
        public String toString() {
            return value + "";
        }
    }

    private static String[] getInput() {
        return ("[[[[3,0],[0,0]],1],4]\n" +
            "[[[[3,4],0],[7,7]],[1,6]]\n" +
            "[[[[2,0],5],7],[[[3,1],[2,6]],[[0,8],6]]]\n" +
            "[[[[5,5],0],1],[[[0,0],1],[[0,6],[0,9]]]]\n" +
            "[[0,[0,[1,7]]],[3,[1,[7,6]]]]\n" +
            "[[[9,[5,2]],[[5,2],[6,8]]],[[[7,0],7],[[2,3],[9,4]]]]\n" +
            "[[[[3,8],7],[[0,7],[2,0]]],[0,[[2,9],0]]]\n" +
            "[[[7,[2,2]],[3,4]],[6,7]]\n" +
            "[8,[[[3,3],8],[[7,1],[6,7]]]]\n" +
            "[[9,[9,8]],[[1,[9,1]],[2,5]]]\n" +
            "[[[7,8],[[1,2],[2,6]]],[[9,7],[6,[7,0]]]]\n" +
            "[[[3,3],[[5,6],5]],[[[2,8],1],9]]\n" +
            "[[[2,[5,0]],[[9,9],[4,0]]],[0,5]]\n" +
            "[[[9,3],[[9,4],[5,8]]],[[[3,2],[7,1]],[[3,8],1]]]\n" +
            "[[3,2],[[6,[0,9]],[8,3]]]\n" +
            "[[[5,7],[[7,4],[4,6]]],[[[9,8],3],3]]\n" +
            "[[[4,[2,8]],9],[[[8,5],[9,7]],[[8,9],[2,6]]]]\n" +
            "[[[1,[2,4]],6],[[8,[5,2]],[[0,7],[4,1]]]]\n" +
            "[[[[4,3],6],[[6,4],[4,2]]],[[9,0],[[5,9],9]]]\n" +
            "[[[[3,0],6],[4,[7,5]]],4]\n" +
            "[[[[1,0],[7,1]],0],[[[8,5],8],2]]\n" +
            "[[[[2,9],[4,1]],[[8,9],[3,3]]],[9,[[0,7],2]]]\n" +
            "[[1,[4,[4,2]]],[[[3,5],[8,8]],2]]\n" +
            "[[[8,[1,4]],[[6,5],5]],[[7,[4,7]],4]]\n" +
            "[[[[0,5],2],[[9,2],0]],0]\n" +
            "[[[[6,2],[2,4]],[0,[7,3]]],[9,[8,[5,9]]]]\n" +
            "[[8,0],2]\n" +
            "[[[[0,2],2],[[9,2],[8,1]]],[[[7,6],[5,3]],6]]\n" +
            "[[[[8,7],[5,3]],[[3,0],8]],[[[8,4],[2,2]],[[8,1],2]]]\n" +
            "[[[[1,5],[4,6]],[[4,0],[2,4]]],[[1,1],[[0,7],[7,3]]]]\n" +
            "[[7,2],[[7,[6,7]],[8,5]]]\n" +
            "[[[9,7],[[6,6],9]],8]\n" +
            "[[4,2],[[[1,0],[9,1]],[[0,7],[8,0]]]]\n" +
            "[[[[5,9],5],[8,9]],[[2,4],[[5,2],[8,3]]]]\n" +
            "[[[[4,5],[7,0]],[4,5]],[[7,[6,4]],[[1,7],[6,3]]]]\n" +
            "[[2,0],4]\n" +
            "[[2,[[5,1],[2,1]]],[[5,[7,2]],[[2,3],[7,0]]]]\n" +
            "[[4,[4,9]],[9,[6,8]]]\n" +
            "[[[[6,1],[1,5]],[0,[4,0]]],[[[7,0],2],4]]\n" +
            "[[[[3,3],[2,2]],[[2,4],2]],[[8,[1,1]],4]]\n" +
            "[[[[1,5],8],[[9,4],[7,7]]],[[[8,7],[7,2]],[0,[7,3]]]]\n" +
            "[9,[[7,[0,4]],4]]\n" +
            "[4,[0,8]]\n" +
            "[[[[2,6],1],[8,[8,4]]],[[8,2],[1,[8,4]]]]\n" +
            "[[7,[8,[8,8]]],[4,1]]\n" +
            "[[0,6],[[7,[5,9]],[[7,1],8]]]\n" +
            "[4,6]\n" +
            "[[[[3,2],[5,6]],[0,7]],[8,[7,[9,5]]]]\n" +
            "[[[3,7],[4,5]],6]\n" +
            "[[[0,[3,9]],[9,1]],6]\n" +
            "[[[[7,3],8],[6,7]],[[1,0],[1,7]]]\n" +
            "[[[5,[4,8]],2],[[[7,1],6],[[0,3],2]]]\n" +
            "[[1,0],[[1,2],[[2,0],1]]]\n" +
            "[[8,[[6,1],[7,1]]],0]\n" +
            "[[9,[2,0]],[[7,[6,2]],4]]\n" +
            "[[[9,[9,4]],[[4,8],3]],[[9,0],[[2,2],[0,6]]]]\n" +
            "[[[7,5],[[2,9],6]],[[2,4],[[1,1],[8,2]]]]\n" +
            "[[[1,[6,3]],[[2,2],[1,8]]],[[[7,3],[6,0]],[4,[7,6]]]]\n" +
            "[6,5]\n" +
            "[[3,[9,[4,4]]],[[6,9],[4,5]]]\n" +
            "[[[4,[1,8]],[[4,0],6]],[[[9,0],[8,3]],[[8,6],[3,2]]]]\n" +
            "[[[8,[1,2]],[[3,9],6]],[[3,0],1]]\n" +
            "[[1,[2,[4,0]]],6]\n" +
            "[0,[[[1,3],[9,1]],[[3,8],[9,4]]]]\n" +
            "[2,[2,[[2,7],[7,8]]]]\n" +
            "[[[3,0],[[4,6],2]],[9,2]]\n" +
            "[[[5,[2,2]],[[2,7],[9,9]]],[[3,[4,4]],[8,[9,8]]]]\n" +
            "[[[[7,5],[7,9]],[[8,5],6]],[[1,[8,4]],[8,2]]]\n" +
            "[[[6,4],[5,5]],[[[8,1],5],[[6,4],[6,9]]]]\n" +
            "[[[[8,9],0],[[4,6],7]],[[[3,9],[6,4]],[8,[7,4]]]]\n" +
            "[4,[[7,7],4]]\n" +
            "[[[[4,9],[1,2]],[8,[4,7]]],[[8,[4,8]],[0,[5,4]]]]\n" +
            "[1,[7,9]]\n" +
            "[[[5,[2,0]],[[4,3],[6,8]]],[9,9]]\n" +
            "[[[[3,9],9],[4,3]],[1,[3,[8,1]]]]\n" +
            "[[[[8,7],[6,1]],[3,9]],[5,[[8,0],4]]]\n" +
            "[[[[8,2],[4,6]],[6,[9,9]]],[1,[[7,7],4]]]\n" +
            "[[7,5],[[5,0],[0,3]]]\n" +
            "[[[6,0],[9,1]],[[[4,3],[5,0]],[[9,5],[0,0]]]]\n" +
            "[8,[[3,6],3]]\n" +
            "[[[[9,3],7],[1,3]],[[[6,4],[8,4]],[1,5]]]\n" +
            "[[[[3,8],2],[5,4]],[[[1,8],5],[2,[2,7]]]]\n" +
            "[[2,9],[6,[0,2]]]\n" +
            "[[2,[7,9]],[[4,1],[[9,2],[0,7]]]]\n" +
            "[[0,[6,4]],[[9,2],[0,[0,7]]]]\n" +
            "[[[[7,2],[8,6]],[6,2]],[[[1,6],[2,2]],1]]\n" +
            "[[1,6],[[[4,3],[8,2]],[3,[9,4]]]]\n" +
            "[[9,[7,3]],[[[7,0],4],[[1,7],[2,2]]]]\n" +
            "[[7,[5,[9,8]]],[[[7,5],[7,6]],[7,[9,8]]]]\n" +
            "[[[[6,1],[4,3]],4],[[[5,9],4],2]]\n" +
            "[[[[5,1],[2,5]],0],[[7,[5,7]],[[4,4],9]]]\n" +
            "[9,2]\n" +
            "[4,[[[6,6],5],7]]\n" +
            "[[8,[[7,3],[0,7]]],8]\n" +
            "[[[3,4],[[2,3],0]],[[[9,6],[1,1]],[4,[0,4]]]]\n" +
            "[[[[3,3],[2,3]],[2,5]],[[4,[2,7]],3]]\n" +
            "[[[8,[0,3]],2],[4,4]]\n" +
            "[[[3,5],[[2,1],[3,4]]],[[0,3],4]]\n" +
            "[[[[4,1],4],2],[[[3,7],2],[[8,1],3]]]\n" +
            "[[[[0,6],[7,3]],[5,[3,9]]],[7,[[4,1],8]]]").split("\n");
    }
}
