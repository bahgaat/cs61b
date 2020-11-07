package byog.Core.RenderTheWorld;

import byog.Core.*;
import byog.Core.Draw.DrawFrame;
import byog.Core.Draw.DrawWorld;
import byog.Core.EndTheGame.EndTheGame;
import byog.Core.EndTheGame.EndTheGameWhenUsingInputString;
import byog.Core.Gui.GuiInteractivityInTheGame;
import byog.Core.Input.InputDevice;
import byog.Core.Input.InputString;
import byog.TileEngine.TETile;


public class RenderTheWorldWhenUsingInputString implements RenderTheWorld {

    @Override
    public void generateTheWorldAfterLoading(SaveAndLoadGame saveAndLoadGame) {

    }

    @Override
    public TETile[][] generateTheWorldAndPlayTheGame(RenderTheWorld renderTheWorld, InputDevice input, String seed,
                                                     SaveAndLoadGame saveAndLoadGame, DrawFrame drawFrame) {

        GenerateDrawWorld generateDrawWorld = new GenerateDrawWorld();
        DrawWorld drawWorld = generateDrawWorld.initializeDrawWorld();
        drawTheWorld(seed, drawWorld);
        InteractivityInTheWorld interActivityInTheWorld = new InteractivityInTheWorld(drawWorld);
        GuiInteractivityInTheGame guiInteractivityInTheGame = new GuiInteractivityInTheGame(interActivityInTheWorld,
                drawWorld);
        EndTheGame endTheGameUsingInputSting = new EndTheGameWhenUsingInputString((InputString) input);
        guiInteractivityInTheGame.playGame(renderTheWorld, input, saveAndLoadGame, endTheGameUsingInputSting, drawFrame);
        return drawWorld.getWorld();
    }


    private void drawTheWorld(String seed, DrawWorld drawWorld) {
        long convertSeedFromStringToLong = Long.parseLong(seed);
        drawWorld.drawWorld(convertSeedFromStringToLong);
    }

}
