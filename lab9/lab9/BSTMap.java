package lab9;

import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (p.key.compareTo(key) == 0) {
            return p.value;
        } else if (key.compareTo(p.key) > 0) {
            return getHelper(key, p.right);
        } else {
            return getHelper(key, p.left);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     * @return
     */
    @Override
    public V get(K key) {
        if (size == 0) {
            return null;
        } else if (key.compareTo(root.key) == 0) {
            return root.value;
        } else if (key.compareTo(root.key) > 0) {
            return getHelper(key, root.right);
        } else {
            return getHelper(key, root.left);
        }
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            p = new Node(key, value);
        } else if (key.compareTo(p.key) == 0) {
            p.value = value;
        } else if (key.compareTo(p.key) > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.left = putHelper(key, value, p.left);
        }

        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        boolean t = true;
        if (size == 0) {
            root = putHelper(key, value, root);
        } else if (key.compareTo(root.key) == 0) {
            root = putHelper(key, value, root);
            t = false;
        } else if (key.compareTo(root.key) > 0) {
            root.right = putHelper(key, value, root.right);
        } else {
            root.left = putHelper(key, value, root.left);
        }
        if (t == true) {
            size += 1;
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
        TreeSet<K> ts = new TreeSet<K>();
        helperKeySet(root, ts);
        return ts;
    }

    private void helperKeySet(Node root, TreeSet<K> ts) {
        if (root == null) {
            return;
        } else {
            ts.add(root.key);
            helperKeySet(root.right, ts);
            helperKeySet(root.left, ts);
        }
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V value;
        value = removeHelper(key, root, root);
        if (value != null) {
            size -= 1;
        }
        return value;
    }

    private V removeHelper(K key, Node root, Node parent) {
        V value = null;
        if (root == null) {
            value = null;
        } else if (root.right != null && root.right.key.compareTo(key) == 0 && root.right.right == null && root.right.left == null) {
            value = root.right.value;
            root.right = null;
        } else if (root.left != null && root.left.key.compareTo(key) == 0 && root.left.right == null && root.left.left == null) {
            value = root.left.value;
            root.left = null;
            /* second case root with one children. */
        } else if (root.right != null && root.right.key.compareTo(key) == 0 && root.right.right != null && root.right.left == null) {
            value = root.right.value;
            root.right = root.right.right;
        } else if (root.right != null && root.right.key.compareTo(key) == 0 && root.right.right == null && root.right.left != null) {
            value = root.right.value;
            root.right = root.right.left;
        } else if (root.left != null && root.left.key.compareTo(key) == 0 && root.left.right != null && root.left.left == null) {
            value = root.left.value;
            root.left = root.left.right;
        } else if (root.left != null && root.left.key.compareTo(key) == 0 && root.left.right == null && root.left.left != null) {
            value = root.left.value;
            root.left = root.left.left;
            /*third case root with 2 children */
        } else if (root.key != null && root.key.compareTo(key) == 0 && root.right != null && root.left != null) {
            value = root.value;
            thirdCase(root.left, root, root);
        } else if (root.key != null && root.key.compareTo(key) > 0) {
            return removeHelper(key, root.left, root);
        } else {
            return removeHelper(key, root.right, root);
        }
        return value;
    }

    private void thirdCase(Node subTree, Node root, Node parent) {
        if (subTree.right == null) {
            root.key = subTree.key;
            root.value = subTree.value;
            if (parent.right == subTree) {
                parent.right = subTree.left;
            } else if (parent.left == subTree) {
                parent.left = subTree.left;
            }
        } else {
            thirdCase(subTree.right, root, subTree);
        }
    }


    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        return removeHelper(key, value, root);
    }

    private V removeHelper(K key, V value, Node root) {
        if (root == null) {
            return null;
        } else if (root.key.compareTo(key) == 0 && root.value.equals(value)) {
            return remove(key);
        } else if (root.key.compareTo(key) > 0) {
            return removeHelper(key, value, root.left);
        } else {
            return removeHelper(key, value, root.right);
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    @Test
    public static void main(String[] args) {
        BSTMap<Double, String> bstmap = new BSTMap<>();
        bstmap.put(4.0, "cat");
        bstmap.put(5.0, "fish");
        bstmap.put(3.0, "zebra");
        bstmap.put(2.0, "ahmed");
        bstmap.put(6.0, "george");
        bstmap.put(4.5, "loay");
        bstmap.put(1.0, "weya");
        bstmap.put(2.5, "koko");
        bstmap.put(4.75, "nada");
        bstmap.put(4.70, "sara");
        bstmap.put(4.71, "gg");
        bstmap.put(4.76, "lol");
        bstmap.put(4.77, "roro");
        bstmap.put(4.69, "ff");
        assertEquals("ff", bstmap.remove(4.69, "ff"));
        assertEquals(null, bstmap.remove(4.69, "ff"));
        assertEquals(null, bstmap.remove(4.75, "nad"));
        assertEquals(null, bstmap.remove(1.0, "y"));
        assertEquals("cat", bstmap.remove(4.0, "cat"));
        assertEquals("zebra", bstmap.remove(3.0, "zebra"));
        assertEquals(null, bstmap.remove(4.77, "zebra"));
        assertEquals("loay", bstmap.remove(4.5, "loay"));

    }

}
