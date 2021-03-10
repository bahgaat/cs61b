package byog.Core.Players;

import byog.Core.Position;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Map;

public class EvilPlayer extends BasePlayer {
    private final TETile type = Tileset.MOUNTAIN;
    private final TETile typeToAttack = Tileset.PLAYER;
    private int distanceMovedTowardPositiveDirection;
    private int distanceMovedTowardNegativeDirection;
    private int totalDistance;
    private String attackDirection;
    private int speed;
    private Map mapOfEvilPlayer;
    private String positiveDirection;
    private String negativeDirection;

    /* the queueEvil contains two maps one in each index. The map has its key a string(horizontal or vertical) and its values
    a Position (the starter position of the evilPlayer). The evilPlayer moves either horizontally or vertically depending
    on the key. */
    public EvilPlayer(Map mapOfEvilPlayer) {
        this.mapOfEvilPlayer = mapOfEvilPlayer;
        setType(Tileset.MOUNTAIN);
        setTypeToAttack(Tileset.PLAYER);
        setToStartPosition();
        setAttackDirection();
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getAttackDirection() {
        return attackDirection;
    }

    void setAttackDirection(String attackDirection) {
        this.attackDirection = attackDirection;
    }

    public int getDistanceMovedTowardPositiveDirection() {
        return distanceMovedTowardPositiveDirection;
    }

    public void setDistanceMovedTowardPositiveDirection(int distanceMovedTowardPositiveDirection) {
        this.distanceMovedTowardPositiveDirection = distanceMovedTowardPositiveDirection;
    }

    public int getDistanceMovedTowardNegativeDirection() {
        return distanceMovedTowardNegativeDirection;
    }

    public void setDistanceMovedTowardNegativeDirection(int distanceMovedTowardNegativeDirection) {
        this.distanceMovedTowardNegativeDirection = distanceMovedTowardNegativeDirection;
    }

    public String getPositiveDirection() {
        return positiveDirection;
    }

    public void setPositiveDirection(String positiveDirection) {
        this.positiveDirection = positiveDirection;
    }

    public String getNegativeDirection() {
        return negativeDirection;
    }

    public void setNegativeDirection(String negativeDirection) {
        this.negativeDirection = negativeDirection;
    }

    public void setToStartPosition() {
        Position position = null;
        if (mapOfEvilPlayer != null && mapOfEvilPlayer.containsKey("horizontal")) {
            position = (Position) mapOfEvilPlayer.get("horizontal");
            attackDirection = "horizontal";
            speed = 5;
        } else if (mapOfEvilPlayer != null && mapOfEvilPlayer.containsKey("vertical")) {
            position = (Position) mapOfEvilPlayer.get("vertical");
            attackDirection = "vertical";
            speed = 5;
        }

        int positionX = position.getX();
        int positionY = position.getY();
        setPositionX(positionX);
        setPositionY(positionY);
        distanceMovedTowardPositiveDirection = 0;
        distanceMovedTowardNegativeDirection = 0;
    }

    /* attack the mainPlayer depending on the attackDirection, Or move randomly . */
    private void setAttackDirection() {
        if (attackDirection.equals("horizontal")) {
            positiveDirection = "right";
            negativeDirection = "left";
        } else if (attackDirection.equals("vertical")) {
            positiveDirection = "up";
            negativeDirection = "down";
        }
    }


    /* update the speed. the less the number the speed is. The faster the evilPlayer is.*/
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}



