package day24;

public class Runner {
        public static void run(IDay d) {
            long part1Start = System.currentTimeMillis();
            String part1 = d.part1();
            if(part1 == null) part1 = "Part 1 has not yet been implemented.";
            System.out.println("Part 1 Solution: "+ part1);
            long part1End = System.currentTimeMillis();
            System.out.println("Part 1 Runtime: " + ((part1End - part1Start)/1000.0) + " seconds");
            long part2Start = System.currentTimeMillis();
            String part2 = d.part2();
            if(part2 == null) part2 = "Part 2 has not yet been implemented.";
            System.out.println("Part 2 Solution: " + part2);
            long part2End = System.currentTimeMillis();
            System.out.println("Part 2 Runtime: " + ((part2End - part2Start)/1000.0) + " seconds");
        }
    }

