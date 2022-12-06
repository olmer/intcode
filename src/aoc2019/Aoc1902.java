package aoc2019;

public class Aoc1902 {
    public static void main(String[] args) {
        Program program = new Program();

        int[] inputProgram = {1, 12, 2, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 1, 10, 19, 1, 9, 19, 23, 1, 13, 23, 27, 1, 5, 27, 31, 2, 31, 6, 35, 1, 35, 5, 39, 1, 9, 39, 43, 1, 43, 5, 47, 1, 47, 5, 51, 2, 10, 51, 55, 1, 5, 55, 59, 1, 59, 5, 63, 2, 63, 9, 67, 1, 67, 5, 71, 2, 9, 71, 75, 1, 75, 5, 79, 1, 10, 79, 83, 1, 83, 10, 87, 1, 10, 87, 91, 1, 6, 91, 95, 2, 95, 6, 99, 2, 99, 9, 103, 1, 103, 6, 107, 1, 13, 107, 111, 1, 13, 111, 115, 2, 115, 9, 119, 1, 119, 6, 123, 2, 9, 123, 127, 1, 127, 5, 131, 1, 131, 5, 135, 1, 135, 5, 139, 2, 10, 139, 143, 2, 143, 10, 147, 1, 147, 5, 151, 1, 151, 2, 155, 1, 155, 13, 0, 99, 2, 14, 0, 0};

        try {
            var input = inputProgram.clone();
            program.run(input);
            System.out.println("Part 1: " + input[0]);

            for (var i = 0; i <= 99; i++) {
                for (var j = 0; j <= 99; j++) {
                    input = inputProgram.clone();
                    input[1] = i;
                    input[2] = j;
                    program.run(input);
                    if (input[0] == 19690720) {
                        System.out.println("Part 2: " + (100 * input[1] + input[2]));
                    }
                }
            }

        } catch (UnknownOpcode e) {
            System.out.println("Caught exception " + e.toString());
        }
    }

    static class Program {
        public void run(int[] data) throws UnknownOpcode {
            int pointer = 0;

            while (true) {
                if (pointer >= data.length) {
                    System.out.println("out of bounds");
                    return;
                }

                Command command = CommandFactory.create(data[pointer]);
                if (command.execute(data, pointer) == -1) {
                    return;
                }

                pointer += command.getCommandSize();
            }
        }
    }

    abstract static class Command {
        private final int commandSize;

        public Command(int size) {
            this.commandSize = size;
        }

        public abstract int execute(int[] data, int pointer);

        public int getCommandSize() {
            return this.commandSize;
        }
    }

    static class Add extends Command {
        public static final int OPCODE = 1;

        public Add() {
            super(4);
        }

        @Override
        public int execute(int[] data, int pointer) {
            int a = data[data[pointer + 1]];
            int b = data[data[pointer + 2]];
            data[data[pointer + 3]] = a + b;
            return 0;
        }
    }

    static class Multiply extends Command {
        public static final int OPCODE = 2;

        public Multiply() {
            super(4);
        }

        @Override
        public int execute(int[] data, int pointer) {
            int a = data[data[pointer + 1]];
            int b = data[data[pointer + 2]];
            data[data[pointer + 3]] = a * b;
            return 0;
        }
    }

    static class Halt extends Command {
        public static final int OPCODE = 99;

        public Halt() {
            super(1);
        }

        @Override
        public int execute(int[] data, int pointer) {
            return -1;
        }
    }

    static class CommandFactory {
        public static Command create(int opcode) throws UnknownOpcode {
            switch (opcode) {
                case Add.OPCODE:
                    return new Add();
                case Multiply.OPCODE:
                    return new Multiply();
                case Halt.OPCODE:
                    return new Halt();
                default:
                    throw new UnknownOpcode();
            }
        }
    }

    static class UnknownOpcode extends Exception {
    }
}
