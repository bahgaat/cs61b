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

    @Override
    public boolean hasNextChar() {
        return true;
    }

    @Override
    public char nextChar() {
        String nextChar = seed.substring(startSlicingIndex, endSlicingIndex);
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
    public void renderTheWorld(TETile[][] world) {

    }

}
