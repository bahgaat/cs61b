package byog.Core;

import byog.TileEngine.TETile;

import java.util.ArrayList;

public class InputString implements InputDevice{
    private String _input;
    private String seed = "";
    private int startSlicingIndex = 0;
    private int endSlicingIndex = 1;
    private int lengthOfInput= _input.length();

    public InputString(String input) {
        _input = input;
    }

    @Override
    public void startGame(String seed) {
        try {
            MyWorld.readTheInputBeforeStartingTheGame(this, seed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    /* return true if the input has more characters to iterate through. */
    public boolean hasNextChar() {
        return lengthOfInput >= endSlicingIndex;
    }

    @Override
    /* return the next character in the String input. */
    public char nextChar() {
        String nextChar = _input.substring(startSlicingIndex, endSlicingIndex);
        startSlicingIndex = endSlicingIndex;
        endSlicingIndex += 1;
        char convertStringIntoChar = nextChar.charAt(0);
        return convertStringIntoChar;
    }

    @Override
    /* collect the seed (the numbers) from the whole input. */
    public void collectTheSeed(String input) {
        seed += input;
    }

    @Override
    /* generate the world an add all components in it. */
    public void generateTheWorld() {
        long convertSeedFromStringToLong = Long.parseLong(seed);
        MyWorld.drawAndAddAllTheComponentsOfTheWorld(convertSeedFromStringToLong, this);
    }

    @Override
    public void generateTheWorldAfterLoading() {
        MyWorld.loadGame(this);
    }

    @Override
    /* return true if all the characters of the input has been iterated on. this means
    show the world. */
    public boolean theGameEnded() {
        return lengthOfInput < endSlicingIndex;
    }


}
