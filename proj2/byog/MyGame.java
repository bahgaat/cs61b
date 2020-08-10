package byog;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import byog.lab5.HexWorld;

/* row is y, column is x. */
/* The starting position of drawing any L is from its vertical part. */
/* I am going to divide the drawing of MyGame into 6 parts. */
public class MyGame {
    private static Position startPosition = new StartPosition();
    private static Position positionBottomWall = new PositionBottomWall();
    private static Position positionUpperWall = new PositionUpperWall();
    private static Position positionHallWay = new PositionHallWay();


    /* draw positive vertical line, which means that the line is moving up. */
    private static void drawPositiveVerticalLine(TETile[][] world, Position P, int size, TETile type) {
        int row;
        int YPosition = P.getYPosition();
        int XPosition = P.getXPosition();
        for (row = YPosition; row < YPosition + size; row += 1) {
            world[XPosition][row] = type;
        }
        P.updateYPosition(row - 1);
    }

    /* draw negative vertical line, which means that the line is moving down. */
    private static void drawNegativeVerticalLine(TETile[][] world, Position P, int size, TETile type) {
        int row;
        int YPosition = P.getYPosition();
        int XPosition = P.getXPosition();
        for (row = YPosition; row > YPosition - size; row -= 1) {
            world[XPosition][row] = type;
        }
        P.updateYPosition(row + 1);
    }

    /* draw positive horizontal line, which means that the line is moving right. */
    private static void drawPositiveHorizontalLine(TETile[][] world, Position P, int size, TETile type) {
        int column;
        int YPosition = P.getYPosition();
        int XPosition = P.getXPosition();
        for (column = XPosition; column < XPosition + size; column += 1) {
            world[column][YPosition] = type;
        }
        P.updateXPosition(column - 1);
    }

    /* draw negative horizontal line, which means that the line is moving left. */
    private static void drawNegativeHorizontalLine(TETile[][] world,Position P, int size, TETile type) {
        int column;
        int YPosition = P.getYPosition();
        int XPosition = P.getXPosition();
        for (column = XPosition; column > XPosition - size; column -= 1) {
            world[column][YPosition] = type;
        }
        P.updateXPosition(column + 1);
    }

    /* draw negative L, which look to the left. */
    private static void drawNegativeL(TETile[][] world, Position P, int sizeOfVerticalPartOfL, int sizeOfHorizontalPartOfL, TETile type) {
        drawNegativeVerticalLine(world, P, sizeOfVerticalPartOfL, type);
        drawNegativeHorizontalLine(world, P, sizeOfHorizontalPartOfL, type);
    }

    /* draw letter L . */
    private static void drawL(TETile[][] world, Position P, int sizeOfVerticalPartOfL, int sizeOfHorizontalPartOfL, TETile type) {
        drawNegativeVerticalLine(world, P, sizeOfVerticalPartOfL, type);
        drawPositiveHorizontalLine(world, P, sizeOfHorizontalPartOfL, type);
    }

    /* draw the positive opposite of L, which looks to the right. */
    private static void drawPositiveOppositeL(TETile[][] world, Position P, int sizeOfVerticalPartOfOppositeL, int sizeOfHorizontalPartOfOppositeL, TETile type) {
        drawPositiveVerticalLine(world, P, sizeOfVerticalPartOfOppositeL, type);
        drawPositiveHorizontalLine(world, P, sizeOfHorizontalPartOfOppositeL, type);
    }

    /* draw the negative opposite of L, which looks to the left. */
    private static void drawNegativeOppositeL(TETile[][] world, Position P, int sizeOfVerticalPartOfOppositeL, int sizeOfHorizontalPartOfOppositeL, TETile type) {
        drawPositiveVerticalLine(world, P, sizeOfVerticalPartOfOppositeL, type);
        drawNegativeHorizontalLine(world, P, sizeOfHorizontalPartOfOppositeL, type);
    }

    /* draw the whole game. */
    public static void drawGame(TETile[][] world) {
        drawPositiveVerticalLine(world, startPosition, 6, Tileset.WALL);
        drawFirstPartOfTheGame(world);
        drawSecondPartOfTheGame(world);
        drawThirdPartOfTheGame(world);

    }


    private static void drawFirstPartOfTheGame(TETile[][] world) {
        drawUpperWallOfTheFirstPart(world);
        drawBottomWallOfTheFirstPart(world);
        drawHallWayOfTheFirstPart(world);
    }

    private static void drawUpperWallOfTheFirstPart(TETile[][] world) {
        drawNegativeHorizontalLine(world, positionUpperWall, 4, Tileset.WALL );
        drawNegativeL(world, positionUpperWall, 4, 7, Tileset.WALL);
        drawL(world, positionUpperWall, 3, 2, Tileset.WALL);
        drawNegativeL(world, positionUpperWall, 5, 2, Tileset.WALL);
        drawPositiveVerticalLine(world, positionUpperWall, 2, Tileset.WALL);
    }

    private static void drawBottomWallOfTheFirstPart(TETile[][] world) {
        drawNegativeHorizontalLine(world, positionBottomWall, 7, Tileset.WALL);
        drawNegativeL(world, positionBottomWall, 7, 4, Tileset.WALL);
        drawNegativeVerticalLine(world, positionBottomWall, 3, Tileset.WALL);
    }

    private static void drawHallWayOfTheFirstPart(TETile[][] world) {
        drawNegativeVerticalLine(world, positionHallWay, 4, Tileset.FLOOR);
        drawNegativeHorizontalLine(world, positionHallWay, 2, Tileset.FLOOR);
        drawPositiveVerticalLine(world, positionHallWay, 4, Tileset.FLOOR);
        drawNegativeL(world, positionHallWay, 4, 7, Tileset.FLOOR);
        drawPositiveHorizontalLine(world, positionHallWay, 2, Tileset.FLOOR);
        drawNegativeL(world, positionHallWay, 7, 3, Tileset.FLOOR);
    }

    private static void drawSecondPartOfTheGame(TETile[][] world) {
        drawUpperWallOfTheSecondPart(world);
        drawBottomWallOfTheSecondPart(world);
        drawHallWayOfTheSecondPart(world);
    }

    private static void drawUpperWallOfTheSecondPart(TETile[][] world) {
        drawNegativeHorizontalLine(world, positionUpperWall, 3, Tileset.WALL);
        drawL(world, positionUpperWall, 2, 1, Tileset.WALL);
        drawNegativeHorizontalLine(world, positionUpperWall, 2, Tileset.WALL);
        drawNegativeOppositeL(world, positionUpperWall, 4, 2, Tileset.WALL);
        drawPositiveOppositeL(world, positionUpperWall, 10, 5, Tileset.WALL);
        drawNegativeOppositeL(world, positionUpperWall, 3, 9, Tileset.WALL);
        drawPositiveOppositeL(world, positionUpperWall, 5, 4, Tileset.WALL);
        drawNegativeOppositeL(world, positionUpperWall, 3, 4, Tileset.WALL);
        drawPositiveVerticalLine(world, positionUpperWall, 2, Tileset.WALL);
    }

    private static void drawBottomWallOfTheSecondPart(TETile[][] world) {
        drawNegativeHorizontalLine(world, positionBottomWall, 3, Tileset.WALL);
        drawPositiveVerticalLine(world, positionBottomWall, 3, Tileset.WALL);
        drawNegativeHorizontalLine(world, positionBottomWall, 2, Tileset.WALL);
        drawNegativeL(world, positionBottomWall, 3, 4, Tileset.WALL);
        drawNegativeOppositeL(world, positionBottomWall, 9, 2, Tileset.WALL);
        drawPositiveOppositeL(world, positionBottomWall, 9, 2, Tileset.WALL);
        drawNegativeL(world, positionBottomWall, 2, 3, Tileset.WALL);
        drawPositiveVerticalLine(world, positionBottomWall, 2, Tileset.WALL);
    }

    private static void drawHallWayOfTheSecondPart(TETile[][] world) {
        drawNegativeHorizontalLine(world, positionHallWay, 2, Tileset.FLOOR);
        drawPositiveVerticalLine(world, positionHallWay, 2, Tileset.FLOOR);
        drawNegativeVerticalLine(world, positionHallWay, 4, Tileset.FLOOR);
        drawPositiveVerticalLine(world, positionHallWay, 3, Tileset.FLOOR);
        drawNegativeHorizontalLine(world, positionHallWay, 4, Tileset.FLOOR);
        drawNegativeL(world, positionHallWay, 3, 2, Tileset.FLOOR);
        drawPositiveOppositeL(world, positionHallWay, 3, 2, Tileset.FLOOR);
        drawNegativeOppositeL(world, positionHallWay, 4, 2, Tileset.FLOOR);
        drawNegativeVerticalLine(world, positionHallWay, 3, Tileset.FLOOR);
        drawPositiveVerticalLine(world, positionHallWay, 11, Tileset.FLOOR);
        drawBottomRectangleTowardLeft(world, positionHallWay, 6, 2, Tileset.FLOOR);
        drawPositiveHorizontalLine(world, positionHallWay, 2, Tileset.FLOOR);
        drawPositiveOppositeL(world, positionHallWay, 4, 5, Tileset.FLOOR);
    }

    /* draw third part of the game. */
    private static void drawThirdPartOfTheGame(TETile[][] world) {
        drawUpperWallOfTheThirdPart(world);
        drawBottomWallOfTheThirdPart(world);
        drawHallWayOfTheThirdPart(world);
    }

    private static void drawUpperWallOfTheThirdPart(TETile[][] world) {
        drawNegativeHorizontalLine(world, positionUpperWall, 3, Tileset.WALL);
        drawNegativeL(world, positionUpperWall, 9, 5, Tileset.WALL);
        drawNegativeOppositeL(world, positionUpperWall,2 ,7, Tileset.WALL);
        drawBottomRectangleTowardLeft(world, positionUpperWall, 2, 2, Tileset.WALL);
        drawUpperRectangleTowardRight(world, positionUpperWall, 2, 2, Tileset.WALL);
        drawNegativeOppositeL(world, positionUpperWall, 5, 5, Tileset.WALL);
        drawNegativeVerticalLine(world, positionUpperWall, 4, Tileset.WALL);

        drawBottomRectangleTowardRight(world, positionUpperWall, 2, 2, Tileset.WALL);
        drawNegativeHorizontalLine(world, positionUpperWall, 2, Tileset.WALL);
        drawL(world, positionUpperWall, 8, 2, Tileset.WALL);
        drawNegativeL(world, positionUpperWall, 9, 5, Tileset.WALL);
        drawPositiveOppositeL(world, positionUpperWall, 21, 4, Tileset.WALL);
        drawNegativeOppositeL(world, positionUpperWall, 3, 4, Tileset.WALL);

    }

    private static void drawBottomWallOfTheThirdPart(TETile[][] world) {

    }

    private static void drawHallWayOfTheThirdPart(TETile[][] world) {


    }



    /* draw a shape which is similar to half square, which looks to the left. Start drawing the shape from bottom */
    private static void drawLeftHalfSquare(TETile[][] world, Position P, int sizeOfBottomHorizontalLine,
                                               int sizeOfVerticalLine, int sizeOfUpperHorizontalLine, TETile type) {
            drawPositiveHorizontalLine(world, P, sizeOfBottomHorizontalLine, type);
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            drawNegativeHorizontalLine(world, P, sizeOfUpperHorizontalLine, type);
    }

    /* draw a shape which is similar to half square, which looks to the right. Start drawing the shape from bottom */
    private static void drawRightHalfSquare(TETile[][] world, Position P, int sizeOfBottomHorizontalLine,
                                               int sizeOfVerticalLine, int sizeOfUpperHorizontalLine, TETile type) {
        drawNegativeHorizontalLine(world, P, sizeOfBottomHorizontalLine, type);
        drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
        drawPositiveHorizontalLine(world, P, sizeOfUpperHorizontalLine, type);
    }

    /* draw a shape which is similar to half square, which looks down. Start drawing the shape from right. */
    private static void drawBottomHalfSquare(TETile[][] world, Position P, int sizeOfRightVerticalLine,
                                            int sizeOfHorizontalLine, int sizeOfLeftVerticalLine, TETile type) {
        drawPositiveVerticalLine(world, P, sizeOfRightVerticalLine, type);
        drawNegativeHorizontalLine(world, P, sizeOfHorizontalLine, type);
        drawNegativeVerticalLine(world, P, sizeOfLeftVerticalLine, type);
    }

    /* draw a shape which is similar to half square, which looks up. Start drawing the shape from right. */
    private static void drawUpperHalfSquare(TETile[][] world, Position P, int sizeOfRightVerticalLine,
                                             int sizeOfHorizontalLine, int sizeOfLeftVerticalLine, TETile type) {
        drawNegativeVerticalLine(world, P, sizeOfRightVerticalLine, type);
        drawNegativeHorizontalLine(world, P, sizeOfHorizontalLine, type);
        drawPositiveVerticalLine(world, P, sizeOfLeftVerticalLine, type);
    }

    /* draw a rectangle, which looks down toward left. the shape ends at the highest part of the last size. */
    private static void drawBottomRectangleTowardLeft(TETile[][] world, Position P, int sizeOfVerticalLine,
                                            int sizeOfHorizontalLine, TETile type) {
        while (sizeOfHorizontalLine > 1) {
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
            drawNegativeHorizontalLine(world, P, 2, type);
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            sizeOfHorizontalLine -= 1;
        }
    }

    /* draw a rectangle, which looks down toward right. the shape ends at the highest part of the last size. */
    private static void drawBottomRectangleTowardRight(TETile[][] world, Position P, int sizeOfVerticalLine,
                                                       int sizeOfHorizontalLine, TETile type) {
        while (sizeOfHorizontalLine > 1) {
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
            drawPositiveHorizontalLine(world, P, 2, type);
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            sizeOfHorizontalLine -= 1;
        }
    }

    /* draw a rectangle, which looks up toward left. The shape ends at the lowest part of the last size. */
    private static void drawUpperRectangleTowardLeft(TETile[][] world, Position P, int sizeOfVerticalLine,
                                                int sizeOfHorizontalLine, TETile type) {
        while (sizeOfHorizontalLine > 1) {
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            drawNegativeHorizontalLine(world, P, 2, type);
            drawNegativeVerticalLine(world, P, sizeOfHorizontalLine, type);
            sizeOfHorizontalLine -= 1;
        }
    }

    /* draw a rectangle, which looks up toward right. The shpae ends at the lowest part of the last size. */
    private static void drawUpperRectangleTowardRight(TETile[][] world, Position P, int sizeOfVerticalLine,
                                                     int sizeOfHorizontalLine, TETile type) {
        while (sizeOfHorizontalLine > 1) {
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            drawPositiveHorizontalLine(world, P, 2, type);
            drawNegativeVerticalLine(world, P, sizeOfHorizontalLine, type);
            sizeOfHorizontalLine -= 1;
        }
    }



}




