public class ArrayDeque<T> implements Deque<T> {
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
    @Override
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
    @Override
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
    @Override
    public boolean isEmpty() {
       return size == 0;
    }

    /* Return the size of the array deque. */
    @Override
    public int size() {
        return size;
    }

    /* print every element of the array deque. */
    @Override
    public void printDeque() {
        int index = nextFirst + 1;
        helperSize = size;
        while (helperSize != 0) {
            if (index >= numOfElemInUnderlyingArray) {
                index = 0;
            }
            System.out.println(items[index]);
            helperSize -= 1;
            index += 1;
        }
    }

    /* Remove first item in the array deque and return it. */
    @Override
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
        checkIfUnderlyingArrayNeededToBeResized();
        return firstItem;
    }

    /* Remove last item in the array deque and return it. */
    @Override
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
        checkIfUnderlyingArrayNeededToBeResized();
        return  lastItem;
    }

    /* Get the item at the specific index from the array deque. */
    @Override
    public T get(int index) {
        if (index >= numOfElemInUnderlyingArray) {
            return null;
        }
        int first = nextFirst + 1;
        int indexOfUnderlyingArray = index + first;
        if (size == 0) {
            return null;
        } else if (indexOfUnderlyingArray >= numOfElemInUnderlyingArray) {
            return items[indexOfUnderlyingArray - numOfElemInUnderlyingArray];
        } else {
            return items[indexOfUnderlyingArray];
        }
    }

    /* Resize the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] resizedArray = (T[]) new Object[capacity];
        int numOfElemInResizedArray = capacity;
        int indexOfOriginalArray = nextFirst + 1;
        int indexOfResizedArray;
        helperSize = size;

        if (capacity > numOfElemInUnderlyingArray) {
            int helper = numOfElemInUnderlyingArray - indexOfOriginalArray;
            indexOfResizedArray = numOfElemInResizedArray - helper;
            nextFirst = indexOfResizedArray - 1;
        } else {
            indexOfResizedArray = indexOfOriginalArray;
            if (indexOfOriginalArray >= numOfElemInResizedArray) {
                indexOfResizedArray = indexOfOriginalArray - numOfElemInResizedArray;
                nextFirst = indexOfResizedArray - 1;
            }
        }

        while (helperSize != 0) {
            if (indexOfOriginalArray >= numOfElemInUnderlyingArray) {
                indexOfOriginalArray = 0;
            }
            if (indexOfResizedArray >= numOfElemInResizedArray) {
                indexOfResizedArray = 0;
            }
            resizedArray[indexOfResizedArray] = items[indexOfOriginalArray];
            helperSize -= 1;
            indexOfOriginalArray += 1;
            indexOfResizedArray += 1;
        }

        items = resizedArray;
        numOfElemInUnderlyingArray = numOfElemInResizedArray;
        nextLast = indexOfResizedArray;
        if (nextLast == numOfElemInUnderlyingArray) {
            nextLast = 0;
        }
        if (nextFirst < 0) {
            nextFirst = numOfElemInUnderlyingArray - 1;
        }
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
}

