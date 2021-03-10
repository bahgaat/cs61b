import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offBy5 = new OffByN(5);
    static CharacterComparator offBy3 = new OffByN(3);
    static CharacterComparator offBy7 = new OffByN(7);

    @Test
    public void testequalchars() {
        assertTrue(offBy5.equalChars('a', 'f'));
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('f', 'h'));
        assertTrue(offBy3.equalChars('a', 'd'));
        assertTrue(offBy3.equalChars('d', 'a'));
        assertFalse(offBy3.equalChars('p', 't'));
        assertTrue(offBy7.equalChars('i', 'p'));
        assertTrue(offBy7.equalChars('p', 'i'));
        assertFalse(offBy7.equalChars('e', 'h'));
    }
}
