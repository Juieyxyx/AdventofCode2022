package day15;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sensor {
    private int x = 0;
    private int y = 0;
    private int beaconX = 0;
    private int beaconY = 0;
    private int manhattan = 0;

    private static Pattern sensorPattern = Pattern.compile(
        "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)"
    );

    /**
     * 接收一行input数据，将Sensor的坐标（x，y）、beacon的坐标（beaconX，beaconY）和曼哈顿距离值（manhattan）存入该Sensor对象中
     * @param line 一行input数据
     */
    public Sensor(String line){
        Matcher m = sensorPattern.matcher(line);
        if (m.find()){
            x = Integer.parseInt(m.group(1));
            y = Integer.parseInt(m.group(2));
            beaconX = Integer.parseInt(m.group(3));
            beaconY = Integer.parseInt(m.group(4));
            manhattan = Math.abs(x-beaconX) + Math.abs(y-beaconY);
        }
    }


    /**
     * 其他与该组sensor-beacon的曼哈顿距离相同的假想beacon：和sensor在同一个y轴上，x坐标由两端延伸
     * @return
     */
    public int minX(){
        return x - manhattan;
    }

    public int maxX(){
        return x + manhattan;
    }



    public boolean shorterThanManhattan(int x, int y) {
        return Math.abs(this.x - x) + Math.abs(this.y - y) <= manhattan;
    }

    public boolean onTheLine(int x, int y) {
        return this.beaconX == x && this.beaconY == y;
    }



    public int yDistance(int y) {
        return Math.abs(this.y - y);
    }

}
