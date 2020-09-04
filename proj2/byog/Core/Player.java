package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

class Player implements Serializable {
    private int positionX;
    private int positionY;


    public Player() {
        Position position = new Position(MyWorld.hallWayPosition._x, MyWorld.hallWayPosition._y - 1, Tileset.PLAYER);
        positionX = position._x;
        positionY = position._y;
        MyWorld.world[positionX][positionY] = Tileset.PLAYER;
    }

    void moveOneStep(String direction) {
        if (direction.equals("up")) {
            if (MyWorld.world[positionX][positionY + 1].description().equals("floor")) {
                MyWorld.world[positionX][positionY] = Tileset.FLOOR;
                MyWorld.world[positionX][positionY] = Tileset.PLAYER;
                positionY += 1;
            }
        } else if (direction.equals("down")) {
            if (MyWorld.world[positionX][positionY- 1].description().equals("floor")) {
                MyWorld.world[positionX][positionY] = Tileset.FLOOR;
                MyWorld.world[positionX][positionY- 1] = Tileset.PLAYER;
                positionY -= 1;
            }
        } else if (direction.equals("right")) {
            if (MyWorld.world[positionX + 1][positionY].description().equals("floor")) {
                MyWorld.world[positionX][positionY] = Tileset.FLOOR;
                MyWorld.world[positionX + 1][positionY] = Tileset.PLAYER;
                positionX += 1;
            }
        } else if (direction.equals("left")) {
            if (MyWorld.world[positionX- 1][positionY].description().equals("floor")) {
                MyWorld.world[positionX][positionY] = Tileset.FLOOR;
                MyWorld.world[positionX - 1][positionY] = Tileset.PLAYER;
                positionX -= 1;
            }
        }

    }

}
