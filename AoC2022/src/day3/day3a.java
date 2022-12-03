package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class day3a {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("inputs/input_day3.txt"));
            String line;
            int sum = 0;
            while ((line = br.readLine()) != null) {
                String compartment1 = line.substring(0, line.length() / 2);
                String compartment2 = line.substring((line.length() / 2), line.length());

                for (int i = 0; i < compartment1.length(); i++) {
                    if (compartment2.contains(compartment1.substring(i, i + 1))) {
                        //System.out.println(compartment1.substring(i, i + 1));
                        if (Character.isUpperCase(compartment1.charAt(i))) {
                            sum += compartment1.charAt(i)-38;
                        } else { //isLowercase
                            sum += compartment1.charAt(i)-96;
                        }
                        break;
                    }
                }
            }
            System.out.println(sum);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

