package graphs;


import java.util.Arrays;

/**
 * In this exercise, we revisit the GlobalWarming class from the sorting package.
 * You are still given a matrix of altitude in parameter of the constructor, with a water level.
 * All the entries whose altitude is under, or equal to, the water level are submerged while the other constitute small islands.
 *
 * For example let us assume that the water level is 3 and the altitude matrix is the following
 *
 *      | 1 | 3 | 3 | 1 | 3 |
 *      | 4 | 2 | 2 | 4 | 5 |
 *      | 4 | 4 | 1 | 4 | 2 |
 *      | 1 | 4 | 2 | 3 | 6 |
 *      | 1 | 1 | 1 | 6 | 3 |
 *
 * If we replace the submerged entries by _, it gives the following matrix
 *
 *      | _ | _ | _ | _ | _ |
 *      | 4 | _ | _ | 4 | 5 |
 *      | 4 | 4 | _ | 4 | _ |
 *      | _ | 4 | _ | _ | 6 |
 *      | _ | _ | _ | 6 | _ |
 *
 * The goal is to implmets two methods that can answer the following questions:
 *      1) Are to entries on the same island?
 *      2) How many island is there
 *
 * Two entries above the water level are connected if they are next to each other on
 * the same row or the same column. They are **not** connected **in diagonal**.
 * Beware that the methods must run in O(1) time complexity, at the cost of a pre-processing in the constructor.
 * To help you, you'll find a `Point` class in the utils package which identified an entry of the grid.
 * Carefully read the expected time complexity of the different methods.
 */
public class GlobalWarming {

    private int [] components;
    private int nbComponent;
    private int [][] altitude;
    private int waterLevel;
    /**
     * Constructor. The run time of this method is expected to be in
     * O(n x log(n)) with n the number of entry in the altitude matrix.
     *
     * @param altitude the matrix of altitude
     * @param waterLevel the water level under which the entries are submerged
     */
    public GlobalWarming(int [][] altitude, int waterLevel) {
        this.altitude = altitude;
        this.waterLevel = waterLevel;
        int n = altitude.length;
        this.components = new int[n*n];
        this.nbComponent = 0;
        Arrays.fill(this.components, -2);
        for (int row = 0; row < n; row ++) {
            for (int col = 0; col < n; col ++) {
                detectComponent(row, col);
                // If the starting cell has been updated to the number of component,
                // then it is not under water. We need to increment the number of component
                if (this.components[row*n + col] == this.nbComponent) {
                    this.nbComponent += 1;
                }
            }
        }
    }

    private void detectComponent(int row, int col) {
        int n = this.altitude.length;
        if (row < 0 || row >= n || col < 0 || col >= n || this.components[row*n + col] != -2)
            return;
        if (this.altitude[row][col] <= waterLevel)
            this.components[row*n + col] = -1;
        else {
            this.components[row*n + col] = this.nbComponent;
            detectComponent(row+1, col);
            detectComponent(row-1, col);
            detectComponent(row, col+1);
            detectComponent(row, col-1);
        }
    }

    /**
     * Returns the number of island
     *
     * Expected time complexity O(1)
     */
    public int nbIslands() {
        return this.nbComponent;
    }

    /**
     * Return true if p1 is on the same island than p2, false otherwise
     *
     * Expected time complexity: O(1)
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     */
    public boolean onSameIsland(Point p1, Point p2) {
        int n = this.altitude.length;
        return this.components[p1.getX()*n + p1.getY()] != -1 && this.components[p1.getX()*n + p1.getY()] == this.components[p2.getX()*n + p2.getY()];
    }


    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
     */
    static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }
    }
}
