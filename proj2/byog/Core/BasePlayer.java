package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

abstract class Player implements Serializable {
    protected int positionX;
    protected int positionY;
    protected String newDirection;

    /* move the player to the newPosition depending on the newDirection. */
    void move(TETile typeOfAttacker) {
        //TODO refactor
        int oldPositionX = positionX;
        int oldPositionY = positionY;
        int newPositionX = positionX;
        int newPositionY = positionY;

        if (newDirection.equals("up")) {
            positionY += 1;
            newPositionY = positionY;
        } else if (newDirection.equals("down")) {
            positionY -= 1;
            newPositionY = positionY;
        } else if (newDirection.equals("right")) {
            positionX += 1;
            newPositionX = positionX;
        } else if (newDirection.equals("left")) {
            positionX -= 1;
            newPositionX = positionX;
        }

        switch (newDirection) {
            case "up": {
                positionY += 1;
                newPositionY = positionY;
            }

            case "down": {
                positionY -= 1;
                newPositionY = positionY;
            }

            case "right": {
                positionX += 1;
                newPositionX = positionX;
            }

            case "left": {
                positionX -= 1;
                newPositionX = positionX;
            }
        }

        helperMove(this, typeOfAttacker, oldPositionX, oldPositionY, newPositionX, newPositionY);
    }

    /* A helper method for the basic move method. it moves the player to the new position. */
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