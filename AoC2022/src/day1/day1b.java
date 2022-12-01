package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class day1b {
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

            Collections.sort(eachELf);
            int countTopThree = eachELf.get(eachELf.size()-1) + eachELf.get(eachELf.size()-2) +eachELf.get(eachELf.size()-3);
            System.out.println(countTopThree);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

