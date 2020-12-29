import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Aoc1909 {
    private static final Map<Long, Class<? extends Command>> opcodes = new HashMap<>() {{
        put(1L, Add.class);
        put(2L, Multiply.class);
        put(3L, Input.class);
        put(4L, Output.class);
        put(5L, JumpIfTrue.class);
        put(6L, JumpIfFalse.class);
        put(7L, LessThan.class);
        put(8L, Equals.class);
        put(9L, AdjustRelativeBase.class);
        put(99L, Halt.class);
    }};

    private static final byte DEBUG_MODE = 0;
    private static final boolean LIVE_MODE = true;

    public static void main(String[] args) throws ReflectiveOperationException {
        if (LIVE_MODE) {
            var program = new long[]{1102, 34463338, 34463338, 63, 1007, 63, 34463338, 63, 1005, 63, 53, 1102, 3, 1, 1000, 109, 988, 209, 12, 9, 1000, 209, 6, 209, 3, 203, 0, 1008, 1000, 1, 63, 1005, 63, 65, 1008, 1000, 2, 63, 1005, 63, 904, 1008, 1000, 0, 63, 1005, 63, 58, 4, 25, 104, 0, 99, 4, 0, 104, 0, 99, 4, 17, 104, 0, 99, 0, 0, 1102, 521, 1, 1028, 1101, 0, 36, 1000, 1102, 30, 1, 1005, 1101, 21, 0, 1013, 1101, 26, 0, 1006, 1102, 31, 1, 1017, 1101, 24, 0, 1007, 1101, 0, 1, 1021, 1102, 27, 1, 1019, 1101, 23, 0, 1010, 1101, 0, 38, 1012, 1102, 35, 1, 1001, 1101, 25, 0, 1003, 1102, 20, 1, 1004, 1101, 0, 37, 1009, 1101, 424, 0, 1023, 1102, 39, 1, 1008, 1102, 406, 1, 1027, 1102, 1, 413, 1026, 1101, 0, 29, 1002, 1102, 1, 0, 1020, 1102, 34, 1, 1014, 1102, 1, 28, 1018, 1102, 1, 33, 1011, 1102, 300, 1, 1025, 1102, 1, 22, 1015, 1102, 305, 1, 1024, 1101, 32, 0, 1016, 1102, 427, 1, 1022, 1101, 512, 0, 1029, 109, 14, 1205, 6, 197, 1001, 64, 1, 64, 1106, 0, 199, 4, 187, 1002, 64, 2, 64, 109, -18, 1207, 8, 19, 63, 1005, 63, 215, 1105, 1, 221, 4, 205, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, 10, 1208, -1, 28, 63, 1005, 63, 237, 1106, 0, 243, 4, 227, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, -2, 2102, 1, 0, 63, 1008, 63, 22, 63, 1005, 63, 263, 1105, 1, 269, 4, 249, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, 11, 21107, 40, 39, 0, 1005, 1015, 289, 1001, 64, 1, 64, 1106, 0, 291, 4, 275, 1002, 64, 2, 64, 109, 9, 2105, 1, 0, 4, 297, 1105, 1, 309, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, -13, 2101, 0, -5, 63, 1008, 63, 25, 63, 1005, 63, 329, 1105, 1, 335, 4, 315, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, 1, 1206, 8, 353, 4, 341, 1001, 64, 1, 64, 1105, 1, 353, 1002, 64, 2, 64, 109, 3, 2108, 37, -6, 63, 1005, 63, 375, 4, 359, 1001, 64, 1, 64, 1106, 0, 375, 1002, 64, 2, 64, 109, -16, 1207, 2, 36, 63, 1005, 63, 397, 4, 381, 1001, 64, 1, 64, 1105, 1, 397, 1002, 64, 2, 64, 109, 28, 2106, 0, 0, 1001, 64, 1, 64, 1106, 0, 415, 4, 403, 1002, 64, 2, 64, 109, -3, 2105, 1, -1, 1106, 0, 433, 4, 421, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, -12, 2108, 25, -6, 63, 1005, 63, 449, 1105, 1, 455, 4, 439, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, -19, 1202, 8, 1, 63, 1008, 63, 38, 63, 1005, 63, 479, 1001, 64, 1, 64, 1105, 1, 481, 4, 461, 1002, 64, 2, 64, 109, 14, 2107, 25, 0, 63, 1005, 63, 497, 1105, 1, 503, 4, 487, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, 24, 2106, 0, -3, 4, 509, 1001, 64, 1, 64, 1105, 1, 521, 1002, 64, 2, 64, 109, -20, 1208, -2, 37, 63, 1005, 63, 543, 4, 527, 1001, 64, 1, 64, 1106, 0, 543, 1002, 64, 2, 64, 109, 7, 21102, 41, 1, 0, 1008, 1018, 43, 63, 1005, 63, 563, 1105, 1, 569, 4, 549, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, -7, 1205, 10, 587, 4, 575, 1001, 64, 1, 64, 1106, 0, 587, 1002, 64, 2, 64, 109, -11, 1202, 5, 1, 63, 1008, 63, 30, 63, 1005, 63, 609, 4, 593, 1106, 0, 613, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, 4, 1201, 5, 0, 63, 1008, 63, 34, 63, 1005, 63, 637, 1001, 64, 1, 64, 1105, 1, 639, 4, 619, 1002, 64, 2, 64, 109, 12, 1206, 5, 651, 1105, 1, 657, 4, 645, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, 9, 21101, 42, 0, -7, 1008, 1018, 39, 63, 1005, 63, 677, 1105, 1, 683, 4, 663, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, -2, 21101, 43, 0, -8, 1008, 1015, 43, 63, 1005, 63, 705, 4, 689, 1106, 0, 709, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, -25, 2107, 38, 10, 63, 1005, 63, 727, 4, 715, 1106, 0, 731, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, 7, 2102, 1, 2, 63, 1008, 63, 24, 63, 1005, 63, 757, 4, 737, 1001, 64, 1, 64, 1105, 1, 757, 1002, 64, 2, 64, 109, -13, 1201, 10, 0, 63, 1008, 63, 29, 63, 1005, 63, 779, 4, 763, 1105, 1, 783, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, 30, 21108, 44, 41, -3, 1005, 1019, 803, 1001, 64, 1, 64, 1106, 0, 805, 4, 789, 1002, 64, 2, 64, 109, -2, 21102, 45, 1, -7, 1008, 1013, 45, 63, 1005, 63, 827, 4, 811, 1105, 1, 831, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, -16, 21107, 46, 47, 7, 1005, 1011, 849, 4, 837, 1106, 0, 853, 1001, 64, 1, 64, 1002, 64, 2, 64, 109, 9, 21108, 47, 47, 0, 1005, 1013, 875, 4, 859, 1001, 64, 1, 64, 1106, 0, 875, 1002, 64, 2, 64, 109, -10, 2101, 0, 2, 63, 1008, 63, 30, 63, 1005, 63, 901, 4, 881, 1001, 64, 1, 64, 1105, 1, 901, 4, 64, 99, 21102, 1, 27, 1, 21102, 1, 915, 0, 1106, 0, 922, 21201, 1, 51805, 1, 204, 1, 99, 109, 3, 1207, -2, 3, 63, 1005, 63, 964, 21201, -2, -1, 1, 21101, 942, 0, 0, 1106, 0, 922, 22101, 0, 1, -1, 21201, -2, -3, 1, 21101, 0, 957, 0, 1105, 1, 922, 22201, 1, -1, -2, 1105, 1, 968, 21201, -2, 0, -2, 109, -3, 2105, 1, 0};

            var computer = new Computer(new long[]{1}, program.clone(), DEBUG_MODE);
            List<Long> res = new ArrayList<>();
            while (!computer.isHalted()) {
                res.add(computer.run());
            }
            computer = new Computer(new long[]{2}, program.clone(), DEBUG_MODE);
            List<Long> res2 = new ArrayList<>();
            while (!computer.isHalted()) {
                res2.add(computer.run());
            }
            System.out.println("Part 1: " + res);
            System.out.println("Part 2: " + res2);
        } else {
            TestRunner.runTests();
        }
    }

    static class Computer {
        private final Io io;
        private final Pointer pointer;
        private long[] program;
        private final Disassembler da;

        public Computer(long[] inputs, long[] program, int debugMode) {
            this.io = new Io(inputs);
            this.pointer = new Pointer();
            this.program = program;
            this.da = new Disassembler(debugMode);
        }

        public void addNextInput(long i) {
            io.addNextInput(i);
        }

        private long[] increaseMemory(long[] memory, Pointer pointer) {
            for (var i = 0; i < 3; i++) {
                var newLength = pointer.getValue() + i + 1;
                newLength = Math.max(newLength, (int) (pointer.getRelativeBase() + memory[pointer.getValue() + i + 1]));
                newLength = Math.max(newLength, (int) memory[pointer.getValue() + i + 1]);

                if (memory.length <= newLength) {
                    memory = Arrays.copyOf(memory, memory.length + memory.length / 2);
                }
            }
            return memory;
        }

        public long run() throws ReflectiveOperationException {
            da.printProgram(program, 1);

            while (true) {
                long opcode = program[pointer.getValue()] % 100;
                long modes = program[pointer.getValue()] / 100;

                da.printProgramWithSteps(program, pointer, 2);

                program = increaseMemory(program, pointer);

                Command command = CommandFactory.create(opcode, modes, program, pointer);

                var executionResult = command.execute(program, pointer, io);

                pointer.setValue(pointer.getValue() + command.getCommandSize());

                if (executionResult == -1) {
                    return io.popOutput();
                }
            }
        }

        public boolean isHalted() {
            if (program.length <= pointer.getValue()) {
                return true;
            }
            return opcodes.get(program[pointer.getValue()] % 100) == Halt.class;
        }

        public long getOutput() {
            return io.popOutput();
        }
    }

    abstract static class Command {
        private final long modes;

        private final long[] params;

        public Command() {
            this.modes = 0;
            this.params = new long[]{};
        }

        public Command(long modes, long[] data, Pointer pointer) {
            this.modes = modes;
            this.params = new long[getParamsCount()];
            for (var i = 0; i < getParamsCount(); i++) {
                var currentMode = getModes() % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                if (currentMode == 1) {
                    this.params[i] = data[pointer.getValue() + i + 1];
                } else if (currentMode == 2) {
                    this.params[i] = data[(int) (pointer.getRelativeBase() + data[pointer.getValue() + i + 1])];
                } else {
                    this.params[i] = data[(int) data[pointer.getValue() + i + 1]];
                }
            }
        }

        public abstract String getName();

        public abstract int getParamsCount();

        public long getParam(int idx) {
            return this.params[idx];
        }

        public int getCommandSize() {
            return getParamsCount() + 1;
        }

        public long getModes() {
            return modes;
        }

        public abstract int execute(long[] data, Pointer pointer, Io io);
    }

    static class Add extends Command {
        public Add() {
            super();
        }

        public Add(long modes, long[] data, Pointer pointer) {
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
        public int execute(long[] data, Pointer pointer, Io io) {
            int storageAddress;
            if (getModes() / 100 == 2) {
                storageAddress = (int) (pointer.getRelativeBase() + data[pointer.getValue() + 3]);
            } else {
                storageAddress = (int) data[pointer.getValue() + 3];
            }
            long a = getParam(0);
            long b = getParam(1);
            data[storageAddress] = a + b;
            return 0;
        }
    }

    static class Multiply extends Command {
        public Multiply() {
            super();
        }

        public Multiply(long modes, long[] data, Pointer pointer) {
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
        public int execute(long[] data, Pointer pointer, Io io) {
            int storageAddress;
            if (getModes() / 100 == 2) {
                storageAddress = (int) (pointer.getRelativeBase() + data[pointer.getValue() + 3]);
            } else {
                storageAddress = (int) data[pointer.getValue() + 3];
            }
            long a = getParam(0);
            long b = getParam(1);
            data[storageAddress] = a * b;
            return 0;
        }
    }

    static class Input extends Command {
        public Input() {
            super();
        }

        public Input(long modes, long[] data, Pointer pointer) {
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
        public int execute(long[] data, Pointer pointer, Io io) {
            if (getModes() % 10 == 2) {
                data[(int) (pointer.getRelativeBase() + data[pointer.getValue() + 1])] = io.nextInput();
            } else {
                data[(int) data[pointer.getValue() + 1]] = io.nextInput();
            }
            return 0;
        }
    }

    static class Output extends Command {
        public Output() {
            super();
        }

        public Output(long modes, long[] data, Pointer pointer) {
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
        public int execute(long[] data, Pointer pointer, Io io) {
            io.setOutput(getParam(0));
            return -1;
        }
    }

    static class JumpIfTrue extends Command {
        public JumpIfTrue() {
            super();
        }

        public JumpIfTrue(long modes, long[] data, Pointer pointer) {
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
        public int execute(long[] data, Pointer pointer, Io io) {
            if (getParam(0) != 0) {
                pointer.setValue((int) (getParam(1) - getCommandSize()));
            }
            return 0;
        }
    }

    static class JumpIfFalse extends Command {
        public JumpIfFalse() {
            super();
        }

        public JumpIfFalse(long modes, long[] data, Pointer pointer) {
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
        public int execute(long[] data, Pointer pointer, Io io) {
            if (getParam(0) == 0) {
                pointer.setValue((int) (getParam(1) - getCommandSize()));
            }
            return 0;
        }
    }

    static class LessThan extends Command {
        public LessThan() {
            super();
        }

        public LessThan(long modes, long[] data, Pointer pointer) {
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
        public int execute(long[] data, Pointer pointer, Io io) {
            var value = getParam(0) < getParam(1) ? 1 : 0;

            int storageAddress;
            if (getModes() / 100 == 2) {
                storageAddress = (int) (pointer.getRelativeBase() + data[pointer.getValue() + 3]);
            } else {
                storageAddress = (int) data[pointer.getValue() + 3];
            }

            data[storageAddress] = value;
            return 0;
        }
    }

    static class Equals extends Command {
        public Equals() {
            super();
        }

        public Equals(long modes, long[] data, Pointer pointer) {
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
        public int execute(long[] data, Pointer pointer, Io io) {
            var value = getParam(0) == getParam(1) ? 1 : 0;

            int storageAddress;
            if (getModes() / 100 == 2) {
                storageAddress = (int) (pointer.getRelativeBase() + data[pointer.getValue() + 3]);
            } else {
                storageAddress = (int) data[pointer.getValue() + 3];
            }

            data[storageAddress] = value;
            return 0;
        }
    }

    static class AdjustRelativeBase extends Command {
        public AdjustRelativeBase() {
            super();
        }

        public AdjustRelativeBase(long modes, long[] data, Pointer pointer) {
            super(modes, data, pointer);
        }

        @Override
        public String getName() {
            return "ADJ";
        }

        @Override
        public int getParamsCount() {
            return 1;
        }

        @Override
        public int execute(long[] data, Pointer pointer, Io io) {
            pointer.offsetRelativeBase((int) getParam(0));
            return 0;
        }
    }

    static class Halt extends Command {
        public Halt() {
            super();
        }

        public Halt(long modes, long[] data, Pointer pointer) {
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
        public int getCommandSize() {
            return 0;
        }

        @Override
        public int execute(long[] data, Pointer pointer, Io io) {
            return -1;
        }
    }

    static class CommandFactory {
        public static Command create(long opcode, long modes, long[] data, Pointer pointer)
            throws ReflectiveOperationException {

            return (Command) Class.forName(opcodes.get(opcode).getName()).getConstructor(
                long.class, long[].class, Pointer.class
            ).newInstance(modes, data, pointer);
        }
    }

    static class Io {
        private long output = 0;
        private final List<Long> inputs;
        private int inputPointer = 0;

        public Io(long[] inputs) {
            this.inputs = ComputerHelper.arrToList(inputs);
        }

        public void addNextInput(long i) {
            inputs.add(i);
        }

        public long nextInput() {
            if (inputPointer >= inputs.size()) {
                System.out.println("NO NEXT INPUT! Read pointer: " + inputPointer + ", inputs: " + inputs.toString());
            }
            return inputs.get(inputPointer++);
        }

        public long popOutput() {
            var o = output;
            output = 0;
            return o;
        }

        public void setOutput(long value) {
            this.output = value;
        }
    }

    static class Pointer {
        private int value = 0;
        private int relativeBase = 0;

        public Pointer() {
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getRelativeBase() {
            return relativeBase;
        }

        public void offsetRelativeBase(int offset) {
            relativeBase += offset;
        }
    }

    static class Test {
        private final String name;
        private final int input;
        private final List<Long> expectedOutput;
        private final long[] program;

        public Test(String name, int input, long[] expectedOutput, long[] program) {
            this.name = name;
            this.input = input;
            this.expectedOutput = ComputerHelper.arrToList(expectedOutput);
            this.program = program;
        }

        public Test(String name, int input, long expectedOutput, long[] program) {
            this(name, input, new long[]{expectedOutput}, program);
        }


        public String getName() {
            return name;
        }

        public int getInput() {
            return input;
        }

        public List<Long> getExpectedOutput() {
            return expectedOutput;
        }

        public long[] getProgram() {
            return program;
        }
    }

    static class TestRunner {
        public static void runTests() throws ReflectiveOperationException {
            var tests = new Test[]{
                new Test("Advent day 5 part 1", 1, new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 5182797}, new long[]{3, 225, 1, 225, 6, 6, 1100, 1, 238, 225, 104, 0, 1101, 40, 27, 224, 101, -67, 224, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 2, 224, 1, 224, 223, 223, 1101, 33, 38, 225, 1102, 84, 60, 225, 1101, 65, 62, 225, 1002, 36, 13, 224, 1001, 224, -494, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 3, 224, 1, 223, 224, 223, 1102, 86, 5, 224, 101, -430, 224, 224, 4, 224, 1002, 223, 8, 223, 101, 6, 224, 224, 1, 223, 224, 223, 1102, 23, 50, 225, 1001, 44, 10, 224, 101, -72, 224, 224, 4, 224, 102, 8, 223, 223, 101, 1, 224, 224, 1, 224, 223, 223, 102, 47, 217, 224, 1001, 224, -2303, 224, 4, 224, 102, 8, 223, 223, 101, 2, 224, 224, 1, 223, 224, 223, 1102, 71, 84, 225, 101, 91, 40, 224, 1001, 224, -151, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 5, 224, 1, 223, 224, 223, 1101, 87, 91, 225, 1102, 71, 19, 225, 1, 92, 140, 224, 101, -134, 224, 224, 4, 224, 1002, 223, 8, 223, 101, 1, 224, 224, 1, 224, 223, 223, 2, 170, 165, 224, 1001, 224, -1653, 224, 4, 224, 1002, 223, 8, 223, 101, 5, 224, 224, 1, 223, 224, 223, 1101, 49, 32, 225, 4, 223, 99, 0, 0, 0, 677, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1105, 0, 99999, 1105, 227, 247, 1105, 1, 99999, 1005, 227, 99999, 1005, 0, 256, 1105, 1, 99999, 1106, 227, 99999, 1106, 0, 265, 1105, 1, 99999, 1006, 0, 99999, 1006, 227, 274, 1105, 1, 99999, 1105, 1, 280, 1105, 1, 99999, 1, 225, 225, 225, 1101, 294, 0, 0, 105, 1, 0, 1105, 1, 99999, 1106, 0, 300, 1105, 1, 99999, 1, 225, 225, 225, 1101, 314, 0, 0, 106, 0, 0, 1105, 1, 99999, 1107, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 329, 101, 1, 223, 223, 8, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 344, 101, 1, 223, 223, 1007, 677, 226, 224, 102, 2, 223, 223, 1005, 224, 359, 101, 1, 223, 223, 8, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 374, 101, 1, 223, 223, 1107, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 389, 1001, 223, 1, 223, 108, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 404, 1001, 223, 1, 223, 108, 677, 677, 224, 1002, 223, 2, 223, 1006, 224, 419, 101, 1, 223, 223, 107, 677, 677, 224, 102, 2, 223, 223, 1006, 224, 434, 101, 1, 223, 223, 108, 226, 226, 224, 1002, 223, 2, 223, 1006, 224, 449, 1001, 223, 1, 223, 8, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 464, 101, 1, 223, 223, 1108, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 479, 1001, 223, 1, 223, 1108, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 494, 101, 1, 223, 223, 7, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 509, 101, 1, 223, 223, 1007, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 524, 101, 1, 223, 223, 7, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 539, 101, 1, 223, 223, 1107, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 554, 101, 1, 223, 223, 107, 226, 677, 224, 1002, 223, 2, 223, 1005, 224, 569, 101, 1, 223, 223, 107, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 584, 101, 1, 223, 223, 1108, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 599, 1001, 223, 1, 223, 1008, 677, 677, 224, 102, 2, 223, 223, 1006, 224, 614, 101, 1, 223, 223, 7, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 629, 101, 1, 223, 223, 1008, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 644, 101, 1, 223, 223, 1007, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 659, 1001, 223, 1, 223, 1008, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 674, 1001, 223, 1, 223, 4, 223, 99, 226}),
                new Test("Advent day 5 part 2", 5, 12077198, new long[]{3, 225, 1, 225, 6, 6, 1100, 1, 238, 225, 104, 0, 1101, 40, 27, 224, 101, -67, 224, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 2, 224, 1, 224, 223, 223, 1101, 33, 38, 225, 1102, 84, 60, 225, 1101, 65, 62, 225, 1002, 36, 13, 224, 1001, 224, -494, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 3, 224, 1, 223, 224, 223, 1102, 86, 5, 224, 101, -430, 224, 224, 4, 224, 1002, 223, 8, 223, 101, 6, 224, 224, 1, 223, 224, 223, 1102, 23, 50, 225, 1001, 44, 10, 224, 101, -72, 224, 224, 4, 224, 102, 8, 223, 223, 101, 1, 224, 224, 1, 224, 223, 223, 102, 47, 217, 224, 1001, 224, -2303, 224, 4, 224, 102, 8, 223, 223, 101, 2, 224, 224, 1, 223, 224, 223, 1102, 71, 84, 225, 101, 91, 40, 224, 1001, 224, -151, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 5, 224, 1, 223, 224, 223, 1101, 87, 91, 225, 1102, 71, 19, 225, 1, 92, 140, 224, 101, -134, 224, 224, 4, 224, 1002, 223, 8, 223, 101, 1, 224, 224, 1, 224, 223, 223, 2, 170, 165, 224, 1001, 224, -1653, 224, 4, 224, 1002, 223, 8, 223, 101, 5, 224, 224, 1, 223, 224, 223, 1101, 49, 32, 225, 4, 223, 99, 0, 0, 0, 677, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1105, 0, 99999, 1105, 227, 247, 1105, 1, 99999, 1005, 227, 99999, 1005, 0, 256, 1105, 1, 99999, 1106, 227, 99999, 1106, 0, 265, 1105, 1, 99999, 1006, 0, 99999, 1006, 227, 274, 1105, 1, 99999, 1105, 1, 280, 1105, 1, 99999, 1, 225, 225, 225, 1101, 294, 0, 0, 105, 1, 0, 1105, 1, 99999, 1106, 0, 300, 1105, 1, 99999, 1, 225, 225, 225, 1101, 314, 0, 0, 106, 0, 0, 1105, 1, 99999, 1107, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 329, 101, 1, 223, 223, 8, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 344, 101, 1, 223, 223, 1007, 677, 226, 224, 102, 2, 223, 223, 1005, 224, 359, 101, 1, 223, 223, 8, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 374, 101, 1, 223, 223, 1107, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 389, 1001, 223, 1, 223, 108, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 404, 1001, 223, 1, 223, 108, 677, 677, 224, 1002, 223, 2, 223, 1006, 224, 419, 101, 1, 223, 223, 107, 677, 677, 224, 102, 2, 223, 223, 1006, 224, 434, 101, 1, 223, 223, 108, 226, 226, 224, 1002, 223, 2, 223, 1006, 224, 449, 1001, 223, 1, 223, 8, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 464, 101, 1, 223, 223, 1108, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 479, 1001, 223, 1, 223, 1108, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 494, 101, 1, 223, 223, 7, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 509, 101, 1, 223, 223, 1007, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 524, 101, 1, 223, 223, 7, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 539, 101, 1, 223, 223, 1107, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 554, 101, 1, 223, 223, 107, 226, 677, 224, 1002, 223, 2, 223, 1005, 224, 569, 101, 1, 223, 223, 107, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 584, 101, 1, 223, 223, 1108, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 599, 1001, 223, 1, 223, 1008, 677, 677, 224, 102, 2, 223, 223, 1006, 224, 614, 101, 1, 223, 223, 7, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 629, 101, 1, 223, 223, 1008, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 644, 101, 1, 223, 223, 1007, 226, 226, 224, 1002, 223, 2, 223, 1005, 224, 659, 1001, 223, 1, 223, 1008, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 674, 1001, 223, 1, 223, 4, 223, 99, 226}),

                new Test("Comparator equals", 8, new long[]{1000, 0}, new long[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}),
                new Test("Comparator less than", 1, new long[]{999, 0}, new long[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}),
                new Test("Comparator greater than", 9, new long[]{1001, 0}, new long[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}),
                new Test("Comparator greater than", 90, new long[]{1001, 0}, new long[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}),

                new Test("Zero checker pos 0", 0, 0, new long[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9}),
                new Test("Zero checker pos 1", 1, 1, new long[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9}),
                new Test("Zero checker pos 2", 2, 1, new long[]{3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9}),

                new Test("Zero checker imm 0", 0, 0, new long[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1}),
                new Test("Zero checker imm 1", 1, 1, new long[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1}),
                new Test("Zero checker imm 2", 2, 1, new long[]{3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1}),

                new Test("Equals 8 pos 8", 8, 1, new long[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Equals 8 pos 0", 0, 0, new long[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Equals 8 pos 1", 1, 0, new long[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Equals 8 pos 5", 5, 0, new long[]{3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8}),

                new Test("Equals 8 imm 8", 8, 1, new long[]{3, 3, 1108, -1, 8, 3, 4, 3, 99}),
                new Test("Equals 8 imm 0", 0, 0, new long[]{3, 3, 1108, -1, 8, 3, 4, 3, 99}),
                new Test("Equals 8 imm 1", 1, 0, new long[]{3, 3, 1108, -1, 8, 3, 4, 3, 99}),
                new Test("Equals 8 imm 5", 5, 0, new long[]{3, 3, 1108, -1, 8, 3, 4, 3, 99}),

                new Test("Less 8 pos 8", 8, 0, new long[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Less 8 pos 18", 18, 0, new long[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Less 8 pos 0", 0, 1, new long[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Less 8 pos 1", 1, 1, new long[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}),
                new Test("Less 8 pos 5", 5, 1, new long[]{3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8}),

                new Test("Less 8 imm 8", 8, 0, new long[]{3, 3, 1107, -1, 8, 3, 4, 3, 99}),
                new Test("Less 8 imm 18", 18, 0, new long[]{3, 3, 1107, -1, 8, 3, 4, 3, 99}),
                new Test("Less 8 imm 0", 0, 1, new long[]{3, 3, 1107, -1, 8, 3, 4, 3, 99}),
                new Test("Less 8 imm 1", 1, 1, new long[]{3, 3, 1107, -1, 8, 3, 4, 3, 99}),
                new Test("Less 8 imm 5", 5, 1, new long[]{3, 3, 1107, -1, 8, 3, 4, 3, 99}),

                new Test("Comparator less than", 5, new long[]{999, 0}, new long[]{3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99}),

                new Test("Copy of itself", 0, new long[]{109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99, 0}, new long[]{109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99}),
                new Test("Large number", 0, 1219070632396864L, new long[]{1102, 34915192, 34915192, 7, 4, 7, 99, 0}),
                new Test("Large number 2", 0, 1125899906842624L, new long[]{104, 1125899906842624L, 99})
            };

            var succeeded = 0;
            var failed = new ArrayList<String>();

            for (var test : tests) {
                var computer = new Computer(new long[]{test.getInput()}, test.getProgram().clone(), DEBUG_MODE);

                List<Long> execResult = new ArrayList<>();
                while (!computer.isHalted()) {
                    execResult.add(computer.run());
                }
                if (execResult.equals(test.getExpectedOutput())) {
                    System.out.print(".");
                    succeeded++;
                } else {
                    failed.add("FAILED! Input: " + test.getInput() + ",  Output: " + execResult.toString() + ", expected: " + test
                        .getExpectedOutput() + " " + test
                        .getName());
                }
            }

            System.out.println("\nDone! [ " + succeeded + " / " + tests.length + " ] succeeded.");
            if (failed.size() > 0) {
                System.out.println("Failed: " + failed.size());
            }
            for (var failedMessage : failed) {
                System.out.println(failedMessage);
            }
        }
    }

    static class ComputerHelper {
        public static ArrayList<ArrayList<Long>> permute(long[] num) {
            ArrayList<ArrayList<Long>> result = new ArrayList<>();

            //start from an empty list
            result.add(new ArrayList<>());

            for (long k : num) {
                //list of list in current iteration of the array num
                ArrayList<ArrayList<Long>> current = new ArrayList<>();

                for (ArrayList<Long> l : result) {
                    // # of locations to insert is largest index + 1
                    for (int j = 0; j < l.size() + 1; j++) {
                        // + add num[i] to different locations
                        l.add(j, k);

                        ArrayList<Long> temp = new ArrayList<>(l);
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

        public static List<Long> arrToList(long[] arr) {
            return Arrays.stream(arr).boxed().collect(Collectors.toList());
        }
    }

    static class Disassembler {
        private final int debugMode;

        public Disassembler(int debugMode) {
            this.debugMode = debugMode;
        }

        public void printProgramWithSteps(long[] program, Pointer pointer, int minDebugModeRequired) {
            if (debugMode >= minDebugModeRequired) {
                System.out.println("\n\n\nstep " + pointer.getValue());
                printProgram(program);
            }
        }

        public void printProgram(long[] program, int minDebugModeRequired) {
            if (debugMode >= minDebugModeRequired) {
                printProgram(program);
            }
        }

        public void printProgram(long[] program) {
            System.out.println("========================");
            for (var i = 0; i < program.length; ) {
                long opcode = program[i] % 100;
                long modes = program[i] / 100;

                var s = new StringBuilder();

                try {
                    var c = createCommand(opcode);
                    s.append(i).append(": ");
                    s.append(c.getName()).append(" ");

                    for (var j = 0; j < c.getParamsCount(); j++) {
                        if (modes % (int) Math.pow(10, j + 1) / (int) Math.pow(10, j) == 0) {
                            s.append("&");
                        }
                        if (program.length <= i + j + 1) {
                            break;
                        }
                        s.append(program[i + j + 1]).append(" ");
                    }

                    i += c.getCommandSize();
                    if (c instanceof Halt) {
                        i++;
                    }
                } catch (ReflectiveOperationException e) {
                    s.append(i).append(": ").append(program[i]);
                    i++;
                }

                System.out.println(s.toString());
            }
        }

        private Command createCommand(long opcode)
            throws ReflectiveOperationException {
            var t = opcodes.get(opcode);
            if (t == null) {
                throw new ReflectiveOperationException();
            }
            return (Command) Class.forName(t.getName()).getConstructor().newInstance();
        }
    }
}
