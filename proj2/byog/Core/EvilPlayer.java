package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class EvilPlayer extends Player {
    int distanceMoved;
    int totalDistance;
    String attackDirection;
    Boolean movedTowardPositiveDirection = false;
    int speed;


    EvilPlayer() {
        Position position = null;
        Map map = MyWorld.queueEvil.poll();
        if (map.containsKey("horizontal")) {
            position = (Position) map.get("horizontal");
            attackDirection = "horizontal";
            speed = 5;
        } else if (map.containsKey("vertical")) {
            position = (Position) map.get("vertical");
            attackDirection = "vertical";
            speed = 10;
        }
        positionX = position._x;
        positionY = position._y;
        typeToAttack = "player";
        distanceMoved = 0;
        MyWorld.world[positionX][positionY] = Tileset.MOUNTAIN;
    }


    /* attack the mainPlayer depending on the attackDirection. */
    void move() {
        String positiveDirection = "";
        String negativeDirection = "";
        TETile directionPositive = null;
        TETile directionNegative = null;
        if (attackDirection.equals("horizontal")) {
            positiveDirection = "right";
            negativeDirection = "left";
            directionPositive = MyWorld.world[positionX + 1][positionY];
            directionNegative = MyWorld.world[positionX - 1][positionY];
        } else if (attackDirection.equals("vertical")) {
            positiveDirection = "up";
            negativeDirection = "down";
            directionPositive = MyWorld.world[positionX][positionY + 1];
            directionNegative = MyWorld.world[positionX][positionY - 1];
        }

        if (directionPositive.description().equals("player")) {
            moveOneStep(positiveDirection, Tileset.MOUNTAIN);
            MyWorld.gameOver = true;
        } else if (directionNegative.description().equals("player")) {
            moveOneStep(negativeDirection, Tileset.MOUNTAIN);
            MyWorld.gameOver = true;
        } else if (directionPositive.description().equals("floor") && distanceMoved == totalDistance) {
            if (!movedTowardPositiveDirection) {
                distanceMoved = 0;
            }
            distanceMoved += 1;
            totalDistance = distanceMoved;
            movedTowardPositiveDirection = true;
            moveOneStep(positiveDirection, Tileset.MOUNTAIN);
        } else if (movedTowardPositiveDirection) {
            distanceMoved = 0;
            movedTowardPositiveDirection = false;
            moveOneStep(negativeDirection, Tileset.MOUNTAIN);
            distanceMoved += 1;
        } else {
            moveOneStep(negativeDirection, Tileset.MOUNTAIN);
            distanceMoved += 1;
        }
    }
}



