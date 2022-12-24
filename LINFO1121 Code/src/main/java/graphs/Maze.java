package graphs;


import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

/**
 * We are interested in solving a maze represented
 * by a matrix of integers 0-1 of size nxm.
 * This matrix is a two-dimensional array.
 * An entry equal to '1' means that there
 * is a wall and therefore this position is not accessible,
 * while '0' means that the position is free.
 * We ask you to write a Java code to discover
 * the shortest path between two coordinates
 * on this matrix from (x1, y1) to (x2, y2).
 * The moves can only be vertical (up/down) or horizontal (left/right)
 * (not diagonal), one step at a time.
 * The result of the path is an Iterable of
 * coordinates from the origin to the destination.
 * These coordinates are represented by integers
 * between 0 and n * m-1, where an integer 'a'
 * represents the position x =a/m and y=a%m.
 * If the start or end position is a wall
 * or if there is no path, an empty Iterable must be returned.
 * The same applies if there is no path
 * between the origin and the destination.
 */
public class Maze {
    public static Iterable<Integer> shortestPath(int[][] maze, int x1, int y1, int x2, int y2) {
        if (maze[x1][y1] == 1 || maze[x2][y2] == 1) return new LinkedList<>();
        LinkedList<Integer> queue = new LinkedList<>();
        int length_x = maze.length;
        int length_y = maze[0].length;
        int[] edgeTo = new int[length_y * length_x];
        boolean[] marked = new boolean[length_x * length_y];
        marked[ind(x1, y1, length_y)] = true; // le noeud de départ est marqué
        queue.add(ind(x1, y1, length_y));

        int[][] movements = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}}; // up, down, right, left
        boolean found = false;
        while(!queue.isEmpty() && !found) {
            int current = queue.remove();
            int curX = row(current, length_y);
            int curY = col(current, length_y);
            for (int[] mov : movements) {
                int x = mov[0];
                int y = mov[1];
                int nextX = curX + x;
                int nextY = curY + y;
                if ((0 <= nextX && nextX < length_x) && (0 <= nextY && nextY < length_y) && (maze[nextX][nextY] != 1)) {
                    int index = ind(nextX, nextY, length_y);
                    if (!marked[index]) {
                        marked[index] = true;
                        queue.add(index);
                        edgeTo[index] = current;
                        if (nextX == x2 && nextY == y2)
                            found = true;
                    }
                }
            }
        }
        int from = ind(x1, y1, length_y);
        Stack<Integer> path = new Stack<>();
        if (!marked[ind(x2, y2, length_y)]) return path;
        for (int x = ind(x2, y2, length_y); x != from; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(from);
        Collections.reverse(path);
        return path;
    }

    public static int ind(int x, int y, int lg) {
        return x * lg + y;
    }

    public static int row(int pos, int mCols) {
        return pos / mCols;
    }

    public static int col(int pos, int mCols) {
        return pos % mCols;
    }
}
