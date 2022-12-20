package day20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day20 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        File file = new File("AoC2022/inputs/input_day20.txt");
        Scanner in = new Scanner(file);
        System.out.println(solve(false, in));
        long endTime = System.currentTimeMillis();
        double elapsedTimeInSeconds = (endTime - startTime) / 1000.0;
        System.out.println("耗时" + elapsedTimeInSeconds);
    }


    public static String solve(boolean part1, Scanner in) throws FileNotFoundException {
        List<Mover> movers = new ArrayList<>();
        int counter = 0;
        while (in.hasNext()) {
            long val = Integer.parseInt(in.nextLine());
            movers.add(new Mover(counter, part1 ? val : val * 811589153));
            counter++;
        }
        for (int k = 0; k < (part1 ? 1 : 10); k++) {
            for (int i = 0; i < movers.size(); i++) {
                Mover mover = null;
                int loc = 0;
                for (int j = 0; j < movers.size(); j++) {
                    if (movers.get(j).uuid == i + 10000 * k) {
                        mover = movers.get(j);
                        loc = j;
                    }
                }
                movers.remove(loc);
                int rotate = (int) (mover.val % (movers.size()));
                movers.add(((loc + rotate) % (movers.size()) + (movers.size())) % (movers.size()), mover);
            }
            for (int i = 0; i < movers.size(); i++)
                movers.get(i).uuid = movers.get(i).uuid + 10000;
        }
        int offset = 0;
        for (int j = 0; j < movers.size(); j++) {
            if (movers.get(j).val == 0)
                offset = j;
        }
        return "" + (movers.get((offset + 1000) % movers.size()).val + movers.get((offset + 2000) % movers.size()).val + movers.get((offset + 3000) % movers.size()).val);
    }
}

class Mover {
    int uuid;
    long val;

    public Mover(int uuid, long val) {
        this.uuid = uuid;
        this.val = val;
    }
}

