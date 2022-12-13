package day12;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Paths {
    private final int currentX;
    private final int currentY;
    private final int maxX;
    private final int maxY;
    private int length = 0;
    private int currentElevation = 0;

    public Paths(int startX, int startY, int maxX, int maxY) {
        this.currentX = startX;
        this.currentY = startY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public Paths(int startX, int startY, int maxX, int maxY, int length, int currentElevation) {
        this(startX, startY, maxX, maxY);
        this.length = length;
        this.currentElevation = currentElevation;
    }

    public List<Paths> getNewPaths(int[] elevGrid, Set<Integer> visited) {
        List<Paths> newPaths = new ArrayList<>();
        if (
                currentX > 0 &&
                        !visited.contains((maxX * currentY) + (currentX - 1)) &&
                        elevGrid[(maxX * currentY) + (currentX - 1)] <= currentElevation + 1
        ) {
            newPaths.add(new Paths(
                    currentX - 1,
                    currentY,
                    maxX,
                    maxY,
                    length + 1,
                    elevGrid[(currentY * maxX) + (currentX - 1)]
            ));
        }

        if (
                currentX < maxX - 1 &&
                        !visited.contains((maxX * currentY) + (currentX + 1)) &&
                        elevGrid[(maxX * currentY) + (currentX + 1)] <= currentElevation + 1
        ) {
            newPaths.add(new Paths(
                    currentX + 1,
                    currentY,
                    maxX,
                    maxY,
                    length + 1,
                    elevGrid[(currentY * maxX) + (currentX + 1)]
            ));
        }
        if (
                currentY > 0 &&
                        !visited.contains(maxX * (currentY - 1) + currentX) &&
                        elevGrid[(currentY - 1) * maxX + currentX] <= currentElevation + 1
        ) {
            newPaths.add(new Paths(
                    currentX,
                    currentY - 1,
                    maxX,
                    maxY,
                    length + 1,
                    elevGrid[(currentY - 1) * maxX + currentX]
            ));
        }
        if (
                currentY < maxY - 1 &&
                        !visited.contains(maxX * (currentY + 1) + currentX) &&
                        elevGrid[(currentY + 1) * maxX + currentX] <= currentElevation + 1
        ) {
            newPaths.add(new Paths(
                    currentX,
                    currentY + 1,
                    maxX,
                    maxY,
                    length + 1,
                    elevGrid[(currentY + 1) * maxX + currentX]
            ));
        }

        return newPaths;
    }
    public boolean foundEnd(int endX, int endY) {
        return endX == currentX && endY == currentY;
    }

    public int getLength() {
        return length;
    }

    public Integer getId() {
        return currentY * maxX + currentX;
    }
}