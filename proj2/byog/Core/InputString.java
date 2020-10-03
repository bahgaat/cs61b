package byog.Core;

import byog.TileEngine.TETile;

import java.util.ArrayList;

public class InputString implements InputDevice{
    private String _input;
    private String seed = "";
    private int startSlicingIndex = 0;
    private int endSlicingIndex = 1;

    public InputString(String input) {
        _input = input;
    }

    public void startGame( String seed) {
        try {
            MyWorld.readFromTheUserBeforeStartingTheGame(this, seed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNextChar() {
        /*
        int lengthOfInput = _input.length();
        if (lengthOfInput >= endSlicingIndex) {
            return true;
        } else {
            MyWorld.gameOver = true;
            return false;
        }

         */
        int lengthOfInput = _input.length();
        return lengthOfInput >= endSlicingIndex;
    }

    @Override
    public char nextChar() {
        String nextChar = _input.substring(startSlicingIndex, endSlicingIndex);
        startSlicingIndex = endSlicingIndex;
        endSlicingIndex += 1;
        char convertStringIntoChar = nextChar.charAt(0);
        return convertStringIntoChar;
    }

    @Override
    public void collectTheSeed(String input) {
        seed += input;
    }

    @Override
    public void generateTheWorld() {
        long convertSeedFromStringToLong = Long.parseLong(seed);
        MyWorld.drawAndAddAllTheComponentsOfTheWorld(convertSeedFromStringToLong, this);
    }

    @Override
    public void generateTheWorldAfterLoading() {
        MyWorld.loadAllObjectsOfTheGame(this);
    }

    @Override
    public boolean theGameEnded() {
        int lengthOfInput = _input.length();
        return lengthOfInput < endSlicingIndex;
    }


}
