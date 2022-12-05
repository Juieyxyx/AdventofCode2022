package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class day5a {
    public static void main(String[] args) {
        new day5a().run();
    }

    private void run(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("inputs/input_day5.txt"));
            String line;
            List<String> input = new ArrayList<>();
            while ((line= br.readLine())!= null) {
                input.add(line);
            }

            int instructionsStart = getInstructionsStart(input);
            int amountOfStacks = getAmountOfStacks(input, instructionsStart);

            Map<String, Stack<String>> stacks = getStacks(input, amountOfStacks, instructionsStart);

            for (int i = instructionsStart; i < input.size(); i++) {
                String instruction = input.get(i);

                String[] instructionSet = instruction.replace("move ", "").replace(" from ", " ").replace(" to ", " ").split(" ");

                int amountToMove = Integer.parseInt(instructionSet[0]);
                String from = instructionSet[1];
                String to = instructionSet[2];

                for (int j = 0; j < amountToMove; j++) {
                    String crate = stacks.get(from).pop();
                    stacks.get(to).push(crate);
                }
            }

            for (Map.Entry<String, Stack<String>> stack : stacks.entrySet()) {
                if (!stack.getValue().isEmpty()) {
                    System.out.print(stack.getValue().peek());
                }
            }
            System.out.println();
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    private Map<String, Stack<String>> getStacks(List<String> input, int amountOfStacks, int instructionsStart) {

        Map<String, Stack<String>> stacks = new HashMap<>();

        for (int i = 0; i < amountOfStacks; i++) {

            Stack<String> stack = new Stack<>();
            stacks.put(String.valueOf(i + 1), stack);


            int column;

            if (i == 0) {
                column = 1;
            } else {
                column = i * 4 + 1;
            }

            for (int j = instructionsStart - 3; j >= 0; j--) {
                String row = input.get(j);
                try {
                    String value = row.substring(column, column + 1);
                    if (!" ".equals(value)) {
                        stack.push(value);
                    }
                } catch (IndexOutOfBoundsException ignored) {}
            }


        }

        return stacks;
    }

    private int getAmountOfStacks(List<String> input, int instructionsStart) {
        String[] counting = input.get(instructionsStart - 2).split(" ");
        return counting.length;
    }


    private int getInstructionsStart(List<String> input) {
        int index = 1;
        for (String line : input) {
            if (line.equals("")) {
                break;
            }
            index++;
        }
        return index;
    }
}