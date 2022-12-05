import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Aoc2205 {
  private static int STACKS = 3;

  public static void main(String[] args) {
    List<Stack<Character>> stacks = initStacks(getInput()[0].split("\n"));

    String[] commands = getInput()[1].split("\n");

    for (int i = 0; i < commands.length; i++) {
      int numCratesToMove = Integer.parseInt(commands[i].split(" ")[1]);
      int from = Integer.parseInt(commands[i].split(" ")[3]) - 1;
      int to = Integer.parseInt(commands[i].split(" ")[5]) - 1;

      Stack<Character> tempStack = new Stack<>();
      while (numCratesToMove > 0) {
        tempStack.push(stacks.get(from).pop());
        numCratesToMove--;
      }
      while (!tempStack.isEmpty())
        stacks.get(to).push(tempStack.pop());
    }

    for (var stack : stacks)
      if (!stack.isEmpty())
        System.out.print(stack.peek());
  }

  private static List<Stack<Character>> initStacks(String[] initPositions) {
    List<Stack<Character>> stacks = new ArrayList<>(STACKS);
    for (int stackNum = 0; stackNum < STACKS; stackNum++) {
      stacks.add(new Stack<>());
      for (int i = initPositions.length - 2; i >= 0; i--) {
        int crateNamePos = stackNum * 4 + 1;
        if (initPositions[i].charAt(crateNamePos) != ' ')
          stacks.get(stackNum).push(initPositions[i].charAt(crateNamePos));
      }
    }

    return stacks;
  }

  private static String[] getInput() {
    String s = "    [D]    \n" +
      "[N] [C]    \n" +
      "[Z] [M] [P]\n" +
      " 1   2   3 \n" +
      "\n" +
      "move 1 from 2 to 1\n" +
      "move 3 from 1 to 3\n" +
      "move 2 from 2 to 1\n" +
      "move 1 from 1 to 2";
    return s.split("\n\n");
  }
}
