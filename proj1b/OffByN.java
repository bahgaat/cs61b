
public class OffByN implements CharacterComparator {

    private int _n;

    public OffByN(int N) {
        _n = N;
    }

    /* Return true for the characters that are off by N and false otherwise. */
    @Override
    public boolean equalChars(char x, char y) {
        int diff = Math.abs(x - y);
        if (diff == _n) {
            return true;
        } else {
            return false;
        }
    }
}
