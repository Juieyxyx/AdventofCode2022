package day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Sabqponm
//abcryxxl
//accszExk
//acctuvwj
//abdefghi

public class day12 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("AoC2022/inputs/input_day12.txt"));
        String line;
        var list = new ArrayList<String>();
        while ((line= br.readLine())!=null){
            if (line.isBlank()) continue;
            list.add(line);
        }

        int[] elevGird = new int[list.size() * list.get(0).length()];

        int i = 0;
        int x = 0;
        int y = 0;

        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        for (String inputLine : list){
            String[] letters = inputLine.split("");

            for (String l : letters){
                if (l.equals("S")){
                    elevGird[i] = 0;
                    startX = x;
                    startY = y;
                } else if (l.equals("E")){
                    elevGird[i] = 25;
                    endX = x;
                    endY = y;
                }else {
                    elevGird[i] = l.getBytes()[0] - 97;
                }
                i++;
                x++;
            }
            x = 0;
            y++;
        }

        Collection<Paths> currentPaths = new ArrayList<>();
        List<Paths> finalPaths = new ArrayList<>();
        currentPaths.add(new Paths(startX, startY, list.get(0).length(), list.size()));

//        //part 2
//        for (int y1 = 0; y1< list.size(); y1++) {
//            for (int x1 = 0; x1 < list.size(); x1++) {
//                if (elevGird[y1 * list.get(0).length() + x1] == 0) {
//                    currentPaths.add(new Paths(x1, y1, list.get(0).length(), list.size()));
//                }
//            }
//        }

        Set<Integer> visited = new HashSet<>();

        while (!currentPaths.isEmpty()){
            Map<Integer, Paths> newPaths = new HashMap<>();
            for (Paths p : currentPaths){
                if (p.foundEnd(endX, endY)){
                    finalPaths.add(p);
                    break;
                } else {
                    for (Paths p1 : p.getNewPaths(elevGird, visited)){
                        newPaths.put(p1.getId(), p1);
                    }
                }
                visited.add(p.getId());
            }
            currentPaths = newPaths.values();
        }

        finalPaths.sort(Comparator.comparingInt(Paths::getLength));

        System.out.println(finalPaths.get(0).getLength());
    }
}
