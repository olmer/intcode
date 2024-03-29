package aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Aoc1907 {
    private static final Map<Integer, Class<? extends Command>> opcodes = new HashMap<>() {{
        put(1, Add.class);
        put(2, Multiply.class);
        put(3, Input.class);
        put(4, Output.class);
        put(5, JumpIfTrue.class);
        put(6, JumpIfFalse.class);
        put(7, LessThan.class);
        put(8, Equals.class);
        put(99, Halt.class);
    }};

    private static final byte DEBUG_MODE = 0;
    private static final boolean LIVE_MODE = true;

    public static void main(String[] args) {
        if (LIVE_MODE) {
            var program = new int[]{3, 8, 1001, 8, 10, 8, 105, 1, 0, 0, 21, 34, 43, 60, 81, 94, 175, 256, 337, 418, 99999, 3, 9, 101, 2, 9, 9, 102, 4, 9, 9, 4, 9, 99, 3, 9, 102, 2, 9, 9, 4, 9, 99, 3, 9, 102, 4, 9, 9, 1001, 9, 4, 9, 102, 3, 9, 9, 4, 9, 99, 3, 9, 102, 4, 9, 9, 1001, 9, 2, 9, 1002, 9, 3, 9, 101, 4, 9, 9, 4, 9, 99, 3, 9, 1001, 9, 4, 9, 102, 2, 9, 9, 4, 9, 99, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 99, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 99, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 99, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 99, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 99};

            var sequences = ComputerHelper.permute(new int[]{9, 8, 7, 6, 5});

            long maxResult = Long.MIN_VALUE;

            for (var sequence : sequences) {
                var computers = new Computer[]{
                    new Computer(new int[]{sequence.get(0)}, program.clone(), DEBUG_MODE),
                    new Computer(new int[]{sequence.get(1)}, program.clone(), DEBUG_MODE),
                    new Computer(new int[]{sequence.get(2)}, program.clone(), DEBUG_MODE),
                    new Computer(new int[]{sequence.get(3)}, program.clone(), DEBUG_MODE),
                    new Computer(new int[]{sequence.get(4)}, program.clone(), DEBUG_MODE)
                };

                var currentComputer = 0;
                var nextInput = 0;
                while (true) {
                    var computer = computers[currentComputer];
                    computer.addNextInput(nextInput);
                    nextInput = computer.run();

                    maxResult = maxResult > nextInput ? maxResult : nextInput;

                    if (computer.isHalted() && currentComputer == computers.length - 1) {
                        break;
                    }

                    currentComputer++;
                    if (currentComputer > 4) {
                        currentComputer = 0;
                    }
                }
            }

            System.out.println("Part 2: " + maxResult);
        } else {
            TestRunner.runTests();
        }
    }

    static class Computer {
        private final Io io;
        private final Pointer pointer;
        private final int[] program;
        private final Disassembler da;

        public Computer(int[] inputs, int[] program, int debugMode) {
            this.io = new Io(inputs);
            this.pointer = new Pointer();
            this.program = program;
            this.da = new Disassembler(debugMode);
        }

        public void addNextInput(int i) {
            io.addNextInput(i);
        }

        public int run() {
            da.printProgram(program, 1);

            while (true) {
                var opcode = program[pointer.getValue()] % 100;
                int modes = program[pointer.getValue()] / 100;

                da.printProgramWithSteps(program, pointer, 2);

                try {
                    Command command = CommandFactory.create(opcode, modes, program, pointer);

                    var executionResult = command.execute(program, pointer, io);

                    pointer.setValue(pointer.getValue() + command.getCommandSize());

                    if (executionResult == -1) {
                        return io.getOutput();
                    }
                } catch (ReflectiveOperationException e) {
                    System.out.println(e.getMessage() + " pointer: " + pointer.getValue());
                    break;
                }
            }

            return -1;
        }

        public boolean isHalted() {
            if (program.length <= pointer.getValue()) {
                return true;
            }
            return opcodes.get(program[pointer.getValue()] % 100) == Halt.class;
        }

        public int getOutput() {
            return io.getOutput();
        }
    }

    abstract static class Command {
        private final int modes;

        private final int[] params;

        public Command() {
            this.modes = 0;
            this.params = new int[]{};
        }

        public Command(int modes, int[] data, Pointer pointer) {
            this.modes = modes;
            this.params = new int[getParamsCount()];
            for (var i = 0; i < getParamsCount(); i++) {
                if (getModes() % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i) == 1) {
                    this.params[i] = data[pointer.getValue() + i + 1];
                } else {
                    this.params[i] = data[data[pointer.getValue() + i + 1]];
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
        public Add() {
            super();
        }

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
        public Multiply() {
            super();
        }

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
        public Input() {
            super();
        }

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
        public Output() {
            super();
        }

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
            return -1;
        }
    }

    static class JumpIfTrue extends Command {
        public JumpIfTrue() {
            super();
        }

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
        public JumpIfFalse() {
            super();
        }

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
        public LessThan() {
            super();
        }

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
        public Equals() {
            super();
        }

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
        public Halt() {
            super();
        }

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
        public int getCommandSize() {
            return 0;
        }

        @Override
        public int execute(int[] data, Pointer pointer, Io io) {
            return -1;
        }
    }

    static class CommandFactory {
        public static Command create(int opcode, int modes, int[] data, Pointer pointer)
            throws ReflectiveOperationException {

            return (Command) Class.forName(opcodes.get(opcode).getName()).getConstructor(
                int.class, int[].class, Pointer.class
            ).newInstance(modes, data, pointer);
        }
    }

    static class UnknownOpcode extends Exception {
        public UnknownOpcode(String message) {
            super(message);
        }
    }

    static class Io {
        private int output = 0;
        private final List<Integer> inputs;
        private int inputPointer = 0;

        public Io(int[] inputs) {
            this.inputs = Arrays.stream(inputs).boxed().collect(Collectors.toList());
        }

        public void addNextInput(int i) {
            inputs.add(i);
        }

        public int nextInput() {
            if (inputPointer >= inputs.size()) {
                System.out.println("NO NEXT INPUT! Read pointer: " + inputPointer + ", inputs: " + inputs.toString());
            }
            return inputs.get(inputPointer++);
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
        public static void runTests() {
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
                var computer = new Computer(new int[]{test.getInput()}, test.getProgram().clone(), DEBUG_MODE);

                while (!computer.isHalted()) {
                    computer.run();
                }
                var programResult = computer.getOutput();
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

    static class Disassembler {
        private final int debugMode;

        public Disassembler(int debugMode) {
            this.debugMode = debugMode;
        }

        public void printProgramWithSteps(int[] program, Pointer pointer, int minDebugModeRequired) {
            if (debugMode >= minDebugModeRequired) {
                System.out.println("\n\n\nstep " + pointer.getValue());
                printProgram(program);
            }
        }

        public void printProgram(int[] program, int minDebugModeRequired) {
            if (debugMode >= minDebugModeRequired) {
                printProgram(program);
            }
        }

        public void printProgram(int[] program) {
            System.out.println("========================");
            for (var i = 0; i < program.length; ) {
                var opcode = program[i] % 100;
                int modes = program[i] / 100;

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

        private Command createCommand(int opcode)
            throws ReflectiveOperationException {
            var t = opcodes.get(opcode);
            if (t == null) {
                throw new ReflectiveOperationException();
            }
            return (Command) Class.forName(t.getName()).getConstructor().newInstance();
        }
    }
}
