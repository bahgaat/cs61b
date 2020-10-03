package byog.Core;

import byog.TileEngine.TETile;

import java.util.ArrayList;

public interface InputDevice {

    void startGame(String seed);

    boolean hasNextChar();

    /* to be able to call nextChar. hasNextChar() has to return true. */
    char nextChar();

    void collectTheSeed(String input);

    void generateTheWorld();

    void generateTheWorldAfterLoading();

    boolean theGameEnded();

    /* this function will be overridden only in KeyBoardInput class. */
    default void renderTheWorld(TETile[][] world) {

    }

    /* this function will be overridden only in KeyBoardInput class. */
    default void endTheGame() {

    }

}
