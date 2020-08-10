package byog;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class TestMyGame {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;
    private static final long SEED = 67977;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        MyGame.drawGame(world);
        ter.renderFrame(world);
    }
}
