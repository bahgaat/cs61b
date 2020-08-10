package byog;

public class PositionHallWay implements Position {
    public int x = 78;
    public int y = 34;

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
