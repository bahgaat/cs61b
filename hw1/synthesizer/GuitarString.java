
package synthesizer;

//Make sure this class is public
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer<Double>(capacity);
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        //       Make sure that your random numbers are different from each other.
        int bufferCapacity = buffer.capacity();
        while (bufferCapacity > 0) {
            double r = Math.random() - 0.5;
            buffer.enqueue(r);
            bufferCapacity -= 1;
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        Double firstItem = buffer.dequeue();
        Double secondItem;
        if (buffer.isEmpty()) {
            secondItem = 0.0;
        } else {
            secondItem = buffer.peek();
        }
        Double result = 0.996 * 1 / 2 * (firstItem + secondItem);
        buffer.enqueue(result);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        if (!buffer.isEmpty()) {
            return buffer.peek();
        } else {
            return 0.0;
        }
    }
}
