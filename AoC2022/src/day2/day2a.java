package day2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class day2a {
    public static void main(String[] args) {

        try {
            BufferedReader br = new BufferedReader(new FileReader("inputs/input_day2.txt"));
            String line;
            int count = 0;
            while ((line=br.readLine())!= null){
                String[] input = line.trim().split(" "); //"A" "Y"
                switch (input[0]){
                    case "A":
                        switch (input[1]){
                            case "Y":
                                count += (6 + 2);
                                break;
                            case "X":
                                count += (3 + 1);
                                break;
                            case "Z":
                                count += (0 + 3);
                                break;
                        }
                        break;
                    case "B":
                        switch (input[1]){
                            case "Y":
                                count += (3 + 2);
                                break;
                            case "X":
                                count += (0 + 1);
                                break;
                            case "Z":
                                count += (6 + 3);
                                break;
                        }
                        break;
                    case "C":
                        switch (input[1]){
                        case "Y":
                            count += (0 + 2);
                            break;
                        case "X":
                            count += (6 + 1);
                            break;
                        case "Z":
                            count += (3 + 3);
                            break;
                    }
                        break;

                }

            }
            System.out.println(count);





        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
