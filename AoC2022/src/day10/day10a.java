package day10;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class day10a {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("AoC2022/inputs/input_Day10.txt"));
        String line;
        long val = 1;
        int cycle = 0;
        long interestingCycleStrength = 0;

        while ((line=br.readLine())!=null){
            cycle++;
            if (check(val,cycle)!=0){
                System.out.println("此时的cycle是：" + cycle +"，此cycle的值是" + check(val,cycle));
            }
            interestingCycleStrength += check(val,cycle);


            if (!line.equals("noop")){
                cycle++;
                if (check(val,cycle)!=0){
                    System.out.println("此时的cycle是：" + cycle +"，此cycle的值是" + check(val,cycle));
                }
                interestingCycleStrength += check(val,cycle);
                int amount = Integer.parseInt(line.trim().split(" ")[1]);
                val += amount;
            }
        }
        System.out.println(interestingCycleStrength);
    }

    private static long check(long val, int cycle){
        if (cycle == 20 || cycle == 60 || cycle == 100
                || cycle == 140 || cycle == 180 || cycle == 220) {

            return (val * cycle);
        }
        return 0;
    }
}
