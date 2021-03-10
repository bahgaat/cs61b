import java.lang.Math;

public class OffByOne implements CharacterComparator {

    /* Return True for the characters that are different by exactly one and False otherwise. */
    @Override
    public boolean equalChars(char x, char y){
        int diff = Math.abs(x - y);
        if (diff == 1){
            return true;
        } else {
            return false;
        }
    }
}
