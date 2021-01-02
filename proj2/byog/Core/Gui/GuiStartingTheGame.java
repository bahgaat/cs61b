package byog.Core.Gui;

import byog.Core.GenerateTheWorld.GenerateTheWorld;
import byog.Core.Input.InputDevice;
import byog.Core.SaveAndLoadGame;
import byog.TileEngine.TETile;


public class GuiStartingTheGame extends Gui {

    public GuiStartingTheGame(String type) {
        this.type = type;
    }

    /* read input , and make decision based on the input. If the input is 'N', get seed from the input and draw the world
    with its  components and then play the game. if the input is 'L', load all the saved objects and play the game. */
    public TETile[][] readTheInputBeforeStartingTheGame(InputDevice input, GenerateTheWorld generateTheWorld,
                                                        SaveAndLoadGame saveAndLoadGame) {
        TETile[][] world = new TETile[0][];
        if (input.hasNextChar()) {
            char nextInputChar = input.getNextChar();
            if (nextInputChar == 'N') {
                ifTypeIsKeyBoardDisplayToUser("Enter the seed");
                boolean collectedSeed = false;
                while (!collectedSeed) {
                    collectedSeed = collectingTheSeed(input, generateTheWorld,
                            saveAndLoadGame, collectedSeed);
                }
                String seed = input.getSeed();
                world = generateTheWorld.generateTheWorldAndPlayTheGame(generateTheWorld, input,
                        seed, saveAndLoadGame);
            } else if (nextInputChar == 'L') {
                generateTheWorld.generateTheWorldAfterLoadingAndPlayTheGame(generateTheWorld,
                        input, saveAndLoadGame);
            }
        }
        return world;
    }

    /* collecting the seed and return true if all seed is collected otherwise return false. */
    private boolean collectingTheSeed(InputDevice input, GenerateTheWorld generateTheWorld,
                                      SaveAndLoadGame saveAndLoadGame, boolean collectedSeed) {

        if (input.hasNextChar()) {
            char nextInputChar = input.getNextChar();
            String convertCharToString = String.valueOf(nextInputChar);
            if (nextInputChar == 'S') {
                collectedSeed = true;
                ifTypeIsKeyBoardDisplayToUser("To win the round you have to collect " +
                        "all flowers and go to locked door");
            } else {
                String seed = input.collectTheSeed(convertCharToString);
                ifTypeIsKeyBoardDisplayToUser(seed);
            }
        }
        return collectedSeed;
    }



}
