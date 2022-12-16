package day15;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class day15b {
    static class Sensor {
        int x, y;
        int beaconX, beaconY;

        public Sensor(String line) {
            int[] vals =  Pattern.compile("\\D*?(-?[0-9]+)").matcher(line).results()
                    .mapToInt( r -> Integer.parseInt(r.group(1))).toArray();

            x = vals[0];
            y = vals[1];
            beaconX = vals[2];
            beaconY = vals[3];
        }

        public String toString() {
            return "Sensor at "+x+", "+y+"  -> Beacon at "+beaconX+", "+beaconY;
        }

        public Range rangeAt(int row) {
            int dist = Math.abs(x-beaconX)+ Math.abs(y-beaconY);
            if( row < y-dist || row > y+dist ) { //超出了最大值
                return null;
            }
            return new Range( x-(dist - Math.abs(y-row)), x+ (dist - Math.abs(y-row)));
            //返回 x坐标的范围：能使在这个row上和这个Sensor的曼哈顿距离相等；
        }
    }

    static class Range {
        //在当前row上的x范围最大值
        int min, max;
        Range (int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int size() {
            return 1+max-min;
        }
    }





    public static void main(String[] args) {
        File file = new File("AoC2022/inputs/input_day15.txt");
        try {
            List<Sensor> sensors = Files.readAllLines(file.toPath())
                    .stream()
                    .map(Sensor::new).toList();

            //sensors.forEach(System.out::println);

            int row = 2000000;
            Stack<Range> combined = getRanges(sensors, row);

            //可能的beacon的数目
            long countRangeSum = combined.stream().mapToInt(Range::size).sum();

            //input数据中，Count所有在此row上的beacon数
            long countInputBeaconX = sensors.stream().filter(s -> s.beaconY == row).map(s -> s.beaconX).distinct().count();
            long total =  countRangeSum - countInputBeaconX;
            //System.out.println("Part one answer is "+total);
            long time = System.currentTimeMillis();

            for(int i=0; i<4000000; i++) {
                combined = getRanges(sensors, i);

                // Crop ranges to our visible area

                //检查栈底
                combined.get(0).min = Math.max(0, combined.get(0).min);
                //检查栈顶
                combined.peek().max = Math.min(4000000, combined.peek().max);


                countRangeSum = combined.stream().mapToInt(Range::size).sum();


                if( countRangeSum != 4000001 ) {
                    // If the ranges don't cover every single square, find the gap
                    for( int ind=0; ind<combined.size()-1; ind++ ) {
                        if(combined.get(ind).max < combined.get(ind+1).min) {
                            System.out.println("On row "+i+" gap is "+combined.get(ind).max+" - "+combined.get(ind+1).min);
                            System.out.println("Part 2 result = "+(4000000L*(combined.get(ind).max+1) + i));
                        }
                    }
                    break;
                }
            }
            System.out.println("Time "+((System.currentTimeMillis()-time)/1000.0)+" seconds");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stack<Range> getRanges(List<Sensor> sensors, int row) {
        //在指定的row中，将input中的所有传感器取出可能的beacon的x范围值，按照从大到小排序
        List<Range> ranges = sensors.stream()
                .map(s -> s.rangeAt(row))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(r -> r.min))
                .toList();

        Stack<Range> combined = new Stack<>();

        //在可能的beacon的x范围中取出每个范围值，找到最大的范围值
        for(Range current: ranges) {
            if( combined.empty() || combined.peek().max < current.min ) {
                combined.push(current);
            }
            else {
                combined.peek().max = Math.max(combined.peek().max, current.max);
            }
        }
        return combined;
    }
}