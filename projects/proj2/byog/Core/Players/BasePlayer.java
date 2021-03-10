package byog.Core.Players;
import byog.Core.Position;
import byog.TileEngine.TETile;

import java.io.Serializable;

public abstract class BasePlayer implements Serializable {
    private final static String inputUp = "up";
    private final static String inputDown = "down";
    private final static String inputLeft = "left";
    private final static String inputRight = "right";
    private TETile type;
    private TETile typeToAttack;
    private int positionX;
    private int positionY;

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionX(int newPositionX) {
        positionX = newPositionX;
    }

    public void setPositionY(int newPositionY) {
        positionY = newPositionY;
    }

    public TETile getType() {
        return type;
    }

    public TETile getTypeToAttack() {
        return typeToAttack;
    }

    public void setType(TETile type) {
        this.type = type;
    }

    public void setTypeToAttack(TETile typeToAttack) {
        this.typeToAttack = typeToAttack;
    }

    public abstract void setToStartPosition();

    /* return NewPosition Of The Player. */
    public Position NewPositionOfThePlayer(String newDirection) {
        int addToX = 0;
        int addToY = 0;
        switch (newDirection) {
            case inputUp -> addToY += 1;
            case inputDown -> addToY -= 1;
            case inputRight -> addToX += 1;
            case inputLeft -> addToX -= 1;
        }

        Position newPosition = new Position(positionX + addToX, positionY + addToY);
        return newPosition;
    }

}