package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class day10b {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("AoC2022/inputs/input_Day10.txt"));
        String line;
        long val = 1;
        int cycle = 0;

        while ((line = br.readLine()) != null) {
            cycle++;
            printCycle(val, cycle);

        if (!line.equals("noop")){
            cycle++;
            printCycle(val, cycle);
            int amount = Integer.parseInt(line.trim().split(" ")[1]);
            val += amount;
        }
    }
}

    private static void printCycle(long val, int cycle) {
        String pixel = ".";
        if ((cycle % 40) >= val && (cycle % 40) <= val + 2) {
            pixel = "#";
        }
        System.out.print(pixel);
        if (cycle % 40 == 0) {
            System.out.print("\n");
        }
    }
}
