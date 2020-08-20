package byog;

import byog.Core.Game;
import byog.TileEngine.TETile;

public class Tryseed {
    public static void main(String[] args) {
        Game game = new Game();
        TETile[][] world = game.playWithInputString("N1234");
        System.out.println(TETile.toString(world));
    }
}

