package byog.Core.GenerateTheWorld;
import byog.Core.*;
import byog.Core.Draw.DrawWorld;
import byog.Core.EndTheGame.EndTheGame;
import byog.Core.Gui.GuiInteractivityInTheGame;
import byog.Core.Input.InputDevice;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import java.io.Serializable;
import java.util.ArrayList;

public class RenderWorld<T> implements Serializable {
    private GenerateWorld generateTheWorld;
    private InputDevice input;
    private SaveAndLoadGame saveAndLoadGame;
    private TERenderer ter;
    private EndTheGame endTheGame;
    private String type;

    public RenderWorld(GenerateWorld generateTheWorld, InputDevice input,
                       SaveAndLoadGame saveAndLoadGame, TERenderer ter, EndTheGame endTheGame,
                       String type) {

        this.generateTheWorld = generateTheWorld;
        this.input = input;
        this.saveAndLoadGame = saveAndLoadGame;
        this.ter = ter;
        this.endTheGame = endTheGame;
        this.type = type;
    }

    public TETile[][] renderTheWorldAfterLoadingAndPlayTheGame() {
        ArrayList<T> arrayList = saveAndLoadGame.loadGame();
        DrawWorld drawWorld = (DrawWorld) arrayList.get(0);
        InteractivityInTheWorld interactivityInTheWorld = (InteractivityInTheWorld) arrayList.get(1);
        if (type.equals("keyBoard")) {
            int worldWidth = drawWorld.getWIDTH();
            int worldHeight = drawWorld.getHEIGHT();
            ter.initialize(worldWidth, worldHeight);
        }
        TETile[][] world = playTheGame(drawWorld, interactivityInTheWorld);
        return world;
    }


    public TETile[][] renderTheWorldAndPlayTheGame(String seed) {

        GenerateClassDrawWorld generateDrawWorld = new GenerateClassDrawWorld();
        DrawWorld drawWorld = generateDrawWorld.initializeDrawWorld();
        generateTheWorld.generateTheWorld(seed, drawWorld);
        InteractivityInTheWorld interActivityInTheWorld = new InteractivityInTheWorld(drawWorld);
        TETile[][] world = playTheGame(drawWorld, interActivityInTheWorld);
        return world;
    }

    private TETile[][] playTheGame(DrawWorld drawWorld, InteractivityInTheWorld interactivityInTheWorld) {

        GuiInteractivityInTheGame guiInteractivityInTheGame = new GuiInteractivityInTheGame(
                interactivityInTheWorld, drawWorld);
        guiInteractivityInTheGame.playGame(endTheGame, input, saveAndLoadGame, ter, type);
        return drawWorld.getWorld();
    }


}
