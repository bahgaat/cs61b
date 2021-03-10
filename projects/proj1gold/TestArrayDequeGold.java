import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testArrayDeque() {
        StudentArrayDeque<Integer> student_array = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution_array = new ArrayDequeSolution<>();

        String error_message = "";

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                student_array.addLast(i);
                solution_array.addLast(i);
                error_message += "\n addLast(" + i + ")";
            } else {
                student_array.addFirst(i);
                solution_array.addFirst(i);
                error_message += "\n addFirst(" + i + ")";
            }
        }

        for (int i = 0; i < 10; i += 1) {

            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne > 0.5) {
                Integer last_item_in_student_array = student_array.removeLast();
                Integer last_item_in_solution_array = solution_array.removeLast();
                error_message += "\n removeLast()";
                assertEquals(error_message, last_item_in_solution_array, last_item_in_student_array);
            } else {
                Integer first_item_in_student_array = student_array.removeFirst();
                Integer first_item_in_solution_array = solution_array.removeFirst();
                error_message += "\n removeFirst()";
                assertEquals(error_message, first_item_in_solution_array, first_item_in_student_array);
            }
        }
    }
}
