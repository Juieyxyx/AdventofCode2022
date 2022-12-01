package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class day1a {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("inputs/input_day1.txt"));
            List<Integer> eachELf = new ArrayList<>();
            String line;
            int count = 0;
            while ((line= br.readLine())!=null){
                if (line.isBlank()) {
                    eachELf.add(count);
                    count =0;
                    continue;
                } else {
                    count += Integer.parseInt(line);
                }
            }
            eachELf.add(count);
            System.out.println(Collections.max(eachELf));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
