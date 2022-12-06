package aoc2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Aoc2018 {
    public static void main(String[] args) {
        var raw = new HashMap<String, Integer>();
//        raw.put("(5 * (5 * 7 * 6 * 7 + 2) * 5 + 7) + 3 * 2 * 8 + 8", 588968);
//        raw.put("((1 + 2) + 3) + 4", 10);
        raw.put("1 + (2 * 3) + (4 * (5 + 6))", 51);
        raw.put("2 * 3 + (4 * 5)", 46);
        raw.put("5 + (8 * 3 + 9 + 3 * 4 * 3)", 1445);
        raw.put("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 669060);
        raw.put("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 23340);
//        raw.put("2 * 1 + 5", 12);
//        raw.put("1 + 5", 6);
//        raw.put("(1 + 2) * (3 + 4)", 21);
//        raw.put("(1 + 2) * 3 + 4", 13);
//        raw.put("1 + 2 * (3 + 4)", 21);
//        raw.put("7 + (4 * 2 + 5) + 6", 26);
//        raw.put("1 + (2 * (3 * 4))", 25);
//        raw.put("2 * 3 + (4 * 5)", 26);
//        raw.put("5 + (8 * 3 + 9 + 3 * 4 * 3)", 437);
//        raw.put("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))", 12240);
//        raw.put("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2", 13632);

        for (var r : raw.entrySet()) {
//            System.out.println("testing " + r.getKey());
            var res = (new Token(r.getKey(), Token.Type.EXPRESSION)).getValue();
            if (res != r.getValue()) {
                System.out.println("FAILED " + r.getKey());
            } else {
                System.out.print(".");
            }
        }
    }

    static class Token {
        enum Type {
            OPERATOR_PLUS,
            OPERATOR_MULT,
            NUMBER,
            EXPRESSION
        }

        private final String value;
        private final Type type;

        public Token(String value, Type type) {
            this.value = value;
            this.type = type;
        }

        public Token(long value) {
            this.value = String.valueOf(value);
            this.type = Type.NUMBER;
        }

        public long getValue() {
            if (type == Type.NUMBER) {
                return Long.parseLong(value);
            } else if (type == Type.EXPRESSION) {
                return eval(value);
            } else {
                return 0L;
            }
        }

        public Type getType() {
            return type;
        }

        private long eval(String expression) {
            List<Token> tokens = new ArrayList<>();
            for (var i = 0; i < expression.length(); i++) {
                if (expression.charAt(i) == '*') {
                    tokens.add(new Token(String.valueOf(expression.charAt(i)), Type.OPERATOR_MULT));
                } else if (expression.charAt(i) == '+') {
                    tokens.add(new Token(String.valueOf(expression.charAt(i)), Type.OPERATOR_PLUS));
                } else if (Character.isDigit(expression.charAt(i))) {
                    tokens.add(new Token(String.valueOf(expression.charAt(i)), Type.NUMBER));
                } else if (expression.charAt(i) == ' ') {
                } else if (expression.charAt(i) == '(') {
                    var brace = findClosingBrace(expression, i + 1);
                    var newExpr = expression.substring(i + 1, brace);
                    tokens.add(new Token(newExpr, Type.EXPRESSION));
                    i = brace;
                } else {
                    System.out.println("ERROR!");
                }
            }

            List<Token> summedTokens = new ArrayList<>();

            for (var i = 0; i < tokens.size(); i++) {
                var token = tokens.get(i);
                if (token.getType() != Type.OPERATOR_PLUS) {
                    summedTokens.add(token);
                    continue;
                }

                var leftOperand = summedTokens.get(summedTokens.size() - 1);
                var rightOperand = tokens.get(i + 1);
                summedTokens.remove(summedTokens.size() - 1);
                summedTokens.add(new Token(leftOperand.getValue() + rightOperand.getValue()));

                i += 1;
            }

            long result = 1;

            for (var token : summedTokens) {
                if (token.getType() == Type.NUMBER || token.getType() == Type.EXPRESSION) result *= token.getValue();
            }

            return result;
        }

        private int findClosingBrace(String expression, int start) {
            var openBraces = 1;

            for (var i = start; i < expression.length(); i++) {
                var c = expression.charAt(i);
                if (c == '(') {
                    openBraces++;
                }
                if (c == ')') {
                    openBraces--;
                }
                if (openBraces == 0) {
                    return i;
                }
            }

            return expression.lastIndexOf(')');
        }
    }
}
