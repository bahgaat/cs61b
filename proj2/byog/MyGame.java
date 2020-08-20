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
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;



    /* draw positive vertical line, which means that the line is moving up. */
    private void drawPositiveVerticalLine(TETile[][] world, Position p, double size, TETile type) {
        int row;
        int yPosition = p._y;
        int xPosition = p._x;
        for (row = yPosition; row < yPosition + size; row += 1) {
            try {
                world[xPosition][row] = type;
            } catch (Exception e) {
                break;
            }

        }
        p._y = row - 1;
    }

    /* draw negative vertical line, which means that the line is moving down. */
    private void drawNegativeVerticalLine(TETile[][] world, Position p, double size, TETile type) {
        int row;
        int yPosition = p._y;
        int xPosition = p._x;
        for (row = yPosition; row > yPosition - size; row -= 1) {
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
            try {
                world[column][yPosition] = type;
            } catch (Exception e) {
                break;
            }

        }
        p._x = column - 1;
    }

    /* draw negative horizontal line, which means that the line is moving left. */
    private void drawNegativeHorizontalLine(TETile[][] world, Position p, double size, TETile type) {
        int column;
        int yPosition = p._y;
        int xPosition = p._x;
        for (column = xPosition; column > xPosition - size; column -= 1) {

            try {
                world[column][yPosition] = type;
            } catch (Exception e) {
                break;
            }

        }
        p._x = column + 1;
    }

    /* draw negative L, which look to the left. */
    private void drawNegativeLStartFromVerticalLine(TETile[][] world, Position P, int sizeOfVerticalPartOfL, int sizeOfHorizontalPartOfL, TETile type) {
        drawNegativeVerticalLine(world, P, sizeOfVerticalPartOfL, type);
        drawNegativeHorizontalLine(world, P, sizeOfHorizontalPartOfL, type);
    }

    /* draw letter L . */
    private void drawLStartFromVerticalLine(TETile[][] world, Position P, int sizeOfVerticalPartOfL, int sizeOfHorizontalPartOfL, TETile type) {
        drawNegativeVerticalLine(world, P, sizeOfVerticalPartOfL, type);
        drawPositiveHorizontalLine(world, P, sizeOfHorizontalPartOfL, type);
    }

    /* draw the positive opposite of L, which looks to the right. */
    private void drawPositiveOppositeLStartFromVerticalLine(TETile[][] world, Position P, int sizeOfVerticalPartOfOppositeL, int sizeOfHorizontalPartOfOppositeL, TETile type) {
        drawPositiveVerticalLine(world, P, sizeOfVerticalPartOfOppositeL, type);
        drawPositiveHorizontalLine(world, P, sizeOfHorizontalPartOfOppositeL, type);
    }

    /* draw the negative opposite of L, which looks to the left. */
    private void drawNegativeOppositeLStartFromVerticalLine(TETile[][] world, Position P, int sizeOfVerticalPartOfOppositeL, int sizeOfHorizontalPartOfOppositeL, TETile type) {
        drawPositiveVerticalLine(world, P, sizeOfVerticalPartOfOppositeL, type);
        drawNegativeHorizontalLine(world, P, sizeOfHorizontalPartOfOppositeL, type);
    }


    /* draw the whole game. */
    public TETile[][] drawGame(long randomNumber) {
        Random r = new Random(randomNumber);
        int finalX;
        int h;
        int x = RandomUtils.uniform(r, 95, 97);
        int y = RandomUtils.uniform(r, 60, 65);
        int x2 = RandomUtils.uniform(r, 50, 55);
        int f = RandomUtils.uniform(r, 1, 10);

        finalX = x2;

        h = RandomUtils.uniform(r, 4, 6);
        int u = RandomUtils.uniform(r, 5, 7);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int p = 0; p < WIDTH; p += 1) {
            for (int g = 0; g < HEIGHT; g += 1) {
                world[p][g] = Tileset.NOTHING;
            }
        }

        int i;
        int number;
        Position startPosition = new Position(finalX, y);
        Position BottomPosition = new Position(finalX, y);
        Position HallWayPosition = new Position(finalX - 1, y - 1);
        i = RandomUtils.uniform(r, 3, 5);
        for (int j = 0; j < h; j += 1) {
            number = r.nextInt(4);
            drawUpperWallOfTheGameFromTheRight(world, startPosition, j, i, number, h);
            drawBottomWallOfTheGameFromTheRight(world, BottomPosition, j, i, number, h);
            drawHallWayOfTheGameFromTheRight(world, HallWayPosition, j, i, number, h);
        }
        /*drawPositiveVerticalLine(world, BottomPosition, 2, Tileset.WALL);*/
        for (int j = 0; j < u; j += 1) {
            number = r.nextInt(3);
            drawUpperWallOfTheGameFromTheLeft(world, startPosition, j, i, number);
            drawBottomWallOfTheGameFromTheLeft(world, BottomPosition, j, i, number);
            drawHallWayOfTheGameFromTheLeft(world, HallWayPosition, j, i, number);
        }
        drawPositiveHorizontalLine(world, BottomPosition, 2, Tileset.WALL);
        return world;
    }

    private void drawUpperWallOfTheGameFromTheRight(TETile[][] world, Position startPosition, int j, int i,
                                                           int number, int h) {
        if (j == 0 && number >= 2) {
            drawNegativeHorizontalLine(world, startPosition, i, Tileset.WALL);
            drawNegativeLStartFromVerticalLine(world, startPosition, i, i, Tileset.WALL);
        } else if (j == 0 && number < 2) {
            drawNegativeHorizontalLine(world, startPosition, i * 2, Tileset.WALL);
        } else if (number == 0) {
            drawBottomHalfSquare(world, startPosition, i + j, i + j, i + j, Tileset.WALL);
            drawNegativeLStartFromVerticalLine(world, startPosition, i + j, i + j, Tileset.WALL);
        } else if (number == 1 ) {
            drawBottomHalfSquare(world, startPosition, i + j, i + j, i + j, Tileset.WALL);
            drawPositiveHorizontalLine(world, startPosition, i, Tileset.WALL);
            drawNegativeHorizontalLine(world, startPosition, i * 2, Tileset.WALL);
        } else if (number == 2 ) {
            drawNegativeHorizontalLine(world, startPosition, i * 4, Tileset.WALL);
        } else if (number == 3) {
            drawPositiveOppositeLStartFromVerticalLine(world, startPosition, i + j + 2, i, Tileset.WALL);
            drawBottomHalfSquare(world, startPosition, i + j, i * 2, i + j, Tileset.WALL);
            drawNegativeOppositeOfLStartFromHorizontalLine(world, startPosition, i - 2, i + j + 2, Tileset.WALL);
            drawNegativeHorizontalLine(world, startPosition, i * 2, Tileset.WALL);
        }
    }


    private void drawBottomWallOfTheGameFromTheRight(TETile[][] world, Position BottomPosition, int j, int i,
                                                            int number, int h) {

        if (j == 0 && number >= 2) {
            drawNegativeVerticalLine(world, BottomPosition, i + 2, Tileset.WALL);
            drawNegativeHorizontalLine(world, BottomPosition, i * 2 - 1, Tileset.WALL);
        } else if (j == 0 && number < 2) {
            drawUpperHalfSquare(world, BottomPosition, i * 2, i, i * 2 - 2, Tileset.WALL);
            drawNegativeHorizontalLine(world, BottomPosition, i + 1, Tileset.WALL);
        } else if (number == 0) {
            drawNegativeLStartFromVerticalLine(world, BottomPosition, i + j, (i + j) * 2 - 1, Tileset.WALL);
        } else if (number == 1 ) {
            drawUpperHalfSquare(world, BottomPosition, i + j, i + j + 3, i + j, Tileset.WALL);
            drawPositiveHorizontalLine(world, BottomPosition, i, Tileset.WALL);
            drawNegativeHorizontalLine(world, BottomPosition, i * 2 - 3, Tileset.WALL);
        } else if (number == 2 ) {
            drawLStartFromVerticalLine(world, BottomPosition, i + j + 2, i + j + 2, Tileset.WALL);
            drawUpperHalfSquare(world, BottomPosition, i + j + 2, i + j + 2, i + j, Tileset.WALL);
            drawLStartFromHorizontalLine(world, BottomPosition, i, i + j + 4, Tileset.WALL);
            drawNegativeHorizontalLine(world, BottomPosition, i * 3 + 1, Tileset.WALL);
        } else if (number == 3) {
            drawNegativeHorizontalLine(world, BottomPosition, i * 2 - (i - 2) + i + 1, Tileset.WALL);
        }

    }



    private void drawHallWayOfTheGameFromTheRight(TETile[][] world, Position HallWayPosition, int j, int i, int number, int h) {

            if (j == 0 && number >= 2) {
                drawBottomRectangleTowardLeft(world, HallWayPosition, i, i - 2, Tileset.FLOOR);
                drawNegativeLStartFromVerticalLine(world, HallWayPosition, i, i + 1, Tileset.FLOOR);
            } else if (j == 0 && number < 2)  {
                drawBottomRectangleTowardLeft(world, HallWayPosition, (i * 2) - 2, i - 2, Tileset.FLOOR);
                drawNegativeHorizontalLine(world, HallWayPosition, i + 2, Tileset.FLOOR);
            } else if (number == 0) {
                drawNegativeHorizontalLine(world, HallWayPosition, 2, Tileset.FLOOR);
                drawUpperRectangleTowardLeft(world, HallWayPosition, i + j, (i + j) - 2, Tileset.FLOOR);
                drawBottomRectangleTowardRight(world, HallWayPosition, i + j , i + j - 2, Tileset.FLOOR);
                drawPositiveOppositeOfLStartFromHorizontalLine(world, HallWayPosition, i + j - 2, i + j , Tileset.FLOOR);
                drawNegativeHorizontalLine(world, HallWayPosition, i + j + 1 , Tileset.FLOOR);
                /* after . */
                /*drawNegativeHorizontalLine(world, HallWayPosition, j + 1, Tileset.FLOOR);*/
            } else if (number == 1) {
               /* before
               drawLStartFromHorizontalLine(world, HallWayPosition, j, 2, Tileset.FLOOR);
               */
                drawNegativeHorizontalLine(world, HallWayPosition, 2, Tileset.FLOOR);
                drawPositiveVerticalLine(world, HallWayPosition, 2, Tileset.FLOOR);
                drawUpperRectangleTowardLeft(world, HallWayPosition, i + j - 1, i + j - 2 ,Tileset.FLOOR);
                drawNegativeOppositeOfLStartFromHorizontalLine(world, HallWayPosition, i + j - 2, 3, Tileset.FLOOR);
                drawBottomRectangleTowardLeft(world, HallWayPosition, i + j - 1, i + j + 3 - 2, Tileset.FLOOR);
                drawNegativeLStartFromHorizontalLine(world, HallWayPosition, i + j + 3 - 2, 2, Tileset.FLOOR);
                drawNegativeHorizontalLine(world, HallWayPosition, (i * 2) + j - 1, Tileset.FLOOR);
            } else if (number == 2 ) {
                drawNegativeHorizontalLine(world, HallWayPosition, 2, Tileset.FLOOR);
                drawBottomRectangleTowardLeft(world, HallWayPosition, i + j + 4, i - 2, Tileset.FLOOR);
                drawLStartFromVerticalLine(world, HallWayPosition, i + j + 4, i, Tileset.FLOOR);
                drawBottomRectangleTowardRight(world, HallWayPosition, i + j, i + j, Tileset.FLOOR);
                drawLStartFromHorizontalLine(world, HallWayPosition, (i + j + 1) + (i - 2), i + j + 4, Tileset.FLOOR);
                drawNegativeHorizontalLine(world, HallWayPosition, i * 3 + 2, Tileset.FLOOR);
            } else if (number == 3) {
                drawNegativeHorizontalLine(world, HallWayPosition, 2 ,Tileset.FLOOR);
                drawUpperRectangleTowardLeft(world, HallWayPosition, i + j + 3, i - (i - 2), Tileset.FLOOR);
                drawPositiveVerticalLine(world, HallWayPosition, i + j + 4, Tileset.FLOOR);
                drawUpperRectangleTowardRight(world, HallWayPosition, i + j - 2, i * 2 - 1 - (i - 2), Tileset.FLOOR);
                if (i > 3) {
                    drawNegativeHorizontalLine(world, HallWayPosition, i * 2 - (i - 2), Tileset.FLOOR );
                    drawUpperRectangleTowardLeft(world, HallWayPosition, i + j - 2, i - 2 - 1, Tileset.FLOOR);
                    drawNegativeOppositeOfLStartFromHorizontalLine(world, HallWayPosition, i - 2, i + j + 4, Tileset.FLOOR);
                    drawNegativeHorizontalLine(world, HallWayPosition, i * 2 + 1, Tileset.FLOOR);
                } else {
                    drawNegativeHorizontalLine(world, HallWayPosition, i + 1, Tileset.FLOOR );
                    drawNegativeVerticalLine(world, HallWayPosition, i + j + 4, Tileset.FLOOR);
                    drawNegativeHorizontalLine(world, HallWayPosition, i * 2 + 1, Tileset.FLOOR);
                }


            }
        }


    private void drawUpperWallOfTheGameFromTheLeft(TETile[][] world, Position p, int j, int i, int number) {
        if (j == 0) {
            drawNegativeHorizontalLine(world, p, 3, Tileset.WALL);
            drawLStartFromVerticalLine(world, p, i + 8 + 8 + 1 + 4 , i * 16 + 2 , Tileset.WALL);
            drawNegativeLStartFromHorizontalLine(world, p, 3, i + 2, Tileset.WALL);

        } else if (number == 0) {
            drawLeftHalfSquare(world, p, i, i, i, Tileset.WALL);
            drawPositiveVerticalLine(world, p, i, Tileset.WALL);
        } else if (number == 1) {
            drawLeftHalfSquare(world, p, i + 2, i + 2, i + 2, Tileset.WALL);
            drawNegativeVerticalLine(world, p, i, Tileset.WALL);
            drawPositiveVerticalLine(world, p, i * 2, Tileset.WALL);
        } else if (number == 2) {
            drawPositiveVerticalLine(world, p, i * 2 + 2, Tileset.WALL);
        }

    }

    private  void drawBottomWallOfTheGameFromTheLeft(TETile[][] world, Position p, int j, int i, int number) {
        if (j == 0) {
            drawLStartFromVerticalLine(world, p, i + 8 + 4 + 1 + 4, i * 16 , Tileset.WALL);
            drawPositiveVerticalLine(world, p, i, Tileset.WALL);
        } else if (number == 0) {
            drawRightHalfSquare(world, p, i, i, i, Tileset.WALL);
            drawPositiveVerticalLine(world, p, i, Tileset.WALL);
        } else if (number == 1) {
            drawPositiveVerticalLine(world, p, i * 2 + 2, Tileset.WALL);
        } else if (number == 2) {
            drawRightHalfSquare(world, p, i + 2, i * 2, i + 2, Tileset.WALL);
            drawNegativeVerticalLine(world, p, i * 2 - 2, Tileset.WALL);
            drawPositiveVerticalLine(world, p, i * 2, Tileset.WALL);
        }




    }

    private void drawHallWayOfTheGameFromTheLeft(TETile[][] world, Position p, int j, int i, int number) {
        if (j == 0) {
            drawNegativeHorizontalLine(world, p, 2, Tileset.FLOOR);
            drawLStartFromVerticalLine(world, p, i + 8 + 8 + 1 + 2 , i * 16 + 1 , Tileset.FLOOR);
            drawNegativeLStartFromHorizontalLine(world, p, 2, i + 1, Tileset.FLOOR);
        } else if (number == 0) {
            drawPositiveVerticalLine(world, p, 2, Tileset.FLOOR);
            drawUpperRectangleTowardLeft(world, p, i - 2, i, Tileset.FLOOR);
            drawPositiveHorizontalLine(world, p ,i, Tileset.FLOOR);
            drawUpperRectangleTowardRight(world, p, i - 2, i, Tileset.FLOOR);
            drawLStartFromHorizontalLine(world, p, i, i - 1, Tileset.FLOOR);
            drawPositiveVerticalLine(world, p, i, Tileset.FLOOR);
        } else if (number == 1) {
            drawPositiveVerticalLine(world, p, 2, Tileset.FLOOR);
            drawPositiveHorizontalLine(world, p, 3, Tileset.FLOOR);
            drawUpperRectangleTowardRight(world, p, i, i, Tileset.FLOOR);
            drawNegativeHorizontalLine(world, p, i + 2, Tileset.FLOOR);
            drawPositiveVerticalLine(world, p, i * 2 + 1, Tileset.FLOOR);
        } else if (number == 2) {
            drawPositiveVerticalLine(world, p, 2, Tileset.FLOOR);
            drawNegativeHorizontalLine(world, p, 3, Tileset.FLOOR);
            drawUpperRectangleTowardLeft(world, p, i * 2 - 2, i, Tileset.FLOOR);
            drawPositiveHorizontalLine(world, p, i + 2, Tileset.FLOOR);
            drawPositiveVerticalLine(world, p, i * 2 + 1, Tileset.FLOOR);

        }



    }

    /* draw a shape which is similar to half square, which looks to the left. Start drawing the shape from bottom */
    private void drawLeftHalfSquare(TETile[][] world, Position P, int sizeOfBottomHorizontalLine,
                                           int sizeOfVerticalLine, int sizeOfUpperHorizontalLine, TETile type) {
        drawPositiveHorizontalLine(world, P, sizeOfBottomHorizontalLine, type);
        drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
        drawNegativeHorizontalLine(world, P, sizeOfUpperHorizontalLine, type);
    }

    /* draw a shape which is similar to half square, which looks to the right. Start drawing the shape from bottom */
    private void drawRightHalfSquare(TETile[][] world, Position P, int sizeOfBottomHorizontalLine,
                                            int sizeOfVerticalLine, int sizeOfUpperHorizontalLine, TETile type) {
        drawNegativeHorizontalLine(world, P, sizeOfBottomHorizontalLine, type);
        drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
        drawPositiveHorizontalLine(world, P, sizeOfUpperHorizontalLine, type);
    }

    /* draw a shape which is similar to half square, which looks down. Start drawing the shape from right. */
    private void drawBottomHalfSquare(TETile[][] world, Position P, int sizeOfRightVerticalLine,
                                             int sizeOfHorizontalLine, int sizeOfLeftVerticalLine, TETile type) {
        drawPositiveVerticalLine(world, P, sizeOfRightVerticalLine, type);
        drawNegativeHorizontalLine(world, P, sizeOfHorizontalLine, type);
        drawNegativeVerticalLine(world, P, sizeOfLeftVerticalLine, type);
    }

    /* draw a shape which is similar to half square, which looks up. Start drawing the shape from right. */
    private void drawUpperHalfSquare(TETile[][] world, Position P, int sizeOfRightVerticalLine,
                                            int sizeOfHorizontalLine, int sizeOfLeftVerticalLine, TETile type) {
        drawNegativeVerticalLine(world, P, sizeOfRightVerticalLine, type);
        drawNegativeHorizontalLine(world, P, sizeOfHorizontalLine, type);
        drawPositiveVerticalLine(world, P, sizeOfLeftVerticalLine, type);
    }

    /* draw a rectangle, which looks down toward left. the shape ends at the highest part of the last size. */
    private void drawBottomRectangleTowardLeft(TETile[][] world, Position P, int sizeOfVerticalLine,
                                                      int sizeOfHorizontalLine, TETile type) {
        if (sizeOfHorizontalLine == 1) {
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
        }
        while (sizeOfHorizontalLine > 1) {
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
            drawNegativeHorizontalLine(world, P, 2, type);
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            sizeOfHorizontalLine -= 1;
        }
    }

    /* draw a rectangle, which looks down toward right. the shape ends at the highest part of the last size. */
    private void drawBottomRectangleTowardRight(TETile[][] world, Position P, int sizeOfVerticalLine,
                                                       int sizeOfHorizontalLine, TETile type) {
        if (sizeOfHorizontalLine == 1) {
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
        }
        while (sizeOfHorizontalLine > 1) {
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
            drawPositiveHorizontalLine(world, P, 2, type);
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            sizeOfHorizontalLine -= 1;
        }
    }

    /* draw a rectangle, which looks up toward left. The shape ends at the lowest part of the last size. */
    private void drawUpperRectangleTowardLeft(TETile[][] world, Position P, int sizeOfVerticalLine,
                                                     int sizeOfHorizontalLine, TETile type) {
        if (sizeOfHorizontalLine == 1) {
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
        }
        while (sizeOfHorizontalLine > 1) {
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            drawNegativeHorizontalLine(world, P, 2, type);
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
            sizeOfHorizontalLine -= 1;
        }
    }

    /* draw a rectangle, which looks up toward right. The shape ends at the lowest part of the last size. */
    private void drawUpperRectangleTowardRight(TETile[][] world, Position P, int sizeOfVerticalLine,
                                                      int sizeOfHorizontalLine, TETile type) {
        if (sizeOfHorizontalLine == 1) {
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
        }
        while (sizeOfHorizontalLine > 1) {
            drawPositiveVerticalLine(world, P, sizeOfVerticalLine, type);
            drawPositiveHorizontalLine(world, P, 2, type);
            drawNegativeVerticalLine(world, P, sizeOfVerticalLine, type);
            sizeOfHorizontalLine -= 1;
        }
    }

    /* draw L, start form horizontal line. */
    private void drawLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                                     int sizeOfVerticalLine, TETile type) {
        drawNegativeHorizontalLine(world, p, sizeOfHorizontalLine, type);
        drawPositiveVerticalLine(world, p, sizeOfVerticalLine, type);
    }

    private void drawNegativeLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                                             int sizeOfVerticalLine, TETile type) {
        drawPositiveHorizontalLine(world, p, sizeOfHorizontalLine, type);
        drawPositiveVerticalLine(world, p, sizeOfVerticalLine, type);
    }

    /* draw Positive opposite of L, which looks to the right. Start from horizontal line. */
    private void drawPositiveOppositeOfLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                                                       int sizeOfVerticalLine, TETile type) {
        drawNegativeHorizontalLine(world, p, sizeOfHorizontalLine, type);
        drawNegativeVerticalLine(world, p, sizeOfVerticalLine, type);
    }

    /* draw Negative opposite of L, which looks to the left. Start from horizontal line. */
    private void drawNegativeOppositeOfLStartFromHorizontalLine(TETile[][] world, Position p, int sizeOfHorizontalLine,
                                                                       int sizeOfVerticalLine, TETile type) {
        drawPositiveHorizontalLine(world, p, sizeOfHorizontalLine, type);
        drawNegativeVerticalLine(world, p, sizeOfVerticalLine, type);
    }

}






