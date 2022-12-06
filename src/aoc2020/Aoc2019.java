package aoc2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Aoc2019 {
    public static void main(String[] args) {
        var raw = ("42: 9 14 | 10 1\n" +
            "9: 14 27 | 1 26\n" +
            "10: 23 14 | 28 1\n" +
            "1: \"a\"\n" +
            "11: 42 31 | 42 11 31\n" +
            "5: 1 14 | 15 1\n" +
            "19: 14 1 | 14 14\n" +
            "12: 24 14 | 19 1\n" +
            "16: 15 1 | 14 14\n" +
            "31: 14 17 | 1 13\n" +
            "6: 14 14 | 1 14\n" +
            "2: 1 24 | 14 4\n" +
            "0: 8 11\n" +
            "13: 14 3 | 1 12\n" +
            "15: 1 | 14\n" +
            "17: 14 2 | 1 7\n" +
            "23: 25 1 | 22 14\n" +
            "28: 16 1\n" +
            "4: 1 1\n" +
            "20: 14 14 | 1 15\n" +
            "3: 5 14 | 16 1\n" +
            "27: 1 6 | 14 18\n" +
            "14: \"b\"\n" +
            "21: 14 1 | 1 14\n" +
            "25: 1 1 | 1 14\n" +
            "22: 14 14\n" +
            "8: 42 | 42 8\n" +
            "26: 14 22 | 1 20\n" +
            "18: 15 15\n" +
            "7: 14 5 | 1 21\n" +
            "24: 14 1\n" +
            "\n" +
            "aaaabbaaaabbaaa").split("\\n\\n");

        Rule[] rules = new Rule[raw[0].length()];
        for (var rule : raw[0].split("\\n")) {
            var numberAndExpr = rule.split(":");
            rules[Integer.parseInt(numberAndExpr[0])] = new Rule(rules, numberAndExpr[1].trim(), Integer.parseInt(numberAndExpr[0]));
        }

        var r = 0;
        for (var msg : raw[1].split("\\n")) {
            var valid = rules[0].matches(msg, 0);
            if (!valid.isEmpty()) {
                r++;
                System.out.println(msg);
            }
        }
        System.out.println(r);
    }

    static class Rule {
        enum Type {
            LETTER,
            EXPR
        }

        private Rule[] allRules;
        private List<List<Integer>> rules;
        private String expression;
        private Type type;
        private int ruleNumber;

        public Rule(Rule[] allRules, String expression, int ruleNumber) {
            this.allRules = allRules;
            this.ruleNumber = ruleNumber;
            if (expression.charAt(0) == '"') {
                expression = String.valueOf(expression.charAt(1));
                this.type = Type.LETTER;
            } else {
                this.type = Type.EXPR;
                var orRules = expression.split("\\|");
                this.rules = new ArrayList<>();
                for (var orRule : orRules) {
                    var andRules = orRule.trim().split(" ");
                    var parsedNumbers = Stream.of(andRules).map(Integer::valueOf).toArray(Integer[]::new);
                    this.rules.add(new ArrayList<>(Arrays.asList(parsedNumbers)));
                }
            }

            this.expression = expression;
        }

        public String matches(String s, int pos) {
            if (type == Type.LETTER) {
                if (s.substring(pos, pos + 1).equals(expression)) {
                    return expression;
                }
                return "";
            }

            for (var orRule : rules) {
                var i = pos;
                var matches = "";
                StringBuilder result = new StringBuilder();
                for (var andRule : orRule) {
                    if (i >= s.length()) {
                        break;
                    }
                    matches = allRules[andRule].matches(s, i);
                    if (matches.isEmpty()) {
                        break;
                    }
                    result.append(matches);
                    i += matches.length();
                }
                if (!matches.isEmpty()) {
                    if (ruleNumber == 0) {
                        if (result.toString().equals(s)) {
                            return s;
                        } else {
                            return "";
                        }
                    }
                    return result.toString();
                }
            }
            return "";
        }
    }
}
