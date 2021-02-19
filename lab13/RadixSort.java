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
        String[] updatedAsciis = new String[asciis.length];
        int max = findMax(asciis);

        /* filling the array that i will be using and putting null at the end of each word that is less
        than the max length. */
        fillTheArray(asciis, updatedAsciis, max);
        String[] sortedArr = sortTheArray(updatedAsciis, max, asciis);
        return sortedArr;
    }

    private static int findMax(String[] asciis) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < asciis.length; i += 1) {
            int length = asciis[i].length();
            if (length > max) {
                max = length;
            }
        }
        return max;
    }

    private static String[] sortTheArray(String[] updatedAsciis, int max,
                                                  String[] asciis) {
        String[] asciisHelper = asciis;
        String[] sortedArr = new String[asciis.length];
        for (int x = 0; x < max; x += 1) {
            int[] count = new int[256];
            int[] starts = new int[256];

            /* build count array. */
            for (int j = 0; j < updatedAsciis.length; j += 1) {
                String string = updatedAsciis[j];
                int digit = (updatedAsciis[j].length() - 1) - x;
                char character = string.charAt(digit);
                int asc = (int) character;
                count[asc] += 1;
            }

            /* build start array. */
            buildStartArray(starts, count);

            /* build sorted array. */;
            for (int i = 0; i < asciis.length; i += 1) {
                String item = asciisHelper[i];
                String string = updatedAsciis[i];
                int digit = (updatedAsciis[i].length() - 1) - x;
                char character = string.charAt(digit);
                int asc = (int) character;
                int place = starts[asc];
                sortedArr[place] = item;
                starts[asc] += 1;
            }

            for (int g = 0; g < sortedArr.length; g += 1) {
                asciisHelper[g] = sortedArr[g];
            }
            fillTheArray(asciisHelper, updatedAsciis, max);
        }
        return sortedArr;
    }

    private static void buildStartArray(int[] starts, int[] count) {
        int pos = 0;
        for (int y = 0; y < starts.length; y += 1) {
            starts[y] = pos;
            pos += count[y];
        }
    }

    private static void fillTheArray(String[] asciis, String[] updatedAsciis, int max) {
        for (int i = 0; i < asciis.length; i += 1) {
            String string = asciis[i];
            for (int y = 0; y < max; y += 1) {
                if (y >= string.length()) {
                    updatedAsciis[i] = updatedAsciis[i]+""+"@";
                } else if (y == 0) {
                    updatedAsciis[i] = String.valueOf(asciis[i].charAt(y));
                } else {
                    updatedAsciis[i] += String.valueOf(asciis[i].charAt(y));
                }
            }
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
