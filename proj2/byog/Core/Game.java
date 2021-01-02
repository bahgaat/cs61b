package byog.Core;

import byog.Core.GenerateTheWorld.GenerateTheWorld;
import byog.Core.GenerateTheWorld.GenerateTheWorldWhenUsingInputString;
import byog.Core.GenerateTheWorld.GenerateTheWorldWhenUsingKeyBoard;
import byog.Core.Gui.GuiInteractivityInTheGame;
import byog.Core.Input.InputDevice;
import byog.Core.Input.InputString;
import byog.Core.Input.KeyBoardInput;
import byog.Core.Gui.GuiStartingTheGame;
import byog.TileEngine.TETile;

import java.io.Serializable;



public class Game implements Serializable {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;



    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard()  {
        InputDevice keyBoardInput = new KeyBoardInput();
        GenerateTheWorld generateTheWorldWhenUsingKeyBoard = new GenerateTheWorldWhenUsingKeyBoard();
        SaveAndLoadGame<GuiInteractivityInTheGame> saveAndLoadGame = new SaveAndLoadGame<> ("interactivity");
        GuiStartingTheGame guiStartingTheGame = new GuiStartingTheGame("keyBoard");
        guiStartingTheGame.display("Ui");
        guiStartingTheGame.readTheInputBeforeStartingTheGame(keyBoardInput, generateTheWorldWhenUsingKeyBoard,
                saveAndLoadGame);
    }
    

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return
     */

    public TETile[][] playWithInputString(String input) {
        InputDevice inputString = new InputString(input);
        GenerateTheWorld generateTheWorldWhenUsingInputString = new GenerateTheWorldWhenUsingInputString();
        SaveAndLoadGame<InteractivityInTheWorld> saveAndLoadGame = new SaveAndLoadGame<> ("interactivity");
        GuiStartingTheGame guiStartingTheGame = new GuiStartingTheGame("input");
        TETile[][] world = guiStartingTheGame.readTheInputBeforeStartingTheGame(inputString,
                generateTheWorldWhenUsingInputString, saveAndLoadGame);
        return world;
    }

}
