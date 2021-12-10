import java.util.*;

public class Aoc2110 {
    public static void main(String[] args) throws Exception {
        var r = new ArrayList<Long>();
        var openings = new ArrayList<Character>() {{
            addAll(List.of('[', '(', '{', '<'));
        }};
        for (var line : getInput()) {
            var data = new Stack<Character>();
            for (var i = 0; i < line.length(); i++) {
                var charinline = line.charAt(i);
                if (openings.contains(charinline)) {
                    data.push(charinline);
                } else {
                    if (data.size() > 0) {
                        var prevhar = data.pop();
                        if (prevhar == '[' && charinline != ']'
                            || prevhar == '(' && charinline != ')'
                            || prevhar == '{' && charinline != '}'
                            || prevhar == '<' && charinline != '>'
                        ) {
                            data = new Stack<>();
                            break;
                        }
                    } else {
                        throw new Exception("More closings than openings!");
                    }
                }
            }

            if (data.size() > 0) {
                var score = 0L;
                while (data.size() > 0) {
                    var nc = data.pop();
                    score *= 5;
                    if (nc == '(') score += 1;
                    if (nc == '[') score += 2;
                    if (nc == '{') score += 3;
                    if (nc == '<') score += 4;
                }
                r.add(score);
            }

        }
        r.sort(Comparator.naturalOrder());
        System.out.println(r.get((r.size() + 1) / 2 - 1));
    }

    private static String[] getInput() {
        return ("[({(<(())[]>[[{[]{<()<>>\n" +
            "[(()[<>])]({[<{<<[]>>(\n" +
            "{([(<{}[<>[]}>{[]{[(<()>\n" +
            "(((({<>}<{<{<>}{[]{[]{}\n" +
            "[[<[([]))<([[{}[[()]]]\n" +
            "[{[{({}]{}}([{[{{{}}([]\n" +
            "{<[[]]>}<{[{[{[]{()[[[]\n" +
            "[<(<(<(<{}))><([]([]()\n" +
            "<{([([[(<>()){}]>(<<{{\n" +
            "<{([{{}}[<[[[<>{}]]]>[]]").split("\n");
    }
}
