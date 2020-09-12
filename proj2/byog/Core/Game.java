package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

import static byog.Core.MyWorld.*;


public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard()  {

        MyWorld.startGame();
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
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        long seed = 0;
        int stringLength = input.length();
        Player player;

        int i = 1;
        String[] arrayOfInputs = input.split("");
        int lengthOfArray = arrayOfInputs.length;

        if (arrayOfInputs[0].equals("L")) {
            MyWorld.world = loadWorld();
            player = loadPlayer();
        } else {
            boolean x = true;
            while (x) {
                if (arrayOfInputs[i].equals("s") || arrayOfInputs[i].equals("S")) {
                    x = false;
                } else {
                    seed = seed * 10 + Long.parseLong(arrayOfInputs[i]);
                }
                i += 1;
            }
            MyWorld.world = MyWorld.drawWorld(seed);
            player = new Player();
        }

        /* the error here is that the world is null. */
        while (lengthOfArray > i) {
            char convertStringToChar = arrayOfInputs[i].charAt(0);
            MyWorld.playGame(MyWorld.world, player, convertStringToChar);
            i += 1;
        }
        return MyWorld.world;
    }


}
