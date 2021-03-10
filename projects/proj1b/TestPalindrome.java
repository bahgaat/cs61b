import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    /* You must use this palindrome, and not instantiate
     new Palindromes, or the autograder might be upset.*/
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testisPalindrome(){
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("bbbbbb"));
        assertTrue(palindrome.isPalindrome("ROTATOR"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertFalse(palindrome.isPalindrome("baac"));
        assertFalse(palindrome.isPalindrome("rrrrra"));
        assertFalse(palindrome.isPalindrome("rrarrr"));
    }

    @Test
    public void testisPalindrome2(){
        assertTrue(palindrome.isPalindrome("e", offByOne));
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("egqphf", offByOne));
        assertTrue(palindrome.isPalindrome("mydxl", offByOne));
        assertTrue(palindrome.isPalindrome("tzbays", offByOne));
        assertFalse(palindrome.isPalindrome("tzaays", offByOne));
        assertFalse(palindrome.isPalindrome("albka", offByOne));
        assertFalse(palindrome.isPalindrome("aaa", offByOne));
        assertFalse(palindrome.isPalindrome("bb", offByOne));
        assertFalse(palindrome.isPalindrome("BbB", offByOne));
        assertFalse(palindrome.isPalindrome("alhnc", offByOne));
    }
}
