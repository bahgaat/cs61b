package byog.Core.GenerateTheWorld;

import byog.Core.*;
import byog.Core.Draw.DrawWorld;
import byog.Core.EndTheGame.EndTheGame;
import byog.Core.EndTheGame.EndTheGameWhenUsingInputString;
import byog.Core.EndTheGame.EndTheGameWhenUsingKeyBoard;
import byog.Core.Gui.GuiInteractivityInTheGame;
import byog.Core.Input.InputDevice;
import byog.Core.Input.InputString;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.io.Serializable;
import java.util.ArrayList;


public class GenerateTheWorldWhenUsingKeyBoard<T> implements GenerateTheWorld, Serializable {
    private TERenderer ter = new TERenderer();

    @Override
    public TETile[][] generateTheWorldAfterLoadingAndPlayTheGame(GenerateTheWorld generateTheWorld,
                                                                 InputDevice input,
                                                                 SaveAndLoadGame saveAndLoadGame) {

        ArrayList<T> arrayList = saveAndLoadGame.loadGame();
        DrawWorld drawWorld = (DrawWorld) arrayList.get(0);
        InteractivityInTheWorld interactivityInTheWorld = (InteractivityInTheWorld) arrayList.get(1);
        TETile[][] world = playTheGame(generateTheWorld, input, saveAndLoadGame, drawWorld,
                interactivityInTheWorld);
        return world;
    }



    @Override
    public TETile[][] generateTheWorldAndPlayTheGame(GenerateTheWorld generateTheWorld, InputDevice input,
                                                     String seed, SaveAndLoadGame saveAndLoadGame) {

        GenerateClassDrawWorld generateDrawWorld = new GenerateClassDrawWorld();
        DrawWorld drawWorld = generateDrawWorld.initializeDrawWorld();
        drawTheWorld(seed, drawWorld);
        InteractivityInTheWorld interActivityInTheWorld = new InteractivityInTheWorld(drawWorld);
        TETile[][] world = playTheGame(generateTheWorld, input, saveAndLoadGame, drawWorld,
                interActivityInTheWorld);
        return world;
    }

    private TETile[][] playTheGame(GenerateTheWorld generateTheWorld, InputDevice input,
                                   SaveAndLoadGame saveAndLoadGame, DrawWorld drawWorld,
                                   InteractivityInTheWorld interactivityInTheWorld) {

        EndTheGame endTheGameUsingInputSting = new EndTheGameWhenUsingInputString((InputString) input,
                interactivityInTheWorld);
        GuiInteractivityInTheGame guiInteractivityInTheGame = new GuiInteractivityInTheGame(
                interactivityInTheWorld, drawWorld);
        guiInteractivityInTheGame.playGame(endTheGameUsingInputSting, input, saveAndLoadGame);
        return drawWorld.getWorld();
    }


    private void drawTheWorld(String seed, DrawWorld drawWorld){
        int worldWidth = drawWorld.getWIDTH();
        int worldHeight = drawWorld.getHEIGHT();
        ter.initialize(worldWidth, worldHeight);
        long convertSeedFromStringToLong = Long.parseLong(seed);
        drawWorld.drawWorld(convertSeedFromStringToLong);
    }
}
