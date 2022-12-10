package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class day8b {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("AoC2022/inputs/input_day8.txt"));
        List<String> rows = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            rows.add(line);
        }
        int numOfRows = rows.size();
        int numOfCols = rows.get(0).length();

        int[][] gird = new int[numOfRows][numOfCols];

        int i = 0;
        for (String row : rows) {
            gird[i] = new int[numOfCols];
            String[] rowArr = row.split("");
            int j = 0;
            for (String colStr : rowArr) {
                gird[i][j] = Integer.parseInt(colStr);
                j++;
            }
            i++;
        }

        int largestScenic = 0;
        for (int y = 0; y < numOfRows; y++) {
            for (int x = 0; x < numOfCols; x++) {
                int newScenic = getScenic(gird, x,y);
                if (newScenic > largestScenic){
                    largestScenic = newScenic;
                }
            }
        }

        System.out.println(largestScenic);
    }

    public static final int east = 0;
    public static final int west = 1;
    public static final int north = 2;
    public static final int south = 3;

    private static int getScenic(int[][]grid, int x, int y){
        int westVal = numSeen(grid, x-1, y, grid[y][x], west);
        int eastVal = numSeen(grid, x+1, y, grid[y][x], east);
        int northVal = numSeen(grid, x, y-1, grid[y][x], north);
        int southVal = numSeen(grid, x, y+1, grid[y][x], south);
        return  westVal * eastVal * northVal * southVal;
    }

    public static int numSeen(int[][]grid, int x, int y, int val, int direction){
        switch (direction){
            case west:
                if (x > -1){
                    if (val > grid[y][x]){
                        return 1 + numSeen(grid, x-1, y , val, direction);
                    }else {
                        return 1;
                    }
                } else{
                    return 0;
                }

            case east:
                if (x < grid[0].length){
                    if (val > grid[y][x]){
                        return 1 + numSeen(grid, x+1, y , val, direction);
                    }else {
                        return 1;
                    }
                } else{
                    return 0;
                }

            case north:
                if (y > -1){
                    if (val > grid[y][x]){
                        return 1 + numSeen(grid, x, y-1 , val, direction);
                    }else {
                        return 1;
                    }
                } else{
                    return 0;
                }

            case south:
                if (y < grid.length){
                    if (val > grid[y][x]){
                        return 1 + numSeen(grid, x, y+1 , val, direction);
                    }else {
                        return 1;
                    }
                } else{
                    return 0;
                }
        }
        return 0;
    }
}
