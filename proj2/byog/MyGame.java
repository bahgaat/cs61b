package byog;

import byog.Core.RandomUtils;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import byog.lab5.HexWorld;

import java.util.Random;

/* row is y, column is x. */
/* The starting position of drawing any L is from its vertical part. */
/* I am going to divide the drawing of MyGame into 6 parts. */
public class MyGame {


    /* draw positive vertical line, which means that the line is moving up. */
    private static void drawPositiveVerticalLine(TETile[][] world, Position p, double size, TETile type) {
        int row;
        int yPosition = p._y;
        int xPosition = p._x;
        for (row = yPosition; row < yPosition + size; row += 1) {
            if (world[xPosition][row] == Tileset.WALL) {
                continue;
            }
            try {
                world[xPosition][row] = type;
            } catch (Exception e) {
                break;
            }

        }
        p._y = row - 1;
    }

    /* draw negative vertical line, which means that the line is moving down. */
    private static void drawNegativeVerticalLine(TETile[][] world, Position p, double size, TETile type) {
        int row;
        int yPosition = p._y;
        int xPosition = p._x;
        for (row = yPosition; row > yPosition - size; row -= 1) {
            if (world[xPosition][row] == Tileset.WALL) {
                continue;
            }
            try {
                world[xPosition][row] = type;
            } catch (Exception e) {
                break;
            }
        }
        p._y = row + 1;
    }

    /* draw positive horizontal line, which means that the line is moving right. */
    private static void drawPositiveHorizontalLine(TETile[][] world, Position p, double size, TETile type) {
        int column;
        int yPosition = p._y;
        int xPosition = p._x;
        for (column = xPosition; column < xPosition + size; column += 1) {
            if (world[column][yPosition] == Tileset.WALL) {
                continue;
            }
            try {
                world[column][yPosition] = type;
            } catch (Exception e) {
                break;
            }

        }
        p._x = column - 1;
    }

    /* draw negative horizontal line, which means that the line is moving left. */
    private static void drawNegativeHorizontalLine(TETile[][] world, Position p, double size, TETile type) {
        int column;
        int yPosition = p._y;
        int xPosition = p._x;
        for (column = xPosition; column > xPosition - size; column -= 1) {
            if (world[column][yPosition] == Tileset.WALL) {
                continue;
            }
            try {
                world[column][yPosition] = type;
            } catch (Exception e) {
                break;
            }

        }
        p._x = column + 1;
    }

    /* draw negative L, which look to the left. */
    private static void drawNegativeLStartFromVerticalLine(TETile[][] world, Position P, int sizeOfVerticalPartOfL, int sizeOfHorizontalPartOfL, TETile type) {
        drawNegativeVerticalLine(world, P, sizeOfVerticalPartOfL, type);
        drawNegativeHorizontalLine(world, P, sizeOfHorizontalPartOfL, type);
    }

    /* draw letter L . */
    private static void drawLStartFromVerticalLine(TETile[][] world, Position P, int sizeOfVerticalPartOfL, int sizeOfHorizontalPartOfL, TETile type) {
        drawNegativeVerticalLine(world, P, sizeOfVerticalPartOfL, type);
        drawPositiveHorizontalLine(world, P, sizeOfHorizontalPartOfL, type);
    }

    /* draw the positive opposite of L, which looks to the right. */
    private static void drawPositiveOppositeLStartFromVerticalLine(TETile[][] world, Position P, int sizeOfVerticalPartOfOppositeL, int sizeOfHorizontalPartOfOppositeL, TETile type) {
        drawPositiveVerticalLine(world, P, sizeOfVerticalPartOfOppositeL, type);
        drawPositiveHorizontalLine(world, P, sizeOfHorizontalPartOfOppositeL, type);
    }

    /* draw the negative opposite of L, which looks to the left. */
    private static void drawNegativeOppositeLStartFromVerticalLine(TETile[][] world, Position P, int sizeOfVerticalPartOfOppositeL, int sizeOfHorizontalPartOfOppositeL, TETile type) {
        drawPositiveVerticalLine(world, P, sizeOfVerticalPartOfOppositeL, type);
        drawNegativeHorizontalLine(world, P, sizeOfHorizontalPartOfOppositeL, type);
    }

    /* draw the whole game. */
    public static void drawGame(TETile[][] world) {
        Random r = new Random();
        int x = RandomUtils.uniform(r, 95, 98);
        int y = RandomUtils.uniform(r, 57, 60);
        int i;
        Position startPosition = new Position(x, y);
        Position BottomPosition = new Position(x, y);
        Position HallWayPosition = new Position(x - 1, y - 1);
        int h = RandomUtils.uniform(r, 3, 5);
        i = RandomUtils.uniform(r, 4, 6);
        drawUpperWallOfTheGameFromTheRight(world, startPosition, h, r, i);
        drawBottomWallOfTheGameFromTheRight(world, BottomPosition, h, r, i);
        drawPositiveVerticalLine(world, BottomPosition, startPosition._y - BottomPosition._y, Tileset.WALL);
        drawHallWayOfTheGameFromTheRight(world, HallWayPosition, h, r, i);
    }

    private static void drawUpperWallOfTheGameFromTheRight(TETile[][] world, Position startPosition, int h, Random r, int i) {
        for (int j = 0; j < h; j += 1) {
            if (j == 0) {
                drawNegativeHorizontalLine(world, startPosition, i, Tileset.WALL);
                drawNegativeLStartFromVerticalLine(world, startPosition, i, i, Tileset.WALL);
            } else if (j % 2 != 0) {
                drawNegativeHorizontalLine(world, startPosition, i, Tileset.WALL);
                drawBottomHalfSquare(world, startPosition, i, i, i, Tileset.WALL);
                drawNegativeHorizontalLine(world, startPosition, i, Tileset.WALL);
                drawBottomHalfSquare(world, startPosition, i, i * 2, i, Tileset.WALL);
                drawLStartFromVerticalLine(world, startPosition, i, i * 2, Tileset.WALL);
                drawNegativeLStartFromVerticalLine(world, startPosition, i, i * 2, Tileset.WALL);
            } else {
                drawNegativeHorizontalLine(world, startPosition, i, Tileset.WALL);
                drawBottomHalfSquare(world, startPosition, i + 2, i, i, Tileset.WALL);
                drawPositiveOppositeOfLStartFromHorizontalLine(world, startPosition, i, i, Tileset.WALL);
                drawNegativeHorizontalLine(world, startPosition, i, Tileset.WALL);
                drawBottomHalfSquare(world, startPosition, i, i * 2, i , Tileset.WALL);
                drawNegativeOppositeOfLStartFromHorizontalLine(world, startPosition, i , i , Tileset.WALL);
                drawNegativeHorizontalLine(world, startPosition, i + 2, Tileset.WALL);
            }
        }
    }

    private static void drawBottomWallOfTheGameFromTheRight(TETile[][] world, Position BottomPosition, int h, Random r, int i) {
        for (int j = 0; j < h; j += 1) {
            if (j == 0) {
                drawNegativeVerticalLine(world, BottomPosition, i * 2, Tileset.WALL);
                drawNegativeHorizontalLine(world, BottomPosition, i * 2 - 1, Tileset.WALL);
            } else if (j % 2 != 0) {
                drawNegativeHorizontalLine(world, BottomPosition, i, Tileset.WALL);
                drawUpperHalfSquare(world, BottomPosition, i, i, i, Tileset.WALL);
                drawNegativeHorizontalLine(world, BottomPosition, i - 2, Tileset.WALL);
                drawNegativeLStartFromVerticalLine(world, BottomPosition, i * 2 + 2, i * 2 + 2, Tileset.WALL);
            } else {
                drawNegativeHorizontalLine(world, BottomPosition, i, Tileset.WALL);
                drawUpperHalfSquare(world, BottomPosition, i, i, i , Tileset.WALL);
                drawNegativeHorizontalLine(world, BottomPosition, i * 4, Tileset.WALL);

            }
        }
    }

    private static void drawHallWayOfTheGameFromTheRight(TETile[][] world, Position HallWayPosition, int h, Random r, int i) {
        for (int j = 0; j < h; j += 1) {
            if (j == 0) {
                drawBottomRectangleTowardLeft(world, HallWayPosition, i * 2 - 2, i - 2, Tileset.FLOOR);
                drawNegativeVerticalLine(world, HallWayPosition, i , Tileset.FLOOR);
                drawBottomRectangleTowardLeft(world, HallWayPosition, i - 1, i, Tileset.FLOOR);

            } else if (j % 2 != 0) {
                drawBottomRectangleTowardLeft(world, HallWayPosition, i - 1 , i + 1, Tileset.FLOOR);
                drawNegativeHorizontalLine(world, HallWayPosition, 2, Tileset.FLOOR);
                drawUpperRectangleTowardLeft(world, HallWayPosition, i , i - 2 , Tileset.FLOOR);
                drawBottomRectangleTowardRight(world, HallWayPosition, i * 2 - 2, i - 2, Tileset.FLOOR);
                drawNegativeHorizontalLine(world, HallWayPosition, i - 1, Tileset.FLOOR);
                drawBottomRectangleTowardLeft(world, HallWayPosition, i - 1, i - 1, Tileset.FLOOR);
                drawNegativeVerticalLine(world, HallWayPosition, i * 2 - 1, Tileset.FLOOR);
                drawBottomRectangleTowardLeft(world, HallWayPosition, i + 2, i * 2, Tileset.FLOOR);
                drawNegativeLStartFromHorizontalLine(world, HallWayPosition, i * 2, i + 2, Tileset.FLOOR);
                drawNegativeHorizontalLine(world, HallWayPosition, 3, Tileset.FLOOR);
                drawUpperRectangleTowardLeft(world,HallWayPosition, i * 2 - 3  , i * 2 - 2  , Tileset.FLOOR );
                drawNegativeLStartFromHorizontalLine(world, HallWayPosition, i * 2 - 1, i - 2, Tileset.FLOOR);
                drawPositiveHorizontalLine(world, HallWayPosition, 2, Tileset.FLOOR);
                drawNegativeVerticalLine(world, HallWayPosition, i * 3 , Tileset.FLOOR);
                drawNegativeHorizontalLine(world, HallWayPosition, i * 2, Tileset.FLOOR);
                drawPositiveVerticalLine(world, HallWayPosition, i + 2, Tileset.FLOOR);
            } else {
                drawBottomRectangleTowardLeft(world, HallWayPosition, i + 2, i + 1, Tileset.FLOOR);
                drawNegativeHorizontalLine(world, HallWayPosition, 2, Tileset.FLOOR);
                drawUpperRectangleTowardLeft(world, HallWayPosition, i + 2, i - 2, Tileset.FLOOR);
                drawBottomRectangleTowardRight(world, HallWayPosition, i * 2 + 1, i - 2, Tileset.FLOOR);
                drawNegativeHorizontalLine(world, HallWayPosition, i - 1, Tileset.FLOOR);
                drawUpperRectangleTowardLeft(world, HallWayPosition, 3, i - 1, Tileset.FLOOR);
                drawBottomRectangleTowardRight(world, HallWayPosition, i + 2, i - 1, Tileset.FLOOR);
                drawNegativeLStartFromVerticalLine(world, HallWayPosition, i - 2, i, Tileset.FLOOR );
                drawBottomRectangleTowardLeft(world, HallWayPosition, 5, i, Tileset.FLOOR );
                drawNegativeHorizontalLine(world, HallWayPosition, 2, Tileset.FLOOR);
                drawUpperRectangleTowardLeft(world, HallWayPosition, i, i - 1, Tileset.FLOOR);
                drawNegativeOppositeLStartFromVerticalLine(world, HallWayPosition, i, 2, Tileset.FLOOR);
                drawBottomRectangleTowardLeft(world, HallWayPosition, i - 2, i - 1, Tileset.FLOOR);
                drawNegativeOppositeOfLStartFromHorizontalLine(world, HallWayPosition, i, i, Tileset.FLOOR);
                drawBottomRectangleTowardRight(world, HallWayPosition, 5, i - 1, Tileset.FLOOR);
                drawNegativeLStartFromVerticalLine(world, HallWayPosition, i, i, Tileset.FLOOR);
                if (i == 5) {
                    drawNegativeHorizontalLine(world, HallWayPosition, i + 1, Tileset.FLOOR);
                } else {
                    drawBottomRectangleTowardLeft(world, HallWayPosition, i - 2, i + 1, Tileset.FLOOR);
                }
            }
        }
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
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
            sizeOfHorizontalLine -= 1;
        }
    }

    /* draw a rectangle, which looks up toward right. The shape ends at the lowest part of the last size. */
    private static void drawUpperRectangleTowardRight(TETile[][] world, Position P, int sizeOfVerticalLine,
                                                      int sizeOfHorizontalLine, TETile type) {
        while (sizeOfHorizontalLine > 1) {
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            drawPositiveHorizontalLine(world, P, 2, type);
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
            sizeOfHorizontalLine -= 1;
        }
    }

    /* draw L, start form horizontal line. */
    private static void drawLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                                     int sizeOfVerticalLine, TETile type) {
        drawNegativeHorizontalLine(world, p, sizeOfHorizontalLine, type);
        drawPositiveVerticalLine(world, p, sizeOfVerticalLine, type);
    }

    private static void drawNegativeLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                                             int sizeOfVerticalLine, TETile type) {
        drawPositiveHorizontalLine(world, p, sizeOfHorizontalLine, type);
        drawPositiveVerticalLine(world, p, sizeOfVerticalLine, type);
    }

    /* draw Positive opposite of L, which looks to the right. Start from horizontal line. */
    private static void drawPositiveOppositeOfLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                                                       int sizeOfVerticalLine, TETile type) {
        drawNegativeHorizontalLine(world, p, sizeOfHorizontalLine, type);
        drawNegativeVerticalLine(world, p, sizeOfVerticalLine, type);
    }

    /* draw Negative opposite of L, which looks to the left. Start from horizontal line. */
    private static void drawNegativeOppositeOfLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                                                       int sizeOfVerticalLine, TETile type) {
        drawPositiveHorizontalLine(world, p, sizeOfHorizontalLine, type);
        drawNegativeVerticalLine(world, p, sizeOfVerticalLine, type);
    }
}







