package hw3.hash;

import java.util.List;
import java.util.*;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        int N = oomages.size();
        int bucketNum;
        for (int i = 0; i < N; i += 1) {
            bucketNum = (oomages.get(i).hashCode() & 0x7FFFFFFF) % M;
            if (hashMap.containsKey(bucketNum)) {
                int value = hashMap.get(bucketNum);
                hashMap.replace(bucketNum, value += 1);
            } else {
                hashMap.put(bucketNum, 1);
            }
        }
        Set<Integer> set = hashMap.keySet();
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            int key = iterator.next();
            int value = hashMap.get(key);
            if (!(value >= N / 50) || !(value <= N / 2.5)) {
                return false;
            }
        }
        return true;
    }
}
