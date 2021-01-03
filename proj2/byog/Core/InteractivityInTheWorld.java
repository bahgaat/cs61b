package byog.Core;

import byog.Core.Draw.DrawWorld;
import byog.Core.Players.BasePlayer;
import byog.Core.Players.EvilPlayer;
import byog.Core.Players.MainPlayer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.*;
import java.util.*;
/**
 * interactivity in the World. for eg, playing the game.
 */
public class InteractivityInTheWorld implements Serializable {
    private boolean gameOver = false;
    private boolean theUserQuitTheGame = false;
    private int round = 1;
    private DrawWorld drawWorld;
    private MainPlayer mainPlayer;
    private ArrayList<EvilPlayer> arrayOfEvilPlayers = new ArrayList<EvilPlayer>();

    public boolean isTheUserQuitTheGame() {
        return theUserQuitTheGame;
    }

    public void theUserQuitTheGame() {
        this.theUserQuitTheGame = true;
    }

    public boolean isHalfGameEnded() {
        return round == 5;
    }

    public ArrayList<EvilPlayer> getArrayOfEvilPlayers() {
        return arrayOfEvilPlayers;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getRound() {
        return round;
    }

    public void addRound() {
        this.round += 1;
    }

    void setGameOver(Boolean state) {
        gameOver = state;
    }

    public MainPlayer getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(MainPlayer mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    /* Constructor. */
    public InteractivityInTheWorld(DrawWorld drawWorld) {
        this.drawWorld = drawWorld;
        this.mainPlayer = new MainPlayer(drawWorld.getStartMainPlayerPosition());
        TETile typeOfMainPlayer = mainPlayer.getType();
        addFlowerToTheWorld(0);
        addEvilPlayerToTheWorld();
        putPlayerInTheStartingPosition(mainPlayer, typeOfMainPlayer);
    }

    public void addEvilPlayerToTheWorld() {
        Map mapOfEvilPlayer = drawWorld.pollFromQueueEvil();
        EvilPlayer evilPlayer = new EvilPlayer(mapOfEvilPlayer);
        this.arrayOfEvilPlayers.add(evilPlayer);
        TETile typeOfEvilPLayer = evilPlayer.getType();
        calculateTotalDistance(evilPlayer);
        putPlayerInTheStartingPosition(evilPlayer, typeOfEvilPLayer);
    }

    public void putPlayerInTheStartingPosition(BasePlayer player, TETile type) {
        int positionX = player.getPositionX();
        int positionY = player.getPositionY();
        drawWorld.setPositionInWorld(positionX, positionY, type);
    }


    public void attack(EvilPlayer evilPlayer) {
        TETile typeOfEvilPlayer = evilPlayer.getType();
        TETile typeToAttack = evilPlayer.getTypeToAttack();
        String positiveDirectionOfTheEvilPlayer = evilPlayer.getPositiveDirection();
        String negativeDirectionOfTheEvilPlayer = evilPlayer.getNegativeDirection();
        Position newPositivePositionOfThePlayer = evilPlayer.NewPositionOfThePlayer(positiveDirectionOfTheEvilPlayer);
        Position newNegativePositionOfThePlayer = evilPlayer.NewPositionOfThePlayer(negativeDirectionOfTheEvilPlayer);

        boolean isPossibleToMoveToPositiveDirection = itIsPossibleToMoveToTheNewPosition(
                newPositivePositionOfThePlayer, typeToAttack);
        boolean isPossibleToMoveToNegativeDirection = itIsPossibleToMoveToTheNewPosition(
                newNegativePositionOfThePlayer, typeToAttack);

        if (isPossibleToMoveToPositiveDirection) {
            move(evilPlayer, newPositivePositionOfThePlayer, typeToAttack);
            gameOver = true;
        } else if (isPossibleToMoveToNegativeDirection) {
            move(evilPlayer, newNegativePositionOfThePlayer, typeToAttack);
            gameOver = true;
        }
    }

    public boolean itIsPossibleToMoveToTheNewPosition(Position newPositionOfThePlayer, TETile typeToCheck) {
        int newPositionXOfPlayer = newPositionOfThePlayer.getX();
        int newPositionYOfPlayer = newPositionOfThePlayer.getY();
        TETile[][] world = drawWorld.getWorld();
        TETile newPositionInTheWorld  = world[newPositionXOfPlayer][newPositionYOfPlayer];
        return newPositionInTheWorld.equals(typeToCheck);
    }

    public void move(BasePlayer player, Position newPositionOfThePlayer, TETile typeToMoveTo) {
        int oldPositionX = player.getPositionX();
        int oldPositionY = player.getPositionY();
        int newPositionX = newPositionOfThePlayer.getX();
        int newPositionY = newPositionOfThePlayer.getY();
        movePlayerToTheDeterminedPosition(player, oldPositionX, oldPositionY, newPositionX, newPositionY);
    }

    private void movePlayerToTheDeterminedPosition(BasePlayer player, int oldPositionX, int oldPositionY,
                                                   int newPositionX, int newPositionY) {
        TETile playerType = player.getType();
        drawWorld.setPositionInWorld(oldPositionX, oldPositionY, Tileset.FLOOR);
        drawWorld.setPositionInWorld(newPositionX, newPositionY, playerType);
        player.setPositionX(newPositionX);
        player.setPositionY(newPositionY);
    }

    public void addOnePointToTheMainPlayer() {
        int pointsOfMainPlayer = mainPlayer.getPoints();
        mainPlayer.setPoints(pointsOfMainPlayer + 1);
    }

    public void addFlowerToTheWorld(int index) {
        ArrayList<Position> arrayOfPointsPositions = drawWorld.getArrayOfFlowersPositions();
        Position pointPosition = arrayOfPointsPositions.get(index);
        int pointPositionX = pointPosition.getX();
        int pointPositionY = pointPosition.getY();
        drawWorld.setPositionInWorld(pointPositionX, pointPositionY, Tileset.FLOWER);
    }

    /* update evilPlayer speed to be faster, and this happens when the round increases.
    Important note. The bigger the speed is, the less faster the evilPlayer is. */
    public void updateEvilPlayerSpeed(ArrayList<EvilPlayer> arrayOfEvilPlayers) {
        for (int y = 0; y < arrayOfEvilPlayers.size(); y += 1) {
            EvilPlayer evilPlayer = arrayOfEvilPlayers.get(y);
            int speedOfEvilPlayer = evilPlayer.getSpeed();
            if (speedOfEvilPlayer < 2) {
                evilPlayer.setSpeed(2);
            } else {
                evilPlayer.setSpeed(evilPlayer.getSpeed() - 1);
            }
        }
    }

    /* calculate totalDistance that the evilPlayer has to take to move to the opposite direction. */
    private void calculateTotalDistance(EvilPlayer evilPlayer) {
        int addToX = 0;
        int addToY = 0;

        /* I start the totalDistance equal -1 because i don't want to calculate the current position. */
        int totalDistance = - 1;
        int positionX = evilPlayer.getPositionX();
        int positionY = evilPlayer.getPositionY();
        String attackDirection = evilPlayer.getAttackDirection();
        TETile[][] world = drawWorld.getWorld();

        while (world[positionX + addToX][positionY + addToY].description().equals("floor")) {
            if (attackDirection.equals("horizontal")) {
                addToX += 1;
            } else if (attackDirection.equals("vertical")) {
                addToY += 1;
            }
            totalDistance += 1;
        }
        evilPlayer.setTotalDistance(totalDistance);
    }


    /* move either horizontally or vertically depending on the attackedDirection,
    move totalDistance toward positive direction, and when reaches the totalDistance (When facing wall) move
    to the negative direction and so on. */
    public void moveUntilReachesTheWall(EvilPlayer evilPlayer) {

        int distanceMovedTowardPositiveDirection = evilPlayer.getDistanceMovedTowardPositiveDirection();
        int distanceMovedTowardNegativeDirection = evilPlayer.getDistanceMovedTowardNegativeDirection();
        int totalDistance = evilPlayer.getTotalDistance();
        String positiveDirection = evilPlayer.getPositiveDirection();
        String negativeDirection = evilPlayer.getNegativeDirection();
        Position newPositivePosition = evilPlayer.NewPositionOfThePlayer(positiveDirection);
        Position newNegativePosition = evilPlayer.NewPositionOfThePlayer(negativeDirection);

        if (distanceMovedTowardPositiveDirection != totalDistance) {

            distanceMovedTowardPositiveDirection = moveTowardDirection(newPositivePosition,
                    distanceMovedTowardPositiveDirection, totalDistance, evilPlayer);
            evilPlayer.setDistanceMovedTowardPositiveDirection(distanceMovedTowardPositiveDirection);

        } else if (distanceMovedTowardNegativeDirection != totalDistance) {

            distanceMovedTowardNegativeDirection = moveTowardDirection(newNegativePosition,
                    distanceMovedTowardNegativeDirection, totalDistance, evilPlayer);
            evilPlayer.setDistanceMovedTowardNegativeDirection(distanceMovedTowardNegativeDirection);

        } else {
            evilPlayer.setDistanceMovedTowardPositiveDirection(0);
            evilPlayer.setDistanceMovedTowardNegativeDirection(0);
        }
    }

    private int moveTowardDirection(Position newPosition, int distanceMovedTowardDirection,
                                    int totalDistance, EvilPlayer evilPlayer) {
        if (itIsPossibleToMoveToTheNewPosition(newPosition, Tileset.FLOOR)) {
            move(evilPlayer, newPosition, Tileset.FLOOR);
            distanceMovedTowardDirection += 1;
        } else {
            distanceMovedTowardDirection = totalDistance;
        }
        return distanceMovedTowardDirection;
    }

}






