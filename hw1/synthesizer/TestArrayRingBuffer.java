package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;


/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue(0);
        arb.enqueue(1);
        arb.enqueue(2);
        assertEquals(arb.fillCount(), 3);
        assertEquals(arb.dequeue(), 0);
        assertEquals(arb.fillCount(), 2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
        assertEquals(arb.peek(), 1);
        assertEquals(arb.fillCount(), 5);
        arb.enqueue(6);
        arb.enqueue(7);
        assertEquals(arb.dequeue(), 1);
        arb.enqueue(8);
        arb.enqueue(9);
        arb.enqueue(10);
        assertEquals(arb.dequeue(), 2);
        assertEquals(arb.fillCount(), 8);
        arb.enqueue(11);
        arb.enqueue(12);
        assertEquals(arb.peek(), 3);
        assertEquals(arb.fillCount(), 10);
        assertTrue(arb.isFull());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
