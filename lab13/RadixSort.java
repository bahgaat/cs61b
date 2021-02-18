/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        String[] arrOfIntStrings = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            String ascii = asciis[i];
            for (int j = 0; j < ascii.length(); j += 1) {
                char character = ascii.charAt(j);
                int asc = (int) character;
                if (arrOfIntStrings[i] != null) {
                    arrOfIntStrings[i] = arrOfIntStrings[i]+""+asc;
                } else {
                    arrOfIntStrings[i] = ""+asc;
                }

            }
        }
        iterateThroughArr(arrOfIntStrings);
        return arrOfIntStrings;
    }

    private static void iterateThroughArr(String[] arrOfIntStrings) {
        int max = Integer.MIN_VALUE;
        /* find the maximum length of a string. */
        for (int m = 0; m < arrOfIntStrings.length; m += 1) {
            int length = arrOfIntStrings[m].length();
            if (arrOfIntStrings[m].length() > max) {
                max = length;
            }
        }

        for (int i = 0; i < max; i += 1) {
            sortHelperLSD(arrOfIntStrings, (max - 1) - i);
        }
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param arrOfIntStrings Input array of Strings
     * @param digit
     */
    private static void sortHelperLSD(String[] arrOfIntStrings, int digit) {
        int[] arrOfInt = new int[arrOfIntStrings.length];
        for (int i = 0; i < arrOfIntStrings.length; i += 1) {

            arrOfInt[i] = Integer.parseInt(arrOfIntStrings[digit]);
            arrOfInt = CountingSort.naiveCountingSort(arrOfInt);
            //TODO make count sorting on each digit
        }

    }



    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
