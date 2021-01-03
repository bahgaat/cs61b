package byog.Core.Input;


public interface InputDevice {

    boolean hasNextChar();

    /* to be able to call nextChar. hasNextChar() has to return true. */
    char getNextChar();

    String collectTheSeed(String input);

    String getSeed();
}
