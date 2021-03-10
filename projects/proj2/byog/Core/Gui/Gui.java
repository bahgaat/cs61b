package byog.Core.Gui;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

public abstract class Gui implements Serializable {

    /* draw to the user either the basic Ui if the argument passed to drawFrame was "Ui" or draw the passed argument. */
    public void display(String s) {
        StdDraw.setCanvasSize(40 * 16, 40 * 16);
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, 40);
        StdDraw.setYscale(0, 40);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);

        // draw the actual Ui
        if (s.equals("Ui")) {
            StdDraw.text(20, 20, "NewGame(N)");
            StdDraw.text(20, 18, "LoadGame(L)");
            StdDraw.text(20, 16, "Quit(Q)");
        } else {
            // draw the text
            font = new Font("Monaco", Font.BOLD, 15);
            StdDraw.setFont(font);
            StdDraw.text(20, 20, s);
        }
        StdDraw.show();
        StdDraw.pause(2500);
    }

    protected void ifTypeIsKeyBoardDisplayToUser(String s, String type) {
        if (type.equals("keyBoard")) {
            display(s);
        }
    }
}
