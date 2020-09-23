package byog.Core;

import byog.TileEngine.Tileset;

import java.io.Serializable;


public class MainPlayer extends Player {

    public MainPlayer() {
        Position position = new Position(MyWorld.hallWayPosition._x, MyWorld.hallWayPosition._y - 1, Tileset.PLAYER);
        positionX = position._x;
        positionY = position._y;
        typeToAttack = "flower";
        MyWorld.world[positionX][positionY] = Tileset.PLAYER;
    }

    @Override
    void attack(){
        points += 1;
    }

    protected void startNewRound() {
        Position position = new Position(MyWorld.hallWayPosition._x, MyWorld.hallWayPosition._y - 1, Tileset.PLAYER);
        positionX = position._x;
        positionY = position._y;
        MyWorld.world[positionX][positionY] = Tileset.PLAYER;
        MyWorld.world[MyWorld.doorPosition._x][MyWorld.doorPosition._y] = Tileset.LOCKED_DOOR;
    }



    boolean winTheRound() {
        return points == MyWorld.round && MyWorld.world[MyWorld.doorPosition._x][MyWorld.doorPosition._y] == Tileset.PLAYER;
    }



}
