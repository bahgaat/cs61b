package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class EvilPlayer extends Player {
    int distanceMoved;
    int totalDistance;
    String attackDirection;
    Boolean movedTowardPositiveDirection = false;
    int speed;


    public EvilPlayer() {
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

    @Override
    void attack() {
        String positiveDirection = "";
        String negativeDirection = "";
        if (attackDirection.equals("horizontal")) {
            positiveDirection = "right";
            negativeDirection = "left";
        } else if (attackDirection.equals("vertical")) {
            positiveDirection = "up";
            negativeDirection = "down";
        }

        if (MyWorld.world[positionX + 1][positionY].description().equals("player")) {
            moveOneStep(positiveDirection, Tileset.MOUNTAIN);
            MyWorld.gameOver = true;
        } else if (MyWorld.world[positionX - 1][positionY].description().equals("player")) {
            moveOneStep(negativeDirection, Tileset.MOUNTAIN);
            MyWorld.gameOver = true;
        } else if (MyWorld.world[positionX + 1][positionY].description().equals("floor") && distanceMoved == totalDistance) {
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



