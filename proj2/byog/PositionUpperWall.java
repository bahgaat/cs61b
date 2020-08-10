package byog;

public class PositionUpperWall implements Position {
    public int x = 79;
    public int y = 35;

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
