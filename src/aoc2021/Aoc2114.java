package aoc2021;

import java.util.HashMap;

public class Aoc2114 {
    public static void main(String[] args) {
        //polymer creation map
        var polymerMap = new HashMap<String, Character>();
        for (var polym : getInput()[1].split("\n")) {
            polymerMap.put(polym.split(" -> ")[0], polym.split(" -> ")[1].charAt(0));
        }
        var startString = getInput()[0];
        //initial pairs count
        var pairsCount = new HashMap<String, Long>();
        for (var i = 0; i < startString.length() - 1; i++) {
            var pairKey = String.valueOf(startString.charAt(i)) + startString.charAt(i + 1);
            if (!pairsCount.containsKey(pairKey)) {
                pairsCount.put(pairKey, 0L);
            }
            pairsCount.put(pairKey, pairsCount.get(pairKey) + 1);
        }
        //initial characters count
        var charactersCount = new HashMap<Character, Long>();
        for (var i = 0; i < startString.length(); i++) {
            if (!charactersCount.containsKey(startString.charAt(i))) {
                charactersCount.put(startString.charAt(i), 0L);
            }
            charactersCount.put(startString.charAt(i), charactersCount.get(startString.charAt(i)) + 1);
        }

        for (var step = 1; step <= 40; step++) {
            var newPairsCount = new HashMap<>(pairsCount);
            for (var pair : pairsCount.entrySet()) {
                var pairOccurrences = pairsCount.get(pair.getKey());
                //old pairs are split and do not count
                newPairsCount.put(pair.getKey(), newPairsCount.get(pair.getKey()) - pairOccurrences);
                if (newPairsCount.get(pair.getKey()) == 0) {
                    newPairsCount.remove(pair.getKey());
                }
                //count first formed pair
                var pair1 = String.valueOf(pair.getKey().charAt(0)) + polymerMap.get(pair.getKey());
                if (!newPairsCount.containsKey(pair1)) {
                    newPairsCount.put(pair1, 0L);
                }
                newPairsCount.put(pair1, newPairsCount.get(pair1) + pairOccurrences);
                //count second formed pair
                var pair2 = polymerMap.get(pair.getKey()) + String.valueOf(pair.getKey().charAt(1));
                if (!newPairsCount.containsKey(pair2)) {
                    newPairsCount.put(pair2, 0L);
                }
                newPairsCount.put(pair2, newPairsCount.get(pair2) + pairOccurrences);
                //newly added character counted
                if (!charactersCount.containsKey(polymerMap.get(pair.getKey()))) {
                    charactersCount.put(polymerMap.get(pair.getKey()), 0L);
                }
                charactersCount.put(polymerMap.get(pair.getKey()), charactersCount.get(polymerMap.get(pair.getKey())) + pairOccurrences);
            }
            pairsCount = newPairsCount;
        }

        var mostCommon = 0L;
        var leastCommon = Long.MAX_VALUE;

        for (var countedCharacter : charactersCount.values()) {
            if (countedCharacter > mostCommon) {
                mostCommon = countedCharacter;
            }
            if (countedCharacter < leastCommon) {
                leastCommon = countedCharacter;
            }
        }

        System.out.println(mostCommon - leastCommon);
    }

    private static String[] getInput() {
        return ("OFSNKKHCBSNKBKFFCVNB\n" +
            "\n" +
            "KC -> F\n" +
            "CO -> S\n" +
            "FH -> K\n" +
            "VP -> P\n" +
            "KF -> S\n" +
            "SV -> O\n" +
            "CB -> H\n" +
            "PN -> F\n" +
            "NC -> N\n" +
            "BC -> F\n" +
            "NP -> O\n" +
            "SK -> F\n" +
            "HS -> C\n" +
            "SN -> V\n" +
            "OP -> F\n" +
            "ON -> N\n" +
            "FK -> N\n" +
            "SH -> B\n" +
            "HN -> N\n" +
            "BO -> V\n" +
            "VK -> H\n" +
            "SC -> K\n" +
            "KP -> O\n" +
            "VO -> V\n" +
            "HC -> P\n" +
            "BK -> B\n" +
            "VH -> N\n" +
            "PV -> O\n" +
            "HB -> H\n" +
            "VS -> F\n" +
            "KK -> B\n" +
            "HH -> B\n" +
            "CF -> F\n" +
            "PH -> C\n" +
            "NS -> V\n" +
            "SO -> P\n" +
            "NV -> K\n" +
            "BP -> N\n" +
            "SF -> V\n" +
            "SS -> K\n" +
            "FP -> N\n" +
            "PC -> S\n" +
            "OH -> B\n" +
            "CH -> H\n" +
            "VV -> S\n" +
            "VN -> O\n" +
            "OB -> K\n" +
            "PF -> H\n" +
            "CS -> C\n" +
            "PP -> O\n" +
            "NF -> H\n" +
            "SP -> P\n" +
            "OS -> V\n" +
            "BB -> P\n" +
            "NO -> F\n" +
            "VB -> V\n" +
            "HK -> C\n" +
            "NK -> O\n" +
            "HP -> B\n" +
            "HV -> V\n" +
            "BF -> V\n" +
            "KO -> F\n" +
            "BV -> H\n" +
            "KV -> B\n" +
            "OF -> V\n" +
            "NB -> F\n" +
            "VF -> C\n" +
            "PB -> B\n" +
            "FF -> H\n" +
            "CP -> C\n" +
            "KH -> H\n" +
            "NH -> P\n" +
            "PS -> P\n" +
            "PK -> P\n" +
            "CC -> K\n" +
            "BS -> V\n" +
            "SB -> K\n" +
            "OO -> B\n" +
            "OK -> F\n" +
            "BH -> B\n" +
            "CV -> F\n" +
            "FN -> V\n" +
            "CN -> P\n" +
            "KB -> B\n" +
            "FO -> H\n" +
            "PO -> S\n" +
            "HO -> H\n" +
            "CK -> B\n" +
            "KN -> C\n" +
            "FS -> K\n" +
            "OC -> P\n" +
            "FV -> N\n" +
            "OV -> K\n" +
            "BN -> H\n" +
            "HF -> V\n" +
            "VC -> S\n" +
            "FB -> S\n" +
            "NN -> P\n" +
            "FC -> B\n" +
            "KS -> N").split("\n\n");
    }
}
