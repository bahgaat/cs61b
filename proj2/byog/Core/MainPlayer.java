package byog.Core;

import byog.TileEngine.Tileset;


class MainPlayer extends Player{
    private int points;
    private boolean winTheRound;

   MainPlayer() {
        Position position = MyWorld.playerPosition;
        positionX = position._x;
        positionY = position._y;
        MyWorld.world[positionX][positionY] = Tileset.PLAYER;
    }

    @Override
    void attack(){
        if (itIsPossibleToMoveToTheNewPosition(newDirection, "flower")) {
            move(Tileset.PLAYER, "flower");
            points += 1;
        }
    }

    /* return true if the MainPlayer collected the whole points and he moved to the locked door.*/
    boolean winTheRound() {
        return itIsPossibleToMoveToTheNewPosition(newDirection, "locked door")  && MyWorld.getRound() == points;
    }

    /* start new round by putting the mainPlayer to the startingPosition and set the points to 0. */
    void setPlayerToStartPosition() {
        MyWorld.world[positionX][positionY] = Tileset.FLOOR;
        Position position = MyWorld.playerPosition;
        positionX = position._x;
        positionY = position._y;
        points = 0;
        MyWorld.world[positionX][positionY] = Tileset.PLAYER;
    }

}
