package day8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class day8a {
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

        int count = 0;
        for (int y = 0; y < numOfRows; y++) {
            for (int x = 0; x < numOfCols; x++) {
                count += isVisible(gird, x, y) ? 1 : 0;
            }
        }

        System.out.println(count);
    }


    public static final int east = 0;
    public static final int west = 1;
    public static final int north = 2;
    public static final int south = 3;

    public static int largest(int[][]grid, int x, int y, int direction){
        switch (direction){
            case west:
                if (x > -1){
                    int val = largest(grid,x-1,y,direction);
                    if (val > grid[y][x]){
                        return val;
                    }else {
                        return grid[y][x];
                    }
                } else{
                    return -1;
                }

            case east:
                if (x < grid[0].length){
                    int val = largest(grid,x+1,y,direction);
                    if (val > grid[y][x]){
                        return val;
                    }else {
                        return grid[y][x];
                    }
                } else{
                    return -1;
                }

            case north:
                if (y >-1){
                    int val = largest(grid,x,y -1,direction);
                    if (val > grid[y][x]){
                        return val;
                    }else {
                        return grid[y][x];
                    }
                } else{
                    return -1;
                }

            case south:
                if (y < grid.length){
                    int val = largest(grid,x,y+1,direction);
                    if (val > grid[y][x]){
                        return val;
                    }else {
                        return grid[y][x];
                    }
                } else{
                    return -1;
                }
        }
        return -1;
    }

    public static boolean isVisible(int[][] grid, int x, int y){
        if (largest(grid,x-1,y,west) < grid[y][x]){
            return true;
        }
        if (largest(grid,x+1,y,east) < grid[y][x]){
            return true;
        }
        if (largest(grid,x,y-1,north) < grid[y][x]){
            return true;
        }
        if (largest(grid,x,y+1,south) < grid[y][x]){
            return true;
        }
        return false;
    }
}

