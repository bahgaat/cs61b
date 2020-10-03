package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.util.ArrayList;

public class KeyBoardInput implements InputDevice{
    private String seed = "";
    private TERenderer ter = new TERenderer();

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
    public char nextChar() {
        char keyTypedFromTheUser = StdDraw.nextKeyTyped();
        if (keyTypedFromTheUser == 'N') {
            MyWorld.drawFrame("Enter the seed");
        }
        return keyTypedFromTheUser;
    }

    @Override
    public boolean hasNextChar() {
        if (StdDraw.hasNextKeyTyped()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
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
        MyWorld.loadAllObjectsOfTheGame(this);
    }

    @Override
    public boolean theGameEnded() {
        return MyWorld.gameOver;
    }

    @Override
    public void renderTheWorld(TETile[][] world) {
        ter.renderFrame(world);
    }

    @Override
    public void endTheGame() {
        MyWorld.drawFrame("gameOver, you reached round" + MyWorld.round);
    }


}
