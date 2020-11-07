package byog.Core.RenderTheWorld;

import byog.Core.Draw.DrawFrame;
import byog.Core.Input.InputDevice;
import byog.Core.InteractivityInTheWorld;
import byog.Core.SaveAndLoadGame;
import byog.TileEngine.TETile;
//TODO Make all renders in a package and so on

public interface RenderTheWorld {


    void generateTheWorldAfterLoading(SaveAndLoadGame saveAndLoadGame);

    TETile[][] generateTheWorldAndPlayTheGame(RenderTheWorld renderTheWorld, InputDevice input,
                                              String seed, SaveAndLoadGame saveAndLoadGame, DrawFrame drawFrame);


    default void renderTheWorld(TETile[][] world, InteractivityInTheWorld interactivityInTheWorld){}


}
