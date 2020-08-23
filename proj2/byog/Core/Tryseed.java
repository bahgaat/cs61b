package byog.Core;

import byog.MyWorld;
import byog.TileEngine.TETile;

public class Tryseed {
    public static void main(String[] args) {
        Game game = new Game();
        TETile[][] world = game.playWithInputString("n98767s");
        System.out.println(TETile.toString(world));
    }
}

