package lab9;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private float loadFactor() {
        return (float) size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    public MyHashMap(int size) {
        buckets = new ArrayMap[size];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index = hash(key);
        ArrayMap<K, V> arrayMap = buckets[index];
        V value = arrayMap.get(key);
        return value;

    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        float loadFactor = loadFactor();
        if (loadFactor >= MAX_LF) {
            resize();
        }
        if (!containsKey(key)) {
            size += 1;
        }
        int index = hash(key);
        ArrayMap<K, V> arrayMap = buckets[index];
        arrayMap.put(key, value);

    }

    private void resize() {
        ArrayMap<K, V>[] oldBuckets = buckets;
        ArrayMap<K, V>[] newBuckets = new ArrayMap[oldBuckets.length * 2];
        this.buckets = newBuckets;
        this.clear();
        for (int i = 0; i < oldBuckets.length; i += 1) {
            ArrayMap<K, V> arrayMap = oldBuckets[i];
            Iterator<K> iterator = arrayMap.iterator();
            while (iterator.hasNext()) {
                K key = iterator.next();
                V value = arrayMap.get(key);
                put(key, value);
            }
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<K>();
        for (int i = 0; i < buckets.length; i += 1) {
            ArrayMap<K, V> arrayMap = buckets[i];
            Iterator<K> iterator = arrayMap.iterator();
            while (iterator.hasNext()) {
                K key = iterator.next();
                set.add(key);
            }
        }
        return set;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        int index = hash(key);
        ArrayMap<K, V> arrayMap = buckets[index];
        V value = arrayMap.remove(key);
        if (value != null) {
            size -= 1;
        }
        return value;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        int index = hash(key);
        ArrayMap<K, V> arrayMap = buckets[index];
        V value2 = arrayMap.remove(key, value);
        if (value2 != null) {
            size -= 1;
        }
        return value2;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    @Test
    public static void main(String[] args) {
        MyHashMap<Integer, String> a = new MyHashMap<Integer, String>();
        a.put(4, "gogo");
        a.put(0, "jojo");
        a.put(14, "jjo");
        a.put(2, "k");
        a.put(7, "io");
        String value1 = a.remove(14, "kl");
        String value2 = a.remove(2, "k");
        String value3 = a.remove(14, "jjo");


    }
}
