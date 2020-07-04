import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {

    @Test
    public void FlikTest(){
        int a = 4;
        int b = 4;
        boolean actual = Flik.isSameNumber(a, b);
        boolean expected = true;
        assertTrue("4 should be the same as 4 but this didn't happen",expected = actual);

        int o = 130;
        int n = 130;
        boolean actual5 = Flik.isSameNumber(o, n);
        boolean expected5 = true;
        assertTrue("128 should be equal 128",expected5 = actual5);

        int g = 128;
        int i = 128;
        boolean actual4 = Flik.isSameNumber(g, i);
        boolean expected4 = true;
        assertTrue("128 should be equal 128",expected4 = actual4);

        int e = 0;
        int f = 70;
        boolean actual3 = Flik.isSameNumber(e, f);
        boolean expected3 = false;
        assertTrue("0 is not the same as 70 but the opposite happens",expected3 = actual3);

        int c = 60;
        int d = 65;
        boolean actual2 = Flik.isSameNumber(c, d);
        boolean expected2 = false;
        assertTrue("60 is not the same as 65 but the opposite happens",expected2 = actual2);
   }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.TestRunner.runTests("failed", FlikTest.class);
    }


}
