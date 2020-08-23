package byog;

import byog.Core.Game;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class TestMyWorld {

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        MyWorld mygame = new MyWorld();
        ter.initialize(mygame.WIDTH, mygame.HEIGHT);
        Game game = new Game();
        TETile[][] worldState = game.playWithInputString( "n654456s");
        ter.renderFrame(worldState);

    }
}