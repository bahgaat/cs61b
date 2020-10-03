package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

abstract class Player implements Serializable {
    protected int positionX;
    protected int positionY;
    protected String typeToAttack;
    protected String newDirection;

    /* move the player (if satisfied all the conditions) to the newPosition depending on the direction. */
    void move(TETile typeOfAttacker, String typeToCheck) {
        if (newDirection.equals("up")) {
            helperMove(this, typeOfAttacker, positionX, positionY, positionX, positionY + 1);
            positionY += 1;
        } else if (newDirection.equals("down")) {
            helperMove(this, typeOfAttacker, positionX, positionY, positionX, positionY - 1);
            positionY -= 1;
        } else if (newDirection.equals("right")) {
            helperMove(this, typeOfAttacker, positionX, positionY, positionX + 1, positionY);
            positionX += 1;
        } else if (newDirection.equals("left")) {
            helperMove(this, typeOfAttacker, positionX, positionY, positionX - 1, positionY);
            positionX -= 1;
        }
    }

    /* move to the new position. */
    private void helperMove(Player player, TETile typeOfAttacker, int oldPositionX, int oldPositionY, int newPositionX, int newPositionY) {
        MyWorld.world[newPositionX][newPositionY] = typeOfAttacker;
        MyWorld.world[oldPositionX][oldPositionY] = Tileset.FLOOR;
    }

    /* determine if it is possible for the player to move to the new position. */
    boolean itIsPossibleToMoveToTheNewPosition(String _newDirection, String typeToCheck) {
        int addToX = 0;
        int addToY = 0;
        newDirection = _newDirection;
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

    abstract void attack();
}