package day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day19 {
    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        File file = new File("AoC2022/inputs/input_day19.txt");
        Scanner in = new Scanner(file);
        System.out.println(solve(false, in));
        long endTime = System.currentTimeMillis();
        double elapsedTimeInSeconds = (endTime - startTime) / 1000.0;
        System.out.println("耗时" + elapsedTimeInSeconds);//Part1-->17s; Part2-->28.374s

    }
    public static String solve(boolean part1, Scanner in) throws FileNotFoundException {
        int answer = part1 ? 0 : 1;
        List<BluePrint> blueprints = new ArrayList<>();
        while (in.hasNext()) {
            String[] line = in.nextLine().split(" ");
            int o1 = Integer.parseInt(line[6]);
            int o2 = Integer.parseInt(line[12]);
            int o3 = Integer.parseInt(line[18]);
            int o4 = Integer.parseInt(line[27]);
            int c3 = Integer.parseInt(line[21]);
            int ob4 = Integer.parseInt(line[30]);
            blueprints.add(new BluePrint(o1, o2, o3, o4, c3, ob4));
        }
        for (int i = 0; i < (part1 ? blueprints.size() : 3); i++) {
            BluePrint.m = 0;
            if (part1) {
                answer += (i + 1) * blueprints.get(i).result(24, new int[] { 1, 0, 0, 0 }, new int[4],
                        new HashMap<String, Integer>());
            } else {
                answer *= blueprints.get(i).result(32, new int[] { 1, 0, 0, 0 }, new int[4],
                        new HashMap<String, Integer>());
            }

        }
        return "" + answer;
    }
}

class BluePrint {
    static int m = 0;
    int[] oreR = new int[4];
    int[] clayR = new int[4];
    int[] obsidianR = new int[4];
    int[] geodeR = new int[4];

    public BluePrint(int o1, int o2, int o3, int o4, int c3, int ob4) {
        oreR[0] = o1;
        clayR[0] = o2;
        obsidianR[0] = o3;
        geodeR[0] = o4;
        obsidianR[1] = c3;
        geodeR[2] = ob4;
    }

    public int result(int min, int[] currR, int[] currRes, Map<String, Integer> seen) {
        String hash = min + " " + currR[0] + " " + currR[1] + " " + currR[2] + " " + currR[3] + " " + currRes[0] + " "
                + currRes[1] + " " + currRes[2] + " " + currRes[3] + " ";
        if (seen.keySet().contains(hash)) {
            return seen.get(hash);
        }
        int answer = 0;
        if (min == 0) {
            if(currRes[3] > m) {
                m = currRes[3];
            }
            return currRes[3];
        }
        if(m > currRes[3] + (min * (currR[3] + min))){
            return -1;
        }
        List<int[]> possibleR = new ArrayList<>();
        List<int[]> possibleRes = new ArrayList<>();
        boolean geodeRpossible = possible(currRes, geodeR);
        boolean obsidianRpossible = possible(currRes, obsidianR);
        boolean clayRpossible = possible(currRes, clayR);
        boolean oreRpossible = possible(currRes, oreR);

        if (geodeRpossible) {
            int[] newRes4 = subtract(currRes, geodeR);
            possibleRes.add(newRes4);
            possibleR.add(new int[] { currR[0], currR[1], currR[2], currR[3] + 1 });
        } else {
            if (obsidianRpossible) {
                if (!(currRes[2] > min * (geodeR[2] - currR[2]))) {
                    int[] newRes4 = subtract(currRes, obsidianR);
                    possibleRes.add(newRes4);
                    possibleR.add(new int[] { currR[0], currR[1], currR[2] + 1, currR[3] });
                }

            }
            if (clayRpossible) {
                if (!(currRes[1] > min * (obsidianR[1] - currR[1]))) {
                    int[] newRes4 = subtract(currRes, clayR);
                    possibleRes.add(newRes4);
                    possibleR.add(new int[] { currR[0], currR[1] + 1, currR[2], currR[3] });
                }

            }
            if (oreRpossible) {
                int max = Math.max(Math.max(oreR[0], clayR[0]), Math.max(obsidianR[0], geodeR[0]));
                if (!(currRes[0] > min * (max - currR[0]))) {
                    int[] newRes4 = subtract(currRes, oreR);
                    possibleRes.add(newRes4);
                    possibleR.add(new int[] { currR[0] + 1, currR[1], currR[2], currR[3] });
                }
            }
            possibleRes.add(currRes);
            possibleR.add(currR);
        }

        for (int i = 0; i < possibleR.size(); i++) {
            int[] newRes = new int[currR.length];
            for (int j = 0; j < currRes.length; j++) {
                newRes[j] += currR[j] + possibleRes.get(i)[j];
            }
            answer = Math.max(answer, result(min - 1, possibleR.get(i), newRes, seen));
        }
        seen.put(hash, answer);
        return answer;
    }

    public int[] subtract(int[] currRes, int[] R) {
        int[] newRes = new int[currRes.length];
        for (int i = 0; i < currRes.length; i++) {
            newRes[i] = currRes[i] - R[i];
        }
        return newRes;
    }

    public boolean possible(int[] currRes, int[] R) {
        for (int i = 0; i < currRes.length; i++) {
            if (currRes[i] < R[i]) {
                return false;
            }
        }
        return true;
    }
}