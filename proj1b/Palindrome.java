import java.util.LinkedList;

public class Palindrome {
    /* Convert string into Deque. */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque<Character>();
        for (int i = 0; i < word.length(); i++) {
            char x = word.charAt(i);
            d.addLast(x);
        }
        return d;
    }

    /* A Helper function of isPalindrome function. it takes deque and return True if this deque is palindrome and false
    otherwise. */
    private boolean helper_isPalindrome(Deque d, CharacterComparator Offbyone) {
        if (d.size() == 0 || d.size() == 1) {
            return true;
        } else {
            char remove_last = (char) d.removeLast();
            char remove_first = (char) d.removeFirst();
            if (Offbyone != null) {
                boolean check_2_char_are_offbyone = Offbyone.equalChars(remove_first, remove_last);
                if (check_2_char_are_offbyone == true) {
                    return helper_isPalindrome(d, Offbyone);
                } else {
                    return false;
                }
            } else {
                if (remove_last == remove_first) {
                    return helper_isPalindrome(d, Offbyone);
                } else {
                    return false;
                }
            }
        }
    }

    /* Return either the given string is palindrome or no. */
    public boolean isPalindrome(String word) {
        Deque<Character> d = wordToDeque(word);
        return helper_isPalindrome(d, null);
    }

    /* Return true if the word is a palindrome according to the character
    comparison test provided by the CharacterComparator passed in as argument cc. */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> d = wordToDeque(word);
        return helper_isPalindrome(d, cc);

    }

    /** This main method is optional. */
    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(Palindrome.class);
    }
}
