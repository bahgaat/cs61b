package byog.Core.Input;

import java.io.Serializable;

public class InputString implements InputDevice, Serializable {
    private String input;
    private String seed = "";
    private int startSlicingIndex = 0;
    private int endSlicingIndex = 1;
    private int lengthOfInput;

    public int getEndSlicingIndex() {
        return endSlicingIndex;
    }

    public int getLengthOfInput() {
        return lengthOfInput;
    }


    public InputString(String input) {
        this.input = input;
    }

    @Override
    public String getSeed() {
        return seed;
    }

    @Override
    /* return true if the input has more characters to iterate through. */
    public boolean hasNextChar() {
        lengthOfInput= input.length();
        return lengthOfInput >= endSlicingIndex;
    }

    @Override
    /* return the next character in the String input. */
    public char getNextChar() {
        String nextChar = input.substring(startSlicingIndex, endSlicingIndex);
        startSlicingIndex = endSlicingIndex;
        endSlicingIndex += 1;
        char convertStringIntoChar = nextChar.charAt(0);
        return convertStringIntoChar;
    }

    @Override
    /* collect the seed (the numbers) from the whole input. */
    public String collectTheSeed(String input) {
        seed += input;
        return seed;
    }


}
