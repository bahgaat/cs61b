import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A Generic heap class. Unlike Java's priority queue, this heap doesn't just
 * store Comparable objects. Instead, it can store any type of object
 * (represented by type T), along with a priority value. Why do it this way? It
 * will be useful later on in the class...
 */
public class ArrayHeap<T> implements ExtrinsicPQ<T> {
    private Node[] contents;
    private int size;

    public ArrayHeap() {
        contents = new ArrayHeap.Node[16];

        /* Add a dummy item at the front of the ArrayHeap so that the getLeft,
         * getRight, and parent methods are nicer. */
        contents[0] = null;

        /* Even though there is an empty spot at the front, we still consider
         * the size to be 0 since nothing has been inserted yet. */
        size = 0;
    }

    /**
     * Returns the index of the node to the left of the node at i.
     */
    private int leftIndex(int i) {
        return i * 2;
    }

    /**
     * Returns the index of the node to the right of the node at i.
     */
    private int rightIndex(int i) {
        /* TODO: Your code here! */
        return i * 2 + 1;
    }

    /**
     * Returns the index of the node that is the parent of the node at i.
     */
    private int parentIndex(int i) {
        /* TODO: Your code here! */
        return i / 2;
    }

    /**
     * Gets the node at the ith index, or returns null if the index is out of
     * bounds.
     */
    private Node getNode(int index) {
        if (!inBounds(index)) {
            return null;
        }
        return contents[index];
    }

    /**
     * Returns true if the index corresponds to a valid item. For example, if
     * we have 5 items, then the valid indices are 1, 2, 3, 4, 5. Index 0 is
     * invalid because we leave the 0th entry blank.
     */
    private boolean inBounds(int index) {
        if ((index > size) || (index < 1)) {
            return false;
        }
        return true;
    }

    /**
     * Swap the nodes at the two indices.
     */
    private void swap(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        contents[index1] = node2;
        contents[index2] = node1;
    }


    /**
     * Returns the index of the node with smaller priority. Precondition: not
     * both nodes are null.
     */
    private int min(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        if (node1 == null) {
            return index2;
        } else if (node2 == null) {
            return index1;
        } else if (node1.myPriority < node2.myPriority) {
            return index1;
        } else {
            return index2;
        }
    }


    /**
     * Bubbles up the node currently at the given index.
     */
    private void swim(int index) {
        // Throws an exception if index is invalid. DON'T CHANGE THIS LINE.
        validateSinkSwimArg(index);
        int parentIndex = parentIndex(index);
        if (index == 1 || contents[index].myPriority > contents[parentIndex].myPriority) {
            return;
        } else {
            T oldIndexItem = contents[index].myItem;
            double priorityOldIndex = contents[index].myPriority;
            contents[index].myItem = contents[parentIndex].myItem;
            contents[index].myPriority = contents[parentIndex].myPriority;
            contents[parentIndex].myItem = oldIndexItem;
            contents[parentIndex].myPriority = priorityOldIndex;
            swim(parentIndex);
        }

    }

    /**
     * Bubbles down the node currently at the given index.
     */
    private void sink(int index) {
        // Throws an exception if index is invalid. DON'T CHANGE THIS LINE.
        validateSinkSwimArg(index);
        int leftIndex = leftIndex(index);
        int rightIndex = rightIndex(index);
        if (leftIndex >= contents.length) {
            return;
        } else if (contents[leftIndex] == null) {
            return;
        } else if (rightIndex >= contents.length) {
            changeLeft(index, leftIndex);
        } else if (contents[rightIndex] == null) {
            changeLeft(index, leftIndex);
        } else if (contents[index].myPriority <= contents[leftIndex].myPriority &&
                contents[index].myPriority <= contents[rightIndex].myPriority) {
            return;
        } else {
            Node oldNode = contents[index];
            T oldItem = oldNode.myItem;
            double oldPriority = oldNode.myPriority;
            int childIndex = min(rightIndex, leftIndex);
            Node child = contents[childIndex];
            contents[index].myItem = child.myItem;
            contents[index].myPriority = child.myPriority;
            child.myItem = oldItem;
            child.myPriority = oldPriority;
            sink(childIndex);
        }
    }

    private void changeLeft(int index, int leftIndex) {
        if (contents[index].myPriority < contents[leftIndex].myPriority) {
            return;
        } else {
            Node oldNode = contents[index];
            T oldItem = oldNode.myItem;
            double oldPriority = oldNode.myPriority;
            contents[index].myItem = contents[leftIndex].myItem;
            contents[index].myPriority = contents[leftIndex].myPriority;
            contents[leftIndex].myItem = oldItem;
            contents[leftIndex].myPriority = oldPriority;
        }
    }


    /**
     * Inserts an item with the given priority value. This is enqueue, or offer.
     * To implement this method, add it to the end of the ArrayList, then swim it.
     */
    @Override
    public void insert(T item, double priority) {
        /* If the array is totally full, resize. */
        if (size + 1 == contents.length) {
            resize(contents.length * 2);
        } else {
            contents[size + 1] = new Node(item, priority);
            size += 1;
            swim(size);
        }

    }

    /**
     * Returns the Node with the smallest priority value, but does not remove it
     * from the heap. To implement this, return the item in the 1st position of the ArrayList.
     */
    @Override
    public T peek() {
        return contents[1].myItem;
    }

    /**
     * Returns the Node with the smallest priority value, and removes it from
     * the heap. This is dequeue, or poll. To implement this, swap the last
     * item from the heap into the root position, then sink the root. This is
     * equivalent to firing the president of the company, taking the last
     * person on the list on payroll, making them president, and then demoting
     * them repeatedly. Make sure to avoid loitering by nulling out the dead
     * item.
     */
    @Override
    public T removeMin() {
        T item = contents[1].myItem;
        swap(1, size);
        contents[size] = null;
        size -= 1;
        if (size > 1) {
            sink(1);
        }
        return item;
    }

    /**
     * Returns the number of items in the PQ. This is one less than the size
     * of the backing ArrayList because we leave the 0th element empty. This
     * method has been implemented for you.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Change the node in this heap with the given item to have the given
     * priority. You can assume the heap will not have two nodes with the same
     * item. Check item equality with .equals(), not ==. This is a challenging
     * bonus problem, but shouldn't be too hard if you really understand heaps
     * and think about the algorithm before you start to code.
     */
    @Override
    public void changePriority(T item, double priority) {
        Node root = contents[1];
        helperChangePriority(root, 1, item, priority);

    }

    private void helperChangePriority(Node root, int rootIndex, T item, double priority) {
        int rightIndex = rightIndex(rootIndex);
        int leftIndex = leftIndex(rootIndex);
        if (root.myItem.equals(item)) {
            helper(root, rootIndex, priority);
        } else if (leftIndex >= contents.length) {
            return;
        } else if (contents[leftIndex] == null) {
            return;
        } else if (rightIndex >= contents.length) {
            Node leftRoot = contents[leftIndex];
            helperChangePriority(leftRoot, leftIndex, item, priority);
        } else if (contents[rightIndex] == null) {
            Node leftRoot = contents[leftIndex];
            helperChangePriority(leftRoot, leftIndex, item, priority);
        } else {
            helperChangePriority(contents[rightIndex], rightIndex, item, priority);
            helperChangePriority(contents[leftIndex], leftIndex, item, priority);
        }
    }

    private void helper(Node root, int rootIndex, double priority) {
        root.myPriority = priority;
        int parentIndex = parentIndex(rootIndex);
        if (parentIndex >= 1) {
            if (contents[parentIndex].myPriority > contents[rootIndex].myPriority) {
                swim(rootIndex);
            } else {
                sink(rootIndex);
            }
        } else {
            sink(rootIndex);
        }
    }

    /**
     * Prints out the heap sideways. Provided for you.
     */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getNode(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = rightIndex(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getNode(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getNode(index) + "\n";
            int leftChild = leftIndex(index);
            if (getNode(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }


    /**
     * Throws an exception if the index is invalid for sinking or swimming.
     */
    private void validateSinkSwimArg(int index) {
        if (index < 1) {
            throw new IllegalArgumentException("Cannot sink or swim nodes with index 0 or less");
        }
        if (index > size) {
            throw new IllegalArgumentException("Cannot sink or swim nodes with index greater than current size.");
        }
        if (contents[index] == null) {
            throw new IllegalArgumentException("Cannot sink or swim a null node.");
        }
    }

    private class Node {
        private T myItem;
        private double myPriority;

        private Node(T item, double priority) {
            myItem = item;
            myPriority = priority;
        }

        public T item() {
            return myItem;
        }

        public double priority() {
            return myPriority;
        }

        @Override
        public String toString() {
            return myItem.toString() + ", " + myPriority;
        }
    }


    /**
     * Helper function to resize the backing array when necessary.
     */
    private void resize(int capacity) {
        Node[] temp = new ArrayHeap.Node[capacity];
        for (int i = 1; i < this.contents.length; i++) {
            temp[i] = this.contents[i];
        }
        this.contents = temp;
    }

}