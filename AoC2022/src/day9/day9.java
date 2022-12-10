package day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class day9 {
    static Position[] followMotion(Position[] rope, String command, Set<Position> visitedByTail) {
        char direction = command.charAt(0);
        int amount = Integer.parseInt(command.substring(1).trim());
        Position[] newPositions = rope.clone();
        for (int i = 0; i < amount; i++) {
            newPositions[0] = move(newPositions[0], direction);
            for (int j = 1; j < newPositions.length; j++) {
                newPositions[j] = newPositions[j].determineNewPosition(newPositions[j - 1]);
            }
            visitedByTail.add(newPositions[newPositions.length - 1]);
        }
        return newPositions;
    }

    static Position move(Position start, char direction) {
        int xIncrement = switch (direction) {
            case 'R' -> 1;
            case 'L' -> -1;
            default -> 0;
        };

        int yIncrement = switch (direction) {
            case 'U' -> 1;
            case 'D' -> -1;
            default -> 0;
        };

        return new Position(start.x() + xIncrement, start.y() + yIncrement);
    }

    static int partA(String fileName) throws IOException {
        return moveRope(fileName, 2);
    }

    static int partB(String fileName) throws IOException {
        return moveRope(fileName, 10);
    }

    private static int moveRope(String fileName, int ropeLenght) throws IOException {
        Position[] rope = new Position[ropeLenght];
        Arrays.setAll(rope, i -> new Position(0, 0));

        Set<Position> visitedByTail = new HashSet<>();
        visitedByTail.add(rope[rope.length - 1]);


        BufferedReader br = new BufferedReader(new FileReader("AoC2022/inputs/input_day9.txt"));
        String line;
        while ((line=br.readLine())!=null){
            rope = followMotion(rope, line, visitedByTail);
        }

        return visitedByTail.size();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Day 9 Part A : " + partA("AoC2022/inputs/input_day9.txt"));
        System.out.println("Day 9 Part B : " + partB("AoC2022/inputs/input_day9.txt"));
    }

}

record Position(int x, int y) {

    Position determineNewPosition(Position head) {

        if (Math.abs(head.x - x) > 1 && Math.abs(head.y - y) > 1) {
            return new Position(x + (head.x < x ? -1 : 1), y + (head.y < y ? -1 : 1));
        }

        if (Math.abs(head.x - x) > 1) {
            return new Position(x + (head.x < x ? -1 : 1), head.y);
        }

        if (Math.abs(head.y - y) > 1) {
            return new Position(head.x, y + (head.y < y ? -1 : 1));
        }

        return this;
    }
}