/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 *
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {

        int firstMax = findMaxValueInArr(arr);
        int[] resultArr = new int[arr.length];
        makePositiveArr(resultArr, firstMax, arr);
        int secondMax = findMaxValueInArr(resultArr);

        // gather all the counts for each value
        int[] counts = new int[Math.abs(secondMax + 1)];
        for (int i : resultArr) {
            counts[i]++;
        }

        // counting sort that uses start position calculation
        int[] starts = new int[secondMax + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[resultArr.length];
        sortTheArr(sorted2, resultArr, starts);
        sortTheOriginalArr(sorted2, firstMax);
        return sorted2;
    }

    private static void sortTheOriginalArr(int[] sorted2, int firstMax) {
        for (int i = 0; i < sorted2.length; i += 1) {
            sorted2[i] -= firstMax;
        }
    }

    private static int findMaxValueInArr(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i += 1) {
            int nonNegativeNum = Math.abs(arr[i]);
            if (nonNegativeNum > max) {
                max = nonNegativeNum;
            }
        }
        return max;
    }

    private static void sortTheArr(int[] sorted2, int[] arr, int[] starts) {
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }
    }

    /* change all array values to positive numbers. */
    private static void makePositiveArr(int[] resultArr, int max, int[] arr) {
        for (int i = 0; i < resultArr.length; i += 1) {
            resultArr[i] = arr[i] + max;
        }
    }


}
