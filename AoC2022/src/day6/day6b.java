package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

public class day6b {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("inputs/input_day6.txt"));
            int i = 0;
            HashSet test = new HashSet<>();
            ArrayList<String> four = new ArrayList<>();

            forLoop:
            for (String s : br.readLine().split("")) {
                four.add(s);
                i++;
                if (four.size() == 14) {
                    for (String ele : four) {
                        if (!test.add(ele)) {
                            four.remove(0);
                            break;
                        }
                    }
                    if (test.size()==14){
                        System.out.println(i);
                        break forLoop;
                    }
                    test.clear();
                }
            }
            System.out.println(i);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}