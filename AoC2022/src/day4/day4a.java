package day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class day4a {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("inputs/input_day4.txt"));
            String line;
            int count = 0 ;
            int sum = 0;

            while ((line = br.readLine()) != null) {
                Set<Integer> section = new HashSet<>();
                String[] arr =  line.trim().split(",");
                String[] sub1 = arr[0].trim().split("-");
                String[] sub2 = arr[1].trim().split("-");
                int sec1=  Integer.parseInt(sub1[1]) - Integer.parseInt(sub1[0]) +1 ;
                int sec2=  Integer.parseInt(sub2[1]) - Integer.parseInt(sub2[0]) +1;
                if (sec1-sec2>=0){
                    for (int i = Integer.parseInt(sub1[0]); i <= Integer.parseInt(sub1[1]); i++) {
                        section.add(i);
                    }

                    for (int i = Integer.parseInt(sub2[0]); i <= Integer.parseInt(sub2[1]); i++) {
                        if (!section.add(i)){
                            count += 1;
                        }
                    }
                    if (count == sec2){
                        sum +=1;
                    }
                }else {
                    for (int i = Integer.parseInt(sub2[0]); i <= Integer.parseInt(sub2[1]); i++) {
                        section.add(i);
                    }

                    for (int i = Integer.parseInt(sub1[0]); i <= Integer.parseInt(sub1[1]); i++) {
                        if (!section.add(i)){
                            count += 1;
                        }
                    }
                    if (count == sec1){
                        sum +=1;
                    }
                }
                count= 0;
            }

            System.out.println(sum);

        } catch (Exception e) {
        e.printStackTrace();
    }
}
}
