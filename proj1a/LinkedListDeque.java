public class LinkedListDeque<T> {
    private class StuffNode {
        private T item;
        private StuffNode previous;
        private StuffNode next;

        private StuffNode(StuffNode p, T i, StuffNode n) {
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
        helperSentintel = sentinel;
        last = sentinel;
        size = 0;
    }


    /* Create LinkedListDeque. */
    /*public LinkedListDeque(T item) {
        sentinel = new StuffNode(last, null, sentinel);
        sentinel.next = new StuffNode(sentinel, item, sentinel);
        last = sentinel.next;
        sentinel.previous = last;
        size = 1;
    } */

    /* Add to the front of LinkedListDeque. */
    public void addFirst(T item) {
        sentinel.next = new StuffNode(sentinel, item, sentinel.next);
        size += 1;
        if (last == sentinel) {
            last = sentinel.next;
        } else if (size > 1) {
            sentinel.next.next.previous = sentinel.next;
        }
        sentinel.previous = last;
    }

    /* Add to the back of the LinkedList. */
    public void addLast(T item) {
        last.next = new StuffNode(last, item, sentinel);
        last = last.next;
        sentinel.previous = last;
        size += 1;
    }

    /* Return true if linkedlist is empty, false otherwise, */
    public boolean isEmpty() {
        return size == 0;
    }

    /* Return the size of the linkedlist. */
    public int size() {
        return size;
    }

    /* prints the items of the linkedlist from first to last. */
    public void printDeque() {
        StuffNode helperSentinel2 = sentinel;
        while (helperSentinel2.next != sentinel) {
            helperSentinel2 = helperSentinel2.next;
            System.out.print(helperSentinel2.item);
        }
    }

    /* Remove first element in the linkedlist and return it. */
    public T removeFirst() {
        StuffNode oldFirstSentinel = sentinel.next;
        if (size == 0) {
            return null;
        } else if (size == 1) {
            sentinel.next = null;
            sentinel.previous = null;
            last = sentinel;
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
        if (size == 0 || index >= size) {
            return null;
        } else if (index == 0) {
            return helperSentintel.next.item;
        } else {
            helperSentintel = helperSentintel.next;
            return this.getRecursive(index - 1);
        }
    }

    /*
    public static void main(String[] args)  {
        LinkedListDeque<Integer> LinkedListDeque = new LinkedListDeque<>();
        LinkedListDeque.addLast(4);
        LinkedListDeque.addLast(5);
        LinkedListDeque.addLast(6);
        LinkedListDeque.addFirst(3);
        LinkedListDeque.addLast(1);
        LinkedListDeque.addFirst(8);
        LinkedListDeque.removeFirst();
        LinkedListDeque.removeLast();
        LinkedListDeque.removeLast();
        LinkedListDeque.addFirst(8);
        LinkedListDeque.addLast(1);
        LinkedListDeque.removeLast();
        System.out.println(LinkedListDeque.getRecursive(8));
    }
   */

}
