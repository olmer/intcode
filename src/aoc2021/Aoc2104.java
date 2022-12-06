package aoc2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Aoc2104 {
    public static void main(String[] args) {
        var input = getInput();
        var commands = getCommands(input);
        var usedCommands = new ArrayList<Integer>();
        var checkedNumbers = new int[input.size() - 1][2][5];
        var remainingCardIndicies = new ArrayList<Integer>();
        List<List<Integer>> cards = new ArrayList<>();
        initCards(input, cards, remainingCardIndicies);

        int winnerCardIdx = 0;
        int winnerNumber = 0;
        boolean winnerFound = false;

        for (var nextNumber : commands) {
            usedCommands.add(nextNumber);
            for (var cardIdx = 0; cardIdx < cards.size(); cardIdx++) {
                var indexInCard = cards.get(cardIdx).indexOf(nextNumber);
                if (indexInCard == -1) continue;
                int x = indexInCard % 5;
                int y = indexInCard / 5;

                checkedNumbers[cardIdx][0][x]++;
                if (checkedNumbers[cardIdx][0][x] == 5) {
                    remainingCardIndicies.remove(new Integer(cardIdx));
                    if (remainingCardIndicies.size() == 0) {
                        winnerFound = true;
                        winnerNumber = nextNumber;
                        winnerCardIdx = cardIdx;
                        break;
                    }
                }
                checkedNumbers[cardIdx][1][y]++;
                if (checkedNumbers[cardIdx][1][y] == 5) {
                    remainingCardIndicies.remove(new Integer(cardIdx));
                    if (remainingCardIndicies.size() == 0) {
                        winnerFound = true;
                        winnerNumber = nextNumber;
                        winnerCardIdx = cardIdx;
                        break;
                    }
                }
            }
            if (winnerFound) break;
        }

        var sum = 0;

        for (var nextNumber : cards.get(winnerCardIdx)) {
            if (!usedCommands.contains(nextNumber)) {
                sum += nextNumber;
            }
        }

        System.out.println(sum * winnerNumber);
    }

    private static void initCards(List<String> input, List<List<Integer>> cards, List<Integer> remainingCardIndicies) {
        for (var i = 1; i < input.size(); i++) {
            cards.add(Arrays.stream(input.get(i).split(" ")).filter(e -> !e.isEmpty()).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()));
            remainingCardIndicies.add(i - 1);
        }
    }

    private static List<Integer> getCommands(List<String> input) {
        return Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
    }

    private static List<String> getInput() {
        return Arrays.stream(("7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n" +
            "\n" +
            "22 13 17 11  0\n" +
            " 8  2 23  4 24\n" +
            "21  9 14 16  7\n" +
            " 6 10  3 18  5\n" +
            " 1 12 20 15 19\n" +
            "\n" +
            " 3 15  0  2 22\n" +
            " 9 18 13 17  5\n" +
            "19  8  7 25 23\n" +
            "20 11 10 24  4\n" +
            "14 21 16 12  6\n" +
            "\n" +
            "14 21 17 24  4\n" +
            "10 16 15  9 19\n" +
            "18  8 23 26 20\n" +
            "22 11 13  6  5\n" +
            " 2  0 12  3  7").split("\n\n")).map(e -> e.replace("\n", " ")).collect(Collectors.toList());
    }
}
