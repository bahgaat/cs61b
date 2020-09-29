package byog.Core;

import byog.TileEngine.Tileset;


class MainPlayer extends Player{
    private int points;
    private boolean winTheRound;

   MainPlayer() {
        Position position = new Position(MyWorld.hallWayPosition._x, MyWorld.hallWayPosition._y - 1, Tileset.PLAYER);
        positionX = position._x;
        positionY = position._y;
        MyWorld.world[positionX][positionY] = Tileset.PLAYER;
    }

    @Override
    void attack(){
        if (itIsPossibleToMoveToTheNewPosition(newDirection, "flower")) {
            checkIfItIsPossibleToMoveToTheNewPositionAndMoveIfItIsOk(newDirection, Tileset.PLAYER, "flower");
            points += 1;
        }
    }

    boolean winTheRound() {
        return itIsPossibleToMoveToTheNewPosition(newDirection, "locked door") && MyWorld.round == points;
    }

    /* start new round by putting the mainPlayer to the startingPosition. */
    void setPlayerToStartPosition() {
        MyWorld.world[positionX][positionY] = Tileset.FLOOR;
        Position position = new Position(MyWorld.hallWayPosition._x, MyWorld.hallWayPosition._y - 1, Tileset.PLAYER);
        positionX = position._x;
        positionY = position._y;
        points = 0;
        MyWorld.world[positionX][positionY] = Tileset.PLAYER;

    }





}
