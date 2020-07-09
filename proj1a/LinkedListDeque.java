/*import java.util.Deque;*/

public class LinkedListDeque<T> implements Deque<T> {
    private class StuffNode {
        private T item;
        private StuffNode previous;
        private StuffNode next;

        public StuffNode(StuffNode p, T i, StuffNode n) {
            item = i;
            previous = p;
            next = n;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private StuffNode sentinel;
    private int size;
    private StuffNode last;
    private StuffNode helperSentintel;

    /* Create an empty LinkedListDeque. */
    public LinkedListDeque() {
        sentinel = new StuffNode(last, null, sentinel);
        last = sentinel;
        size = 0;
    }


    /* Create LinkedListDeque. */
    public LinkedListDeque(T item) {
        sentinel = new StuffNode(last, null, sentinel);
        sentinel.next = new StuffNode(sentinel, item, sentinel);
        last = sentinel.next;
        sentinel.previous = last;
        size = 1;
    }

    /* Add to the front of LinkedListDeque. */
    @Override
    public void addFirst(T item) {
        sentinel.next = new StuffNode(sentinel, item, sentinel.next);
        size += 1;
    }

    /* Add to the back of the LinkedList. */
    @Override
    public void addLast(T item) {
        last.next = new StuffNode(last, item, sentinel);
        last = last.next;
        sentinel.previous = last;
        size += 1;
    }

    /* Return true if linkedlist is empty, false otherwise, */
    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /* Return the size of the linkedlist. */
    @Override
    public int size() {
        return size;
    }

    /* prints the items of the linkedlist from first to last. */
    @Override
    public void printDeque() {
        StuffNode helperSentinel2 = sentinel;
        while (helperSentinel2.next != sentinel) {
            helperSentinel2 = helperSentinel2.next;
            System.out.print(helperSentinel2.item);
        }
    }

    /* Remove first element in the linkedlist and return it. */
    @Override
    public T removeFirst() {
        StuffNode oldFirstSentinel = sentinel.next;
        if (size == 0) {
            return null;
        } else if (size == 1) {
            sentinel.next.next = null;
            sentinel.next.previous = null;
        } else {
            sentinel.next = sentinel.next.next;
            sentinel.next.previous = sentinel;
            oldFirstSentinel.next = null;
            oldFirstSentinel.previous = null;
        }
        size -= 1;
        return oldFirstSentinel.item;
    }

    /* Remove last element of the LinkedList and return it. */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            /* keep track of the last sentinel which will be removed later in the function. */
            StuffNode oldLastSentinel = last;

            last = last.previous;
            last.next = sentinel;
            oldLastSentinel.next = null;
            oldLastSentinel.previous = null;
            sentinel.previous = last;
            size -= 1;
            return oldLastSentinel.item;
        }
    }

    /* Get the specific index of the linkedlist where 0 is the front,
     1 is the next, and so forth. */
    @Override
    public T get(int index) {
        StuffNode helperSentinel3 = sentinel;
        while (helperSentinel3.next != sentinel) {
            helperSentinel3 = helperSentinel3.next;
            if (index == 0) {
                return helperSentinel3.item;
            } else {
                index -= 1;
            }
        }
        return null;
    }


    /* Get the specific index of the linkedlist where 0 is the front,
    1 is the next, and so forth, but this time by using recurion. */
    public T getRecursive(int index) {
        if (size == 0) {
            return null;
        } else if (index == 0) {
            return helperSentintel.next.item;
        } else if (helperSentintel.next == helperSentintel) {
            return null;
        } else {
            helperSentintel = helperSentintel.next;
            return this.getRecursive(index - 1);
        }
    }
}
