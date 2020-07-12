public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int numOfElemInUnderlyingArray;
    private int nextLast;
    private int helperSize;

    /* Create an empty array deque. */
    public ArrayDeque() {
        numOfElemInUnderlyingArray = 8;
        items = (T[]) new Object[numOfElemInUnderlyingArray];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    /*  Add to the front of the array deque. */
    public void addFirst(T item) {
        checkIfUnderlyingArrayNeededToBeResized();
        items[nextFirst] = item;
        size += 1;
        nextFirst -= 1;
        if (nextFirst < 0) {
            nextFirst = numOfElemInUnderlyingArray - 1;
        }
    }

    /* Add to the back of the array deque. */
    public void addLast(T item) {
        checkIfUnderlyingArrayNeededToBeResized();
        items[nextLast] = item;
        size += 1;
        nextLast += 1;
        if (nextLast == numOfElemInUnderlyingArray) {
            nextLast = 0;
        }
    }

    /* check if the array is empty. */
    public boolean isEmpty() {
       return size == 0;
    }

    /* Return the size of the array deque. */
    public int size() {
        return size;
    }

    /* print every element of the array deque. */
    public void printDeque() {
        int index = nextFirst + 1;
        helperSize = size;
        while (helperSize != 0) {
            if (index < 0) {
                System.out.println(items[numOfElemInUnderlyingArray + index]);
            } else {
                System.out.println(items[index]);
            }
            helperSize -= 1;
            index += 1;
        }
    }

    /* Remove first item in the array deque and return it. */
    public T removeFirst() {
        int first;
        if (size == 0) {
            return null;
        } else if (nextFirst == numOfElemInUnderlyingArray - 1) {
            first = 0;
        } else {
            first = nextFirst + 1;
        }
        T firstItem = items[first];
        items[first] = null;
        size -= 1;
        nextFirst = first;
        return firstItem;
    }

    /* Remove last item in the array deque and return it. */
    public T removeLast() {
        int last;
        if (size == 0) {
            return null;
        } else if (nextLast == 0) {
            last = numOfElemInUnderlyingArray - 1;
        } else  {
            last = nextLast - 1;
        }
        T lastItem = items[last];
        items[last] = null;
        size -= 1;
        nextLast = last;
        return  lastItem;
    }

    /* Get the item at the specific index from the array deque. */
    public T get(int index) {
        int first = nextFirst + 1;
        int indexOfUnderlyingArray = index + first;
        if (size == 0) {
            return null;
        } else if (indexOfUnderlyingArray < 0) {
            return items[indexOfUnderlyingArray - numOfElemInUnderlyingArray];
        } else {
            return items[indexOfUnderlyingArray];
        }
    }

    /* Resize the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] resizedArray = (T[]) new Object[capacity];
        int numOfElemInResizedArray = capacity;
        int index = nextFirst + 1;
        helperSize = size;
        while (helperSize != 0) {
            if (index < 0) {
                resizedArray[numOfElemInResizedArray + index] = items[numOfElemInUnderlyingArray + index];
            } else {
                resizedArray[index] = items[index];
            }
            helperSize -= 1;
            index += 1;
        }
        items = resizedArray;
        numOfElemInUnderlyingArray = numOfElemInResizedArray;
    }

    /* Check if the underlying array need to be resized. */
    private void checkIfUnderlyingArrayNeededToBeResized() {
        double itemsLength = items.length;
        if (items.length == size) {
            resize(items.length * 2);
        } else if (items.length >= 16 && size / itemsLength < 0.25) {
            resize(items.length / 2);
        }
    }

    /*
    public static void main(String[] args)  {
        ArrayDeque<Integer> ArrayDeque = new ArrayDeque<>();
        ArrayDeque.addFirst(0);
        ArrayDeque.addFirst(1);
        ArrayDeque.addFirst(2);
        ArrayDeque.removeFirst();
        ArrayDeque.removeLast();
        ArrayDeque.removeLast();
        ArrayDeque.addFirst(6);
        ArrayDeque.addLast(7);
        ArrayDeque.addFirst(8);
        ArrayDeque.removeLast();
        ArrayDeque.addFirst(10);
        ArrayDeque.get(1);
    }
     */
}
