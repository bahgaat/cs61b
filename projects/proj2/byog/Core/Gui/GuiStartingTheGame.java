package byog.Core.Gui;

import byog.Core.EndTheGame.EndTheGame;
import byog.Core.GenerateTheWorld.GenerateWorld;
import byog.Core.GenerateTheWorld.RenderWorld;
import byog.Core.Input.InputDevice;
import byog.Core.SaveAndLoadGame;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;


public class GuiStartingTheGame extends Gui {


    /* read input , and make decision based on the input. If the input is 'N', get seed from the input and draw the world
    with its  components and then play the game. if the input is 'L', load all the saved objects and play the game. */
    public TETile[][] readTheInputBeforeStartingTheGame(InputDevice input, GenerateWorld generateTheWorld,
                                                        SaveAndLoadGame saveAndLoadGame, TERenderer ter,
                                                        EndTheGame endTheGame, String type) {

        RenderWorld renderWorld = new RenderWorld(generateTheWorld, input, saveAndLoadGame,
                ter, endTheGame, type);
        TETile[][] world = new TETile[0][];
        if (input.hasNextChar()) {
            char nextInputChar = input.getNextChar();
            char nextInputCharInSensitive = Character.toUpperCase(nextInputChar);
            if (nextInputCharInSensitive == 'N') {
                ifTypeIsKeyBoardDisplayToUser("Enter the seed", type);
                boolean collectedSeed = false;
                while (!collectedSeed) {
                    collectedSeed = collectingTheSeed(input, saveAndLoadGame, collectedSeed, type);
                }
                String seed = input.getSeed();
                world = renderWorld.renderTheWorldAndPlayTheGame(seed);
            } else if (nextInputCharInSensitive == 'L') {
                world = renderWorld.renderTheWorldAfterLoadingAndPlayTheGame();
            }
        }
        return world;
    }

    /* collecting the seed and return true if all seed is collected otherwise return false. */
    private boolean collectingTheSeed(InputDevice input, SaveAndLoadGame saveAndLoadGame,
                                      boolean collectedSeed, String type) {

        if (input.hasNextChar()) {
            char nextInputChar = input.getNextChar();
            String convertCharToString = String.valueOf(nextInputChar);
            if (nextInputChar == 'S') {
                collectedSeed = true;
                ifTypeIsKeyBoardDisplayToUser("To win the round you have to collect " +
                        "all flowers and go to locked door", type);
            } else {
                String seed = input.collectTheSeed(convertCharToString);
                ifTypeIsKeyBoardDisplayToUser(seed, type);
            }
        }
        return collectedSeed;
    }



}
