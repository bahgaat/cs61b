package byog.Core.Gui;

import byog.Core.Draw.DrawWorld;
import byog.Core.EndTheGame.EndTheGame;
import byog.Core.Input.InputDevice;
import byog.Core.InteractivityInTheWorld;
import byog.Core.Players.EvilPlayer;
import byog.Core.Players.MainPlayer;
import byog.Core.Position;
import byog.Core.SaveAndLoadGame;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.ArrayList;


public class GuiInteractivityInTheGame<T> extends Gui {
    private InteractivityInTheWorld interactivityInTheWorld;
    private DrawWorld drawWorld;

    public GuiInteractivityInTheGame(InteractivityInTheWorld interActivityInTheWorld, DrawWorld drawWorld) {
        this.drawWorld = drawWorld;
        this.interactivityInTheWorld = interActivityInTheWorld;
    }

    /* play the game. This including moving the mainPlayer toward his direction, and moving evilPlayer ,his speed
        depends on the round. The higher the round, the faster the evilPlayer. */
    public void playGame(EndTheGame endTheGame, InputDevice input, SaveAndLoadGame saveAndLoadGame,
                         TERenderer ter, String type) {
        int i = 0;
        boolean moreThanHalfGameEnded = false;
        while (!endTheGame.isTheGameEnded(interactivityInTheWorld)) {
            TETile[][] world = drawWorld.getWorld();
            if (type.equals("keyBoard")) {
                ter.renderFrame(world, interactivityInTheWorld);
            }

            moveTheEvilPlayerAndAttackTheMain(i);
            i += 1;

            String newDirection = null;
            if (input.hasNextChar()) {
                char nextInputChar = input.getNextChar();
                char nextInputCharInSensitive = Character.toUpperCase(nextInputChar);
                switch (nextInputCharInSensitive) {
                    case 'W':
                        newDirection = "up";
                        break;
                    case 'A':
                        newDirection = "left";
                        break;
                    case 'S':
                        newDirection = "down";
                        break;
                    case 'D':
                        newDirection = "right";
                        break;
                    case 'Q':
                        ArrayList<T> arrayList = new ArrayList<>();
                        arrayList.add((T) drawWorld);
                        arrayList.add((T) interactivityInTheWorld);
                        saveAndLoadGame.saveGame(arrayList);
                        interactivityInTheWorld.theUserQuitTheGame();
                        ifTypeIsKeyBoardDisplayToUser("We will save your efforts, See you soon.", type);
                        break;
                }

                if (newDirection != null) {
                    moveTheMainPlayerToTheNewPosition(newDirection, type);

                    /* if halfGame is ended the game has to be much harder and this happens
                    by adding another evilPlayer that attacks vertically. */
                    if (interactivityInTheWorld.isHalfGameEnded() && moreThanHalfGameEnded == false) {
                        interactivityInTheWorld.addEvilPlayerToTheWorld();
                        moreThanHalfGameEnded = true;
                    }
                }
            }
        }

        if (interactivityInTheWorld.isGameOver()) {
            ifTypeIsKeyBoardDisplayToUser("gameOver, you reached round" +
                    interactivityInTheWorld.getRound(), type);
        }

    }

    /* move mainPlayer to the NewPosition and start New round if the player gained all the points. */
    private void moveTheMainPlayerToTheNewPosition(String newDirection, String type) {

        MainPlayer mainPlayer = interactivityInTheWorld.getMainPlayer();
        Position newPositionOfThePlayer = mainPlayer.NewPositionOfThePlayer(newDirection);
        boolean isTheNewPositionFlower = interactivityInTheWorld.itIsPossibleToMoveToTheNewPosition
                (newPositionOfThePlayer, Tileset.FLOWER);

        boolean isTheNewPositionFloor = interactivityInTheWorld.itIsPossibleToMoveToTheNewPosition
                (newPositionOfThePlayer, Tileset.FLOOR);

        if (isTheRoundEnded(mainPlayer, newPositionOfThePlayer)) {
            startNewRound(mainPlayer, type);
        } else if (isTheNewPositionFlower) {
            interactivityInTheWorld.move(mainPlayer, newPositionOfThePlayer, Tileset.FLOWER);
            interactivityInTheWorld.addOnePointToTheMainPlayer();
        } else if (isTheNewPositionFloor) {
            interactivityInTheWorld.move(mainPlayer, newPositionOfThePlayer, Tileset.FLOOR);
        }

    }

    private boolean isTheRoundEnded(MainPlayer mainPlayer, Position newPositionOfThePlayer) {
        int round = interactivityInTheWorld.getRound();
        int pointsOfMainPlayer = mainPlayer.getPoints();
        return round == pointsOfMainPlayer && interactivityInTheWorld.itIsPossibleToMoveToTheNewPosition(
                newPositionOfThePlayer, Tileset.LOCKED_DOOR);
    }

    /* start newRound in the game, and this happens when the MainPlayer win the round. The new round
    will be harder. For example, the evilPlayer will be faster and the points (flowers) will be more. */
    private void startNewRound(MainPlayer player, String type) {
        drawWorld.setPositionInWorld(player.getPositionX(), player.getPositionY(), Tileset.FLOOR);
        player.setToStartPosition();
        interactivityInTheWorld.putPlayerInTheStartingPosition(player, player.getType());
        interactivityInTheWorld.addRound();
        player.setPoints(0);
        int round = interactivityInTheWorld.getRound();
        int x = 0;
        while (round > x) {
            try {
                interactivityInTheWorld.addFlowerToTheWorld(x);
                x += 1;
            } catch (Exception e) {
                ifTypeIsKeyBoardDisplayToUser("Congratulations, You have won the game", type);
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
    }

}