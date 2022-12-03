package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class day3b {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("inputs/input_day3.txt"));
            String line;
            List<String> groups = new ArrayList<>();
            List<Character> commonItemTypes = new ArrayList<>();
            //Deque<Character> finalItems = new ArrayDeque<>();
            int sum = 0;

            int i =0;
            while ((line = br.readLine()) != null) {
                i++;
                groups.add(line);

                ifLoop:
                if (i % 3 == 0){
                    for (int j = 0; j < groups.get(0).length(); j++) {
                        char c = groups.get(0).charAt(j);
                        for (int k = 0; k < groups.get(1).length(); k++) {
                            if (c == groups.get(1).charAt(k)) {
                                for (int l = 0; l < groups.get(2).length(); l++) {
                                    if (c == groups.get(2).charAt(l)) {
                                        commonItemTypes.add(c);
                                        groups = new ArrayList<>();
                                        break ifLoop;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (char c :commonItemTypes){
                if (Character.isUpperCase(c)){
                    sum += c - 38;
                }else {
                    sum += c- 96;
                }
            }
            System.out.println(sum);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


            /*
            for (char c :commonItemTypes){
                if (finalItems.isEmpty()) {
                    finalItems.push(c);
                    continue;
                }


             */
                /*
                if (finalItems.peek()!=c) {
                    finalItems.push(c);
                }else {
                    continue;
                }
            }




                 */

