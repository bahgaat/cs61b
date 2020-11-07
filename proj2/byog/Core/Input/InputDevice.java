package byog.Core.Gui.Input;


public interface InputDevice {
    //Todo i have to make 2 functions startGame. one to keyboard and one to input string
    // eg, i can do startGameWithInput and startGameWithoutInput

    boolean hasNextChar();

    /* to be able to call nextChar. hasNextChar() has to return true. */
    char getNextChar();

    String collectTheSeed(String input);

    String getSeed();
}
