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
            int Manhattan = Math.abs(x-beaconX)+ Math.abs(y-beaconY);

            //如果纵坐标超出了该Sensor已匹配的曼哈顿距离：
            if( row < y-Manhattan || row > y+Manhattan ) {
                return null;
            }

            //如果纵坐标未超出该Sensor已匹配的曼哈顿距离：
            // -- >返回在此row上小于等于该Sensor已匹配的曼哈顿距离的横坐标范围(在此范围内不可能存在未探测到的beacon）
            return new Range( x-(Manhattan - Math.abs(y-row)), x+ (Manhattan - Math.abs(y-row)));

        }
    }



    static class Range {
        //在当前row上，小于等于该Sensor已匹配的曼哈顿距离的横坐标范围-->在此范围内不可能存在未探测到的beacon
        int minX, maxX;
        Range (int minX, int maxX) {
            this.minX = minX;
            this.maxX = maxX;
        }

        public int size() {
            return 1+maxX-minX;
        }
       //个数总和
    }





    public static void main(String[] args) {
    //1. 将所有input数据封装为Sensor对象，即获得已知S-B的坐标
        File file = new File("AoC2022/inputs/input_day15.txt");
        try {
            List<Sensor> sensors = Files.readAllLines(file.toPath())
                    .stream()
                    .map(Sensor::new).toList();


    //2. 获得part1的答案：找到当前row上不可能存在beacon的位置总和，减去已探测到的beacon个数
            int row = 2000000;

            Stack<Range> combined = getRanges(sensors, row);//简化的、合并后的Range大全

            //在该row上，不可能存在的beacon的数目总和
            long countRangeSum = combined.stream().mapToInt(Range::size).sum();

            //在该row上，已经被检测到存在的beacon数目总和
            long countInputBeaconX = sensors.stream().filter(s -> s.beaconY == row).map(s -> s.beaconX).distinct().count();


            long total =  countRangeSum - countInputBeaconX;
            //System.out.println("Part one answer is "+total);
            long time = System.currentTimeMillis();



    //3. 获得part2的答案：在指定的探测范围内，逐行寻找不可能存在beacon区域是否存在gap，若找到此gap点，该位置即为distress signal的位置
            for(int y=0; y<4_000_000; y++) {
                combined = getRanges(sensors, y); //在指定的y轴范围内，找到每行row上不可能存在beacon的位置总和，并简化合并

                //分别检查栈底的minX和栈顶的maxX，与指定的x轴范围做裁剪
                combined.get(0).minX = Math.max(0, combined.get(0).minX);
                combined.peek().maxX = Math.min(4_000_000, combined.peek().maxX);

                //在该row上，不可能存在的beacon的数目总和
                countRangeSum = combined.stream().mapToInt(Range::size).sum();


                //如果在该row上，不可能存在的beacon的数目总和没有填满整行，则说明存在gap：找到gap
                if( countRangeSum != 4_000_001 ) {
                    for( int i=0; i<combined.size()-1; i++ ) {//在combined里，从底部（minX最小值）开始，挨个寻找

                        if(combined.get(i).maxX < combined.get(i+1).minX) {
                            System.out.println("On row "+row+" gap is "+combined.get(i+1).minX+" - "+combined.get(i).maxX);
                            System.out.println("Part 2 result = "+(4_000_000L*(combined.get(i).maxX+1) + row));
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

        //在指定的row中，取出不可能匹配其他beacon的Range，根据minX从小到大排序
        List<Range> ranges = sensors.stream()
                .map(s -> s.rangeAt(row))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(r -> r.minX))
                .toList();

        Stack<Range> combined = new Stack<>();


        //合并Range
        for(Range currentMinX: ranges) {
            //对于无法合并的区域（x轴存在gap）：存进combined
            if( combined.empty() || combined.peek().maxX < currentMinX.minX ) {
                combined.push(currentMinX);
            }
            else {
            //对于可合并的区域：直接合并Range
                combined.peek().maxX = Math.max(combined.peek().maxX, currentMinX.maxX);
            }
        }
        return combined; //返回：在指定的row中不可能匹配其他beacon的Range（合并版）
    }
}