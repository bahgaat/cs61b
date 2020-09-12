package byog.Core;

import byog.Core.Game;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

public class TestMyWorld implements Serializable {

    public static void main(String[] args) {
        Game game = new Game();
        game.playWithKeyboard();
    }
}