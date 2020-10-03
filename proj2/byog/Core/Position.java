package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;


public class Position implements Serializable {
    int _x;
    int _y;

    public Position(int x, int y) {
        _x = x;
        _y = y;
    }

}
