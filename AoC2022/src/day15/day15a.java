package day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class day15a {

    public static void main(String[] args) throws Exception {
        List<Sensor> sensors = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("AoC2022/inputs/input_day15.txt"));
        String line;

        //将所有的input数据封装为Sensor对象，解析出input中所有sensor-beacon pair的坐标
        while ((line = br.readLine()) != null) {
            sensors.add(new Sensor(line));
        }

        int minX = Integer.MAX_VALUE;
        int maxX = 0;


        //通过对input数据中所有sensor-beacon进行处理，找出所有可能beacon的x坐标取值范围
        for (Sensor s : sensors){
            minX = Math.min(s.minX(), minX);
            maxX = Math.max(s.maxX(), maxX);
        }

        int count = 0;
        int y = 2000000;


        main:
        for (int x = minX; x<= maxX; x++) {
            for (Sensor s : sensors) {
                if (s.onTheLine(x, y)) {
                    //System.out.print("B"); //将input中所有y=10的已经被探测到的beacon输出
                    continue main;
                }
            }


            //如果在该y轴上没有已探测存在的beacon
            boolean found = false;
            for (Sensor s : sensors) {
                if (s.shorterThanManhattan(x, y)) {
                    //和所有input中的pair做对比，比pair中的beacon曼哈顿值相同或更小的beacon（即，不可能存在的beacon）
                    found = true;
                    count++;
                    break ;
                }
            }
            //System.out.print(found? "#" : ".");
        }


        System.out.println();
        System.out.println("result : "+ count);
    }
}