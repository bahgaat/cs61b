package byog;

import byog.lab5.HexWorld;

import javax.swing.text.Position;

public class StartPosition implements byog.Position{
    public int x = 79;
    public int y = 30;

    public int getYPosition() {
        return this.y;
    }

    public int getXPosition() {
        return this.x;
    }

    public void updateYPosition(int newY) {
        this.y = newY;
    }

    public void updateXPosition(int newX) {
        this.x = newX;
    }


}
