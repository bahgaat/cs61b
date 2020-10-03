package byog.Core;

import byog.TileEngine.Tileset;

import java.io.Serializable;

public class Point implements Serializable {

    /* take pointPosition from arrayOfPoints and add point(flower) in this position in gthe world. */
    void addPoint(int index) {
        Position p = MyWorld.arrayOfPoints.get(index);
        MyWorld.world[p._x][p._y] = Tileset.FLOWER;
    }


}
