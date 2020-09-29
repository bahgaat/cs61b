package byog.Core;

import byog.TileEngine.TETile;

import java.util.ArrayList;

public interface InputDevice {

    boolean hasNextChar();

    char nextChar();

    void collectTheSeed(String input);

    void generateTheWorld();

    void generateTheWorldAfterLoading();

    void renderTheWorld(TETile[][] world);
}
