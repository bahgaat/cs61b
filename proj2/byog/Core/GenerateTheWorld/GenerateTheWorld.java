package byog.Core.GenerateTheWorld;

import byog.Core.Input.InputDevice;
import byog.Core.SaveAndLoadGame;
import byog.TileEngine.TETile;
//TODO Make all renders in a package and so on

public interface GenerateTheWorld {


    TETile[][] generateTheWorldAfterLoadingAndPlayTheGame(GenerateTheWorld generateTheWorld, InputDevice input,
                                            SaveAndLoadGame saveAndLoadGame);

    TETile[][] generateTheWorldAndPlayTheGame(GenerateTheWorld generateTheWorld, InputDevice input,
                                              String seed, SaveAndLoadGame saveAndLoadGame);

}
