package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

abstract class Player implements Serializable {
    protected int positionX;
    protected int positionY;
    protected String newDirection;
    protected String typeToAttack;

    /* move the player (if satisfied all the conditions) to the newPosition depending on the direction. */
    void checkIfItIsPossibleToMoveToTheNewPositionAndMoveIfItIsOk(String direction, TETile typeOfAttacker, String typeToCheck) {
        if (direction.equals("up")) {
            newDirection = "up";
            if (itIsPossibleToMoveToTheNewPosition(newDirection, typeToCheck)) {
                move(this, typeOfAttacker, positionX, positionY, positionX, positionY + 1);
                positionY += 1;
            }
        } else if (direction.equals("down")) {
            newDirection = "down";
            if (itIsPossibleToMoveToTheNewPosition(newDirection, typeToCheck)) {
                move(this, typeOfAttacker, positionX, positionY, positionX, positionY - 1);
                positionY -= 1;
            }
        } else if (direction.equals("right")) {
            newDirection = "right";
            if (itIsPossibleToMoveToTheNewPosition(newDirection, typeToCheck)) {
                move(this, typeOfAttacker, positionX, positionY, positionX + 1, positionY);
                positionX += 1;
            }
        } else if (direction.equals("left")) {
            newDirection = "left";
            if (itIsPossibleToMoveToTheNewPosition( newDirection, typeToCheck)) {
                move(this, typeOfAttacker, positionX, positionY, positionX - 1, positionY);
                positionX -= 1;
            }
        }
    }

    /* determine if it is possible for the player to move to the new position. */
    boolean itIsPossibleToMoveToTheNewPosition(String newDirection, String typeToCheck) {
        int addToX = 0;
        int addToY = 0;
        if (newDirection.equals("up")) {
            addToY += 1;
        } else if (newDirection.equals("down")) {
            addToY -= 1;
        } else if (newDirection.equals("right")) {
            addToX += 1;
        } else if (newDirection.equals("left")) {
            addToX -= 1;
        }
        return MyWorld.world[positionX + addToX][positionY + addToY].description().equals(typeToCheck);
    }

    /* move to the new position. */
    private void move(Player player, TETile typeOfAttacker, int oldPositionX, int oldPositionY, int newPositionX, int newPositionY) {
        MyWorld.world[newPositionX][newPositionY] = typeOfAttacker;
        MyWorld.world[oldPositionX][oldPositionY] = Tileset.FLOOR;
    }

    abstract void attack();
}