
public class ArrayDeque<Type> implements Deque<Type> {
    private Type[] items;
    private int size;
    private int nextFirst;
    private int numOfElemInUnderlyingArray;
    private int nextLast;
    private int helperSize;

    /* Create an empty array deque. */
    public ArrayDeque() {
        numOfElemInUnderlyingArray = 8;
        items = (Type[]) new Object[numOfElemInUnderlyingArray];
        size = 0;
        nextFirst = 0;
        nextLast = 0;
    }

    /*  Add to the front of the array deque. */
    @Override
    public void addFirst(Type item) {
        checkIfUnderlyingArrayNeededToBeResized();
        if (nextFirst < 0) {
            items[numOfElemInUnderlyingArray + nextFirst] = item;
        } else {
            items[nextFirst] = item;
            nextLast += 1;
        }
        size += 1;
        nextFirst -= 1;
    }

    /* Add to the back of the array deque. */
    @Override
    public void addLast(Type item) {
        checkIfUnderlyingArrayNeededToBeResized();
        items[nextLast] = item;
        size += 1;
        nextLast += 1;
        if (nextFirst == 0 && items[0] != null) {
            nextFirst = -1;
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
    @Override
    public Type removeFirst() {
        int first = nextFirst + 1;
        Type firstItem = null;
        if (size == 0) {
            return firstItem;
        } else if (first < 0) {
            firstItem = items[numOfElemInUnderlyingArray + first];
            items[numOfElemInUnderlyingArray + first] = null;
        } else {
            firstItem = items[first];
            items[first] = null;
        }
        size -= 1;
        nextFirst = first;
        return firstItem;
    }

    /* Remove last item in the array deque and return it. */
    @Override
    public Type removeLast() {
        int last = nextLast - 1;
        Type lastItem = items[last];
        if (size == 0) {
            return null;
        } else {
            items[last] = null;
            size -= 1;
            nextLast = last;
            return  lastItem;
        }
    }

    /* Get the item at the specific index from the array deque. */
    @Override
    public Type get(int index) {
        int first = nextFirst + 1;
        int indexOfUnderlyingArray = index + first;
        if (size == 0) {
            return null;
        } else if (indexOfUnderlyingArray < 0) {
            return items[numOfElemInUnderlyingArray + indexOfUnderlyingArray];
        } else {
            return items[indexOfUnderlyingArray];
        }
    }

    /* Resize the underlying array to the target capacity. */
    public void resize(int capacity) {
        Type[] resizedArray = (Type[]) new Object[capacity];
        int numOfElemInResizedArray = capacity;
        int index = nextFirst + 1;
        helperSize = size;
        while (helperSize != 0) {
            if (index < 0) {
                resizedArray[numOfElemInResizedArray + index] = items[numOfElemInResizedArray + index];
            } else {
                resizedArray[index] = items[index];
            }
            helperSize -= 1;
            index += 1;
        }
        items = resizedArray;
    }

    /* Check if the underlying array need to be resized. */
    public void checkIfUnderlyingArrayNeededToBeResized() {
        double itemsLength = items.length;
        if (items.length == size) {
            resize(items.length * 2);
        } else if (items.length >= 16 && size / itemsLength < 0.25) {
            resize(items.length / 2);
        }
    }
}
