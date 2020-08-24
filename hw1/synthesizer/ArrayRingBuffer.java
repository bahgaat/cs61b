package synthesizer;
// package <package name>;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
    }

    public class KeyIterator implements Iterator<T> {
        private int wizardPosition;

        public KeyIterator() {
            wizardPosition = 0;
        }

        public boolean hasNext() {
            return wizardPosition < capacity;
        }

        public T next() {
            T returnValue = rb[wizardPosition];
            wizardPosition += 1;
            return returnValue;
        }
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        if (last == capacity) {
            last = 0;
        }
        rb[last] = x;
        last += 1;
        fillCount += 1;


    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        if (first == capacity) {
            first = 0;
        }
        fillCount -= 1;
        T result = rb[first];
        rb[first] = null;
        first += 1;
        return result;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException( "Ring Buffer Underflow");
        }
        if (first == capacity) {
            first = 0;
        }
        return rb[first];

    }


    public Iterator<T> iterator()  {
        return new KeyIterator();
    }
}
