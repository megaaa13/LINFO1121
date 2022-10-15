package sorting;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,1,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Each entry in the matrix represents an altitude point at the corresponding grid coordinate.
 * The goal is to implement a GlobalWarmingImpl class that extends the GlobalWarming abstract class described below.
 *
 * Given a global water level, all positions in the matrix with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water level
 *
 * Complete the code below. Pay attention to the expected time complexity of each method.
 * To meet this time complexity, you need to do some pre-computation in the constructor.
 * Feel free to create internal classes in GlobalWarmingImpl to make your implementation easier.
 * Feel free to use any method or data structure available in the Java language and API.
 */

abstract class GlobalWarming {


    protected final int[][] altitude;

    /**
     * @param altitude is an n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);

}


public class GlobalWarmingImpl extends GlobalWarming {

    int[] flat = new int[altitude.length * altitude.length];

    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        for(int i = 0; i < altitude.length; i++) {
            System.arraycopy(altitude[i], 0, flat, altitude.length * i, altitude.length);
        }
        Arrays.sort(flat);

    }

    private int binarySearch(int[] array, int low, int high, int searched) {
        int mid = low + (high - low) / 2;
        if (low <= high) {
            if (array[mid] == searched && (mid + 1) < array.length && array[mid + 1] > searched)
                return mid + 1; // Parce que l'array commence à 0 :)
            if (array[mid] > searched)
                return binarySearch(array, low, mid - 1, searched);
            return binarySearch(array, mid + 1, high, searched);
        }
        if (mid < array.length && array[mid] > searched) {
            return mid;
        }
        return -1;
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {
        int limit = binarySearch(flat, 0, flat.length - 1, waterLevel);
        if (limit > - 1)
            return flat.length - limit;
        return 0;
    }
}
