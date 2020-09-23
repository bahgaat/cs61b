package byog.Core;

import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class Point implements Serializable {

    void addPoint(int index) {
        Position p = MyWorld.arrayOfPoints.get(index);
        MyWorld.world[p._x][p._y] = Tileset.FLOWER;
    }


}
