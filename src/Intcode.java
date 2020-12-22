import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Intcode {
    private static final boolean DEBUG = false;

    private static final boolean RUN_MODE = false;

    public static void main(String[] args) {
        Program computer = new Program();

        if (RUN_MODE == true) {
            var program = new int[]{3, 26, 1001, 26, -4, 26, 3, 27, 1002, 27, 2, 27, 1, 27, 26,
                27, 4, 27, 1001, 28, -1, 28, 1005, 28, 6, 99, 0, 0, 5};

            var sequences = ComputerHelper.permute(new int[]{9, 8, 7, 6, 5});

            long maxResult = Long.MIN_VALUE;

            for (var sequence : sequences) {
                var nextInput = 0;
                for (var sequenceAmp : sequence) {
                    nextInput = computer.run(program.clone(), new int[]{sequenceAmp, nextInput}, DEBUG);
                }
                maxResult = maxResult > nextInput ? maxResult : nextInput;
            }

            System.out.println(maxResult);
        } else {
            TestRunner.runTests(computer, DEBUG);
        }
    }

    static class Program {
        public int run(int[] data, int[] inputs, boolean debug) {
            var programCodeAsMnemonics = new ArrayList<String>();
            data = data.clone();

            var pointer = new Pointer();

            var io = new Io(inputs);

            while (true) {
                if (pointer.getValue() >= data.length) {
                    System.out.println("out of bounds");
                    return -1;
                }

                var opcode = data[pointer.getValue()] % 100;
                int modes = data[pointer.getValue()] / 100;

                try {
                    Command command = CommandFactory.create(opcode, modes, data, pointer);

                    programCodeAsMnemonics.add(command.getName() + " (" + modes + ") " + Arrays.toString(command.getParams()));

                    if (command.execute(data, pointer, io) == -1) {
                        if (debug) {
                            printProgram(programCodeAsMnemonics);
                        }
                        return io.getOutput();
                    }

                    pointer.setValue(pointer.getValue() + command.getCommandSize());
                } catch (UnknownOpcode e) {
                    System.out.println(e.getMessage() + " pointer: " + pointer.getValue());
                    break;
                }
            }

            return -1;
        }

        public void printProgram(List<String> commands) {
            for (var d : commands) {
                System.out.println(d);
            }
        }
    }

    abstract static class Command {
        private final int modes;

        private final int[] params;

        public Command(int modes, int[] data, Pointer pointer) {
            this.modes = modes;
            this.params = new int[getParamsCount()];
            for (var i = 0; i < getParamsCount(); i++) {
                if (getModes() % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i) == 1) {
                    this.params[i] = data[pointer.getValue() + 1 + i];
                } else {
                    this.params[i] = data[data[pointer.getValue() + 1 + i]];
                }
            }
        }

        public abstract String getName();

        public abstract int getParamsCount();

        public int getParam(int idx) {
            return this.params[idx];
        }

        public int[] getParams() {
            return Arrays.copyOfRange(this.params, 0, getParamsCount());
        }

        public int getCommandSize() {
            return getParamsCount() + 1;
        }

        public int getModes() {
            return modes;
        }

        public abstract int execute(int[] data, Pointer pointer, Io io);
    }

    static class Add extends Command {
        public static final int OPCODE = 1;

        public Add(int modes, int[] data, Pointer pointer) {
            super(modes, data, pointer);
        }

        @Override
        public String getName() {
            return "ADD";
        }

        @Override
        public int getParamsCount() {
            return 3;
        }

        @Override
        public int execute(int[] data, Pointer pointer, Io io) {
            int a = getParam(0);
            int b = getParam(1);
            data[data[pointer.getValue() + 3]] = a + b;
            return 0;
        }
    }

    static class Multiply extends Command {
        public static final int OPCODE = 2;

        public Multiply(int modes, int[] data, Pointer pointer) {
            super(modes, data, pointer);
        }

        @Override
        public String getName() {
            return "MUL";
        }

        @Override
        public int getParamsCount() {
            return 3;
        }

        @Override
        public int execute(int[] data, Pointer pointer, Io io) {
            int a = getParam(0);
            int b = getParam(1);
            data[data[pointer.getValue() + 3]] = a * b;
            return 0;
        }
    }

    static class Input extends Command {
        public static final int OPCODE = 3;

        public Input(int modes, int[] data, Pointer pointer) {
            super(modes, data, pointer);
        }

        @Override
        public String getName() {
            return "INP";
        }

        @Override
        public int getParamsCount() {
            return 1;
        }

        @Override
        public int execute(int[] data, Pointer pointer, Io io) {
            data[data[pointer.getValue() + 1]] = io.nextInput();
            return 0;
        }
    }

    static class Output extends Command {
        public static final int OPCODE = 4;

        public Output(int modes, int[] data, Pointer pointer) {
            super(modes, data, pointer);
        }

        @Override
        public String getName() {
            return "OUT";
        }

        @Override
        public int getParamsCount() {
            return 1;
        }

        @Override
        public int execute(int[] data, Pointer pointer, Io io) {
            io.setOutput(getParam(0));
            return 0;
        }
    }

    static class JumpIfTrue extends Command {
        public static final int OPCODE = 5;

        public JumpIfTrue(int modes, int[] data, Pointer pointer) {
            super(modes, data, pointer);
        }

        @Override
        public String getName() {
            return "JMT";
        }

        @Override
        public int getParamsCount() {
            return 2;
        }

        @Override
        public int execute(int[] data, Pointer pointer, Io io) {
            if (getParam(0) != 0) {
                pointer.setValue(getParam(1) - getCommandSize());
            }
            return 0;
        }
    }

    static class JumpIfFalse extends Command {
        public static final int OPCODE = 6;

        public JumpIfFalse(int modes, int[] data, Pointer pointer) {
            super(modes, data, pointer);
        }

        @Override
        public String getName() {
            return "JMF";
        }

        @Override
        public int getParamsCount() {
            return 2;
        }

        @Override
        public int execute(int[] data, Pointer pointer, Io io) {
            if (getParam(0) == 0) {
                pointer.setValue(getParam(1) - getCommandSize());
            }
            return 0;
        }
    }

    static class LessThan extends Command {
        public static final int OPCODE = 7;

        public LessThan(int modes, int[] data, Pointer pointer) {
            super(modes, data, pointer);
        }

        @Override
        public String getName() {
            return "LTH";
        }

        @Override
        public int getParamsCount() {
            return 3;
        }

        @Override
        public int execute(int[] data, Pointer pointer, Io io) {
            var value = getParam(0) < getParam(1) ? 1 : 0;
            data[data[pointer.getValue() + 3]] = value;
            return 0;
        }
    }

    static class Equals extends Command {
        public static final int OPCODE = 8;

        public Equals(int modes, int[] data, Pointer pointer) {
            super(modes, data, pointer);
        }

        @Override
        public String getName() {
            return "EQL";
        }

        @Override
        public int getParamsCount() {
            return 3;
        }

        @Override
        public int execute(int[] data, Pointer pointer, Io io) {
            var value = getParam(0) == getParam(1) ? 1 : 0;
            data[data[pointer.getValue() + 3]] = value;
            return 0;
        }
    }

    static class Halt extends Command {
        public static final int OPCODE = 99;

        public Halt(int modes, int[] data, Pointer pointer) {
            super(modes, data, pointer);
        }

        @Override
        public String getName() {
            return "HLT";
        }

        @Override
        public int getParamsCount() {
            return 0;
        }

        @Override
        public int execute(int[] data, Pointer pointer, Io io) {
            return -1;
        }
    }

    static class CommandFactory {
        public static Command create(int opcode, int modes, int[] data, Pointer pointer) throws UnknownOpcode {
            switch (opcode) {
                case Add.OPCODE:
                    return new Add(modes, data, pointer);
                case Multiply.OPCODE:
                    return new Multiply(modes, data, pointer);
                case Input.OPCODE:
                    return new Input(modes, data, pointer);
                case Output.OPCODE:
                    return new Output(modes, data, pointer);
                case JumpIfFalse.OPCODE:
                    return new JumpIfFalse(modes, data, pointer);
                case JumpIfTrue.OPCODE:
                    return new JumpIfTrue(modes, data, pointer);
                case LessThan.OPCODE:
                    return new LessThan(modes, data, pointer);
                case Equals.OPCODE:
                    return new Equals(modes, data, pointer);
                case Halt.OPCODE:
                    return new Halt(modes, data, pointer);
                default:
                    throw new UnknownOpcode("Unkown opcode: " + opcode);
            }
        }
    }

    static class UnknownOpcode extends Exception {
        public UnknownOpcode(String message) {
            super(message);
        }
    }

    static class Io {
        private int output = 0;
        private final int[] inputs;
        private int inputPointer = 0;

        public Io(int[] inputs) {
            this.inputs = inputs;
        }

        public int nextInput() {
            if (inputPointer >= inputs.length) {
                return 0;
            }
            return inputs[inputPointer++];
        }

        public int getOutput() {
            return output;
        }

        public void setOutput(int value) {
            this.output = value;
        }
    }

    static class Pointer {
        private int value;

        public Pointer() {
            this.value = 0;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    static class Test {
        private final String name;
        private final int input;
        private final int expectedOutput;
        private final int[] program;

        public Test(String name, int input, int expectedOutput, int[] program) {
            this.name = name;
            this.input = input;
            this.expectedOutput = expectedOutput;
            this.program = program;
        }

        public String getName() {
            return name;
        }

        public int getInput() {
            return input;
        }

        public int getExpectedOutput() {
            return expectedOutput;
        }

        public int[] getProgram() {
            return program;
        }
    }

    static class TestRunner {
        public static void runTests(Program computer, boolean DEBUG) {
            var tests = new Test[]{
                new Test("Advent day 5 part 1", 1, 5182797, new int[]{3, 225, 1, 225, 6, 6, 1100, 1, 238, 225, 104, 0, 1101, 40, 27, 224, 101, -67, 224, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 2, 224, 1, 224, 223, 223, 1101, 33, 38, 225, 1102, 84, 60, 225, 1101, 65, 62, 225, 1002, 36, 13, 224, 1001, 224, -494, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 3, 224, 1, 223, 224, 223, 1102, 86, 5, 224, 101, -430, 224, 224, 4, 224, 1002, 223, 8, 223, 101, 6, 224, 224, 1, 223, 224, 223, 1102, 23, 50, 225, 1001, 44, 10, 224, 101, -72, 224, 224, 4, 224, 102, 8, 223, 223, 101, 1, 224, 224, 1, 224, 223, 223, 102, 47, 217, 224, 1001, 224, -2303, 224, 4, 224, 102, 8, 223, 223, 101, 2, 224, 224, 1, 223, 224, 223, 1102, 71, 84, 225, 101, 91, 40, 224, 1001, 224, -151, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 5, 224, 1, 223, 224, 223, 1101, 87, 91, 225, 1102, 71, 19, 225, 1, 92, 140, 224, 101, -134, 224, 224, 4, 224, 1002, 223, 8, 223, 101, 1, 224, 224, 1, 224, 223, 223, 2, 170, 165, 224, 1001, 224, -1653, 224, 4, 224, 1002, 223, 8, 223, 101, 5, 224, 224, 1, 223, 224, 223, 1101, 49, 32, 225, 4, 223, 99, 0, 0, 0, 677, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1105, 0, 99999, 1105, 227, 247, 1105, 1, 99999, 1005, 227, 99999, 1005, 0, 256, 1105, 1, 99999, 1106, 227, 99999, 1106, 0, 265, 1105, 1, 99999, 1006, 0, 99999, 1006, 227, 274, 1105, 1, 99999, 1105, 1, 280, 1105, 1, 99999, 1, 225, 225, 225, 1101, 294, 0, 0, 105, 1, 0, 1105, 1, 99999, 1106, 0, 300, 1105, 1, 99999, 1, 225, 225, 225, 1101, 314, 0, 0, 106, 0, 0, 1105, 1, 99999, 1107, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 329, 101, 1, 223, 223, 8, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 344, 101, 1, 223, 223, 1007, 677, 226, 224, 102, 2, 223, 223, 1005, 224, 359, 101, 1, 223, 223, 8, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 374, 101, 1, 223, 223, 1107, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 389, 1001, 223, 1, 223, 108, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 404, 1001, 223, 1, 223, 108, 677, 677, 224, 1002, 223, 2, 223, 1006, 224, 419, 101, 1, 223, 223, 107, 677, 677, 224, 102, 2, 223, 223, 1006, 224, 434, 101, 1, 223, 223, 108, 226, 226, 224, 1002, 223, 2, 223, 1006, 224, 449, 1001, 223, 1, 223, 8, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 464, 101, 1, 223, 223, 1108, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 479, 1001, 223, 1, 223, 1108, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 494, 101, 1, 223, 223, 7, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 509, 101, 1, 223, 223, 1007, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 524, 101, 1, 223, 223, 7, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 539, 101, 1, 223, 223, 1107, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 554, 101, 1, 223, 223, 107, 226, 677, 224, 1002, 223, 2, 223, 1005, 224, 569, 101, 1, 223, 223, 107, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 584, 101, 1, 223, 223, 1108, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 599, 1001, 223, 1, 223, 1008, 677, 677, 224, 102, 2, 223, 223, 1006, 224, 614, 101, 1, 223, 223, 7, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 629, 101, 1, 223, 223, 1008, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 644, 101, 1, 223, 223, 1007, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 659, 1001, 223, 1, 223, 1008, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 674, 1001, 223, 1, 223, 4, 223, 99, 226}),
                new Test("Advent day 5 part 2", 5, 12077198, new int[]{3, 225, 1, 225, 6, 6, 1100, 1, 238, 225, 104, 0, 1101, 40, 27, 224, 101, -67, 224, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 2, 224, 1, 224, 223, 223, 1101, 33, 38, 225, 1102, 84, 60, 225, 1101, 65, 62, 225, 1002, 36, 13, 224, 1001, 224, -494, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 3, 224, 1, 223, 224, 223, 1102, 86, 5, 224, 101, -430, 224, 224, 4, 224, 1002, 223, 8, 223, 101, 6, 224, 224, 1, 223, 224, 223, 1102, 23, 50, 225, 1001, 44, 10, 224, 101, -72, 224, 224, 4, 224, 102, 8, 223, 223, 101, 1, 224, 224, 1, 224, 223, 223, 102, 47, 217, 224, 1001, 224, -2303, 224, 4, 224, 102, 8, 223, 223, 101, 2, 224, 224, 1, 223, 224, 223, 1102, 71, 84, 225, 101, 91, 40, 224, 1001, 224, -151, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 5, 224, 1, 223, 224, 223, 1101, 87, 91, 225, 1102, 71, 19, 225, 1, 92, 140, 224, 101, -134, 224, 224, 4, 224, 1002, 223, 8, 223, 101, 1, 224, 224, 1, 224, 223, 223, 2, 170, 165, 224, 1001, 224, -1653, 224, 4, 224, 1002, 223, 8, 223, 101, 5, 224, 224, 1, 223, 224, 223, 1101, 49, 32, 225, 4, 223, 99, 0, 0, 0, 677, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1105, 0, 99999, 1105, 227, 247, 1105, 1, 99999, 1005, 227, 99999, 1005, 0, 256, 1105, 1, 99999, 1106, 227, 99999, 1106, 0, 265, 1105, 1, 99999, 1006, 0, 99999, 1006, 227, 274, 1105, 1, 99999, 1105, 1, 280, 1105, 1, 99999, 1, 225, 225, 225, 1101, 294, 0, 0, 105, 1, 0, 1105, 1, 99999, 1106, 0, 300, 1105, 1, 99999, 1, 225, 225, 225, 1101, 314, 0, 0, 106, 0, 0, 1105, 1, 99999, 1107, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 329, 101, 1, 223, 223, 8, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 344, 101, 1, 223, 223, 1007, 677, 226, 224, 102, 2, 223, 223, 1005, 224, 359, 101, 1, 223, 223, 8, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 374, 101, 1, 223, 223, 1107, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 389, 1001, 223, 1, 223, 108, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 404, 1001, 223, 1, 223, 108, 677, 677, 224, 1002, 223, 2, 223, 1006, 224, 419, 101, 1, 223, 223, 107, 677, 677, 224, 102, 2, 223, 223, 1006, 224, 434, 101, 1, 223, 223, 108, 226, 226, 224, 1002, 223, 2, 223, 1006, 224, 449, 1001, 223, 1, 223, 8, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 464, 101, 1, 223, 223, 1108, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 479, 1001, 223, 1, 223, 1108, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 494, 101, 1, 223, 223, 7, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 509, 101, 1, 223, 223, 1007, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 524, 101, 1, 223, 223, 7, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 539, 101, 1, 223, 223, 1107, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 554, 101, 1, 223, 223, 107, 226, 677, 224, 1002, 223, 2, 223, 1005, 224, 569, 101, 1, 223, 223, 107, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 584, 101, 1, 223, 223, 1108, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 599, 1001, 223, 1, 223, 1008, 677, 677, 224, 102, 2, 223, 223, 1006, 224, 614, 101, 1, 223, 223, 7, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 629, 101, 1, 223, 223, 1008, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 644, 101, 1, 223, 223, 1007, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 659, 1001, 223, 1, 223, 1008, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 674, 1001, 223, 1, 223, 4, 223, 99, 226}),

                new Test("Comparator equals", 8, 1000, new int[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}),
                new Test("Comparator less than", 1, 999, new int[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}),
                new Test("Comparator greater than", 9, 1001, new int[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}),
                new Test("Comparator greater than", 90, 1001, new int[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}),

                new Test("Zero checker pos 0", 0, 0, new int[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9}),
                new Test("Zero checker pos 1", 1, 1, new int[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9}),
                new Test("Zero checker pos 2", 2, 1, new int[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9}),

                new Test("Zero checker imm 0", 0, 0, new int[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1}),
                new Test("Zero checker imm 1", 1, 1, new int[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1}),
                new Test("Zero checker imm 2", 2, 1, new int[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1}),

                new Test("Equals 8 pos 8", 8, 1, new int[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Equals 8 pos 0", 0, 0, new int[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Equals 8 pos 1", 1, 0, new int[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Equals 8 pos 5", 5, 0, new int[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8}),

                new Test("Equals 8 imm 8", 8, 1, new int[]{3, 3, 1108, -1, 8, 3, 4, 3, 99}),
                new Test("Equals 8 imm 0", 0, 0, new int[]{3, 3, 1108, -1, 8, 3, 4, 3, 99}),
                new Test("Equals 8 imm 1", 1, 0, new int[]{3, 3, 1108, -1, 8, 3, 4, 3, 99}),
                new Test("Equals 8 imm 5", 5, 0, new int[]{3, 3, 1108, -1, 8, 3, 4, 3, 99}),

                new Test("Less 8 pos 8", 8, 0, new int[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Less 8 pos 18", 18, 0, new int[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Less 8 pos 0", 0, 1, new int[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Less 8 pos 1", 1, 1, new int[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Less 8 pos 5", 5, 1, new int[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}),

                new Test("Less 8 imm 8", 8, 0, new int[]{3, 3, 1107, -1, 8, 3, 4, 3, 99}),
                new Test("Less 8 imm 18", 18, 0, new int[]{3, 3, 1107, -1, 8, 3, 4, 3, 99}),
                new Test("Less 8 imm 0", 0, 1, new int[]{3, 3, 1107, -1, 8, 3, 4, 3, 99}),
                new Test("Less 8 imm 1", 1, 1, new int[]{3, 3, 1107, -1, 8, 3, 4, 3, 99}),
                new Test("Less 8 imm 5", 5, 1, new int[]{3, 3, 1107, -1, 8, 3, 4, 3, 99}),

                new Test("Comparator less than", 5, 999, new int[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99})
            };

            var succeeded = 0;
            var failed = new ArrayList<String>();

            for (var test : tests) {
                var programResult = computer.run(test.getProgram(), new int[]{test.getInput()}, DEBUG);
                if (programResult == test.getExpectedOutput()) {
                    System.out.print(".");
                    succeeded++;
                } else {
                    failed.add("FAILED! Input: " + test.getInput() + ",  Output: " + programResult + ", expected: " + test
                        .getExpectedOutput() + " " + test
                        .getName());
                }
            }

            System.out.println("\nDone! [ " + succeeded + " / " + tests.length + " ] succeeded. Failed: " + failed.size());
            for (var failedMessage : failed) {
                System.out.println(failedMessage);
            }
        }
    }

    static class ComputerHelper {
        public static ArrayList<ArrayList<Integer>> permute(int[] num) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<>();

            //start from an empty list
            result.add(new ArrayList<>());

            for (int k : num) {
                //list of list in current iteration of the array num
                ArrayList<ArrayList<Integer>> current = new ArrayList<>();

                for (ArrayList<Integer> l : result) {
                    // # of locations to insert is largest index + 1
                    for (int j = 0; j < l.size() + 1; j++) {
                        // + add num[i] to different locations
                        l.add(j, k);

                        ArrayList<Integer> temp = new ArrayList<>(l);
                        current.add(temp);

                        //System.out.println(temp);

                        // - remove num[i] add
                        l.remove(j);
                    }
                }

                result = new ArrayList<>(current);
            }

            return result;
        }
    }
}
