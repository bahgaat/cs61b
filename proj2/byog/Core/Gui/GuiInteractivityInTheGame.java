package byog.Core.Gui;

import byog.Core.Draw.DrawFrame;
import byog.Core.Draw.DrawWorld;
import byog.Core.EndTheGame.EndTheGame;
import byog.Core.Input.InputDevice;
import byog.Core.InteractivityInTheWorld;
import byog.Core.Players.EvilPlayer;
import byog.Core.Players.MainPlayer;
import byog.Core.Position;
import byog.Core.RenderTheWorld.RenderTheWorld;
import byog.Core.SaveAndLoadGame;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;

public class GuiInteractivityInTheGame {
    private InteractivityInTheWorld interactivityInTheWorld;
    private DrawWorld drawWorld;

    public GuiInteractivityInTheGame(InteractivityInTheWorld interActivityInTheWorld, DrawWorld drawWorld) {
        this.drawWorld = drawWorld;
        this.interactivityInTheWorld = interActivityInTheWorld;
    }

    /* play the game. This including moving the mainPlayer toward his direction, and moving evilPlayer ,his speed
        depends on the round. The higher the round, the faster the evilPlayer. */
    // TODO you can't pass the gui.
    public void playGame(RenderTheWorld renderTheWorld, InputDevice input, SaveAndLoadGame saveAndLoadGame,
                         EndTheGame endTheGame, DrawFrame drawFrame) {
        int i = 0;
        while (!endTheGame.isTheGameEnded()) {
            TETile[][] world = drawWorld.getWorld();
            renderTheWorld.renderTheWorld(world);
            String newDirection = null;
            if (input.hasNextChar()) {
                char nextInputChar = input.getNextChar();
                // TODO search about ignore case because if anyone insert small 'w'.
                switch (nextInputChar) {
                    case 'W':
                        newDirection = "up";
                    case 'A':
                        newDirection = "left";
                    case 'S':
                        newDirection = "down";
                    case 'D':
                        newDirection = "right";
                    case 'Q':
                        saveAndLoadGame.setFileNameToBeSaved("interactivity");
                        saveAndLoadGame.saveGame(this);
                }

                moveTheMainPlayerToTheNewPosition(newDirection, drawFrame);
                moveTheEvilPlayerAndAttackTheMain(i);


                /* if halfGame is ended the game has to be much harder and this happens by adding another evilPlayer
                 that attacks vertically. */
                if (interactivityInTheWorld.isHalfGameEnded()) {
                    interactivityInTheWorld.addEvilPlayerToTheWorld();
                }
            }
        }
        drawFrame.display( "gameOver, you reached round" + interactivityInTheWorld.getRound());
    }

    /* move mainPlayer to the NewPosition and start New round if the player gained all the points. */
    private void moveTheMainPlayerToTheNewPosition(String newDirection, DrawFrame drawFrame) {

        MainPlayer mainPlayer = interactivityInTheWorld.getMainPlayer();
        Position newPositionOfThePlayer = mainPlayer.NewPositionOfThePlayer(newDirection);
        int round = interactivityInTheWorld.getRound();
        int pointsOfMainPlayer = mainPlayer.getPoints();
        boolean theNewPositionIsPoint = interactivityInTheWorld.itIsPossibleToMoveToTheNewPosition
                (newPositionOfThePlayer, Tileset.FLOWER);

        boolean theNewPositionIsFloor = interactivityInTheWorld.itIsPossibleToMoveToTheNewPosition
                (newPositionOfThePlayer, Tileset.FLOOR);

        if (pointsOfMainPlayer == round) {
            startNewRound(mainPlayer, drawFrame);
        } else if (theNewPositionIsPoint) {
            interactivityInTheWorld.move(mainPlayer, newPositionOfThePlayer, Tileset.FLOWER);
            mainPlayer.addOnePoint();
        } else if (theNewPositionIsFloor) {
            interactivityInTheWorld.move(mainPlayer, newPositionOfThePlayer, Tileset.FLOOR);
        }

    }

    /* start newRound in the game, and this happens when the MainPlayer win the round. The new round
    will be harder. For example, the evilPlayer will be faster and the points (flowers) will be more. */
    private void startNewRound(MainPlayer player, DrawFrame drawFrame) {
        player.setPlayerToStartPosition();
        Position doorPosition = drawWorld.getDoorPosition();
        int doorPositionX = doorPosition.getX();
        int doorPositionY = doorPosition.getY();
        drawWorld.setPositionInWorld(doorPositionX, doorPositionY, Tileset.LOCKED_DOOR);
        int round = interactivityInTheWorld.getRound();
        interactivityInTheWorld.addRound();
        int x = 0;
        while (round  > x) {
            try {
                interactivityInTheWorld.addPoint(x);
                x += 1;
            } catch (Exception e) {
                drawFrame.display("Congratulations, You have won the game");
            }
        }
        ArrayList<EvilPlayer> arrayOfEvilPlayers = interactivityInTheWorld.getArrayOfEvilPlayers();
        interactivityInTheWorld.updateEvilPlayerSpeed(arrayOfEvilPlayers);
    }

    /* attack the mainPlayer and if not possible move the evil player until reaches the wall.*/
    private void moveTheEvilPlayerAndAttackTheMain(int i) {
        ArrayList<EvilPlayer> arrayOfEvilPlayers = interactivityInTheWorld.getArrayOfEvilPlayers();
        for (int j = 0; j < arrayOfEvilPlayers.size(); j += 1) {
            EvilPlayer evilPlayer = arrayOfEvilPlayers.get(j);
            if (i % evilPlayer.getSpeed() == 0) {
                interactivityInTheWorld.attack(evilPlayer);
                interactivityInTheWorld.moveUntilReachesTheWall(evilPlayer);
            }
        }
        i += 1;
    }
}
