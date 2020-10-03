package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.util.ArrayList;

public class KeyBoardInput implements InputDevice{
    private String seed = "";
    private TERenderer ter = new TERenderer();

    @Override
    /* draw the Ui and read input from the user. */
    public void startGame(String seed) {
        try {
            MyWorld.drawFrame("Ui");
            MyWorld.readFromTheUserBeforeStartingTheGame(this, seed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    /* return thr character the user typed. */
    public char nextChar() {
        char keyTypedFromTheUser = StdDraw.nextKeyTyped();
        if (keyTypedFromTheUser == 'N') {
            MyWorld.drawFrame("Enter the seed");
        }
        return keyTypedFromTheUser;
    }

    @Override
    /* return true if the user has typed a character, else false. */
    public boolean hasNextChar() {
        return StdDraw.hasNextKeyTyped();
    }


    @Override
    /* collect the seed form the user. */
    public void collectTheSeed(String input){
        seed += input;
        MyWorld.drawFrame(seed);
    }

    @Override
    public void generateTheWorld() {
        ter.initialize(MyWorld.WIDTH, MyWorld.HEIGHT);
        long convertSeedFromStringToLong = Long.parseLong(seed);
        MyWorld.drawAndAddAllTheComponentsOfTheWorld(convertSeedFromStringToLong, this);
    }

    @Override
    public void generateTheWorldAfterLoading() {
        ter.initialize(MyWorld.WIDTH, MyWorld.HEIGHT);
        MyWorld.loadGame(this);
    }

    @Override
    /* return true if the gameOver is true, this means that the evilPlayer attacked the MainPlayer and killed him
    else false. */
    public boolean theGameEnded() {
        return MyWorld.gameOver;
    }

    @Override
    /* render the world means show the world. */
    public void renderTheWorld(TETile[][] world) {
        ter.renderFrame(world);
    }

    @Override
    /* show to the user that the game is over and which round he has reached. */
    public void endTheGame() {
        MyWorld.drawFrame("gameOver, you reached round" + MyWorld.getRound());
    }


}
