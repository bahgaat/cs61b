package byog.Core.RenderTheWorld;

import byog.Core.*;
import byog.Core.Draw.DrawFrame;
import byog.Core.Draw.DrawWorld;
import byog.Core.EndTheGame.EndTheGame;
import byog.Core.EndTheGame.EndTheGameWhenUsingKeyBoard;
import byog.Core.Gui.GuiInteractivityInTheGame;
import byog.Core.Input.InputDevice;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;


public class RenderTheWorldWhenUsingKeyBoard implements RenderTheWorld {
    private TERenderer ter = new TERenderer();


    @Override
    public void generateTheWorldAfterLoading(SaveAndLoadGame saveAndLoadGame) {
        /*
        InteractivityInTheWorld interactivityInTheWorld = (InteractivityInTheWorld) saveAndLoadGame.loadGame();
         */

    }

    @Override
    public TETile[][] generateTheWorldAndPlayTheGame(RenderTheWorld renderTheWorld, InputDevice input,
                                                     String seed, SaveAndLoadGame saveAndLoadGame, DrawFrame drawFrame) {

        GenerateDrawWorld generateDrawWorld = new GenerateDrawWorld();
        DrawWorld drawWorld = generateDrawWorld.initializeDrawWorld();
        drawTheWorld(seed, drawWorld);
        InteractivityInTheWorld interActivityInTheWorld = new InteractivityInTheWorld(drawWorld);
        GuiInteractivityInTheGame guiInteractivityInTheGame = new GuiInteractivityInTheGame(interActivityInTheWorld,
                drawWorld);
        EndTheGame endTheGameUsingKeyBoard = new EndTheGameWhenUsingKeyBoard(interActivityInTheWorld);
        guiInteractivityInTheGame.playGame(renderTheWorld, input, saveAndLoadGame, endTheGameUsingKeyBoard, drawFrame);
        return drawWorld.getWorld();
    }

    @Override
    /* render the world means show the world. */
    public void renderTheWorld(TETile[][] world, InteractivityInTheWorld interactivityInTheWorld) {
        ter.renderFrame(world, interactivityInTheWorld);
    }


    private void drawTheWorld(String seed, DrawWorld drawWorld) {
        int worldWidth = drawWorld.getWIDTH();
        int worldHeight = drawWorld.getHEIGHT();
        ter.initialize(worldWidth, worldHeight);
        long convertSeedFromStringToLong = Long.parseLong(seed);
        drawWorld.drawWorld(convertSeedFromStringToLong);
    }


}
