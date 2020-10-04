package byog.Core;

import byog.TileEngine.Tileset;


import java.util.Map;


class EvilBasePlayer extends BasePlayer {
    private int distanceMovedTowardPositiveDirection;
    private int distanceMovedTowardNegativeDirection;
    private int totalDistance;
    private String attackDirection;
    private int speed;

    /* the queueEvil contains two maps one in each index. The map has its key a string(horizontal or vertical) and its values
    a Position (the starter position of the evilPlayer). The evilPlayer moves either horizontally or vertically depending
    on the key. */
    EvilBasePlayer() {
        Position position = null;
        Map map = MyWorld.queueEvil.poll();
        if (map.containsKey("horizontal")) {
            position = (Position) map.get("horizontal");
            attackDirection = "horizontal";
            speed = 5;
        } else if (map.containsKey("vertical")) {
            position = (Position) map.get("vertical");
            attackDirection = "vertical";
            speed = 8;
        }
        positionX = position._x;
        positionY = position._y;
        distanceMovedTowardPositiveDirection = 0;
        distanceMovedTowardNegativeDirection = 0;
        totalDistance = calculateTotalDistance();
        MyWorld.world[positionX][positionY] = Tileset.MOUNTAIN;
    }

    /* calculate totalDistance that the evilPlayer has to take to move to the opposite direction. */
    private int calculateTotalDistance() {
        int addToX = 0;
        int addToY = 0;

        /* I start the totalDistance equal -1 because i don't want to calculate the current position. */
        int totalDistance = - 1;
        while (MyWorld.world[positionX + addToX][positionY + addToY].description().equals("floor")) {
            if (attackDirection.equals("horizontal")) {
                addToX += 1;
            } else if (attackDirection.equals("vertical")) {
                addToY += 1;
            }
            totalDistance += 1;
        }
        return totalDistance;
    }

     @Override
    /* attack the mainPlayer depending on the attackDirection, Or move randomly . */
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

        if (attackedPlayer(positiveDirection, negativeDirection)) {
            MyWorld.gameOver = true;
        } else {
            moveUntilReachesTheWall(positiveDirection, negativeDirection);
        }
    }


    private boolean attackedPlayer(String positiveDirection, String negativeDirection) {
        // TODO refactor
        boolean attackedPlayer = false;
        if (itIsPossibleToMoveToTheNewPosition(positiveDirection, "player")) {
            move(Tileset.MOUNTAIN);
            attackedPlayer = true;
        } else if (itIsPossibleToMoveToTheNewPosition(negativeDirection, "player")) {
            move(Tileset.MOUNTAIN);
            attackedPlayer = true;
        }
        return attackedPlayer;
    }

    /* move either horizontally or vertically depending on the attackedDirection,
    move totalDistance toward positive direction, and when reaches the totalDistance (When facing wall) move
    to the negative direction and so on. */
    private void moveUntilReachesTheWall(String positiveDirection, String negativeDirection) {
        if (distanceMovedTowardPositiveDirection != totalDistance &&
                itIsPossibleToMoveToTheNewPosition(positiveDirection, "floor")) {

                move(Tileset.MOUNTAIN);
                distanceMovedTowardPositiveDirection += 1;
        } else if (distanceMovedTowardNegativeDirection != totalDistance &&
                itIsPossibleToMoveToTheNewPosition(negativeDirection, "floor")) {

                move(Tileset.MOUNTAIN);
                distanceMovedTowardNegativeDirection += 1;
        } else {
            distanceMovedTowardPositiveDirection = 0;
            distanceMovedTowardNegativeDirection = 0;

        }

    }

    /* update the speed. the less the number the speed is. The faster the evilPlayer is.*/
    void updateSpeed() {
        if (speed < 2) {
            speed = 2;
        } else {
            speed -= 1;
        }
    }

    public int getSpeed() {
        return speed;
    }
}



