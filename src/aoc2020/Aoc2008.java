package aoc2020;

import java.util.HashMap;

public class Aoc2008 {
    public static void main(String[] args) {
        var raw = "nop +0\n" +
            "acc +1\n" +
            "jmp +4\n" +
            "acc +3\n" +
            "jmp -3\n" +
            "acc -99\n" +
            "acc +1\n" +
            "jmp -4\n" +
            "acc +6";

        var inputs = raw.split("\\n");

        for (var replaceAttemptPointer = 0; replaceAttemptPointer < inputs.length; replaceAttemptPointer++) {
            var accu = 0;
            var commands = new HashMap<Integer, Integer>();
            var successfulFix = false;
            for (var pointer = 0; pointer < inputs.length;) {
                if (commands.containsKey(pointer)) {
                    break;
                }
                commands.put(pointer, 0);
                var command = inputs[pointer].split("\\s");
                if (pointer == replaceAttemptPointer) {
                    switch (command[0]) {
                        case "nop":
                            command[0] = "jmp";
                            break;
                        case "jmp":
                            command[0] = "nop";
                            break;
                    }
                }
                switch (command[0]) {
                    case "acc":
                        accu += Integer.parseInt(command[1]);
                        pointer++;
                        break;
                    case "jmp":
                        pointer += Integer.parseInt(command[1]);
                        break;
                    default:
                        pointer++;
                        break;
                }

                successfulFix = pointer == inputs.length;
            }
            if (successfulFix)
            System.out.println(accu);
        }

    }
}
