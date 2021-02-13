import edu.princeton.cs.algs4.Queue;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Quick;
import org.junit.Test;

import java.util.Iterator;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {

        Queue<Queue<Item>> bigQueue = new Queue<>();
        Queue<Item> smallQueue;
        Iterator iteratorOverItems = items.iterator();
        while (iteratorOverItems.hasNext()) {
            Item item = (Item) iteratorOverItems.next();
            smallQueue = new Queue<>();
            smallQueue.enqueue(item);
            bigQueue.enqueue(smallQueue);
        }
        return bigQueue;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {

        Queue<Item> resultedQueue = new Queue<>();
        while (q1.size() > 0 || q2.size() > 0) {
            Item item1 = getMin(q1, q2);
            resultedQueue.enqueue(item1);
        }
        return resultedQueue;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if (items.size() == 0 || items.size() == 1) {
            return items;
        } else {
            Queue<Item> queue1 = new Queue<>();
            Queue<Item> queue2 = new Queue<>();
            addToQueues(queue1, queue2, items);
            queue1 = mergeSort(queue1);
            queue2 = mergeSort(queue2);
            return mergeSortedQueues(queue1, queue2);
        }
    }

    private static <Item extends Comparable> void addToQueues(Queue<Item> queue1, Queue<Item> queue2,
                                                              Queue<Item> items) {
        int halfSize = items.size() / 2;
        Iterator<Item> iterator = items.iterator();
        int size = items.size();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (size > halfSize) {
                queue1.enqueue(item);
            } else {
                queue2.enqueue(item);
            }
            size -= 1;
        }
    }

    @Test
    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        students.enqueue("Bahgat");
        students.enqueue("Bbbhagt");
        students.enqueue("yassmine");
        System.out.println(students);
        Queue<String> actualSortedStudents = mergeSort(students);
        Queue<String> expectedSortedStudents = new Queue<String>();
        expectedSortedStudents.enqueue("Alice");
        expectedSortedStudents.enqueue("Bahgat");
        expectedSortedStudents.enqueue("Bbbhagt");
        expectedSortedStudents.enqueue("Ethan");
        expectedSortedStudents.enqueue("Vanessa");
        expectedSortedStudents.enqueue("yassmine");
        Queue<String> newStudents = students;
        assertEquals(students, newStudents);
        assertEquals(expectedSortedStudents, actualSortedStudents);
        Queue<Queue<String>> answer = makeSingleItemQueues(students);

    }


}
